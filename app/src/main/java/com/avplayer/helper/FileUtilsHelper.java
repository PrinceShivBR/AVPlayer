package com.avplayer.helper;

import com.avplayer.video.models.FileInfo;

import java.io.File;

/**
 * Created by shivappar.b on 15-03-2019
 */
public class FileUtilsHelper {
    public FileUtilsHelper() {

    }

    public static boolean deleteFolder(String folderName) {
        File file = new File(folderName);
        if (file != null) {
            return file.delete();
        }
        return false;
    }

    public static void deleteFiles(FileInfo fileToDelete) {
        File file = new File(fileToDelete.getFilePath());
        if (file.exists()) {
            file.delete();
        }
    }
}
