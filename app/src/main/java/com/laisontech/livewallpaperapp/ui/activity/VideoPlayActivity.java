package com.laisontech.livewallpaperapp.ui.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.laisontech.livewallpaperapp.R;
import com.laisontech.livewallpaperapp.base.BaseActivity;
import com.laisontech.livewallpaperapp.entity.VideoEvent;
import com.laisontech.livewallpaperapp.wallpaper.LiveWallpaperManager;
import com.laisontech.wallpaperlibrary.VideoWallpaper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SDP on 2018/3/13.
 */

public class VideoPlayActivity extends BaseActivity {
    @BindView(R.id.video_view)
    VideoView videoView;
    private String mPath;
    @BindView(R.id.iv_cut)
    ImageView ivCut;
    private VideoWallpaper mVideoWallpaper;
    private File mFile;

    @Override
    protected int layoutID() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {
        mVideoWallpaper = new VideoWallpaper();
    }

    @Override
    protected void initEvent() {
        setTitle("视频播放");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetLoginEvent(VideoEvent videoEvent) {
        mPath = videoEvent.getPath();
        if (mPath != null && !mPath.isEmpty()) {
            mFile = new File(mPath);
            if (!mFile.exists()) {
                showToast("视频资源错误");
            } else {
                setWallpaper();
                ///storage/emulated/0/video2.mp4
                this.finish();
            }
        }
    }

    public void setWallpaper() {
        mVideoWallpaper.setToWallPaper(this, mFile.getAbsolutePath());
    }


    @OnClick(R.id.iv_cut)
    public void onViewClicked() {
        ivCut.setVisibility(View.GONE);
        LiveWallpaperManager.getInstance().setWallpaper(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivCut.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
