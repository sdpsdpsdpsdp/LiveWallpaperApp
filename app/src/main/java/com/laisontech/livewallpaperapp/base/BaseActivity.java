package com.laisontech.livewallpaperapp.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.laisontech.livewallpaperapp.R;
import com.laisontech.livewallpaperapp.view.CircleDialog;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by SDP on 2018/3/13.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected boolean isNeedOnKeyDown = false;
    private Toast mToast;
    private CircleDialog mDialogWaiting;

    //设置App字体大小不变
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //填充布局
        setContentView(layoutID());
        //初始化控件(可以使用ButterKnife、也可以不使用)
        ButterKnife.bind(this);
        //加载资源
        initData();
        //控件的操作事件
        initEvent();
    }

    protected abstract @LayoutRes
    int layoutID();

    protected abstract void initData();

    protected void initEvent() {

    }

    //弹出toast
    protected void showToast(Object obj) {
        String msg = "";
        if (obj instanceof String) {
            msg = (String) obj;
        } else if (obj instanceof Integer) {
            msg = getResources().getString((Integer) obj);
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    //不可取消的dialog
    protected void showWaitingDialog(String tip) {
        showWaitingDialog(tip, false);
    }

    /**
     * 显示等待提示框
     */
    protected Dialog showWaitingDialog(String tip, boolean needCancelable) {
        View view = View.inflate(this, R.layout.include_dialog_waiting, null);
        if (!TextUtils.isEmpty(tip)) {
            ((TextView) view.findViewById(R.id.tv_load_title)).setText(tip);
        }
        if (mDialogWaiting == null) {
            mDialogWaiting = new CircleDialog(this, view, R.style.CircleDialog);
        }
        if (!mDialogWaiting.isShowing()) {
            mDialogWaiting.show();
        }
        mDialogWaiting.setCanceledOnTouchOutside(false);
        mDialogWaiting.setCancelable(needCancelable);
        return mDialogWaiting;
    }

    /**
     * 隐藏等待提示框
     */
    protected void hideWaitingDialog() {
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }
    }

    protected String getResStr(int resId) {
        return getResources().getString(resId);
    }

    private long currentTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isNeedOnKeyDown) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - currentTime > 2000) {
                    showToast(R.string.exit);
                    currentTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //传入Bundle的方式打开activity
    protected void openActivity(Bundle bundle, Class<?> clz) {
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //传入Bundle的方式打开activity
    protected void openActivity(String keyName, String valueName, Class<?> clz) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(keyName, valueName);
        startActivity(intent);
    }

    //传入Bundle的方式打开activity
    protected void openActivity(String keyName, boolean valueName, Class<?> clz) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(keyName, valueName);
        startActivity(intent);
    }

    //传入Bundle的方式打开activity
    protected void openActivity(String parcelableName, Parcelable parcelable, Class<?> clz) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(parcelableName, parcelable);
        startActivity(intent);
    }

    //传入Bundle的方式打开activity
    protected void openActivity(String serializableName, Serializable serializable, Class<?> clz) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(serializableName, serializable);
        startActivity(intent);
    }

    //打开Activity
    protected void openActivity(Class<?> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    /**
     * 使用反射打开activity
     *
     * @param activityName 要打开的activity全路径 com.laisontech.xxx.xxxActivity
     * @param intent       传参使用的Intent
     */
    public void navigatorTo(final String activityName, final Intent intent) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(activityName);
            if (clazz != null) {
                intent.setClass(this, clazz);
                this.startActivity(intent);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
