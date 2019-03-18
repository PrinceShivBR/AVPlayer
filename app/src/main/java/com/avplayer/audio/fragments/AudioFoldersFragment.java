package com.avplayer.audio.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avplayer.R;
import com.avplayer.audio.adapters.AudioFolderAdapter;
import com.avplayer.video.models.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFoldersFragment extends Fragment {
    public static final String TAG = "FolderListFragment";
    RecyclerView recyclerView;
    AudioFolderAdapter adapter;

    public AudioFoldersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        adapter = new AudioFolderAdapter(getAudioFolderList(), getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public List<Folder> getAudioFolderList() {
        List<Folder> folderList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        String[] projection = {MediaStore.Audio.AudioColumns.DATA};
        Cursor c = getContext().getContentResolver().query(uri, projection, null, null, sortOrder);
        if (c != null && c.getCount() > 0) {
            Folder folder = new Folder();
            System.out.println("count------" + c.getCount());
            while (c.moveToNext()) {
                String filePath = c.getString(0);
                System.out.println(filePath);
                File file = new File(filePath);

                String folderName = file.getParent();
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

                    if (aDirList.getName().toLowerCase(Locale.getDefault()).endsWith(".mp3")) {
                        fileCount++;
                    }
                }
//                folder.setFolderName(file.getParentFile().getName());
                folder.setFolderName(folderName);
                folder.setFolderPath(file.getParentFile().getAbsolutePath());
                folder.setVideoCount(String.valueOf(fileCount));
                if (fileCount != 0) {
                    folderList.add(folder);
                    System.out.println("folder name----" + file.getParentFile().getAbsolutePath() + "\tcount----" + fileCount);
                }
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

    public boolean isFolderExist(final List<Folder> list, final String folderPath) {
        boolean result = false;
        for (Folder folder : list) {
            if (folder.getFolderPath().equals(folderPath)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
