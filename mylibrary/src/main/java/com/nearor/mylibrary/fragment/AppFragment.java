package com.nearor.mylibrary.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.nearor.mylibrary.route.Route;
import com.nearor.mylibrary.route.RouteUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public class AppFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        //onManagerDestroy();
        super.onDestroy();
    }



    public void startModule(String module) {
        startModule(module, "");
    }

    public void startModule(String module, String jsonFormatParams) {
        Route.getSharedInstance().startModule(module, null, RouteUtils.jsonParamToMap(jsonFormatParams), getActivity());
    }

    public void startModule(String module, HashMap<String, String> params) {
        Route.getSharedInstance().startModule(module, null, params, getActivity());
    }

    public HashMap<String, String> getModuleParams() {
        HashMap<String, String> params = new HashMap<>();

        Serializable serializable = getArguments().getSerializable("data");
        if (serializable instanceof HashMap) {
            params = (HashMap<String, String>)serializable;
        }

        return params;
    }
}
