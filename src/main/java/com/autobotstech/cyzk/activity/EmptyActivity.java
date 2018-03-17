package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autobotstech.cyzk.R;


public class EmptyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

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

    }

}
