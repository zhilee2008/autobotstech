package com.autobotstech.cyzk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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


public class PreviewOfficeOnline extends AppCompatActivity {

    String documentURL;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview_officeonline);

        Intent getIntent = getIntent();
        documentURL = getIntent.getStringExtra("documentURL");
        webView = (WebView) findViewById(R.id.officeonline);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);

            }
        });

// webview必须设置支持Javascript才可打开
        webView.getSettings().setJavaScriptEnabled(true);

// 设置此属性,可任意比例缩放
        webView.getSettings().setUseWideViewPort(true);


        webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=" + documentURL);


    }

}
