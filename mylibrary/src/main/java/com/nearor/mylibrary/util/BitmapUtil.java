package com.nearor.mylibrary.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

import com.nearor.mylibrary.constants.Constants;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;


/**
 * 图片加载工具类 支持的图片地址格式如下列：
 * 
 * <Pre>
 * String imageUri = "http://site.com/image.png"; // from Web
 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
 * String imageUri = "content://media/external/audio/albumart/13"; // from content
 * 																// provider
 * String imageUri = "assets://image.png"; // from assets
 * String imageUri = "drawable://"; + R.drawable.image; // from drawables (only
 * 													// images, non-9patch)
 * </Pre>
 */
public class BitmapUtil {

	private static BitmapUtil instance;
	private static final Drawable imageOnLoading = new ColorDrawable(Color.TRANSPARENT);

	private final ImageLoader imageLoader;
	private final DisplayImageOptions optionNotCacheInDiscAndMemory;
	private final Pattern pPre;
	private static final Pattern pEx = Pattern.compile("_\\d+x\\d+\\.jpg");



	private BitmapUtil() {

		optionNotCacheInDiscAndMemory = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
				.cacheInMemory(false).cacheOnDisk(false).considerExifParams(false).showImageOnLoading(imageOnLoading)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		pPre = Pattern.compile("http://d([0-1]*[0-9]|20).yihaodian(img)?.com/");
		
		//initImageLoader(AppContext.getOriginAppContext());

		imageLoader = ImageLoader.getInstance();
	}
	
	/**
	 * just called by applicatoin, do not call explict
	 */
	private void initImageLoader(Context context) {
		if (ImageLoader.getInstance().isInited()) {
			return;
		}
		DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
				.cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).showImageOnLoading(imageOnLoading)
				.build();

		File cacheDir = new File(BitmapUtil.getImageCacheDirPath(context));

		if (cacheDir != null) {
			Lg.v("",cacheDir.getAbsolutePath());
		} else {
			Lg.e("","No cache dir");
		}

		// WeakMemoryCache wmCache = new WeakMemoryCache();
		// Least frequently used bitmap is deleted when cache size limit is
		// exceeded
		UsingFreqLimitedMemoryCache uflCache = new UsingFreqLimitedMemoryCache(2 * 1024 * 1024);

