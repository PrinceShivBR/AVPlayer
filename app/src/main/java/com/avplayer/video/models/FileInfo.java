package com.avplayer.video.models;

/**
 * Created by shivappar.b on 11-03-2019
 */
public class FileInfo {
    private String fileName;
    private String filePath;
    private boolean isSelected;
    private int position;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isSelected=" + isSelected +
                ", position=" + position +
                '}';
    }
}
