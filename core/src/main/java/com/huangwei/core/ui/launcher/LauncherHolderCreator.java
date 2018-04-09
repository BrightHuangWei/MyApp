package com.huangwei.core.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by HuangWei on 2018/4/8
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {

    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
