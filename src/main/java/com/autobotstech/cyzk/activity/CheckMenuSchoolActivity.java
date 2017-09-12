package com.autobotstech.cyzk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.fragment.BaseFragement;


public class CheckMenuSchoolActivity extends Fragment {

    private AppGlobals appGlobals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_checkmenu_schoolbus, container, false);

        appGlobals = (AppGlobals)getActivity().getApplication();

        view.findViewById(R.id.change_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.initFragment(R.id.checkmenucontainer, new CheckMenuActivity());

            }
        });
        view.findViewById(R.id.B14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("14");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("15");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("16");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });


        return view;
    }



}


