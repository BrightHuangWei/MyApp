package com.huangwei.core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.huangwei.core.R;
import com.huangwei.core.delegates.MyAppDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by HuangWei on 2018/3/26.
 */

public abstract class ProxyActivity extends SupportActivity {


    public abstract MyAppDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }


    private void initContainer(@Nullable Bundle saveInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (saveInstanceState == null)
            loadRootFragment(R.id.delegate_container,setRootDelegate());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //退出App后进行垃圾回收
        System.gc();
        System.runFinalization();
    }
}
