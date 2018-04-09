package com.huangwei.core.util.timer;

import java.util.TimerTask;

/**
 * Created by HuangWei on 2018/4/8
 */

public class BaseTimerTask extends TimerTask {
    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
