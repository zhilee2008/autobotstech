package com.autobotstech.cyzk.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.util.HttpConnections;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import org.json.JSONObject;

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
    PDFView  pdfView;
    Uri documentURI;

//    .doc   .xls  .ppt .DOC
//.docx   .xlsx   .pptx

    String regOffice = ".doc|.xls|.ppt|.DOC|.XLS|.PPT|.docx|.xlsx|.pptx|.DOCX|.XLSX|.PPTX";
    String regPdf = ".pdf|.PDF";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview_officeonline);

        Intent getIntent = getIntent();
        documentURL = getIntent.getStringExtra("documentURL");

        boolean resultOffice = Pattern.compile(regOffice).matcher(documentURL).find();
        boolean resultPdf = Pattern.compile(regPdf).matcher(documentURL).find();
        if(resultOffice){
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
        }else if(resultPdf){
            pdfView = (PDFView)findViewById(R.id.pdfView);
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
