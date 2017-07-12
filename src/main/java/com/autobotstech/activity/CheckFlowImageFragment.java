package com.autobotstech.activity;

import android.widget.Toast;

import com.autobotstech.activity.fragment.BaseFragement;


public class CheckFlowImageFragment extends BaseFragement {

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
