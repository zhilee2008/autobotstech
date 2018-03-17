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


public class ChangePwDFragment extends Fragment {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private ChangePWDTask mTask = null;
    private String newpwdString = "";

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        view = inflater.inflate(R.layout.activity_changepwd, container, false);
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
        Button messageButton = (Button) vg.findViewById(R.id.button_message);
        messageButton.setVisibility(View.INVISIBLE);
        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.changepassword);

        Button changePwDButton = (Button) view.findViewById(R.id.changePWD);

        changePwDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                TextView oldpwd = (TextView) view.findViewById(R.id.oldpwd);

                String oldpwdString = oldpwd.getText().toString().trim();

                TextView newpwd = (TextView) view.findViewById(R.id.newpwd);

                newpwdString = newpwd.getText().toString().trim();

                TextView renewpwd = (TextView) view.findViewById(R.id.renewpwd);

                String renewpwdString = renewpwd.getText().toString().trim();

                String currentpwd = sp.getString("password", "");

                if ("".equals(newpwdString) || "".equals(renewpwdString)) {
                    Toast.makeText(getContext(), "新密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!newpwdString.equals(renewpwdString)) {
                    Toast.makeText(getContext(), "两次输入新密码不一致", Toast.LENGTH_SHORT).show();
                } else if (!currentpwd.equals(oldpwdString)) {
                    Toast.makeText(getContext(), "旧密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    mTask = new ChangePWDTask(token);
                    mTask.execute((Void) null);
                }

            }
        });


        return view;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ChangePWDTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mToken;

        ChangePWDTask(String token) {
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            JSONObject obj = new JSONObject();
            try {
                HttpConnections httpConnections = new HttpConnections(getContext());

                obj = httpConnections.httpsPut(Constants.URL_PREFIX + Constants.CHANGE_PWD, newpwdString, mToken);

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

            return obj;
        }

        @Override
        protected void onPostExecute(final JSONObject result) {
            mTask = null;
            if (result != null) {
                try {
                    if ("success".equals(result.getString("result"))) {
//                        new Thread() {
//                            public void run() {
//                                try {
//                                    Instrumentation inst = new Instrumentation();
//                                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
                        Toast.makeText(getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "密码修改失败请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getContext(), "密码修改失败请联系管理员", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
        }
    }


}
