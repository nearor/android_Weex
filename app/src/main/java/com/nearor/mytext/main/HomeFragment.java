package com.nearor.mytext.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nearor.mylibrary.fragment.AppFragment;
import com.nearor.mylibrary.route.AppModuleMap;
import com.nearor.mytext.R;


public class HomeFragment extends AppFragment {

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        return view;
    }

    private void initView(View container){
        button = (Button) container.findViewById(R.id.btn_native);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntent();
            }
        });


    }

    private void setIntent() {

        startModule(AppModuleMap.MODULE_NAME_NATIVE);

//        Intent intent = new Intent(getActivity(),NativeActivity.class);
//        startActivity(intent);
    }


}
