package com.nearor.mytext.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nearor.commomui.activity.AppNavigationActivity;
import com.nearor.mylibrary.route.AppModuleMap;
import com.nearor.mylibrary.route.RouteUtils;
import com.nearor.mytext.R;

import java.util.Map;

/**
 *
 * Created by Nearor on 16/7/30.
 */
public class MainActivity extends AppNavigationActivity implements View.OnClickListener{

    public static final String EXTRA_EXIT = "extra_exit";

    private HomeFragment mHomeFragment;
    private ImFragment mImFragment;
    private MeFragment mMeFragment;

    /***
     * 保存当前正在显示的Fragment ，下次显示新的fragment之前移除
     */
    private Object currentShowFragment;


    private TextView mTitleText;
    private Button mBtnHomeTab;
    private Button mBtnImTab;
    private Button mBtnMeTab;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHomeFragment = new HomeFragment();
        mImFragment = new ImFragment();
        mMeFragment = new MeFragment();

        mTitleText = (TextView) findViewById(R.id.pm_main_activity_title_text_view);
        mBtnHomeTab = (Button) findViewById(R.id.pm_main_activity_home_button);
        mBtnImTab = (Button) findViewById(R.id.pm_main_activity_im_button);
        mBtnMeTab = (Button) findViewById(R.id.pm_main_activity_me_button);

        mBtnHomeTab.setOnClickListener(this);
        mBtnImTab.setOnClickListener(this);
        mBtnMeTab.setOnClickListener(this);

        showModule(AppModuleMap.MODULE_NAME_HOME);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        String moduleName = AppModuleMap.MODULE_NAME_HOME;
        Map params = RouteUtils.parseModuleUrlParams(intent.getData().toString());
        if (params != null) {
            moduleName = params.get("moduleName") instanceof String ? (String)params.get("moduleName") : "";
        }

        moduleName = TextUtils.isEmpty(moduleName) ? AppModuleMap.MODULE_NAME_HOME : moduleName;
        showModule(moduleName);
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnHomeTab){
            showModule(AppModuleMap.MODULE_NAME_HOME);
        }
        else if(v == mBtnImTab){
            showModule(AppModuleMap.MODULE_NAME_IM);
        }
        else if(v == mBtnMeTab){
            showModule(AppModuleMap.MODULE_NAME_ME);
        }
    }

    private void showModule(String moduleName){
        mBtnHomeTab.setSelected(false);
        mBtnImTab.setSelected(false);
        mBtnMeTab.setSelected(false);

        if(!TextUtils.isEmpty(moduleName)){
            Object fragment = null;

            if(moduleName.equalsIgnoreCase(AppModuleMap.MODULE_NAME_HOME)){
                mBtnHomeTab.setSelected(true);
                fragment = mHomeFragment;
                mTitleText.setText("工作台");
            }
            else if(moduleName.equalsIgnoreCase(AppModuleMap.MODULE_NAME_IM)){
                mBtnImTab.setSelected(true);
                fragment = mImFragment;
                mTitleText.setText("IM");
            }
            else if(moduleName.equalsIgnoreCase(AppModuleMap.MODULE_NAME_ME)){
                mBtnMeTab.setSelected(true);
                fragment = mMeFragment;
                mTitleText.setText("个人中心");
            }
            else {
                fragment = mHomeFragment;
            }

            showFragment(fragment,moduleName);
        }
    }

    private void showFragment(Object fragment,String moduleName) {

        if(fragment == currentShowFragment){
            return;
        }

        if(currentShowFragment instanceof Fragment){
            getFragmentManager().beginTransaction().remove((Fragment) currentShowFragment).commit();
        }
        else if(currentShowFragment instanceof android.support.v4.app.Fragment){
            getSupportFragmentManager().beginTransaction().remove((android.support.v4.app.Fragment) currentShowFragment).commit();
        }

        currentShowFragment = fragment;
        if(fragment instanceof Fragment){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.pm_main_activity_content_container, (Fragment) fragment,moduleName);
            fragmentTransaction.commit();
        }
        else if(fragment instanceof android.support.v4.app.Fragment){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.pm_main_activity_content_container, (android.support.v4.app.Fragment)fragment,moduleName);
            transaction.commit();
        }

    }
}
