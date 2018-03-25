package com.huangwei.core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HuangWei on 2018/3/23.
 */

public final class Configurator {

    private static final HashMap<String,Object> APP_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        //配置App开始，但未完成
        APP_CONFIGS.put(ConfigKeys.CONFIG_READY.name(),false);
    }

    final HashMap<String,Object> getAppConfigs(){
        return APP_CONFIGS;
    }

    private static class Holder{
       private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }


    public final void configure(){
        initIcons();
        APP_CONFIGS.put(ConfigKeys.CONFIG_READY.name(),true);
    }

    public final Configurator withApiHost(String host){
        APP_CONFIGS.put(ConfigKeys.API_HOST.name(),host);
        return this;
    }

    private void initIcons(){
        if (ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }



    private void checkConfigrations(){
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is no ready,call configure !");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfigration(Enum<ConfigKeys> key){
        checkConfigrations();
        return (T) APP_CONFIGS.get(key);
    }














}
