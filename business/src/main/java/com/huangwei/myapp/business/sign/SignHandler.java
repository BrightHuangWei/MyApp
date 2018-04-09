package com.huangwei.myapp.business.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huangwei.core.app.AccountManager;
import com.huangwei.core.util.log.MyAppLogger;
import com.huangwei.myapp.business.database.DatabaseManager;
import com.huangwei.myapp.business.database.UserProfile;

/**
 * Created by HuangWei on 2018/3/30
 */

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);

        try {
            DatabaseManager.getInstance().getUserProfileDao().insert(profile);
        }catch (Exception e){
            MyAppLogger.d(e);
        }

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }


    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);

        try {
            DatabaseManager.getInstance().getUserProfileDao().insert(profile);
        }catch (Exception e){
            MyAppLogger.d(e);
        }

        //已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
}
