package com.huangwei.myapp;

import android.app.Application;

import com.huangwei.business.icon.FontBusinessModule;
import com.huangwei.core.app.MyApp;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by HuangWei on 2018/3/25.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontBusinessModule()) //引入自定义字体
                .configure();
    }
}
