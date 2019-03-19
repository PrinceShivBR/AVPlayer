package com.avplayer.audio.fragments;

import com.avplayer.video.models.Folder;

import java.util.List;

/**
 * Created by shivappar.b on 18-03-2019
 */
public interface IAudioFoldersContract {
    interface FoldersView {

        void updateFoldersList(List<Folder> folderList);
    }

    interface FoldersPresenter {

        void getFolderList();
    }
}
