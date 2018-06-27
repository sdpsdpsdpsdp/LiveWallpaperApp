package com.laisontech.livewallpaperapp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.laisontech.livewallpaperapp.R;
import com.laisontech.livewallpaperapp.base.CheckPermissionsActivity;
import com.laisontech.livewallpaperapp.entity.EntityVideo;
import com.laisontech.livewallpaperapp.ui.adapter.VideoThumbAdapter;
import com.laisontech.livewallpaperapp.utils.Helper;
import com.laisontech.livewallpaperapp.wallpaper.LiveWallpaperManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends CheckPermissionsActivity {
    @BindView(R.id.gridView)
    GridView gridView;
    private LiveWallpaperManager mInstance;
    private List<EntityVideo> mVideoFromSDCard;

    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mInstance = LiveWallpaperManager.getInstance();
        needPermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        isNeedOnKeyDown = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoFromSDCard != null && Helper.isSameList(mVideoFromSDCard, mInstance.getVideoFromSDCard())) {
            return;
        }
        mVideoFromSDCard = mInstance.getVideoFromSDCard();
        if (mVideoFromSDCard != null) {
            setTitle(getResStr(R.string.AllVedio)+"("+mVideoFromSDCard.size()+")");
            Log.e("MainActivity", "onViewClicked: " + mVideoFromSDCard.toString());
            VideoThumbAdapter videoThumbAdapter = new VideoThumbAdapter(this, mVideoFromSDCard);
            gridView.setAdapter(videoThumbAdapter);
        }
    }

}
