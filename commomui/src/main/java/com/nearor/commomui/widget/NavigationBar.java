package com.nearor.commomui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearor.commomui.R;


/**
 * 页面顶部导航栏。左侧按钮区域，中间标题区域，右侧按钮区域
 */
public class NavigationBar extends RelativeLayout {

    private RelativeLayout mLeftContainer;
    private RelativeLayout mMidContainer;
    private RelativeLayout mRightContainer;

    private ImageButton mLeftDefaultBtn;
    private TextView mDefaultTitleTextView;
    private Button mRightDefaultBtn;

    private View mSplitLine;

    public NavigationBar(Context context) {
        super(context);

        initSubviews();
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initSubviews();
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initSubviews();
    }

    private void initSubviews() {
        inflate(getContext(), R.layout.navigation_bar, this);
        mLeftContainer = (RelativeLayout)findViewById(R.id.navigation_bar_left_container);
        mMidContainer = (RelativeLayout)findViewById(R.id.navigation_bar_mid_container);
        mRightContainer = (RelativeLayout)findViewById(R.id.navigation_bar_right_container);

        mLeftDefaultBtn = (ImageButton)findViewById(R.id.navigation_bar_left_default_btn);
        mDefaultTitleTextView = (TextView)findViewById(R.id.navigation_bar_mid_default_text_view);
        mRightDefaultBtn = (Button)findViewById(R.id.navigation_bar_right_default_btn);

        mSplitLine = (View)findViewById(R.id.navigation_bar_split_line);
    }

    public void setDefaultLeftButtonOnClickListener(OnClickListener listener) {
        if (mLeftDefaultBtn != null && listener != null) {
            mLeftDefaultBtn.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        mDefaultTitleTextView.setText(title);
    }

    public void setCustomTitleView(int layoutId) {
        View titleView = inflate(getContext(), layoutId, mMidContainer);
        setCustomTitleView(titleView);
    }

    public void setCustomTitleView(View titleView) {
        setCustomTitleView(titleView, null);
    }

    public void setCustomTitleView(View titleView, LayoutParams layoutParams) {
        if (titleView == null) {
            mDefaultTitleTextView.setVisibility(GONE);
        } else {
            mDefaultTitleTextView.setVisibility(GONE);
            mMidContainer.addView(titleView, layoutParams);
        }
    }

    public void setCustomLeftView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomLeftView(view, null);
    }

    public void setCustomLeftView(View view) {
        setCustomLeftView(view, null);
    }

    public void setCustomLeftView (View view, LayoutParams layoutParams) {
        if (view == null) {
            mLeftDefaultBtn.setVisibility(GONE);
        } else {
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            }
            mLeftDefaultBtn.setVisibility(GONE);
            mLeftContainer.addView(view, layoutParams);
        }
    }

    public void setRightText(String text) {
        if (TextUtils.isEmpty(text)) {
            mRightDefaultBtn.setVisibility(GONE);
        } else {
            mRightDefaultBtn.setVisibility(VISIBLE);
            mRightDefaultBtn.setText(text);
        }
    }

    public void setCustomRightView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomRightView(view, null);
    }

    public void setCustomRightView(View view) {
        setCustomRightView(view, null);
    }

    public void setCustomRightView (View view, LayoutParams layoutParams) {
        if (view == null) {
            mRightDefaultBtn.setVisibility(GONE);
        } else {
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            view.setLayoutParams(layoutParams);
            mRightContainer.addView(view, layoutParams);
        }
    }

    public void hidenSplitLine() {
        mSplitLine.setVisibility(GONE);
    }

    public void showSpliteLine() {
        mSplitLine.setVisibility(VISIBLE);
    }

}
