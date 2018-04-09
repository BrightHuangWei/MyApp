package com.huangwei.core.net.callback;

import android.os.Handler;

import com.huangwei.core.app.ConfigKeys;
import com.huangwei.core.app.MyApp;
import com.huangwei.core.net.RestCreator;
import com.huangwei.core.ui.loader.LoaderStyle;
import com.huangwei.core.ui.loader.MyAppLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HuangWei on 2018/3/26.
 */

public class RequestCallbacks implements Callback<String> {

    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IError error,
                            IFailure failure,
                            IRequest request,
                            ISuccess success ,
                            LoaderStyle loaderStyle) {
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null)
            FAILURE.onFailure();
        if (REQUEST != null)
            REQUEST.onRequestEnd();
        onRequestFinish();

    }


    private void onRequestFinish() {
        final long delayed = MyApp.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreator.getParams().clear();
                    MyAppLoader.stopLoading();
                }
            }, delayed);
        }
    }
}
