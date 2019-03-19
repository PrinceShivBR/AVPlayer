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
public class AudioFoldersFragment extends Fragment implements IAudioFoldersContract.FoldersView {
    public static final String TAG = "AudioFoldersFragment";
    RecyclerView recyclerView;
    AudioFolderAdapter adapter;
    private IAudioFoldersContract.FoldersPresenter presenter;

    public AudioFoldersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        presenter = new AudioFolderPresenterImpl(this);
        presenter.getFolderList();
        return view;
    }

    @Override
    public void updateFoldersList(List<Folder> folderList) {
        adapter = new AudioFolderAdapter(folderList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
