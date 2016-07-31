package com.nearor.mytext.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nearor.commomui.activity.AppNavigationActivity;
import com.nearor.mytext.R;
import com.nearor.mytext.adapter.CommonAdapter;
import com.nearor.mytext.adapter.base.ViewHolder;
import com.nearor.mytext.adapter.wrapper.EmptyWrapper;
import com.nearor.mytext.adapter.wrapper.HeaderAndFooterWrapper;
import com.nearor.mytext.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by Nearor on 16/7/31.
 */
public class NativeActivity extends AppNavigationActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, String s, int position)
            {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
            }
        };

        initHeaderAndFooter();

//        initEmptyView();

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (int i = 0; i < 10; i++)
                        {
                            mDatas.add("Add:" + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<String>()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position)
            {
                Toast.makeText(NativeActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o, int position)
            {
                return false;
            }
        });
    }

    private void initEmptyView()
    {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, mRecyclerView, false));
    }

    private void initHeaderAndFooter()
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addHeaderView(t2);
    }

    private void initDatas()
    {
        for (int i = 'A'; i <= 'z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();


    }

    @Override
    public void onStop()
    {
        super.onStop();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

}
