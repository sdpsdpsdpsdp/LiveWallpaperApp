package com.laisontech.livewallpaperapp.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;

import com.laisontech.livewallpaperapp.base.WallpaperApp;
import com.laisontech.livewallpaperapp.entity.EntityVideo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDP on 2018/3/13.
 * 对壁纸的管理帮助类
 */

public class LiveWallpaperManager {
    private static final String TAG = LiveWallpaperManager.class.getSimpleName();
    private static LiveWallpaperManager mInstance;
    private Context mContext;
    private WallpaperManager mWallpaperManager;

    public static LiveWallpaperManager getInstance() {
        if (mInstance == null) {
            synchronized (LiveWallpaperManager.class) {
                if (mInstance == null) {
                    mInstance = new LiveWallpaperManager();
                }
            }
        }
        return mInstance;
    }

    private LiveWallpaperManager() {
        mContext = WallpaperApp.getInstance().getApplicationContext();
        mWallpaperManager = WallpaperManager.getInstance(mContext);
    }

    //设置壁纸
    public void setWallpaper(Activity activity) {
        mWallpaperManager.suggestDesiredDimensions(getPhoneWH()[0], getPhoneWH()[1]);
        Bitmap bitmap = captureScreen(activity);
        try {
            mWallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取手机的宽和高
    public int[] getPhoneWH() {
        int w = 0;
        int h = 0;
        try {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            w = displayMetrics.widthPixels;
            h = displayMetrics.heightPixels;
        } catch (Exception e) {
            Log.e(TAG, "getPhoneWH: " + e.getMessage());
        }
        return new int[]{w, h};
    }

    //截屏操作
    private Bitmap captureScreen(Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        return activity.getWindow().getDecorView().getDrawingCache();
    }

    //获取手机中所有的视频地址 uri
    public List<EntityVideo> getVideoFromSDCard() {
        String[] mediaColumns = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};
        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        if (cursor == null) return null;
        List<EntityVideo> videos = new ArrayList<>();
        if (cursor.moveToLast()) {
            do {
                EntityVideo video = new EntityVideo();
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                video.setPath(path);//设置video的路径
                video.setDuration(duration);//设置时长
                String[] thumbnailsColumns = new String[]{MediaStore.Video.Thumbnails.DATA,//视频缩略图的文件路径
                        MediaStore.Video.Thumbnails.VIDEO_ID};
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));//获取media的id
                Cursor thumbnailsCursor = mContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                        , thumbnailsColumns
                        , MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id
                        , null
                        , null);
                if (thumbnailsCursor != null) {
                    if (thumbnailsCursor.moveToFirst()) {
                        String thumbPath = thumbnailsCursor.getString(
                                thumbnailsCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                        video.setThumbPath(thumbPath);//设置缩略图的路径
                    }
                    thumbnailsCursor.close();
                }
                videos.add(video);
            } while (cursor.moveToPrevious());
            cursor.close();
        }
        return videos;
    }

    //播放视频
    public void playVideo() {

    }
}

