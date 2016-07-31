package com.nearor.mylibrary.network;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by Nearor on 16/7/20.
 */
public class RetrofitHelp {

    private Retrofit retrofit;
    private Map<Class,Object> mCachedService = new HashMap<>();

    private static RetrofitHelp sSharedInstance;

    public static RetrofitHelp getShareInstance(){
        if(sSharedInstance == null)
            synchronized (RetrofitHelp.class){
                if(sSharedInstance == null)
                    sSharedInstance = new RetrofitHelp();
            }
        return sSharedInstance;
    }

    public void init(String baseURL){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(new APICallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    @SuppressWarnings("unchecked")
    public  <T> T create(Class<T> apiInterfaceClass) {
        if(mCachedService.containsKey(apiInterfaceClass)
                && mCachedService.get(apiInterfaceClass) != null){
            return (T) mCachedService.get(apiInterfaceClass);
        }else {
            T apiImp = retrofit.create(apiInterfaceClass);
            mCachedService.put(apiInterfaceClass,apiImp);
            return apiImp;
        }
    }


}
