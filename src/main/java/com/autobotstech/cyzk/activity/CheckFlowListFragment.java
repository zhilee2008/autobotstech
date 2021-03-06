package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.fragment.BaseFragement;
import com.autobotstech.cyzk.adapter.RecyclerFlowListAdapter;
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


public class CheckFlowListFragment extends BaseFragement {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckFlowListTask mCheckFlowListTask = null;

    private List<RecyclerItem> checkFlowList;
    RecyclerFlowListAdapter recyclerAdapter;
    RecyclerView recyclerView;
    HttpConnections httpConnections;

    @Override
    protected void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");
        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerviewflow);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        appGlobals = (AppGlobals) getActivity().getApplication();
        try {
            httpConnections = new HttpConnections(getContext());
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

        mCheckFlowListTask = new CheckFlowListTask(token);
        mCheckFlowListTask.execute((Void) null);
//        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_flow_list;
    }

    @Override
    protected void getDataFromServer() {

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
//            LitePal.initialize(getContext().getApplicationContext());
//            LitePal.getDatabase();
            JSONObject obj = new JSONObject();
            checkFlowList = new ArrayList<RecyclerItem>();

            List<String> conditionslist = new ArrayList<String>();
            conditionslist.add("businessType=" + appGlobals.getBusinessType());
            conditionslist.add("vehicleType=" + appGlobals.getVehicleType());
            conditionslist.add("carStandard=" + appGlobals.getCarStandard());
            conditionslist.add("useProperty=" + appGlobals.getUseProperty());

            String conditionString = "";
            if (conditionslist.size() > 0) {
                conditionString = "?" + Utils.join("&", conditionslist);
            }
            obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.CHECK_FLOW + conditionString, mToken);
            if (obj != null) {
                try {
                    JSONArray flowArr = obj.getJSONArray("detail");
                    for (int i = 0; i < flowArr.length(); i++) {
                        RecyclerItem recyclerItem = new RecyclerItem();
                        recyclerItem.setId(flowArr.getJSONObject(i).getString("_id"));
                        recyclerItem.setName(flowArr.getJSONObject(i).getString("inspectItem"));
                        boolean mustCheck = flowArr.getJSONObject(i).getBoolean("mustCheck");
                        String checkCondition = "";
                        checkCondition = mustCheck ? "1" : "0";
//                            checkCondition = mustCheck ? "此项为必查项目" : "存疑时查验此项";
                        recyclerItem.setCheckComment(checkCondition);
//                            conditionCheck
//                            mustCheck
//                            recyclerItem.setImage(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp));

                        checkFlowList.add(recyclerItem);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return checkFlowList;
        }

        @Override
        protected void onPostExecute(final List result) {
            mCheckFlowListTask = null;

            if (result != null) {
                recyclerAdapter = new RecyclerFlowListAdapter(result, appGlobals);
                recyclerView.setAdapter(recyclerAdapter);

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mCheckFlowListTask = null;
//            showProgress(false);
        }
    }
}
