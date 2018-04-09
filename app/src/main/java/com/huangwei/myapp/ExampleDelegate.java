package com.huangwei.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huangwei.core.delegates.MyAppDelegate;
import com.huangwei.core.net.RestClient;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.ui.loader.LoaderStyle;
import com.huangwei.core.util.encode.MD5Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by HuangWei on 2018/3/26.
 */

public class ExampleDelegate extends MyAppDelegate {


    private static final HashMap<Object,Object> MAP = new HashMap<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

//        getFragmentManager().beginTransaction().remove(new LauncherDelegate());

        testRestClient();
        final String s = "123456";
        try {
            String k= MD5Util.getMD5String(s);
            Log.d("md50",k);
            Toast.makeText(getContext(),"未加密的MD5："+k,Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void testRestClient(){
        RestClient.builder()
                .url("http://10.0.0.101:8080/RestServer/api/user_profile.php")
                .loader(getContext(), LoaderStyle.PacmanIndicator)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Log.d("testRestClient",response);
                        Toast.makeText(getContext(), response,Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }

}
