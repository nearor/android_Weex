package com.nearor.mylibrary.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;


import com.nearor.mylibrary.BuildConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Nearor on 6/1/16.
 */
public class SimpleImageLoader {

    private static DisplayImageOptions.Builder getDefaultOptionsBuilder (){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true) //设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)//设置图片的解码类型
                .considerExifParams(true) //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .resetViewBeforeLoading(true);//设置图片在下载前是否重置，复位
    }

    public static void init(Context context){
        DisplayImageOptions imageOptions = getDefaultOptionsBuilder().build();
        ImageLoaderConfiguration configuration;
        if(BuildConfig.DEBUG){
            configuration = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(imageOptions)
                    .writeDebugLogs()
                    .build();
        } else {
            configuration = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(imageOptions)
                    .build();
        }
        ImageLoader.getInstance().init(configuration);
        ImageLoader.getInstance().setDefaultLoadingListener(new com.nearor.mylibrary.bitmap.ImageLoadingListener());
    }

    public static void display(String url, ImageView imageView){
        ImageLoader.getInstance().displayImage(url,imageView);
    }

    public static void display(String url, ImageView imageView,int placeholderResId){
        DisplayImageOptions options = getDefaultOptionsBuilder()
                .showImageForEmptyUri(placeholderResId)
                .showImageOnLoading(placeholderResId)
                .showImageOnFail(placeholderResId)
                .build();
        ImageLoader.getInstance().displayImage(url,imageView,options);
    }

}
