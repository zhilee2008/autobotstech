package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class LecturehallDetailFragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private LecturehallDetailTask mTask = null;

    private WebView webView;
    private String htmlbody = "";

    private String[] imageUrls;

    private String lecturehallId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        lecturehallId = getArguments().getString("detail");

        View view = inflater.inflate(R.layout.activity_lecturehall_detail, container, false);
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
        backbutton.setText(R.string.change_finished);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.title_auditorium);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


        webView = (WebView) view.findViewById(R.id.lecturehalldetail);

        mTask = new LecturehallDetailTask(token);
        mTask.execute((Void) null);

        return view;
    }


    public class LecturehallDetailTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mToken;

        LecturehallDetailTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.LECTUREHALL_DETAIL + lecturehallId, mToken);
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
                try {
                    String title = result.getString("title");
                    String keyword = result.getString("keyword");
                    String description = result.getString("description");
                    String video = result.getString("video");
                    String videoHtml = "<video width=\"305\" height=\"305\" controls=\"controls\" preload=\"none\"  >" +
                            "<source src=\"" + video + "\" type=\"video/mp4\">" +
                            "</video>";

                    htmlbody = title+keyword+description+videoHtml;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAppCacheEnabled(true);
                webView.getSettings().setDatabaseEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);

                webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                webView.getSettings().setUseWideViewPort(true);

//                WebSettings settings = webView.getSettings();
//                settings.setUseWideViewPort(true);
//                settings.setLoadWithOverviewMode(true);
//                settings.setTextSize(WebSettings.TextSize.LARGEST);
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

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
