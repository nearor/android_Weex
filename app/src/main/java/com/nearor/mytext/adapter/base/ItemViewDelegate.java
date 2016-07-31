package com.nearor.mytext.adapter.base;

/**
 * Created by Nearor on 16/7/31.
 */
public interface ItemViewDelegate <T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);


}