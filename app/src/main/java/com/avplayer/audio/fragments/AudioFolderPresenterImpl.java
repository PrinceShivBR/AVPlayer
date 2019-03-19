package com.avplayer.audio.fragments;

import com.avplayer.video.models.Folder;

import java.util.List;

/**
 * Created by shivappar.b on 18-03-2019
 */
public class AudioFolderPresenterImpl implements IAudioFoldersContract.FoldersPresenter {

    private final IAudioFoldersContract.FoldersView mView;
    private IAudioFoldersInteractor interactor;

    AudioFolderPresenterImpl(IAudioFoldersContract.FoldersView mView) {
        this.mView = mView;
        this.interactor = new AudioFoldersInteractorImpl();
    }

    @Override
    public void getFolderList() {
        List<Folder> folderList = interactor.getAudioFolderList();
        if (folderList != null)
            mView.updateFoldersList(folderList);
    }
}
