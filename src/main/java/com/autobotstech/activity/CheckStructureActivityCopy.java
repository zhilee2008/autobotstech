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
import android.widget.Toast;

import com.autobotstech.AppGlobals;


public class CheckStructureActivityCopy extends Fragment {

    private AppGlobals appGlobals;

    LinearLayout view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        appGlobals = new AppGlobals();

        View view = inflater.inflate(R.layout.activity_check_structure, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();
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
        backbutton.setText(R.string.business);
        backbutton.setVisibility(View.VISIBLE);

        view1 = (LinearLayout) view.findViewById(R.id.structurecontainer);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("++++"+view1.findFocus());
                Toast.makeText(getContext(),"aa",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}


