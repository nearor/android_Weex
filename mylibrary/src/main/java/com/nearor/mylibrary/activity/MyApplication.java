package com.nearor.mylibrary.activity;


import android.app.Application;
import android.util.Log;

import com.nearor.mylibrary.bitmap.FrescoImageAdapter;
import com.nearor.mylibrary.util.ClientInfo;
import com.nearor.mylibrary.util.Lg;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;


/**
 * 初始化页面路由， ClientInfo, 启动weex渲染
 * Created by Nearor on 16/7/6.
 */
public abstract class MyApplication extends Application {

    private static final String TAG = Lg.makeLogTag(MyApplication.class);

    private static MyApplication sharedInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        ClientInfo.getInstance().initWithContext(this);
        sharedInstance = this;

        WXEnvironment.addCustomOptions("appName","TBSample");

        InitConfig initConfig = new InitConfig.Builder().setImgAdapter(new FrescoImageAdapter()).build();

        WXSDKEngine.initialize(this,initConfig);


    }

    public static MyApplication getSharedInstance(){
        if(sharedInstance == null){
            throw new IllegalStateException("Application has not been created");
        }else {
            return sharedInstance;
        }
    }

    public int logLevel(){
        return Log.VERBOSE;
    }

   // public abstract void logout(Activity activity);
}
