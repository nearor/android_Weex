package com.nearor.mylibrary.route;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.nearor.mylibrary.util.Lg;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public class Route {
    private static final String TAG = Lg.makeLogTag(Route.class);
    public static final String INTENT_EXTAR_ACTIVITY = "route.key.activity";
    public static final String INTENT_EXTAR_FRAGMENT = "route.key.fragment";

    private Map<String,String> mActivityModule;
    private Map<String,String> mFragmentModule;

    private Application mApplication;
    private RouteCallback mCallback;

    private Route() {
        mActivityModule = new HashMap<>();
        mFragmentModule = new HashMap<>();
    }

    public static Route getSharedInstance(){
        return new Route();
    }

    public void init(Application application){
        if(application == null){
            throw new IllegalStateException("appLication must not be null");
        }
        mApplication  = application;
    }

    public RouteCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(RouteCallback Callback) {
        this.mCallback = Callback;
    }

    public void registerActivityModule(String host,String classPath){
        registerModule(host,classPath, Module.ModuleType.ACTIVITY);
    }

    public void registerFragmentModule(String host,String classPath){
        registerModule(host,classPath, Module.ModuleType.FRAGMENT);
    }



    private void registerModule(String host, String classPath, Module.ModuleType type) {
        if (TextUtils.isEmpty(host)){
            Lg.e(TAG,"Empty host");
            throw new IllegalArgumentException("Empty host");
        }

        host = host.toLowerCase();
        if(type.equals(Module.ModuleType.ACTIVITY)){
            if(mActivityModule.containsKey(host)){
                Log.e(TAG,"Already contains module" + host);
            }else {
                mActivityModule.put(host,classPath);
            }
        }else if(type.equals(Module.ModuleType.FRAGMENT)){
            if(mFragmentModule.containsKey(host)){
                Log.e(TAG,"Already contains module" + host);
            }else {
                mFragmentModule.put(host,classPath);
            }
        }
    }


    /**
     * 如果fragment和activity都存在时，优先Activity
     *
     */

    public void startModule(String moduleName, String from, Map<String,String> params, Activity activity){
        if(mActivityModule.containsKey(moduleName.toLowerCase())){
            startActivity(moduleName.toLowerCase(),from,params,activity);
        }else if(mFragmentModule.containsKey(moduleName.toLowerCase())){
            startFragment(moduleName.toLowerCase(), from, params, activity);
        }else {
            Log.e(TAG,"can`t find module:"+ moduleName);
        }

    }

    public void startActivity(String moduleName,String from,Map<String,String> params,Activity activity){
        Intent intent = createModuleIntent(moduleName, from, params);
        if (mCallback != null) {
            mCallback.beforeStartActivity(moduleName, intent);
        }
        activity.startActivity(intent);

    }

    public void startFragment(String moduleName,String from,Map<String,String> params,Activity activity){
        // TODO: 16/7/12
    }

    private Intent createModuleIntent(String url,String from,Map<String,String> params){
        String host = RouteUtils.getModuleURlHost(url);
        Map<String,String> urlParams = RouteUtils.parseModuleUrlParams(url);
        if(params == null){
            params = urlParams;
        }else {
            params.putAll(urlParams);
        }


        Intent moduleIntent;
        if (params !=null && params.size()>0) {
            if(!TextUtils.isEmpty(from)){
                params.put("from",from);
            }

            JSONObject paramsJsonObject = new JSONObject(params);

            try {
                String forwarUrl = String.format("%s?body=%s",host, URLEncoder.encode(paramsJsonObject.toString(),"utf-8"));
                moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(forwarUrl));
            } catch (UnsupportedEncodingException e) {
                moduleIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("%s?body=%s",host,paramsJsonObject.toString())));
                Lg.e(TAG,"URL encode error",e);
            }
        } else if(!TextUtils.isEmpty(from)){
            moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?body={\"from\":\"%s\"}",host,from)));
        } else {
            moduleIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        }


        configIntentforModule(moduleIntent);

        return moduleIntent;
    }

    private void configIntentforModule(Intent intent) {
        if(intent.getComponent()== null){
            Uri uri = intent.getData();
            if(uri != null && uri.getScheme() != null && RouteUtils.SCHEME.equalsIgnoreCase(uri.getScheme())){
                String host = uri.getHost();

                if(!TextUtils.isEmpty(host)){
                    host = host.toLowerCase(Locale.US);
                    if(!TextUtils.isEmpty(mActivityModule.get(host))){
                        intent.setClassName(mApplication, mActivityModule.get(host));
                        intent.putExtra(INTENT_EXTAR_ACTIVITY,true);
                    }
                    if(!TextUtils.isEmpty(mFragmentModule.get(host))){
                        intent.setClass(mApplication,ModuleFragmentActivity.class);
                        intent.putExtra(INTENT_EXTAR_FRAGMENT,mFragmentModule.get(host));
                    }
                }
            }

        }

    }


}
