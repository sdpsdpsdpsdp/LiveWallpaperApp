package com.laisontech.livewallpaperapp.utils;

import android.content.Context;
import android.content.Intent;

import com.laisontech.livewallpaperapp.base.WallpaperApp;
import com.laisontech.livewallpaperapp.entity.EntityVideo;

import java.util.List;

/**
 * Created by SDP on 2018/3/13.
 */

public class Helper {
    public static boolean isSameList(List<EntityVideo> videos1, List<EntityVideo> videos2) {
        if (videos1 == null || videos2 == null || videos1.size() < 1 || videos2.size() < 1 || videos1.size() != videos2.size())
            return false;
        for (int i = 0; i < videos1.size(); i++) {
            String path1 = videos1.get(i).getPath();
            String path2 = videos2.get(i).getPath();
            if (!path1.equals(path2)) {
                return false;
            }
        }
        return true;
    }

    public static void OpenActivity(Context context,Class<?> clz) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        context.startActivity(intent);
    }
}
