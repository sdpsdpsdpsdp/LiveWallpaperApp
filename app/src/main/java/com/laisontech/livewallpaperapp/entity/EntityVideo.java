package com.laisontech.livewallpaperapp.entity;

/**
 * Created by SDP on 2018/3/13.
 */

public class EntityVideo {
    private String path;
    private int duration;
    private String thumbPath;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @Override
    public String toString() {
        return "EntityVideo{" +
                "path='" + path + '\'' +
                ", duration=" + duration +
                ", thumbPath='" + thumbPath + '\'' +
                '}';
    }
}
