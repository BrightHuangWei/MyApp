package com.huangwei.core.delegates;

/**
 * Created by HuangWei on 2018/3/26.
 */

public abstract class MyAppDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends MyAppDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

}
