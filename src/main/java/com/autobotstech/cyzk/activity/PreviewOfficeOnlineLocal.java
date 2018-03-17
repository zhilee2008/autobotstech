package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.autobotstech.cyzk.R;


public class PreviewOfficeOnlineLocal extends AppCompatActivity {

    String documentURL;
    private WebView webView;

//    .doc   .xls  .ppt .DOC
//.docx   .xlsx   .pptx

    String regOffice = ".doc|.xls|.ppt|.DOC|.XLS|.PPT|.docx|.xlsx|.pptx|.DOCX|.XLSX|.PPTX";
    String regPdf = ".pdf|.PDF";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview_officeonline);

        Button backbutton = (Button) findViewById(R.id.button_backward);
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

        TextView titlebar = (TextView) findViewById(R.id.text_title);
        titlebar.setText(R.string.preview);

        Intent getIntent = getIntent();
        documentURL = "file:///android_asset/yjbg.doc";
//        file:///android_asset/
        webView = (WebView) findViewById(R.id.officeonline);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (error.getPrimaryError() == SslError.SSL_INVALID) {// 校验过程遇到了bug
                    handler.proceed();
                } else {
                    handler.cancel();
                }
            }
        });
// webview必须设置支持Javascript才可打开
        webView.getSettings().setJavaScriptEnabled(true);
// 设置此属性,可任意比例缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=" + documentURL);
        webView.setVisibility(View.VISIBLE);


    }

}
