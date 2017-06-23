package com.autobotstech.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autobotstech.activity.fragment.BaseFragement;


public class CheckMenuChangeActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_checkmenu_change, container, false);

        view.findViewById(R.id.change_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                BaseFragement.initFragment(R.id.checkmenucontainer, new CheckActivity());

            }
        });
        view.findViewById(R.id.changecolor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivity.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });


        return view;
    }



}


