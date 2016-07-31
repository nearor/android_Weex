package com.nearor.mylibrary.activity;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.nearor.mylibrary.route.Route;
import com.nearor.mylibrary.route.RouteUtils;

import java.util.Map;

/**
 *
 * Created by Nearor on 16/7/6.
 */
public class AppActiviy extends AppCompatActivity {


    public Map<String, String> getModuleParams() {
        Uri uri = getIntent().getData();
        return RouteUtils.parseModuleUrlParams(uri.toString());
    }

    public void startModule(String module) {
        startModule(module, "");
    }

    public void startModule(String module, String jsonFormatParams) {
        Route.getSharedInstance().startModule(module, null, RouteUtils.jsonParamToMap(jsonFormatParams), this);
    }

    public void startModule(String module, Map<String, String> params) {
        Route.getSharedInstance().startModule(module, null, params, this);
    }
}
