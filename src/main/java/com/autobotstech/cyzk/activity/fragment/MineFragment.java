package com.autobotstech.cyzk.activity.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.LecturehallListFragment;
import com.autobotstech.cyzk.activity.LoginActivity;
import com.autobotstech.cyzk.activity.MineActivity;
import com.autobotstech.cyzk.util.Utils;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class MineFragment extends BaseFragement {



    @Override
    protected void initView() {

        initFragment(R.id.minemainpage,new MineActivity());


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void getDataFromServer() {


    }



}
