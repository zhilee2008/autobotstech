package com.autobotstech.cyzk.activity.fragment;

import android.content.SharedPreferences;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.LecturehallListFragment;


public class LecturehallFragment extends BaseFragement {

    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;


    @Override
    protected void initView() {

//        TextView titlebar = (TextView) mView.findViewById(R.id.text_title);
//        titlebar.setText(R.string.title_auditorium);
        initFragment(R.id.lecturehallmainpage, new LecturehallListFragment());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lecturehall;
    }

    @Override
    protected void getDataFromServer() {
//        Toast.makeText(mContext, "PublishFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }


}
