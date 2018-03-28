package com.huangwei.core.net;

import android.content.Context;

import com.huangwei.core.net.callback.IError;
import com.huangwei.core.net.callback.IFailure;
import com.huangwei.core.net.callback.IRequest;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.net.callback.RequestCallbacks;
import com.huangwei.core.net.download.DownloadHandler;
import com.huangwei.core.ui.LoaderStyle;
import com.huangwei.core.ui.MyAppLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final RequestBody BODY;
    private final File FILE;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;

    public RestClient(String url,
                      Map<String, Object> params,
                      IError error,
                      IFailure failure,
                      IRequest request,
                      String downloadDir,
                      String extension,
                      String name,
                      ISuccess success,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.BODY = body;
        this.FILE = file;
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
            case PUT_RAM:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAM:
                call = service.postRaw(URL,BODY);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = service.upload(URL,body);

                //call = service.upload(URL,)
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
        if (BODY == null){
            request(HttpMethod.POST);
        }
        else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("post params must be null");
            }
            request(HttpMethod.POST_RAM);
        }
    }

    public final void put(){
        if (BODY == null){
            request(HttpMethod.PUT);
        }
        else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("put params must be null");
            }
            request(HttpMethod.PUT_RAM);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR)
                .handleDownload();
    }

}
