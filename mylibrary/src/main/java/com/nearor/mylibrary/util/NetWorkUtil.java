package com.nearor.mylibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;



/**
 * 网络状态相关判断的工具类
 * 
 * @author Nearor
 * 
 */
public class NetWorkUtil {

	/**
	 * 判断网络类型是否属于低速网络，如2G
	 * 
	 * @return
	 */
	public static boolean isConnectFast(Context ctx) {
		String connectionType = NetWorkUtil.getConnectionTypeName(ctx);
		if ("TYPE_1xRTT".equals(connectionType) || "NETWORK_TYPE_CDMA".equals(connectionType)
				|| "NETWORK_TYPE_EDGE".equals(connectionType) || "TYPE_GPRS".equals(connectionType)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取网络连接类型
	 * 
	 * @return
	 */
	public static String getConnectionTypeName(Context ctx) {
		String netType = "TYPE_UNKNOWN";

		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				netType = "TYPE_WIFI";
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				switch (networkInfo.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					netType = "TYPE_1xRTT";
					break; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_CDMA:
					netType = "TYPE_CDMA";
					break; // ~ 14-64 kbps
				case TelephonyManager.NETWORK_TYPE_EDGE:
					netType = "TYPE_EDGE";
					break; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					netType = "TYPE_EVDO_0";
					break; // ~ 400-1000 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					netType = "TYPE_EVDO_A";
					break; // ~ 600-1400 kbps
				case TelephonyManager.NETWORK_TYPE_GPRS:
					netType = "TYPE_GPRS";
					break; // ~ 100 kbps
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					netType = "TYPE_HSDPA";
					break; // ~ 2-14 Mbps
				case TelephonyManager.NETWORK_TYPE_HSPA:
					netType = "TYPE_HSPA";
					break; // ~ 700-1700 kbps
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					netType = "TYPE_HSUPA";
					break; // ~ 1-23 Mbps
				case TelephonyManager.NETWORK_TYPE_UMTS:
					netType = "TYPE_UMTS";
					break; // ~ 400-7000 kbps
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					netType = "TYPE_MOBILE";
					break;
				}
			}
		}
		return netType;
	}

	/**
	 * 获取网络类型名称：2g or 3g or wifi
	 * 
	 * @return
	 */
	public static String getNetTypeName(Context ctx) {
		String netType = "";

		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				// networkInfo.getTypeName();
				netType = "wifi";
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				switch (networkInfo.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					netType = "2g";
					break; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_CDMA:
					netType = "2g";
					break; // ~ 14-64 kbps
				case TelephonyManager.NETWORK_TYPE_EDGE:
					netType = "2g";
					break; // ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					netType = "3g";
					break; // ~ 400-1000 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					netType = "3g";
					break; // ~ 600-1400 kbps
				case TelephonyManager.NETWORK_TYPE_GPRS:
					netType = "2g";
					break; // ~ 100 kbps
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					netType = "3g";
					break; // ~ 2-14 Mbps
				case TelephonyManager.NETWORK_TYPE_HSPA:
					netType = "3g";
					break; // ~ 700-1700 kbps
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					netType = "3g";
					break; // ~ 1-23 Mbps
				case TelephonyManager.NETWORK_TYPE_UMTS:
					netType = "3g";
					break; // ~ 400-7000 kbps
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					netType = "";
					break;
				}
			}
		}
		return netType;
	}

	/**
	 * 判断是否处于2g或者3g网络状态
	 * 
	 * @return
	 */
	public static boolean is2G3G(Context ctx) {
		String connectName = getConnectionTypeName(ctx);
		return !("TYPE_WIFI".equals(connectName));
	}

	/**
	 * 判断是否处于移动网络
	 * 
	 * @return
	 */
	public static boolean isMobileNet(Context ctx) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/***
	 * 判断是否连接wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiNet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null && wifi.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否已经联网
	 * 
	 * @return true已联网 false未联网
	 */
	public static boolean isConnectNet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

}
