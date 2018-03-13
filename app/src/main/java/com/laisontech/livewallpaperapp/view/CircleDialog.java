package com.laisontech.livewallpaperapp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by SDP on 2018/3/13.
 * 自定义的loading dialog
 */

public class CircleDialog extends Dialog {

    /**
     * 宽高由布局文件中指定（但是最底层的宽度无效，可以多嵌套一层解决）
     */
    public CircleDialog(Context context, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

    }

    /**
     * 宽高由该方法的参数设置
     */
    public CircleDialog(Context context, int width, int height, View layout,
                              int style) {
        super(context, style);
        // 设置内容
        setContentView(layout);
        // 设置窗口属性
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置宽度、高度、密度、对齐方式
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

    }

    /**
     * 获取显示密度
     *
     * @param context
     * @return
     */
    public float getDensity(Context context) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.density;
    }
}
