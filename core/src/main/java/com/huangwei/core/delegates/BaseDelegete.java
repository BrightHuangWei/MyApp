package com.huangwei.core.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by HuangWei on 2018/3/26.
 */


public abstract class BaseDelegete extends SwipeBackFragment {

    //abstract，因不希望被new出实例

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;
    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstanceState , View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null){
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }


    @Override
    public void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
