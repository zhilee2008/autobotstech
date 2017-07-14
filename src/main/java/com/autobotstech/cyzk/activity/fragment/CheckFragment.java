package com.autobotstech.cyzk.activity.fragment;


import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivity;


public class CheckFragment extends BaseFragement{


    @Override
    protected void initView() {

        initFragment(R.id.checkmainpage,new CheckActivity());

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_check;
    }
    @Override
    protected void getDataFromServer() {


    }


}
