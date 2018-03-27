package com.huangwei.core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by HuangWei on 2018/3/23.
 */

public class MyApp {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigKeys.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getAppConfigs();
    }

    public static Context getApplicationContext(){
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT.name());
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

}
