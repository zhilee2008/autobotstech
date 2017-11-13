package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.fragment.BaseFragement;
import com.autobotstech.cyzk.adapter.RecyclerDownloadListAdapter;
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


public class InfoDownloadListFragment extends BaseFragement {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private DownloadListTask mTask = null;

    private List<RecyclerItem> downloadList;
    RecyclerDownloadListAdapter recyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");
        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerviewdownload);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        appGlobals = (AppGlobals) getActivity().getApplication();

        mTask = new DownloadListTask(token);
        mTask.execute((Void) null);
//        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_download_list;
    }

    @Override
    protected void getDataFromServer() {

    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class DownloadListTask extends AsyncTask<Void, Void, List> {

        private final String mToken;

        DownloadListTask(String token) {
            mToken = token;
        }

        @Override
        protected List doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            JSONObject obj = new JSONObject();
            downloadList = new ArrayList<RecyclerItem>();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());
                List<String> conditionslist = new ArrayList<String>();

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.TECHSUPPORTS, mToken);
                if (obj != null) {
                    try {
                        JSONArray flowArr = obj.getJSONArray("detail");
                        for (int i = 0; i < flowArr.length(); i++) {
                            RecyclerItem recyclerItem = new RecyclerItem();
                            recyclerItem.setId(flowArr.getJSONObject(i).getString("_id"));
                            recyclerItem.setName(flowArr.getJSONObject(i).getString("originalFileName"));
                            recyclerItem.setFilePath(flowArr.getJSONObject(i).getString("file"));
//                            recyclerItem.setName(flowArr.getJSONObject(i).getString("file"));
                            recyclerItem.setImage(R.drawable.ic_dashboard_black_24dp);
                            downloadList.add(recyclerItem);

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

            return downloadList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mTask = null;

            if (result != null) {
                recyclerAdapter = new RecyclerDownloadListAdapter(result, appGlobals);
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
