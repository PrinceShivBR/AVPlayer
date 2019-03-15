package com.avplayer.video.models;

/**
 * Created by shivappar.b on 11-03-2019
 */
public class FileInfo {
    private String fileName;
    private String filePath;
    private boolean isSelected;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
