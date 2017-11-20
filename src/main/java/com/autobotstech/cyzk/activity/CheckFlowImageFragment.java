package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.fragment.BaseFragement;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;
import com.autobotstech.cyzk.util.MJavascriptInterface;
import com.autobotstech.cyzk.util.MyWebViewClient;
import com.autobotstech.cyzk.util.StringUtils;
import com.autobotstech.cyzk.util.Utils;
import com.bumptech.glide.Glide;

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


public class CheckFlowImageFragment extends BaseFragement {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private CheckFlowChartTask mTask = null;

    private WebView webView;
    private String htmlbody = "";

    private String[] imageUrls;

    @Override
    protected void initView() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        appGlobals = (AppGlobals) getActivity().getApplication();

        webView = (WebView) mView.findViewById(R.id.flowimage);


        mTask = new CheckFlowChartTask(token);
        mTask.execute((Void) null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_flow_image;
    }

    @Override
    protected void getDataFromServer() {
//        Toast.makeText(mContext, "MessageFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }

    public class CheckFlowChartTask extends AsyncTask<Void, Void, JSONArray> {

        private final String mToken;

        CheckFlowChartTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            JSONArray array = new JSONArray();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());
                List<String> conditionslist = new ArrayList<String>();
                conditionslist.add("businessType=" + appGlobals.getBusinessType());
                conditionslist.add("vehicleType=" + appGlobals.getVehicleType());
                conditionslist.add("carStandard=" + appGlobals.getCarStandard());
                conditionslist.add("useProperty=" + appGlobals.getUseProperty());

                String conditionString = "";
                if (conditionslist.size() > 0) {
                    conditionString = "?" + Utils.join("&", conditionslist);
                }
                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.CHECK_FLOW_CHART + conditionString, mToken);
                if (obj != null) {
                    array = obj.getJSONArray("detail");

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

            return array;
        }

        @Override
        protected void onPostExecute(final JSONArray result) {
            mTask = null;
            List<String> imageSrcList = new ArrayList<String>();
            List<String> imageHtmlList = new ArrayList<String>();
            if (result != null) {
                try {
                    for (int j = 0; j < result.length(); j++) {
                        JSONArray imageArray = result.getJSONObject(j).getJSONArray("chart");
                        for (int i = 0; i < imageArray.length(); i++) {
                            imageSrcList.add(imageArray.getJSONObject(i).getString("url"));
                            imageHtmlList.add("<img src=\"" + imageArray.getJSONObject(i).getString("url") + "\" />");
                        }

                    }

                    htmlbody = Utils.join("<br/>", imageHtmlList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imageUrls = StringUtils.returnImageUrlsFromHtml(htmlbody);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setDatabaseEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);

                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);

                // 设置可以支持缩放
                webView.getSettings().setSupportZoom(true);

//              webView.loadUrl("http://a.mp.uc.cn/article.html?uc_param_str=frdnsnpfvecpntnwprdssskt&client=ucweb&wm_aid=c51bcf6c1553481885da371a16e33dbe&wm_id=482efebe15ed4922a1f24dc42ab654e6&pagetype=share&btifl=100");
                String css = "<style type=\"text/css\"> img {" +
                        "width:100%;" +
                        "height:auto;" +
                        "}" +
                        "body {" +
                        "margin-right:15px;" +
                        "margin-left:15px;" +
                        "margin-top:15px;" +
                        "font-size:45px;" +
                        "}" +
                        "</style>";
                htmlbody = "<html><header>" + css + "</header><body>" + htmlbody + "</body></html>";

                webView.loadDataWithBaseURL(null, htmlbody, "text/html", "utf-8", null);
//                webView.addJavascriptInterface(new MJavascriptInterface(getActivity(),imageSrcList.toArray(new String[imageSrcList.size()])), "imagelistener");
                //点击图片弹出图片
                webView.addJavascriptInterface(new MJavascriptInterface(getActivity(), imageUrls), "imagelistener");
                webView.setWebViewClient(new MyWebViewClient());
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }

    @Override
    public void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getContext()).clearDiskCache();//清理磁盘缓存需要在子线程中执行
            }
        }).start();
        Glide.get(getContext()).clearMemory();//清理内存缓存可以在UI主线程中进行
        super.onDestroy();
    }
}
