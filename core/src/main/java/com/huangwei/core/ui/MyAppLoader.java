package com.huangwei.core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.huangwei.core.R;
import com.huangwei.core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 *
 * Created by HuangWei on 2018/3/27.
 */

public class MyAppLoader {

    private static final int LOADER_SIZE_SCALE = 8; //8倍屏幕缩放比例
    private static final int LOADER_OFFSET_SCALE = 10; // 偏移量
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog_loading);
        final AVLoadingIndicatorView view = LoaderCreator.create(type, context);
        dialog.setContentView(view);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS) {
            if (LOADERS != null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }


}
