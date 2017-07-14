package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.RecyclerStandarAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class CheckStandarActivity extends Fragment {

    private AppGlobals appGlobals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_standar, container, false);
        appGlobals = (AppGlobals) getActivity().getApplication();

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
        backbutton.setText(R.string.structure);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.standar);

        String standarStr = getArguments().getString("standar");
        try {
            JSONArray standarArr = new JSONArray(standarStr);
//            Toast.makeText(getActivity(),
//                    "activity说的："+standarArr.getJSONObject(0).getString("id"), Toast.LENGTH_SHORT).show();

            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerviewstandar);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            List<RecyclerItem> standarList = new ArrayList<>();

            for(int i=0;i<standarArr.length();i++){
                RecyclerItem recyclerItem = new RecyclerItem();
                recyclerItem.setId(standarArr.getJSONObject(i).getString("id"));
                recyclerItem.setName(standarArr.getJSONObject(i).getString("name"));
                recyclerItem.setImage(R.drawable.ic_dashboard_black_24dp);
                standarList.add(recyclerItem);
            }

            RecyclerStandarAdapter recyclerAdapter = new RecyclerStandarAdapter(standarList,appGlobals);
            recyclerView.setAdapter(recyclerAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }



//        LinearLayout standar_1 = (LinearLayout) view.findViewById(R.id.G1);
//        standar_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                appGlobals.setCarStandard("G1");
//                CheckActivity.changeFragment(R.id.checkmainpage, new CheckUsageActivity());
//
//            }
//        });

        return view;
    }


}


