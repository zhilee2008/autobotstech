package com.autobotstech.cyzk.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;


public class CheckMenuActivity extends Fragment {

    private AppGlobals appGlobals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_menu, container, false);

        appGlobals = (AppGlobals)getActivity().getApplication();

        view.findViewById(R.id.B1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("1");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.checkmenucontainer, new CheckMenuChangeActivity());

            }
        });
        view.findViewById(R.id.B9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("9");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.checkmenucontainer, new CheckMenuRecordActivity());

            }

        });
        view.findViewById(R.id.B13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("13");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.schollbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.checkmenucontainer, new CheckMenuSchoolActivity());

            }
        });
        view.findViewById(R.id.B17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("17");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });
        view.findViewById(R.id.B18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                appGlobals.setBusinessType("18");
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckStructureActivity());

            }
        });


        return view;
    }


}


