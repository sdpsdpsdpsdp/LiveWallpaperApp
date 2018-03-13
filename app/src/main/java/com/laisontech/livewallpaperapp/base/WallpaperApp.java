package com.laisontech.livewallpaperapp.base;

import android.app.Application;

/**
 * Created by SDP on 2018/3/13.
 */

public class WallpaperApp extends Application {
    private static WallpaperApp mInstance;

    public static WallpaperApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
        }
    }
}
