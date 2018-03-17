package com.autobotstech.cyzk.notification;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.WebView;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.LoginActivity;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import cn.jpush.android.api.JPushInterface;

public class MessageActivity extends Activity {

    SharedPreferences sp;
    private String token;
    private MessageDetailTask mTask = null;
    String messageId;
    private WebView messagewebView;
    private String htmlbody = "";

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //System.exit(0);
        if (!isRunningForeground(getApplicationContext())) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            this.startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");

        setContentView(R.layout.activity_message_detail);

        Bundle bundle = getIntent().getExtras();
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String title = bundle.getString(JPushInterface.EXTRA_ALERT);
        //{id:id}
        String extraJSON = bundle.getString(JPushInterface.EXTRA_EXTRA);
        try {
            JSONObject extraJSONObject = new JSONObject(extraJSON);
            messageId = extraJSONObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        Button closebutton = (Button) findViewById(R.id.closemessage);
//        closebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
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

        messagewebView = (WebView) findViewById(R.id.messagedetail);

        mTask = new MessageDetailTask(token);
        mTask.execute((Void) null);


    }

    public class MessageDetailTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mToken;

        MessageDetailTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            JSONObject obj = new JSONObject();

            try {
                HttpConnections httpConnections = new HttpConnections(MessageActivity.this.getApplicationContext());

                obj = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.EXPERIENCES_DETAIL + messageId, mToken);
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
                    String title = "<div><h2>" + result.getString("title") + "</h2></div>";
                    String description = "<div>" + result.getString("description") + "</div>";
                    htmlbody = title + description;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                messagewebView.loadDataWithBaseURL(null, htmlbody, "text/html", "utf-8", null);

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
//            showProgress(false);
        }
    }


    //判断是否在前台执行
    private boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

}
