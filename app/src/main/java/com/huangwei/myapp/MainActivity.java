package com.huangwei.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.huangwei.core.activities.ProxyActivity;
import com.huangwei.core.delegates.MyAppDelegate;
import com.huangwei.myapp.business.launcher.ILauncherListener;
import com.huangwei.myapp.business.launcher.LauncherDelegate;
import com.huangwei.myapp.business.launcher.OnLauncherFinishTag;
import com.huangwei.myapp.business.sign.ISignListener;
import com.huangwei.myapp.business.sign.SignInDelegate;

public class MainActivity extends ProxyActivity implements ISignListener,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public MyAppDelegate setRootDelegate() {
        return new LauncherDelegate();
    }


    @Override
    public void onSignInSuccess() {
        // 启动目标Fragment，并关闭当前Fragment
        startWithPop(new ExampleDelegate());

        Toast.makeText(this,"登录成功！",Toast.LENGTH_LONG).show();

//        // 启动新的Fragment，启动者和被启动者是在同一个栈的
//        start(new ExampleDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功！",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                Toast.makeText(this,"启动结束，用户登录了",Toast.LENGTH_LONG).show();
                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"启动结束，用户未登录",Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
