package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.util.HttpConnections;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.regex.Pattern;


public class PreviewOfficeOnline extends AppCompatActivity {

    String documentURL;
    private WebView webView;
    DocumentPreviewTask mTask;
    PDFView pdfView;
    Uri documentURI;

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
        documentURL = getIntent.getStringExtra("documentURL");

        webView = (WebView) findViewById(R.id.officeonline);
        pdfView = (PDFView) findViewById(R.id.pdfView);

        boolean resultOffice = Pattern.compile(regOffice).matcher(documentURL).find();
        boolean resultPdf = Pattern.compile(regPdf).matcher(documentURL).find();
        if (resultOffice) {

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
                    if (error.getPrimaryError() == android.net.http.SslError.SSL_INVALID) {// 校验过程遇到了bug
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

        } else if (resultPdf) {
            pdfView.setVisibility(View.VISIBLE);
            mTask = new DocumentPreviewTask();
            mTask.execute((Void) null);
        }

    }

    public class DocumentPreviewTask extends AsyncTask<Void, Void, InputStream> {

        @Override
        protected InputStream doInBackground(Void... params) {
            InputStream is = null;
            try {
                HttpConnections httpConnections = new HttpConnections(PreviewOfficeOnline.this);
                is = httpConnections.httpsGetPDFStream(documentURL);

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
            return is;
        }

        @Override
        protected void onPostExecute(final InputStream stream) {
            pdfView.fromStream(stream)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {

                        }
                    })
                    .onPageScroll(new OnPageScrollListener() {
                        @Override
                        public void onPageScrolled(int page, float positionOffset) {

                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(getApplicationContext(), "加载失败请重试", Toast.LENGTH_SHORT).show();
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .load();


        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }

}
