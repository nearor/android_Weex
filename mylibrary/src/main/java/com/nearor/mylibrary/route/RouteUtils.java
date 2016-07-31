package com.nearor.mylibrary.route;

import android.net.Uri;
import android.text.TextUtils;

import com.nearor.mylibrary.util.Lg;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Created by Nearor on 16/7/12.
 */
public class RouteUtils {

    private static final String TAG = Lg.makeLogTag(RouteUtils.class);

    public static final String SCHEME = "hj";

    public static String getModuleURlHost(String urlOrName){
        urlOrName = urlOrName.toLowerCase();
        Uri uri = Uri.parse(urlOrName);
        String host = uri.getHost();
        host = host==null ? urlOrName : host;
        return SCHEME + "://" + host;
    }

    public static Map<String,String> parseModuleUrlParams(String url){
        Map<String,String> params = new HashMap<>();
        if(url.indexOf("body")>0){
            try {
                String urlParam = url.substring(url.indexOf("=") + 1);
                params = jsonParamToMap(urlParam);
            } catch (Exception e) {
                Lg.e(TAG, "Parse module params error", e);
            }
        }
        return params;
    }

    public static Map<String,String> jsonParamToMap(String jsonString){
        HashMap<String,String> params = new HashMap<>();
        if(!TextUtils.isEmpty(jsonString)){
            try {
                String decodeString =  URLDecoder.decode(jsonString,"utf-8");
                if (!TextUtils.isEmpty(decodeString)) {
                    JSONObject jsonObject = new JSONObject(decodeString);
                    Iterator objKey =  jsonObject.keys();
                    while (objKey.hasNext()){
                        String jKey = objKey.next().toString();
                        params.put(jKey,jsonObject.getString(jKey));
                    }
                }
            } catch (Exception e) {
                Lg.e(TAG, "Decode module params error", e);
            }
        }
        return params;
    }







}
