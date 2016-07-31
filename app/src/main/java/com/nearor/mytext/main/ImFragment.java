package com.nearor.mytext.main;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nearor.mylibrary.constants.Constants;
import com.nearor.mylibrary.fragment.AppFragment;
import com.nearor.mytext.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXViewUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImFragment extends AppFragment {

    RelativeLayout viewGroup;
    private static final String DEFAULT_IP = "192.168.16.239";
    private static String CURRENT_IP= DEFAULT_IP; // your_current_IP
    private static final String WEEX_INDEX_URL = "http://"+CURRENT_IP+":12580/examples/build/index.js";


    public ImFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im,container,false);
        iniView(view);
        initInstance(getActivity());
        return view;
    }



    private void iniView(View container) {
        viewGroup = (RelativeLayout) container.findViewById(R.id.viewGroup);

    }

    private void initInstance(Activity activity) {
        WXSDKInstance mInstance = new WXSDKInstance(activity);
        mInstance.registerRenderListener(new IWXRenderListener() {
            @Override
            public void onViewCreated(WXSDKInstance instance, View view) {
                viewGroup.addView(view);
            }

            @Override
            public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

            }

            @Override
            public void onException(WXSDKInstance instance, String errCode, String msg) {

            }
        });

        renderPage(mInstance,getActivity().getPackageName(), WXFileUtils.loadFileContent("hello.js",getActivity()),WEEX_INDEX_URL,null);

    }
    protected void renderPage(WXSDKInstance mInstance,String packageName,String template,String source,String jsonInitData){
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, source);
        mInstance.render(
                packageName,
                template,
                options,
                jsonInitData,
                WXViewUtils.getScreenWidth(getActivity()),
                WXViewUtils.getScreenHeight(getActivity()),
                WXRenderStrategy.APPEND_ASYNC);
    }

}
