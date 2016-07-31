package com.nearor.mylibrary.network;

import java.io.IOException;

/**
 * 自定义Call {@link retrofit2.Call}
 * Created by Nearor on 16/7/21.
 */
public interface APICall<T>  {
    void cancel();
    void enqueue( APICallBack<T> callback);
    T execute() throws IOException;
    APICall<T> clone();
}
