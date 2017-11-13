package com.autobotstech.cyzk.activity.fragment;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.MineActivity;


public class MineFragment extends BaseFragement {


    @Override
    protected void initView() {

        initFragment(R.id.minemainpage, new MineActivity());


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void getDataFromServer() {


    }


}
