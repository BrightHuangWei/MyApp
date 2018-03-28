package com.huangwei.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.huangwei.core.app.MyApp;

/**
 * 屏幕适配工具类
 * Created by HuangWei on 2018/3/27.
 */

public class DimenUtil {

    /**
     *  获取设备屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        final Resources resources = MyApp.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     *  获取设备屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        final Resources resources = MyApp.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


}
