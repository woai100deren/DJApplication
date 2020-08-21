package com.bigimage;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.coordinator.StatusBarUtil;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.google.android.material.appbar.AppBarLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjing4 on 2017/7/12.
 */

public class BigImageActivity extends BaseActivity {
    @BindView(R.id.rvToDoList)
    RecyclerView rvToDoList;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimage);
        ButterKnife.bind(this);

        initToolbar();


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = verticalOffset * 1.0F / appBarLayout.getHeight();
                mToolbar.setAlpha(-alpha);
            }
        });

        List<String> datas = new ArrayList<>();
        datas.add("11111111111111");
        datas.add("22222222222222");
        datas.add("33333333333333");
        datas.add("44444444444444");
        datas.add("55555555555555");
        datas.add("66666666666666");
        datas.add("77777777777777");
        datas.add("88888888888888");
        datas.add("99999999999999");
        datas.add("00000000000000");
        datas.add("11111111111111");
        datas.add("22222222222222");
        datas.add("33333333333333");
        datas.add("44444444444444");
        datas.add("55555555555555");
        datas.add("66666666666666");
        datas.add("77777777777777");
        datas.add("88888888888888");
        datas.add("99999999999999");
        datas.add("00000000000000");

        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter mDataAdapter = new DataAdapter(datas);
        rvToDoList.setAdapter(mDataAdapter);
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        StatusBarUtil.transparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        }
    }

    class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
        private List<String> datas;
        public DataAdapter(List<String> datas){
            this.datas = datas;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }
        }
    }
}
