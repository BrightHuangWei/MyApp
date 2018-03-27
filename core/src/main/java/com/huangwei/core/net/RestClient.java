package com.huangwei.core.net;

import android.content.Context;

import com.huangwei.core.net.callback.IError;
import com.huangwei.core.net.callback.IFailure;
import com.huangwei.core.net.callback.IRequest;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.net.callback.RequestCallbacks;
import com.huangwei.core.ui.LoaderStyle;
import com.huangwei.core.ui.MyAppLoader;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by HuangWei on 2018/3/26.
 */

public class RestClient {


    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;

    public RestClient(String url,
                      Map<String, Object> params,
                      IError error,
                      IFailure failure,
                      IRequest request,
                      ISuccess success,
                      RequestBody body,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){

        final RestService service = RestCreator.getRestService();

        Call<String> call = null;
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null){
            MyAppLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            default:
        }

        if (call != null){
            call.enqueue(getRequestCallbacks());
        }

    }


    private Callback<String> getRequestCallbacks(){
        return new RequestCallbacks(
                ERROR,
                FAILURE,
                REQUEST,
                SUCCESS,
                LOADER_STYLE
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }


}
