package com.nearor.mylibrary.route;

import com.nearor.mylibrary.util.Lg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nearor on 16/7/20.
 */
public class AppModuleMap implements IModuleMap {

    private static final String TAG = Lg.makeLogTag(AppModuleMap.class);

    public static final String MODULE_NAME_LOGIN = "login";
    public static final String MODULE_NAME_HOME = "home";
    public static final String MODULE_NAME_IM = "im_pm";
    public static final String MODULE_NAME_ME = "me";
    public static final String MODULE_NAME_NATIVE = "native";

    private List<Module> mModules;
    private Map<String, Module> mModuleMap = new HashMap<>();


    public AppModuleMap() {
        mModules = initModules();
    }

    private static class Hodler {
        private static AppModuleMap sInstance = new AppModuleMap();
    }

    public static AppModuleMap getSharedInstance() {
        return Hodler.sInstance;
    }

    @Override
    public List<Module> getModules() {
        return mModules;
    }

    private List<Module> initModules() {
        List<Module> modules = new ArrayList<>();
        for (AppModule appModule : AppModule.values()) {
            Module module = new Module(appModule.getModuleName(), appModule.getModulePath(), "");
            modules.add(module);
            mModuleMap.put(appModule.name(), module);
        }

        return modules;
    }

    public enum AppModule {
        LOGIN(MODULE_NAME_LOGIN, "com.nearor.app.common.login.LoginActivity",false),
        NATIVE(MODULE_NAME_NATIVE, "com.nearor.mytext.demo.NativeActivity",false),
        HOME(MODULE_NAME_HOME, "com.nearor.mytext.main.MainActivity",true);

        /**
         * 模块名
         */
        private String moduleName;
        /**
         * 模块 Activity 详细类名
         */
        private String modulePath;

        /**
         * true：Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
         */
        private final boolean isTopView;

        AppModule(String moduleName, String modulePath,boolean isTopView) {
            this.moduleName = moduleName;
            this.modulePath = modulePath;
            this.isTopView = isTopView;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getModulePath() {
            return modulePath;
        }

        public boolean isTopView() {
            return isTopView;
        }

        public static AppModule getModule(String moduleName) {
            AppModule module = null;
            for (AppModule m : AppModule.values()) {
                if (m.getModuleName().equalsIgnoreCase(moduleName)) {
                    module = m;
                    break;
                }
            }
            return module;
        }
    }

}
