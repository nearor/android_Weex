package com.nearor.mylibrary.bitmap;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;


/**
 * Created by Nearor on 16/7/28.
 */
public class FrescoImageAdapter implements IWXImgLoaderAdapter {


    public FrescoImageAdapter() {
    }

    @Override
    public void setImage(final String url, final ImageView view,
                         WXImageQuality quality, WXImageStrategy strategy) {

        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (view == null || view.getLayoutParams() == null) {
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    view.setImageBitmap(null);
                    return;
                }
                String temp = url;
                if (url.startsWith("//")) {
                    temp = "http:" + url;
                }
                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    return;
                }

                Uri uri = Uri.parse(temp);

                ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
                        .setBackgroundColor(Color.GREEN)
                        .build();

                ImageRequest request = ImageRequestBuilder
                        .newBuilderWithSource(uri)
                        .setImageDecodeOptions(decodeOptions)
                        .setAutoRotateEnabled(true)
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                        .setProgressiveRenderingEnabled(false)
                        .build();


                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                DataSource<CloseableReference<CloseableImage>>
                        dataSource = imagePipeline.fetchDecodedImage(request, new Object());

                DataSubscriber dataSubscriber =
                        new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
                            @Override
                            public void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                                CloseableReference<CloseableImage> imageReference = dataSource.getResult();
                                if (imageReference != null) {
                                    try {
                                        // do something with the image
                                        Preconditions.checkState(CloseableReference.isValid(imageReference));
                                        CloseableImage closeableImage = imageReference.get();
                                        if (closeableImage instanceof CloseableStaticBitmap) {
                                            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
                                            view.setImageBitmap(closeableStaticBitmap.getUnderlyingBitmap());
                                            // boolean hasResult =  null != closeableStaticBitmap.getUnderlyingBitmap();
                                        } else {
                                            throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
                                        }
                                    } finally {
                                        imageReference.close();
                                    }
                                }
                            }

                            @Override
                            public void onFailureImpl(DataSource dataSource) {
                            }
                        };

                dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());

            }
        }, 0);
    }
}
