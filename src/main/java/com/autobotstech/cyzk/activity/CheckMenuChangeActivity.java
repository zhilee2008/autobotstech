package com.autobotstech.cyzk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;


public class CheckMenuChangeActivity extends Fragment {

    private AppGlobals appGlobals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_checkmenu_change, container, false);

        appGlobals = (AppGlobals) getActivity().getApplication();

        view.findViewById(R.id.change_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.checkmenucontainer, new CheckMenuActivity());

            }
        });
        view.findViewById(R.id.B2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("2");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("3");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("4");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("5");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("6");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("7");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("8");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });


        return view;
    }


}


