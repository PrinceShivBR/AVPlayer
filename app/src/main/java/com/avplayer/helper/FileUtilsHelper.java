package com.avplayer.helper;

import java.io.File;
import java.util.List;

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

    public static void deleteFiles(List<File> fileList) {
        if (fileList.size() > 0) {
            for (File file : fileList) {
                file.delete();
            }
        }
    }
}
