package com.autobotstech.cyzk.activity;


import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;


public class InfoQaReply extends AppCompatActivity {
    private AppGlobals appGlobals;
    SharedPreferences sp;
    private String token;
    private String huifucontent = "";

    QaAddTask mTask;

    private String qaId;

    public static int RESULT_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        qaId = bundle.getString("detail");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_reply);

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
        backbutton.setText("");
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) findViewById(R.id.text_title);
        titlebar.setText(R.string.title_huifu);

        Button messageButton = (Button) findViewById(R.id.button_message);
        messageButton.setVisibility(View.VISIBLE);
        messageButton.setText(R.string.save);
//        Drawable drawable = getResources().getDrawable(R.drawable.ic_add_message);
//        drawable.setBounds(0, 0, 100, 100);
        messageButton.setCompoundDrawables(null, null, null, null);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                huifucontent = ((TextView) findViewById(R.id.huifucontent)).getText().toString().trim();
                if (huifucontent.equals("")) {
                    Toast.makeText(InfoQaReply.this, "请输入回复内容", Toast.LENGTH_SHORT).show();
                } else {
                    mTask = new QaAddTask(token);
                    mTask.execute((Void) null);
                }
            }
        });


//        Button save = (Button) findViewById(R.id.submit);
//        save.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                huifucontent = ((TextView) findViewById(R.id.huifucontent)).getText().toString().trim();
//                if (huifucontent.equals("")) {
//                    Toast.makeText(InfoQaReply.this, "请输入回复内容", Toast.LENGTH_SHORT).show();
//                } else {
//                    mTask = new QaAddTask(token);
//                    mTask.execute((Void) null);
//                }
//
//
//
//            }
//        });

    }


    public class QaAddTask extends AsyncTask<Void, Void, String> {

        private final String mToken;

        QaAddTask(String token) {
            mToken = token;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();
            String result = "";

            try {
                HttpConnections httpConnections = new HttpConnections(InfoQaReply.this.getApplicationContext());
                obj = httpConnections.httpsPost(Constants.URL_PREFIX + Constants.FORUMS_ADD_ANSWER + qaId, huifucontent, mToken);

                result = obj.getString("result");
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

            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            mTask = null;
            if ("success".equals(result)) {
                Intent intent = new Intent();
                Toast.makeText(InfoQaReply.this, "回复成功", Toast.LENGTH_SHORT).show();
                intent.putExtra("result", "success");
                setResult(RESULT_CODE, intent);
                finish();

            } else {
                Toast.makeText(InfoQaReply.this, "回复失败", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }

}
