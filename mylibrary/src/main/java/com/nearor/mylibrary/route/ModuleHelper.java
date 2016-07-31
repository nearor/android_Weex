package com.nearor.mylibrary.route;

import android.app.Application;
import android.content.Intent;

import java.util.List;

/**
 *
 * Created by Nearor on 16/7/7.
 */
public class ModuleHelper {
    public static void appInitRoute(Application application){
        Route.getSharedInstance().init(application);
        List<Module> modules = AppModuleMap.getSharedInstance().getModules();
        for(Module module : modules){
            Route.getSharedInstance().registerActivityModule(module.getName(),module.getPath());
        }

        Route.getSharedInstance().setmCallback(new RouteCallback() {
            @Override
            public void beforeStartActivity(String moduleName, Intent intent) {
                if (AppModuleMap.AppModule.getModule(moduleName).isTopView()) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                }
            }
        });

    }
}
