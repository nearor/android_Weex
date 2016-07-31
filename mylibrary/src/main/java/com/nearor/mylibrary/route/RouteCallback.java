package com.nearor.mylibrary.route;

import android.content.Intent;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public interface RouteCallback {
    /**
     * before Activity.startActivity();
     */
    void beforeStartActivity(String moduleName, Intent intent);

}
