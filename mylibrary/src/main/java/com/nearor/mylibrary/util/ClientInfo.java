package com.nearor.mylibrary.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;


/**
 * 客户端 APP 相关信息。会以 JSON 形式放在接口请求时的 HTTP Header 里面
 */
public class ClientInfo {

    public static final String TRADER = "androidphone";
    // 设计师端 TRADER_NAME = "android_common_idesigner"
    public static final String TRADER_NAME = "android_common_ihome";

    private transient String clientInfoString = null;

    private String trader;
    private String traderName;

    /**
     * 客户端程序版本号:x.x.x
     */
    private String clientAppVersion;

    /**
     * 客户端系统详细信息，例如 xiaomi, 4.4, 4.8.r46723987
     */
    private String clientSystem;

    /**
     * Android version
     */
    private String clientVersion;

    private String phoneType;

    /**
     * 客户端唯一标识
     */
    private String deviceCode;

    // 网络标识Wifi,3g,2g,4g
    private String nettype;

    private static class Holder {
        static final ClientInfo INSTANCE = new ClientInfo();
    }

    public static ClientInfo getInstance() {
        return Holder.INSTANCE;
    }

    private ClientInfo() {
        this.clientSystem = getClientSystemDetail();
        this.clientVersion = android.os.Build.VERSION.RELEASE;
        this.phoneType = getClientSystemDetail();

        this.trader = ClientInfo.TRADER;
        this.traderName = ClientInfo.TRADER_NAME;
    }

    public void initWithContext(Context ctx) {
        this.clientAppVersion = getClientAppVersion(ctx);
        this.deviceCode = DeviceId.getUUid(ctx);
        this.nettype = NetWorkUtil.getNetTypeName(ctx);

        updateJSONString();
    }

    public String getClientAppVersion() {
        return clientAppVersion;
    }
    private String getClientAppVersion(Context ctx) {
        String vName = "";
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            if (!TextUtils.isEmpty(pi.versionName)) {
                vName = pi.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vName;
    }

    public String getClientSystem() {
        return clientSystem;
    }
    private String getClientSystemDetail() {
        return "android" + "," + android.os.Build.MODEL + "," + android.os.Build.VERSION.SDK_INT + "," + android.os.Build.VERSION.RELEASE;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getTrader() {
        return trader;
    }
    public String getTraderName() {
        return traderName;
    }

    public String getNettype() {
        return nettype;
    }

    public void setNettype(String nettype) {
        this.nettype = nettype;
        updateJSONString();
    }

    private void updateJSONString() {
        this.clientInfoString = new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return clientInfoString;
    }

    public String getPhoneType() {
        return phoneType;
    }

}
