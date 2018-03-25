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

    private static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getAppConfigs();
    }

}
