package com.nearor.mytext;




import com.nearor.mylibrary.CommonApplication;
import com.nearor.mylibrary.route.ModuleHelper;

/**
 *
 * Created by Nearor on 16/7/6.
 */
public class AppApplication extends CommonApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ModuleHelper.appInitRoute(this);
    }



}
