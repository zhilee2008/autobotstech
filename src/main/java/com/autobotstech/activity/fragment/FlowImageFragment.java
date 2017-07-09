package com.autobotstech.activity.fragment;

import android.widget.Toast;

import com.autobotstech.activity.R;


public class FlowImageFragment extends BaseFragement{

    @Override
    protected void initView() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void getDataFromServer() {
        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }
}
