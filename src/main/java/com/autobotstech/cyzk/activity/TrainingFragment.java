package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;


public class TrainingFragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        view = inflater.inflate(R.layout.activity_training_container, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);

        backbutton.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.training2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.lecturehallmainpage, new LecturehallListTraining2Fragment());

            }
        });

        view.findViewById(R.id.training3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.lecturehallmainpage, new LecturehallListTraining3Fragment());

            }
        });

        view.findViewById(R.id.training4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.lecturehallmainpage, new LecturehallListTraining4Fragment());

            }
        });

        view.findViewById(R.id.training5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                CheckActivityContainer.changeFragment(R.id.lecturehallmainpage, new LecturehallListTraining5Fragment());

            }
        });

        return view;
    }



}
