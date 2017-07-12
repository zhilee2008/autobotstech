package com.autobotstech.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.fragment.BaseFragement;
import com.autobotstech.util.Constants;
import com.autobotstech.util.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;


public class CheckFlowListDetailMeasureFragment extends BaseFragement {

    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckFlowOBJTask mTask = null;

    private WebView webView;
    private String htmlbody = "";
    private String currentFlowId="";

    @Override
    protected void initView() {

        appGlobals = (AppGlobals) getActivity().getApplication();
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        webView = (WebView) mView.findViewById(R.id.flowdetailmeasure);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setTextSize(WebSettings.TextSize.LARGEST);

        mTask = new CheckFlowOBJTask(token);
        mTask.execute((Void) null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_flow_list_detail_measure;
    }

    @Override
    protected void getDataFromServer() {
//        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CheckFlowOBJTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mToken;

        CheckFlowOBJTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.CHECK_FLOW_BY_ID +"/"+ appGlobals.getCurrentFlowId(), mToken);
                if (obj != null) {
                    obj = obj.getJSONObject("detail");

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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return obj;
        }

        @Override
        protected void onPostExecute(final JSONObject result) {
            mTask = null;

            if (result != null) {
                String htmlbody = "";
                try {
                    htmlbody = result.getString("measure");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                webView.loadDataWithBaseURL(null, htmlbody, "text/html", "utf-8", null);
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
