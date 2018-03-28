package com.huangwei.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.huangwei.core.app.MyApp;
import com.huangwei.core.net.callback.IRequest;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

final class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
//    private final IFailure FAILURE;
//    private final IError ERROR;


    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... objects) {

        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[3];
        final InputStream inputStream = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")){
            extension = "";  //后续处理
        }
        if (name == null || name.equals("")){
            return FileUtil.writeToDisk(inputStream,downloadDir,extension.toUpperCase(),extension);
        }
        else
            return FileUtil.writeToDisk(inputStream,downloadDir,name);
    }


    //执行完异步，回到主线程
    @Override
    protected void onPostExecute(File file) {
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

    }

    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            MyApp.getApplicationContext().startActivity(install);
        }
    }


}
