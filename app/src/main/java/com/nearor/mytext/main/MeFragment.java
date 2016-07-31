package com.nearor.mytext.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nearor.mylibrary.fragment.AppFragment;
import com.nearor.mylibrary.util.ToastUtil;
import com.nearor.mytext.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends AppFragment implements View.OnClickListener{

   // private MeService mMeService = new MeService(this);
   // private MeCenterData mMeCenterData;
    private ImageView mIcon;
    private TextView mName;
    private TextView mPhone;
    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initViews(view);
        serService();
        return view;
    }

    private void initViews(View container) {
        mIcon = (ImageView) container.findViewById(R.id.pm_me_icon);
        mName = (TextView) container.findViewById(R.id.pm_me_name);
        mPhone = (TextView) container.findViewById(R.id.pm_me_phone);

        mIcon.setOnClickListener(this);
    }

    private void initData(){
        //用户头像
//        if (mMeCenterData.getImg()!= null) {
//            SimpleImageLoader.display(mMeCenterData.getImg(),mIcon,R.mipmap.pm_me_origin);
//        }
//        //用户姓名
//        if(mMeCenterData.getName()!=null){
//            mName.setText(mMeCenterData.getName());
//        }else {
//            mName.setVisibility(View.GONE);
//        }
//        //用户电话
//        if(mMeCenterData.getMobilephone()!=null){
//            mPhone.setText(mMeCenterData.getMobilephone());
//        }else {
//            mPhone.setVisibility(View.GONE);
//        }


    }

    /**
     * 连接接口
     */
    private void serService(){
//        mMeService.getData(new APICallBack<MeCenterData>() {
//            @Override
//            public void onSuccess(@NonNull MeCenterData object) {
//                mMeCenterData = object;
//                initData();
//            }
//
//            @Override
//            public void onError(Throwable throwable, @Nullable APIResponse apiResponse) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if(v == mIcon){
            ToastUtil.showMessage("改变图像");
           // PickImageDialogFragment.getInstance(MeFragment.this,"设置头像").show(getFragmentManager(),"chooseAvatar");
        }

    }
}
