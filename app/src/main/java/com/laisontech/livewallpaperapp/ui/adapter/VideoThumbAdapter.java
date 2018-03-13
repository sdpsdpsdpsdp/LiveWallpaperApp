package com.laisontech.livewallpaperapp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.laisontech.livewallpaperapp.R;
import com.laisontech.livewallpaperapp.base.CommonAdapter;
import com.laisontech.livewallpaperapp.base.CommonViewHolder;
import com.laisontech.livewallpaperapp.entity.EntityVideo;
import com.laisontech.livewallpaperapp.entity.VideoEvent;
import com.laisontech.livewallpaperapp.ui.activity.VideoPlayActivity;
import com.laisontech.livewallpaperapp.utils.Helper;
import com.laisontech.livewallpaperapp.utils.TimeFormatUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by SDP on 2018/3/13.
 */

public class VideoThumbAdapter extends CommonAdapter<EntityVideo> {
    public VideoThumbAdapter(Context mContext, List<EntityVideo> mData) {
        super(mContext, mData);
    }

    @Override
    public int layoutId() {
        return R.layout.gv_video_layout_item;
    }

    @Override
    public void convert(CommonViewHolder holder, EntityVideo entityVideo, int position) {
        ImageView ivThumb = holder.getView(R.id.iv_thumb);
        ImageView ivPlay = holder.getView(R.id.iv_play);
        TextView tvDuration = holder.getView(R.id.tv_duration);
        String thumbPath = entityVideo.getThumbPath();
        if (thumbPath == null || thumbPath.isEmpty()) {
            ivThumb.setImageResource(R.drawable.icon_nodata);
        } else {
            Glide.with(mContext)
                    .load(thumbPath)
                    .error(R.drawable.icon_nodata)
                    .into(ivThumb);
        }
        tvDuration.setText(TimeFormatUtils.getFormatTime(entityVideo.getDuration()));
        ivPlay.setOnClickListener(new OnPlayListener(position));
    }

    private class OnPlayListener implements View.OnClickListener {
        private int position;

         OnPlayListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            String path = mData.get(position).getPath();
            if (path == null || path.isEmpty()) {
                Toast.makeText(mContext, "路径不存在，请播放其他的视频", Toast.LENGTH_SHORT).show();
                return;
            }
            EventBus.getDefault().postSticky(new VideoEvent(path));
            Helper.OpenActivity(mContext,VideoPlayActivity.class);
        }
    }
}
