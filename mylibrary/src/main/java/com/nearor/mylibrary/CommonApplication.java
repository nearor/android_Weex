package com.nearor.mylibrary;


import com.nearor.mylibrary.activity.MyApplication;
import com.nearor.mylibrary.network.RetrofitHelp;
import com.nearor.mylibrary.util.UrlUtil;

/**
 * 1.retrofit的init
 * 2.IM的init
 * 3.Bugtags的start
 * Created by Nearor on 16/7/7.
 */
public abstract class CommonApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitHelp.getShareInstance().init(UrlUtil.BASE_URL);

    }
}
