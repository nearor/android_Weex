package com.nearor.mylibrary.network;


import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link retrofit2.Call} to {@link APICall}
 * Created by Nearor on 16/7/21.
 */
public class APICallAdapter<T> implements APICall<T> {

    private Call<T> call;
    private Executor mCallBackExecutor = new MainThreadExecutor();

    public APICallAdapter(Call<T> call) {
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public void enqueue(final APICallBack<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError(new Throwable("Response.body is null"));
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call, final Throwable t) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            if (t instanceof IOException) {
                                callback.onError(t);
                            } else {
                                callback.onError(t);
                            }
                        }
                    }
                });

            }
        });


    }

    @Override
    public T execute() throws IOException {
        Response<T> response = call.execute();
        return response == null ? null : response.body();
    }

    @Override
    public APICall<T> clone() {
        return new APICallAdapter<>(call.clone());
    }
}
