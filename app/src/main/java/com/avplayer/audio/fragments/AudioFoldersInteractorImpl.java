package com.avplayer.audio.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.avplayer.AVPlayerApplication;
import com.avplayer.video.models.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivappar.b on 18-03-2019
 */
public class AudioFoldersInteractorImpl implements IAudioFoldersInteractor {

    AudioFoldersInteractorImpl(){

    }

    @Override
    public List<Folder> getAudioFolderList() {
        List<Folder> folderList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        String[] projection = {MediaStore.Audio.AudioColumns.DATA};
        Cursor c = AVPlayerApplication.getAppContext().getContentResolver().query(uri, projection, null, null, sortOrder);
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

    private boolean isFolderExist(final List<Folder> list, final String folderPath) {
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
