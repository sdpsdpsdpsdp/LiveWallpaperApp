package com.laisontech.livewallpaperapp.utils;

/**
 * Created by SDP on 2018/3/13.
 */

public class TimeFormatUtils {
    public static String getFormatTime(int duration) {
        if (duration <= 0) return "";
        if (duration < 1000) {
            return duration + "毫秒";
        } else if (duration >= 1000 && duration < 60 * 1000) {
            return duration / 1000 + "秒" + getFormatTime(duration % 1000);
        } else {
            return duration / 1000 / 60 + "分" + getFormatTime(duration % (1000 * 60));
        }
    }
}
