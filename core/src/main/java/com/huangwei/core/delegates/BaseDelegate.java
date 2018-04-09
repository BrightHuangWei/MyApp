package com.huangwei.core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangwei.core.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by HuangWei on 2018/3/26.
 */


public abstract class BaseDelegate extends SwipeBackFragment {


    private final SupportFragmentDelegate DELEGATE = new SupportFragmentDelegate(this);

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstanceState , View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbinder = ButterKnife.bind(this,rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }


    @Override
    public void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }
}
