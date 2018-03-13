package com.laisontech.livewallpaperapp.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.laisontech.livewallpaperapp.R;
import com.laisontech.livewallpaperapp.base.BaseActivity;
import com.laisontech.livewallpaperapp.entity.VideoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDP on 2018/3/13.
 */

public class VideoPlayActivity extends BaseActivity {
    @BindView(R.id.video_view)
    VideoView videoView;
    private String mPath;

    @Override
    protected int layoutID() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        setTitle("视频播放");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        videoView.setMediaController(new MediaController(this));//显示控制栏
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (mPath != null && !mPath.isEmpty()) {
                    if (!videoView.isPlaying()) {
                        videoView.start();
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetLoginEvent(VideoEvent videoEvent) {
        mPath = videoEvent.getPath();
        if (mPath != null && !mPath.isEmpty()) {
            videoView.setVideoPath(mPath);
        }
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