		// The fastest cache, doesn't limit cache size
		// UnlimitedDiscCache is 30%-faster than other limited disc cache
		// implementations.
		UnlimitedDiskCache udCache = new UnlimitedDiskCache(cacheDir);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				// Reduce thread pool size in configuration. 1 - 5 is
				// recommended
				.threadPoolSize(5)
				.threadPriority(Thread.MIN_PRIORITY + 3)
				// default 4
				.denyCacheImageMultipleSizesInMemory().defaultDisplayImageOptions(options).memoryCache(uflCache)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(udCache).build();
		ImageLoader.getInstance().init(config);
		L.writeLogs(true);
	}



	/**
	 * 
	 * @return return BitmapUtil instance
	 */
	public static BitmapUtil getInstance() {
		if (instance == null) {
			synchronized (BitmapUtil.class) {
				if (instance == null) {
					instance = new BitmapUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 显示图片，根据控件尺寸动态取图，并缓存到sdcard和内存
	 * 
	 * @param container
	 *            图片显示在当前控件上
	 * @param uri
	 *            图片地址
	 */
	public void display(final ImageView container, final String uri) {
		display(container, uri, true, true);
	}

	/**
	 * 
	 * 显示图片，根据控件尺寸动态取图
	 * 
	 * @param container
	 *            图片显示在当前控件上
	 * @param uri
	 *            图片地址
	 * @param isNeedCache
	 *            是否需要缓存到内存和sdcard
	 */
	public void display(final ImageView container, final String uri, boolean isNeedCache) {
		display(container, uri, isNeedCache, true);
	}

	/**
	 * 
	 * 显示图片
	 * 
	 * @param container
	 *            图片显示在当前控件上
	 * @param uri
	 *            图片地址
	 * @param isNeedCache
	 *            是否需要缓存到内存和sdcard
	 * @param isAutoResize
	 *            是否根据控件尺寸动态取图
	 */
	public void display(final ImageView container, final String uri, final boolean isNeedCache,
			final boolean isAutoResize) {

		// 如果不需要根据容器计划图片大小，则直接显示原始图片
		if (!isAutoResize || !isNeedCache) {
			//Lg.v("不需要根据容器计划图片大小，直接显示原始图片", uri);
			display(container, uri, isNeedCache, 0, 0);
			return;
		}

		int width = 0;
		int height = 0;
		width = container.getWidth();
		height = container.getHeight();
		// 先尝试直接去取控件大小，如果没有得到就去测量
		if (width <= 1 || height <= 1) {
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
			container.measure(w, h);
			width = container.getMeasuredWidth();
			height = container.getMeasuredHeight();
		}

		if (width <= 1 || height <= 1) {
			//Lg.v("获取的控件尺寸失败，通过ViewTreeObserver进行回调", uri);
			final ViewTreeObserver vto = container.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					if (container.getWidth() > 1 && container.getHeight() > 1) {
						display(container, uri, isNeedCache, container.getWidth(), container.getHeight());
					} else {
						display(container, uri, isNeedCache, 0, 0);
					}
					container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			});
		} else {
			//Lg.v("获取的控件尺寸为", width, height, uri);
			display(container, uri, isNeedCache, width, height);
		}
	}

	/**
	 * 
	 * @param container
	 * @param uri
	 * @param needCache
	 * @param width
	 * @param height
	 */
	private void display(final ImageView container, String uri, boolean needCache, int width, int height) {
		if (uri != null) {
			if (width > 0 && height > 0) {
				Matcher m = pPre.matcher(uri);
				if (m.find()) {
					//float sqrtScale = (float) Math.sqrt(AppContext.getDeviceInfo().scale);
					//uri = replaceUri(uri, (int) (width / sqrtScale), (int) (height / sqrtScale));
				}
			}
			//Lg.v("图片地址：", uri, " View width=", width, "View height=", height, " sqrtScale=",
			//		AppContext.getDeviceInfo().scale);
		}
		if (needCache) {
			imageLoader.displayImage(uri, container);
		} else {
			imageLoader.displayImage(uri, container, optionNotCacheInDiscAndMemory);
		}
	}

	/**
	 * 同步下载图片，会阻塞当前线程 下载的图片不会缓存到内存和sdcard
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap loadImageSync(String uri) {
		return imageLoader.loadImageSync(uri, optionNotCacheInDiscAndMemory);
	}

	/**
	 * 异步下载图片 下载的图片会缓存到内存和sdcard
	 * 
	 * @param uri
	 */
	public void loadImage(String uri) {
		imageLoader.loadImage(uri, null);
	}

	/**
	 * 异步下载图片 下载的图片会缓存到内存和sdcard
	 * 
	 * @param uri
	 */
	public void loadImage(String uri, ImageLoadingListener imageLoadingListener) {
		imageLoader.loadImage(uri, imageLoadingListener);
	}

//	/**
//	 *
//	 * @param uri
//	 * @param width
//	 * @param height
//	 * @return
//	 */
//	public static String replaceUri(String uri, int width) {
//		return replaceUri(uri, width, width);
//	}

//	/**
//	 *
//	 * @param uri
//	 * @param width
//	 * @param height
//	 * @return
//	 */
//	private static String replaceUri(String uri, int width, int height) {
//		if (TextUtils.isEmpty(uri)) {
//			return uri;
//		}
//
//		if (NetWorkUtil.is2G3G() && !Settings.getShowHightImage()) {
//			width = width / 2;
//			height = height / 2;
//		}
//
//		if (uri.lastIndexOf(".jpg") != -1) {
//			Matcher m = pEx.matcher(uri);
//			if (m.find()) {
//				uri = m.replaceFirst(".jpg");
//			}
//			StringBuilder ext = new StringBuilder("_").append(width).append("x").append(height);
//			uri = new StringBuilder(uri).insert(uri.lastIndexOf(".jpg"), ext.toString()).toString();
//		}
//		return uri;
//	}

	public static String getCacheDir(Context context) {

		String diskcachepath = "/sdcard/yihaodian";
		if (Util.isExistSdCard()) {
			File exCacheDir = context.getExternalCacheDir();
			diskcachepath = exCacheDir.getAbsolutePath();
		}
		Lg.v("image disc cache dir", diskcachepath);
		return diskcachepath;
	
	}
	
	/**
	 * 获取缓存图片文件路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getImageCacheDirPath(Context context) {
		String cacheDir = getCacheDir(context);
		File exCacheDir = new File(cacheDir, Constants.BITMAP_CACHE_DIR);
		if (!exCacheDir.exists() || !exCacheDir.isDirectory()) {
			exCacheDir.mkdir();
		}
		Lg.v("image disc cache dir", exCacheDir.getAbsolutePath());
		return exCacheDir.getAbsolutePath();
	}

	/**
	 * 保存图片到sdcard
	 * 
	 * @param bitmap
	 *            要保持的图片
	 * @param absolutePath
	 *            保存到的位置，如/mnt/sdcard/xxx.jpg
	 */
	public static void saveImageToSDCard(Bitmap bitmap, String absolutePath) {

		FileOutputStream out = null;
		try {
			File imageSdcard = new File(absolutePath);
			out = new FileOutputStream(imageSdcard);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			imageSdcard.setLastModified(new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 清除内存和磁盘缓存
	 */
	public void clearCache() {
		imageLoader.clearDiskCache();
		imageLoader.clearMemoryCache();
	}

	/**
	 * 从内存缓存中获取图片
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap getImgFromMemory(String uri) {
		return imageLoader.getMemoryCache().get(uri);
	}

	/**
	 * 从磁盘中获取图片
	 * 
	 * @param uri
	 * @return
	 */
	public Drawable getDrawableFromDiskCache(String uri) {
		File diskCache = DiskCacheUtils.findInCache(uri, ImageLoader.getInstance().getDiscCache());
		if (diskCache != null) {
			return new BitmapDrawable(diskCache.getPath());
		}
		return null;
	}

	public boolean isImgExistOnDisk(String uri) {
		File diskCache = DiskCacheUtils.findInCache(uri, ImageLoader.getInstance().getDiscCache());
		if (diskCache != null) {
			return diskCache.exists() && diskCache.isFile();
		}
		return false;
	}

	public boolean isImgExistOnMemory(String uri) {
		List<Bitmap> listBitMap = MemoryCacheUtils.findCachedBitmapsForImageUri(uri, ImageLoader.getInstance()
				.getMemoryCache());

		if (listBitMap != null && listBitMap.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 从磁盘中获取图片
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap getBitmapFromDiskCache(String uri) {
		File diskCache = DiskCacheUtils.findInCache(uri, ImageLoader.getInstance().getDiscCache());
		if (diskCache != null) {
			return new BitmapDrawable(diskCache.getPath()).getBitmap();
		}
		return null;
	}

	/**
	 * 从内存和磁盘缓存中删除指定图片
	 * 
	 * @param url
	 */
	public void removeCache(String url) {
		DiskCacheUtils.removeFromCache(url, ImageLoader.getInstance().getDiskCache());
		MemoryCacheUtils.removeFromCache(url, ImageLoader.getInstance().getMemoryCache());
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
