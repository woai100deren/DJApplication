package com.dj.ca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coordinator.CoordinatorActivity;
import com.dj.ca.ann.CheckLogin;
import com.dj.ca.ann.SingleClick;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.databinding.ActivityCustomAnnotationsBinding;
import com.dj.logutil.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义注解+Aspectj示例
 */
public class CustomAnnotationsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomAnnotationsBinding dataBinding =  DataBindingUtil.setContentView(this, R.layout.activity_custom_annotations);
        dataBinding.button.setOnClickListener(new View.OnClickListener() {

            @Override
            @SingleClick(describe="this is a value")
            public void onClick(View v) {
                LogUtils.e("点击了按钮："+System.currentTimeMillis());
            }
        });

        dataBinding.textView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(describe = "this is a value")
            @Override
            public void onClick(View v) {
                LogUtils.e("点击了textview："+System.currentTimeMillis());
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

        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAnnotationsActivity.DataAdapter mDataAdapter = new CustomAnnotationsActivity.DataAdapter(datas);
        dataBinding.recyclerView.setAdapter(mDataAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    class DataAdapter extends RecyclerView.Adapter<CustomAnnotationsActivity.DataAdapter.ViewHolder> {
        private List<String> datas;

        public DataAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public CustomAnnotationsActivity.DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            CustomAnnotationsActivity.DataAdapter.ViewHolder vh = new CustomAnnotationsActivity.DataAdapter.ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(CustomAnnotationsActivity.DataAdapter.ViewHolder holder, int position) {
            holder.mTextView.setText(datas.get(position));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(describe = "this is a value")
                @CheckLogin(toLogin = true,toastInfo = "我是登陆拦截")
                @Override
                public void onClick(View v) {
                    LogUtils.e("点击了列表的textview："+datas.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }
        }
    }
}
