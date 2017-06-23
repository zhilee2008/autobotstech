package com.autobotstech.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autobotstech.activity.fragment.BaseFragement;

import static com.autobotstech.activity.CheckActivity.initFragment;


public class CheckMenuActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_menu, container, false);


        view.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });
        view.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });
        view.findViewById(R.id.transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });
        view.findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }

        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });
        view.findViewById(R.id.schollbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                initFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });


        return view;
    }



}


