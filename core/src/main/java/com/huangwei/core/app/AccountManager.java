package com.huangwei.core.app;


import com.huangwei.core.util.storage.MyAppPreference;

/**
 * Created by HuangWei on 2018/3/30.
 */


public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        MyAppPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return MyAppPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }


}
