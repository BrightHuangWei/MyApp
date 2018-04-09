package com.huangwei.core.net;

import android.content.Context;

import com.huangwei.core.app.ConfigKeys;
import com.huangwei.core.app.MyApp;
import com.huangwei.core.net.callback.IError;
import com.huangwei.core.net.callback.IFailure;
import com.huangwei.core.net.callback.IRequest;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by HuangWei on 2018/3/26.
 */

//建造者模式

public class RestClientBuilder {

    private String mUrl = null;
    private static final Map<String,Object> PARAMS = RestCreator.getParams() ;
    private IError mIError = null;
    private IFailure mIFailure = null;
    private IRequest mIRequest = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    private ISuccess mISuccess = null;
    private RequestBody mBody = null;
    private File mFile = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;

    RestClientBuilder(){

    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.mExtension = extension;
        return this;
    }


    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder apiUrl(String url){
        this.mUrl = MyApp.getConfigurations().get(ConfigKeys.API_HOST) + url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key , Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file){
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name;
        return this;
    }


    /**
     * 原始数据
     * @param raw
     * @return
     */
    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),raw);
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public RestClient build(){
        return new RestClient(
                mUrl,
                PARAMS,
                mIError,
                mIFailure,
                mIRequest,
                mDownloadDir,
                mExtension,
                mName,
                mISuccess,
                mBody,
                mFile,
                mContext,
                mLoaderStyle);
    }










}
