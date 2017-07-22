package com.autobotstech.cyzk.activity.fragment;

import android.widget.Toast;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.InfoActivity;
import com.autobotstech.cyzk.activity.LecturehallListFragment;


public class InfoFragment extends BaseFragement{

    @Override
    protected void initView() {
        initFragment(R.id.infomainpage,new InfoActivity());
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
