package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
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


public class InfoQaDetail extends AppCompatActivity {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private InfoQaDetailTask mTask = null;

    private WebView qawebView;
    private WebView answerswebView;
    private String htmlbody = "";
    private String answerhtmlbody = "";

    private String[] imageUrls;

    private String qaId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");

//        qaId = getArguments().getString("detail");
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        qaId = bundle.getString("detail");
        setContentView(R.layout.activity_qa_detail_old);
//        View view = inflater.inflate(R.layout.activity_qa_detail, container, false);
//        ViewGroup vg = (ViewGroup) container.getParent();
//        Button backbutton = (Button) vg.findViewById(R.id.button_backward);
//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                new Thread() {
//                    public void run() {
//                        try {
//                            Instrumentation inst = new Instrumentation();
//                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//
//            }
//        });
//        backbutton.setText(R.string.change_finished);
//        backbutton.setVisibility(View.VISIBLE);

//        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
//        titlebar.setText(R.string.title_check_1);



        qawebView = (WebView) findViewById(R.id.qadetail);
        answerswebView = (WebView) findViewById(R.id.answersdetail);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        qawebView.setLayoutManager(linearLayoutManager);

        mTask = new InfoQaDetailTask(token);
        mTask.execute((Void) null);

    }


    public class InfoQaDetailTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mToken;

        InfoQaDetailTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            try {
                HttpConnections httpConnections = new HttpConnections(InfoQaDetail.this.getApplicationContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS_DETAIL + qaId, mToken);
                if (obj != null) {
                    obj = obj.getJSONObject("forum");

                }

//                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS_DETAIL_ANSWERS + qaId, mToken);
//                if (obj != null) {
//                    obj = obj.getJSONObject("forum");
//
//                }

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
                try {
                    String title = "<div><h2>"+result.getString("title")+"</h2></div>";
                    String description = "<div>"+result.getString("question")+"</div>";
                    answerhtmlbody ="";
                    JSONArray answerArray = result.getJSONArray("answer");
                    for(int i=0;i<answerArray.length();i++){
                        String answerperson = "<div>"+answerArray.getJSONObject(i).getString("person")+"</div>";
                        String answerContent = "<div>"+answerArray.getJSONObject(i).getString("question")+"</div><hr/>";
                        answerhtmlbody=answerperson+answerContent+answerhtmlbody;
                    }

                    htmlbody = title+description;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.getSettings().setAppCacheEnabled(true);
//                webView.getSettings().setDatabaseEnabled(true);
//                webView.getSettings().setDomStorageEnabled(true);
//
//                webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//                webView.getSettings().setUseWideViewPort(true);
//
////                WebSettings settings = webView.getSettings();
////                settings.setUseWideViewPort(true);
////                settings.setLoadWithOverviewMode(true);
////                settings.setTextSize(WebSettings.TextSize.LARGEST);
//                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                qawebView.loadDataWithBaseURL(null, htmlbody, "text/html", "utf-8", null);
                answerswebView.loadDataWithBaseURL(null, answerhtmlbody, "text/html", "utf-8", null);

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
