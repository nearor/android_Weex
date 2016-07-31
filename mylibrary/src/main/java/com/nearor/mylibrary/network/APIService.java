package com.nearor.mylibrary.network;



/**
 *
 * Created by Nearor on 16/7/23.
 */
public abstract class APIService<T> {

    private T api;
    private Class<T> mApiInterface;

    protected APIService(Class<T> apiInterface) {
        mApiInterface = apiInterface;
    }

    protected T getApi(){
        if(api == null){
            api = RetrofitHelp.getShareInstance().create(mApiInterface);
        }
        return api;
    }

    public <R> void startCall(APICall<R> call, APICallBack<R> callback){
        call.enqueue(callback);//异步
    }


}
