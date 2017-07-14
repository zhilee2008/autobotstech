package com.autobotstech.cyzk.activity.fragment;

import android.widget.Toast;

import com.autobotstech.cyzk.R;


public class AuditoriumFragment extends BaseFragement {

    @Override
    protected void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_auditorium;
    }

    @Override
    protected void getDataFromServer() {
        Toast.makeText(mContext, "PublishFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }
}
