package com.nearor.mylibrary;

import com.google.gson.Gson;
import com.nearor.mylibrary.util.Lg;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public class ValueObject {

    private static final String TAG = Lg.makeLogTag(ValueObject.class);

    public String toJsonString(){
        String jsonString = "";
        try {
            jsonString = new Gson().toJson(this);
        }catch (Exception e){
            Lg.e(TAG,e.getMessage());
        }

        return jsonString;
    }

}
