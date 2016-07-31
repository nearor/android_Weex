package com.nearor.mylibrary.weex;

import android.widget.ImageView;

import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by Nearor on 16/7/29.
 */
public interface IWXImgLoaderAdapter {
    void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy);
}
