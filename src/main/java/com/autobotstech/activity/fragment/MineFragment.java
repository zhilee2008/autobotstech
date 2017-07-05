package com.autobotstech.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;

import com.autobotstech.activity.LoginActivity;
import com.autobotstech.activity.R;


public class MineFragment extends BaseFragement{

    private String mobile;
    private String password;
    SharedPreferences sp;
    private String token;

    @Override
    protected void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        LinearLayout logout = (LinearLayout) mView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token", "");
                editor.putString("mobile", "");
                editor.putString("password", "");
                editor.commit();

                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                MineFragment.this.getActivity().startActivity(intent);
                MineFragment.this.getActivity().finish();

            }
        });

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }
    @Override
    protected void getDataFromServer() {


    }


}
