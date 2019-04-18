package com.zxx.lib_common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

/**
 * @author : zdy
 * @date : 2019/3/14 0014.
 * @company: 华梦
 * @description :
 */

public class ComUtil {

    public static void changeStatusBarTextColor(Context context, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //恢复状态栏白色字体
                ((Activity)context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            }
        }
    }
}
