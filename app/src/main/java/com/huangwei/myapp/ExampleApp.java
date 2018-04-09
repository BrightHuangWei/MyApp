package com.huangwei.myapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.huangwei.business.icon.FontBusinessModule;
import com.huangwei.core.app.MyApp;
import com.huangwei.core.net.interceptor.DebugInterceptor;
import com.huangwei.myapp.business.database.DatabaseManager;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by HuangWei on 2018/3/25.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontBusinessModule()) //引入自定义字体
                .withLoaderDelayed(3000)
//                .withApiHost("http://127.0.0.1")
                .withApiHost("http://10.0.0.101:8080")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
        DatabaseManager.getInstance().init(this);

//        initStetho();

    }



    //查看数据库 调试时打开Chrome ，输入  Chrome://inspect
    private void initStetho() {
    Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
    }

}
