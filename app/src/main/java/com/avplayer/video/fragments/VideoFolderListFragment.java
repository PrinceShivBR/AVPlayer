package com.avplayer.video.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avplayer.R;
import com.avplayer.video.adapters.VideoFolderListAdapter;
import com.avplayer.video.models.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class VideoFolderListFragment extends Fragment {
    public static final String TAG = "VideoFolderListFragment";
    RecyclerView recyclerView;
    VideoFolderListAdapter adapter;

    public VideoFolderListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        adapter = new VideoFolderListAdapter(getVideoFolderList(), getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public List<Folder> getVideoFolderList() {
        List<Folder> folderList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor c = getContext().getContentResolver().query(uri, projection, null, null, MediaStore.Video.VideoColumns.DISPLAY_NAME);
        if (c != null && c.getCount() > 0) {
            Folder folder = new Folder();
            while (c.moveToNext()) {
                String filePath = c.getString(0);
                File file = new File(filePath);

                String folderName = file.getParentFile().getName();
                if (folderName.equals(folder.getFolderName()) || isFolderExist(folderList, folderName))
                    continue;
                File folderFile = new File(file.getParent());
                File list[] = folderFile.listFiles();
                if (list == null) continue;
                int fileCount = 0;
                for (File aDirList : list) {
                    if (folder.getFolderName() != null && !folder.getFolderName().equals(folderName)) {
                        folder = new Folder();
                    }

                    if (aDirList.getName().toLowerCase(Locale.getDefault()).endsWith(".mp4")) {
                        fileCount++;
                    }
                }
                folder.setFolderName(folderName);
                folder.setFolderPath(file.getParentFile().getAbsolutePath());
                folder.setVideoCount(String.valueOf(fileCount));
                folderList.add(folder);
            }
            c.close();
        }

        Collections.sort(folderList, new Comparator<Folder>() {
            @Override
            public int compare(Folder o1, Folder o2) {
                return o1.getFolderName().compareToIgnoreCase(o2.getFolderName());
            }
        });
        return folderList;
    }

    public boolean isFolderExist(final List<Folder> list, final String folderName) {
        boolean result = false;
        for (Folder folder : list) {
            if (folder.getFolderName().equals(folderName)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
