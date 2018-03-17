package com.autobotstech.cyzk.activity;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.RecyclerQaAnswerListAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class InfoQaDetail extends AppCompatActivity {
    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;
    private InfoQaDetailTask mTask = null;

    private TextView qContentView;
    private TextView qTitleView;
    private TextView answerCountView;

    private String[] imageUrls;

    private String qaId;

    private List<RecyclerItem> checkFlowList;
    RecyclerQaAnswerListAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Bitmap bitmap = null;
    ImageView recycleritemauthorimage;
    Drawable qimage;


    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        token = sp.getString("token", "");

//        qaId = getArguments().getString("detail");
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        qaId = bundle.getString("detail");

        setContentView(R.layout.activity_qa_detail);

        qTitleView = (TextView) findViewById(R.id.qTitleView);
        qContentView = (TextView) findViewById(R.id.qContentView);
        answerCountView = (TextView) findViewById(R.id.answercount);
        recycleritemauthorimage = (ImageView) findViewById(R.id.recycleritemauthorimage);

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
        Drawable drawable = getResources().getDrawable(R.drawable.ic_add_message);
        drawable.setBounds(0, 0, 100, 100);
        messageButton.setCompoundDrawables(null, null, drawable, null);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("detail", qaId);
                intent.putExtras(bundle);
                intent.setClass(InfoQaDetail.this, InfoQaReply.class);
                startActivityForResult(intent, 1);
//                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewanswer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mTask = new InfoQaDetailTask(token);
        mTask.execute((Void) null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// 当otherActivity中返回数据的时候，会响应此方法
// requestCode和resultCode必须与请求startActivityForResult()和返回setResult()的时候传入的值一致。
        if (requestCode == 1 && resultCode == InfoQaReply.RESULT_CODE) {
            Bundle bundle = data.getExtras();
            String strResult = bundle.getString("result");
//            Log.i(TAG,"onActivityResult: "+ strResult);
//            Toast.makeText(InfoQaDetail.this, strResult, Toast.LENGTH_LONG).show();
            mTask = new InfoQaDetailTask(token);
            mTask.execute((Void) null);
        }
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

            JSONObject objq = new JSONObject();
            JSONObject obja = new JSONObject();
            try {
                HttpConnections httpConnections = new HttpConnections(InfoQaDetail.this.getApplicationContext());

                objq = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS_DETAIL + qaId, mToken);
                if (objq != null) {
                    objq = objq.getJSONObject("forum");
                    boolean hasPortrait = objq.getJSONObject("createPerson").has("portrait");
                    if (!hasPortrait) {
                        qimage = getResources().getDrawable(R.drawable.default_personal);
                    } else {
                        String imageString = objq.getJSONObject("createPerson").getJSONObject("portrait").getString("small");
                        if ("".equals(imageString)) {
                            qimage = getResources().getDrawable(R.drawable.default_personal);
                        } else {
                            InputStream is = httpConnections.httpsGetPDFStream(imageString);
                            bitmap = BitmapFactory.decodeStream(is);
                            if (bitmap == null) {
                                qimage = getResources().getDrawable(R.drawable.default_personal);
                            } else {
                                qimage = new BitmapDrawable(bitmap);
                            }
                        }
                    }
                    obj.put("objq", objq);
                } else {
                    obj.put("objq", null);
                }

                obja = httpConnections.httpsGet(Constants.URL_PREFIX + Constants.FORUMS_DETAIL_ANSWERS + qaId, mToken);
                if (obja != null) {
                    checkFlowList = new ArrayList<RecyclerItem>();
                    try {
                        JSONArray flowArr = obja.getJSONArray("detail");
                        for (int i = 0; i < flowArr.length(); i++) {
                            RecyclerItem recyclerItem = new RecyclerItem();
                            recyclerItem.setName(flowArr.getJSONObject(i).getString("answer"));
                            String createTimeString = flowArr.getJSONObject(i).getString("createTime");
                            Format f = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            String dateString = "";
                            try {
                                date = (Date) f.parseObject(createTimeString);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                dateString = sdf.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            recyclerItem.setCreateTime(dateString);
                            boolean hasPortrait = flowArr.getJSONObject(i).getJSONObject("createPerson").has("portrait");
                            if (!hasPortrait) {
                                recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                            } else {
                                String imageString = flowArr.getJSONObject(i).getJSONObject("createPerson").getJSONObject("portrait").getString("small");
                                if ("".equals(imageString)) {
                                    recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                                } else {
                                    InputStream is = httpConnections.httpsGetPDFStream(imageString);
                                    bitmap = BitmapFactory.decodeStream(is);
                                    if (bitmap == null) {
                                        recyclerItem.setImage(getResources().getDrawable(R.drawable.default_personal));
                                    } else {
                                        Drawable drawable = new BitmapDrawable(bitmap);
                                        recyclerItem.setImage(drawable);
                                    }
                                }
                            }

                            String author = flowArr.getJSONObject(i).getJSONObject("createPerson").getString("name");
                            recyclerItem.setAuthor(author);
                            checkFlowList.add(recyclerItem);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    obj.put("obja", checkFlowList);

                } else {
                    obj.put("obja", null);
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
                    JSONObject objq = result.getJSONObject("objq");
//                    JSONObject obja = result.getJSONObject("obja");

                    if (objq != null) {
                        recycleritemauthorimage.setImageDrawable(qimage);
                        qTitleView.setText(objq.getString("title"));
                        qContentView.setText(objq.getString("question"));

                    }
                    answerCountView.setText("" + checkFlowList.size());

                    recyclerAdapter = new RecyclerQaAnswerListAdapter(checkFlowList, appGlobals);
                    recyclerView.setAdapter(recyclerAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
