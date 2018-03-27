package com.huangwei.myapp;

import com.huangwei.core.activities.ProxyActivity;
import com.huangwei.core.delegates.MyAppDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public MyAppDelegate setRootDelegate() {
        return new ExampleDelegete();
    }


}
