package com.nearor.mylibrary.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 *
 * Created by Nearor on 16/7/21.
 */
public class MainThreadExecutor implements Executor {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
