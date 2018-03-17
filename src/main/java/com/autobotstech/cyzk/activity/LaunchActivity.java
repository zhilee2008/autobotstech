package com.autobotstech.cyzk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

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

public class LaunchActivity extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;
    private String mobile;
    private String password;
    SharedPreferences sp;
    private String token;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sp.getString("token", "");
        mobile = sp.getString("mobile", "");
        password = sp.getString("password", "");
        name = sp.getString("name", "");
        setContentView(R.layout.activity_launch);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //加载启动图片
        setContentView(R.layout.activity_launch);
//        //后台处理耗时任务
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //耗时任务，比如加载网络数据
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //跳转至 MainActivity
//                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        //结束当前的 Activity
//                        LaunchActivity.this.finish();
//                    }
//                });
//            }
//        }).start();
        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token.equals("")) {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                    finish();
                } else {
                    mAuthTask = new UserLoginTask(mobile, password);
                    mAuthTask.execute((Void) null);
                }

            }
        }, time);

    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mMobile;
        private final String mPassword;

        UserLoginTask(String mobile, String password) {
            mMobile = mobile;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            boolean authenticated = false;
            JSONObject obj = new JSONObject();
            //JSONObject obj = httpConnections.HttpsPostLogin(getApplicationContext(),Constants.URL_PREFIX+ Constants.LOGIN,"");
            try {
                HttpConnections httpConnections = new HttpConnections(getBaseContext());
                obj = httpConnections.httpsPostLogin(Constants.URL_PREFIX + Constants.LOGIN, mMobile, mPassword);

                if (obj != null) {
                    try {
                        if (!"error".equals(obj.get("result"))) {
                            authenticated = true;
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", obj.getJSONObject("detail").getString("token"));
//                            editor.putString("mobile", obj.getJSONObject("detail").getJSONObject("user").getString("mobile"));
//                            editor.putString("password", obj.getJSONObject("detail").getJSONObject("user").getString("password"));
                            editor.putString("mobile", mMobile);
                            editor.putString("password", mPassword);
                            editor.putString("name", obj.getJSONObject("detail").getJSONObject("user").getString("name"));
                            editor.commit();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
            }

            return authenticated;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(LaunchActivity.this, MainActivity.class);
                //bundle.putSerializable("role", msg.getData().getString("role"));
                //intent.putExtras(bundle);
                startActivity(intent);

            } else {
                Intent intent = new Intent();
                intent.setClass(LaunchActivity.this, LoginActivity.class);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token", "");
                editor.putString("mobile", "");
                editor.putString("password", "");
                editor.putString("name", "");
                editor.commit();
            }
            finish();

        }

    }
}
