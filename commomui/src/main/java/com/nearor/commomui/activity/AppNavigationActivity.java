package com.nearor.commomui.activity;

import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nearor.commomui.R;
import com.nearor.mylibrary.activity.AppActiviy;
import com.nearor.mylibrary.util.Lg;

public class AppNavigationActivity extends AppActiviy {

    private static final String TAG = Lg.makeLogTag(AppNavigationActivity.class);

    private TextView mTitleView;
    private View mBackView;

    private LinearLayout mRightItemContainer;
    private Button mDefaultRightButton;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        View toolbarView = findViewById(R.id.commonui_navigation_bar);
        if (toolbarView instanceof Toolbar) {
            Toolbar toolbar = (Toolbar)toolbarView;
            setSupportActionBar(toolbar);

            View titleView = toolbar.findViewById(R.id.navigation_bar_title_view);
            if (titleView instanceof TextView && getSupportActionBar() != null) {
                mTitleView = (TextView)titleView;
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            mBackView = toolbar.findViewById(R.id.navigation_bar_back_view);
            if (mBackView != null) {
                mBackView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

            View rightContainer = toolbar.findViewById(R.id.navigation_bar_right_item_container);
            if (rightContainer instanceof LinearLayout) {
                mRightItemContainer = (LinearLayout)rightContainer;

                View rightItem = mRightItemContainer.findViewById(R.id.navigation_bar_right_default_btn);
                if (rightItem instanceof Button) {
                    mDefaultRightButton = (Button)rightItem;
                    mDefaultRightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rightActionButtonClick(v);
                        }
                    });
                }
            }
        }
    }

    public void rightActionButtonClick(View actionItem) {

    }

}
