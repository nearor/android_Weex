/**
 * 
 */
package com.nearor.mylibrary.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;



/**
 * 常用工具类
 * 
 * @author cailiming
 * 
 */
public class Util {

	private static char[] ca = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static final String FORMATTER_PRICE_STYLE = "0.00";
	public static final String FORMATTER_PRICE_STYLE2 = "0.0";
	public static final String FORMATTER_PRICE_STYLE3 = "￥0.0#";
	public static final String FORMATTER_RATING_STYLE = "#0.0";

	/**
	 * 生成n位随机字符串
	 * 
	 * @param n
	 * @return
	 */
//	public static String generateMixed(int n) {
//		Random random = new Random(AppContext.getSystemTime());
//		char[] cr = new char[n];
//		for (int i = 0; i < n; i++) {
//			int x = random.nextInt(32);
//			cr[i] = ca[x];
//		}
//		return (new String(cr));
//	}

	/**
	 * 得到安卓系统版本号和机型，如galaxy s2,4.2.2, 1.4b02
	 * 
	 * <Pre>
	 * <Code>
	 * android.os.Build.MODEL + "," + android.os.Build.VERSION.SDK
				+ "," + android.os.Build.VERSION.RELEASE;
	 * </Code>
	 * 
	 * <Pre>
	 * @return
	 */
	public static String getClientVersion() {
		return android.os.Build.MODEL + "," + android.os.Build.VERSION.SDK + "," + android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取APP版本号 如3.1.2
	 * 
	 * @return
	 */
	public static String getClientAppVersion(Context ctx) {
		String vName = "";
		try {
			PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			if (!TextUtils.isEmpty(pi.versionName)) {
				vName = pi.versionName;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return vName;
	}

	/**
	 * 获取APP版本号代码 如：69
	 * 
	 * @return
	 */
	public static int getClientAppCode(Context ctx) {
		int vCode = 0;
		try {
			PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			vCode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return vCode;
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isExistSdCard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 
	 * 小数点后面有两位除0之外的数字，保留两位小数， 否则保留一位小数
	 * 
	 * 
	 * 
	 * @param
	 * 
	 * @return 价格
	 */

	public static Double getDecimalPoint(Double d) {
		if (d == null) {
			return Double.valueOf(0.0);
		}
		String priceStr = d + "";
		if (priceStr.lastIndexOf(".") != -1) {
			// 有小数点
			priceStr = priceStr.substring(priceStr.lastIndexOf(".") + 1, priceStr.length());
			if (priceStr.length() > 1) {
				// 小数点后面有两位除0之外的数字，保留两位小数
				return getDecimalPoint(d, "0.00");
			}
		}
		return getDecimalPoint(d, "0.0");
	}

	public static Double getDecimalPoint(Double d, String format) {
		if (d == null) {
			return Double.valueOf(0.0);
		}
		return Double.parseDouble(new DecimalFormat(format).format(d));
	}

	public static Float getDecimalPoint(Float f, String format) {
		if (f == null) {
			return Float.valueOf(0.0f);
		}
		return Float.parseFloat(new DecimalFormat(format).format(f));
	}

	/**
	 * use UiUtil.setPriceSpan instead
	 * 
	 * @param tv
	 * @param price
	 */
	@Deprecated
	public static void setPriceSpan(TextView tv, String price) {
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	public static String getPriceString(Double price) {
		return "￥" + getDecimalPoint(price);
	}

	public static String getPriceString(Double price, String format) {
		return "￥" + getDecimalPoint(price, format);
	}

	/**
	 * 创建临时图片
	 * 
	 * @return
	 */
	public static File getTempFile(String dirName, String fileName) {

		File f = null;
		try {
			File storagePath = new File(Environment.getExternalStorageDirectory() + "/" + dirName + "/");
			if (!storagePath.exists()) {
				storagePath.mkdirs();
			}
			f = new File(storagePath, fileName);

		} catch (Exception e) {
			//UiUtil.showToast("无存储空间可用，请删除设备无用的数据！");
		//	Lg.printException("", e);
		}
		return f;
	}

	/**
	 * 转换图片地址 http://d12.yihaodianimg.com/t1/2012/1025/373/427/
	 * a0851e200591fd62c77fecedee40a191.jpg
	 * http://d12.yihaodianimg.com/t1/2012/1025
	 * /373/427/a0851e200591fd62c77fecedee40a191_100x100.jpg
	 * 
	 * @param pic
	 * @param size
	 * @return
	 */
	public static String getPic(String pic, int size) {
		return getPic(pic, size, size);
	}

	/**
	 * 转换图片地址 http://d12.yihaodianimg.com/t1/2012/1025/373/427/
	 * a0851e200591fd62c77fecedee40a191.jpg
	 * http://d12.yihaodianimg.com/t1/2012/1025
	 * /373/427/a0851e200591fd62c77fecedee40a191_100x100.jpg
	 * 
	 * @param pic
	 * @param width
	 * @param height
	 * @return
	 */
	public static String getPic(String pic, int width, int height) {
		if (!TextUtils.isEmpty(pic)) {

			StringBuilder extSB = new StringBuilder();
			extSB.append('_').append(width).append('x').append(height);

			if (pic.lastIndexOf('.') != -1) {
				StringBuilder sb = new StringBuilder(pic);
				sb.insert(pic.lastIndexOf('.'), extSB.toString());

				String ret = sb.toString();
				Lg.d("PIC", ret);
				return ret;
			}
		}
		return null;
	}

	public static String formatPrice(Object price) {
		return new DecimalFormat(FORMATTER_PRICE_STYLE).format(price);
	}

	public static String formatConfirmPrice(Object price) {
		return new DecimalFormat(FORMATTER_PRICE_STYLE3).format(price);
	}

	// zhangWei add start
	public static String formatDetailPrice(Object price) {
		return new DecimalFormat(FORMATTER_PRICE_STYLE2).format(price);
	}

	// zhangWei add end

	public static String fomatRating(Object rating) {
		return new DecimalFormat(FORMATTER_RATING_STYLE).format(rating);
	}

	/**
	 * 图片压缩
	 */
	public static void imageCompress(String path, Bitmap bitmap) {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bos = null;
		try {
			fileOutputStream = new FileOutputStream(path);
			bos = new BufferedOutputStream(fileOutputStream);
			if (null != bitmap) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bos) {
					bos.flush();
					bos.close();
				}
				if (null != fileOutputStream) {
					fileOutputStream.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
