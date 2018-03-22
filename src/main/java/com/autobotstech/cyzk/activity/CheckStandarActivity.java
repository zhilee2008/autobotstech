package com.autobotstech.cyzk.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.RecyclerStandarAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class CheckStandarActivity extends Fragment {

    private AppGlobals appGlobals;
    ReadResourceTask mTask = null;
    RecyclerView recyclerView;

    LinearLayout listContainer;
    private View mProgressView;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewstandar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listContainer = (LinearLayout) view.findViewById(R.id.listcontainer);
        mProgressView = (ProgressBar) view.findViewById(R.id.progressbar);
        showProgress(true);
//
//        String standarStr = getArguments().getString("standar");
//        try {
//            JSONArray standarArr = new JSONArray(standarStr);
////            Toast.makeText(getActivity(),
////                    "activity说的："+standarArr.getJSONObject(0).getString("id"), Toast.LENGTH_SHORT).show();
//
//            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewstandar);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(linearLayoutManager);
//
//            List<RecyclerItem> standarList = new ArrayList<>();
//
//            for (int i = 0; i < standarArr.length(); i++) {
//                RecyclerItem recyclerItem = new RecyclerItem();
//                recyclerItem.setId(standarArr.getJSONObject(i).getString("id"));
//                recyclerItem.setName(standarArr.getJSONObject(i).getString("name"));
//                recyclerItem.setImage(Utils.getImageID(this.getContext(), standarArr.getJSONObject(i).getString("img")));
//                standarList.add(recyclerItem);
//            }
//
//            RecyclerStandarAdapter recyclerAdapter = new RecyclerStandarAdapter(standarList, appGlobals);
//            recyclerView.setAdapter(recyclerAdapter);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        LinearLayout standar_1 = (LinearLayout) view.findViewById(R.id.G1);
//        standar_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                appGlobals.setCarStandard("G1");
//                CheckActivity.changeFragment(R.id.checkmainpage, new CheckUsageActivity());
//
//            }
//        });
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
            List<RecyclerItem> standarList = new ArrayList<>();
            String standarStr = getArguments().getString("standar");
            try {
                JSONArray standarArr = new JSONArray(standarStr);

                for (int i = 0; i < standarArr.length(); i++) {
                    RecyclerItem recyclerItem = new RecyclerItem();
                    recyclerItem.setId(standarArr.getJSONObject(i).getString("id"));
                    recyclerItem.setName(standarArr.getJSONObject(i).getString("name"));
                    recyclerItem.setKeyword(standarArr.getJSONObject(i).getString("tag"));
                    recyclerItem.setImage(Utils.getImageID(getContext(), standarArr.getJSONObject(i).getString("img")));
                    standarList.add(recyclerItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return standarList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                RecyclerStandarAdapter recyclerAdapter = new RecyclerStandarAdapter(result, appGlobals);
                recyclerView.setAdapter(recyclerAdapter);
            } else {

            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = AppGlobals.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

            listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            listContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}


