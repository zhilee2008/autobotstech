package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.os.AsyncTask;
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
import com.autobotstech.cyzk.adapter.RecyclerUsageAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CheckUsageActivity extends Fragment {

    private AppGlobals appGlobals;
    ReadResourceTask mTask = null;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_usage, container, false);
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
        backbutton.setText(R.string.title_check);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.usage);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewusage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

//        List<RecyclerItem> usageList = new ArrayList<>();
//
//        JSONObject structOBJ = Utils.readJSONFromFile(getContext(), "usage.json");
//        if (structOBJ != null) {
//            try {
//                JSONArray usageArr = structOBJ.getJSONArray("usage");
//                for (int i = 0; i < usageArr.length(); i++) {
//                    RecyclerItem recyclerItem = new RecyclerItem();
//                    recyclerItem.setId(usageArr.getJSONObject(i).getString("id"));
//                    recyclerItem.setName(usageArr.getJSONObject(i).getString("name"));
//                    recyclerItem.setImage(Utils.getImageID(getContext(), usageArr.getJSONObject(i).getString("img")));
//                    usageList.add(recyclerItem);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        RecyclerUsageAdapter recyclerAdapter = new RecyclerUsageAdapter(usageList, appGlobals);
//        recyclerView.setAdapter(recyclerAdapter);
        mTask = new ReadResourceTask();
        mTask.execute((Void) null);
        return view;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ReadResourceTask extends AsyncTask<Void, Void, List> {

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            List<RecyclerItem> usageList = new ArrayList<>();
            try {
                JSONObject structOBJ = Utils.readJSONFromFile(getContext(), "usage.json");
                JSONArray usageArr = structOBJ.getJSONArray("usage");
                for (int i = 0; i < usageArr.length(); i++) {
                    RecyclerItem recyclerItem = new RecyclerItem();
                    recyclerItem.setId(usageArr.getJSONObject(i).getString("id"));
                    recyclerItem.setName(usageArr.getJSONObject(i).getString("name"));
                    recyclerItem.setImage(Utils.getImageID(getContext(), usageArr.getJSONObject(i).getString("img")));
                    usageList.add(recyclerItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return usageList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                RecyclerUsageAdapter recyclerAdapter = new RecyclerUsageAdapter(result, appGlobals);
                recyclerView.setAdapter(recyclerAdapter);
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }


}


