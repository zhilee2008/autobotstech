package com.autobotstech.cyzk.activity.fragment;


import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivityContainer;


public class CheckFragment extends BaseFragement {


    @Override
    protected void initView() {

        initFragment(R.id.checkmainpage, new CheckActivityContainer());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_check;
    }

    @Override
    protected void getDataFromServer() {


    }


}
