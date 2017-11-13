package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.RecyclerQaListAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;


public class InfoQaListFragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckFlowListTask mTask = null;

    private List<RecyclerItem> checkFlowList;
    RecyclerQaListAdapter recyclerAdapter;
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");


        view = inflater.inflate(R.layout.activity_info_qa_list, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);

        backbutton.setVisibility(View.INVISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.title_auditorium);

        Button messageButton = (Button) vg.findViewById(R.id.button_message);
        messageButton.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_add_message);
        drawable.setBounds(0, 0, 100, 100);
        messageButton.setCompoundDrawables(null, null, drawable, null);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                changeFragment(R.id.checkmainpage,new MessageListFragment());
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mTask = new CheckFlowListTask(token);
        mTask.execute((Void) null);
        return view;


//        recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerviewinfo);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        appGlobals = (AppGlobals) getActivity().getApplication();
//
//        mCheckFlowListTask = new CheckFlowListTask(token);
//        mCheckFlowListTask.execute((Void) null);
//        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();

    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckFlowListTask extends AsyncTask<Void, Void, List> {

        private final String mToken;

        CheckFlowListTask(String token) {
            mToken = token;
        }

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            checkFlowList = new ArrayList<RecyclerItem>();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS, mToken);
                if (obj != null) {
                    try {
                        JSONArray flowArr = obj.getJSONArray("detail");
                        for (int i = 0; i < flowArr.length(); i++) {
                            RecyclerItem recyclerItem = new RecyclerItem();
                            recyclerItem.setId(flowArr.getJSONObject(i).getString("_id"));
                            recyclerItem.setName(flowArr.getJSONObject(i).getString("title"));
                            recyclerItem.setImage(R.drawable.ic_dashboard_black_24dp);
                            checkFlowList.add(recyclerItem);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            return checkFlowList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                recyclerAdapter = new RecyclerQaListAdapter(result, appGlobals);
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
