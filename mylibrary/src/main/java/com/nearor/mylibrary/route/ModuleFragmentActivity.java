package com.nearor.mylibrary.route;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 承载 AppModule Fragment
 */
public class ModuleFragmentActivity extends AppCompatActivity {

    private TextView textView;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout rootView = new FrameLayout(this);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        setContentView(rootView);

        textView = new TextView(this);
        rootView.addView(textView);

        String fragmentName = getIntent().getStringExtra(Route.INTENT_EXTAR_FRAGMENT);
        if (savedInstanceState == null && !TextUtils.isEmpty(fragmentName)) {
            try {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                if (fragment != null) {
                    transaction.remove(fragment);
                }
                fragment = (Fragment)getClassLoader().loadClass(fragmentName).newInstance();

                transaction.show(fragment);
                transaction.commit();
            } catch (Exception e) {
                textView.setText("无法载入页面\n");
                textView.append(e.getMessage());
                textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
            }
        }
    }
}
