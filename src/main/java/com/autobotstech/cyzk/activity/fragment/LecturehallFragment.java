package com.autobotstech.cyzk.activity.fragment;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivity;
import com.autobotstech.cyzk.activity.CheckFlowListFragment;
import com.autobotstech.cyzk.activity.LecturehallListFragment;
import com.autobotstech.cyzk.adapter.RecyclerFlowListAdapter;
import com.autobotstech.cyzk.adapter.RecyclerLecturehallListAdapter;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.Constants;
import com.autobotstech.cyzk.util.HttpConnections;
import com.autobotstech.cyzk.util.Utils;

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


public class LecturehallFragment extends BaseFragement {

    private AppGlobals appGlobals;

    SharedPreferences sp;
    private String token;


    @Override
    protected void initView() {

//        TextView titlebar = (TextView) mView.findViewById(R.id.text_title);
//        titlebar.setText(R.string.title_auditorium);
        initFragment(R.id.lecturehallmainpage,new LecturehallListFragment());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lecturehall;
    }

    @Override
    protected void getDataFromServer() {
//        Toast.makeText(mContext, "PublishFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }



}
