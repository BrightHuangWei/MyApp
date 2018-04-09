package com.huangwei.myapp.business.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.huangwei.core.delegates.MyAppDelegate;
import com.huangwei.core.net.RestClient;
import com.huangwei.core.net.callback.IError;
import com.huangwei.core.net.callback.IFailure;
import com.huangwei.core.net.callback.ISuccess;
import com.huangwei.core.util.encode.MD5Util;
import com.huangwei.core.util.log.MyAppLogger;
import com.huangwei.myapp.business.R;
import com.huangwei.myapp.business.R2;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends MyAppDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        try {
            String encryptedPsd = MD5Util.getMD5String(mPassword.getText().toString());
            if (checkForm()) {
                RestClient.builder()
//                        .url("http://10.0.0.101:8080/RestServer/api/user_profile.php")
                        .apiUrl("/RestServer/api/user_profile.php")
                        .params("email", mEmail.getText().toString())
                        .params("password", encryptedPsd)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                MyAppLogger.json("USER_PROFILE", response);
                                SignHandler.onSignIn(response, mISignListener);
                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError(int code, String msg) {
                                Toast.makeText(getContext(),"error! API closed!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFailure() {
                                Toast.makeText(getContext(),"failure! API closed!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .build()
                        .post();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
//
//    @OnClick(R2.id.icon_sign_in_wechat)
//    void onClickWeChat() {
//        LatteWeChat
//                .getInstance()
//                .onSignSuccess(new IWeChatSignInCallback() {
//                    @Override
//                    public void onSignInSuccess(String userInfo) {
//                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
//                    }
//                })
//                .signIn();
//    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }






    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
