package com.huangwei.core.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by HuangWei on 2018/4/4.
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
