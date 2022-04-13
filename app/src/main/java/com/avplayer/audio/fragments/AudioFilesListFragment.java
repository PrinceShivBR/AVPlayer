package com.avplayer.audio.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avplayer.R;
import com.avplayer.audio.adapters.AudioFileListAdapter;
import com.avplayer.video.models.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFilesListFragment extends Fragment {
    public static final String TAG = "AudioFilesListFragment";
    RecyclerView recyclerView;
    AudioFileListAdapter adapter;

    public AudioFilesListFragment() {

    }

    public static AudioFilesListFragment getInstance(String folderPath) {
        AudioFilesListFragment filesFragment = new AudioFilesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", folderPath);
        filesFragment.setArguments(bundle);
        return filesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String folderName = bundle.getString("path");
            adapter = new AudioFileListAdapter(getAudioList(folderName), getActivity());
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private List<FileInfo> getAudioList(String folderName) {
        List<FileInfo> myList = new ArrayList<>();
        File file = new File(folderName);
        File[] list = file.listFiles();
        for (File aList : list) {
            if (aList.getName().toLowerCase(Locale.getDefault()).endsWith(".mp3")) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(aList.getName());
                fileInfo.setFilePath(aList.getPath());
                myList.add(fileInfo);
            }
        }

        Collections.sort(myList, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                return o1.getFileName().compareToIgnoreCase(o2.getFileName());
            }
        });

        return myList;
    }

    public void deleteFiles() {
        HashMap<Integer, FileInfo> filesToDelete = adapter.getFileToDelete();
        adapter.deleteFiles(filesToDelete);
    }
}
