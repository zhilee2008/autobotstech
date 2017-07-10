package com.autobotstech.activity.fragment.framefragment;


import com.autobotstech.activity.CheckActivity;
import com.autobotstech.activity.R;


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
