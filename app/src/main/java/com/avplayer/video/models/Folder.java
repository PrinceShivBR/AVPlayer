package com.avplayer.video.models;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class Folder {
    private String folderName;
    private String folderPath;
    private String videoCount;
    private int isVisible;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderName='" + folderName + '\'' +
                ", folderPath='" + folderPath + '\'' +
                ", videoCount='" + videoCount + '\'' +
                ", isVisible=" + isVisible +
                '}';
    }
}
