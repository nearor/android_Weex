package com.nearor.mylibrary.bitmap;

import android.graphics.Bitmap;
import android.view.View;

import com.nearor.mylibrary.util.Lg;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 *
 */
public class ImageLoadingListener extends SimpleImageLoadingListener {

    private static final String TAG = Lg.makeLogTag(ImageLoadingListener.class);

    @Override
    public void onLoadingStarted(String imageUri, View view) {
//        Lg.d(TAG, "Started loading image from: " + imageUri + "\nto view: " + view);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//        Lg.e(TAG, "Loading image failed: " + failReason + "\nurl: + " + imageUri + "\nto view: " + view);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//        Lg.d(TAG, "Loading image complete: " + imageUri + "\nto view: " + view + "\nbitmap info: " + loadedImage);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
//        Lg.e(TAG, "Loading image cancelled." + "\nurl: + " + imageUri + "\nto view: " + view);
    }
}
