package com.autobotstech.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class CheckStandarActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_standar, container, false);
        ViewGroup vg=(ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
        backbutton.setText(R.string.standar);
        backbutton.setVisibility(View.VISIBLE);


        LinearLayout structure_1 = (LinearLayout) view.findViewById(R.id.standar_1);
        structure_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CheckActivity.changeFragment(R.id.checkmainpage, new CheckUsageActivity());

            }
        });

        return view;
    }


}


