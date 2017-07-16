package com.autobotstech.cyzk.activity.fragment;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckFlowListFragment;
import com.autobotstech.cyzk.adapter.RecyclerFlowListAdapter;
import com.autobotstech.cyzk.adapter.RecyclerLecturehallListAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;
import com.autobotstech.cyzk.util.Utils;

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


public class LecturehallFragment extends BaseFragement {

    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckLecturehallListTask mTask = null;

    private List<RecyclerItem> lecturehallList;
    RecyclerLecturehallListAdapter recyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        TextView titlebar = (TextView) mView.findViewById(R.id.text_title);
        titlebar.setText(R.string.title_auditorium);

        recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerviewlecturehall);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mTask = new CheckLecturehallListTask(token);
        mTask.execute((Void) null);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lecturehall;
    }

    @Override
    protected void getDataFromServer() {
//        Toast.makeText(mContext, "PublishFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckLecturehallListTask extends AsyncTask<Void, Void, List> {

        private final String mToken;

        CheckLecturehallListTask(String token) {
            mToken = token;
        }

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
//            LitePal.initialize(getContext().getApplicationContext());
//            LitePal.getDatabase();
            JSONObject obj = new JSONObject();
            lecturehallList = new ArrayList<RecyclerItem>();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX+Constants.LECTUREHALL,mToken);
                if (obj != null) {
                    try {
                        JSONArray flowArr = obj.getJSONArray("detail");
                        for (int i = 0; i < flowArr.length(); i++) {
                            RecyclerItem recyclerItem = new RecyclerItem();
                            recyclerItem.setId(flowArr.getJSONObject(i).getString("_id"));
                            recyclerItem.setName(flowArr.getJSONObject(i).getString("title"));
                            recyclerItem.setImage(R.drawable.ic_dashboard_black_24dp);
                            lecturehallList.add(recyclerItem);

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

            return lecturehallList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result!=null) {
                recyclerAdapter = new RecyclerLecturehallListAdapter(result,appGlobals);
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
