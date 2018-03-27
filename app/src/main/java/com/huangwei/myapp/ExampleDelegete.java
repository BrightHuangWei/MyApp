package com.huangwei.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.huangwei.core.delegates.MyAppDelegate;
import com.huangwei.core.net.RestClient;
import com.huangwei.core.net.callback.IError;
import com.huangwei.core.net.callback.IRequest;
import com.huangwei.core.net.callback.ISuccess;

/**
 * Created by HuangWei on 2018/3/26.
 */

public class ExampleDelegete extends MyAppDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        testRestClient();
    }

    private void testRestClient(){

        RestClient.builder()
                .url("http://news.baidu.com")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                    }
                })
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();

    }

}
