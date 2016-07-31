package com.nearor.mylibrary.network;

import android.support.annotation.NonNull;

/**
 * Created by Nearor on 16/7/21.
 */
public interface APICallBack<T> {
    void onSuccess(@NonNull T object);

    void onError(Throwable throwable);
}
