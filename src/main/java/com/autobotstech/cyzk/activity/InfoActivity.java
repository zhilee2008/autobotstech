package com.autobotstech.cyzk.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.autobotstech.cyzk.R;

import java.util.ArrayList;


public class InfoActivity extends Fragment {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton qalist, downloadlist, specialtopiclist;
    private ArrayList<Fragment> alFragment;

    SharedPreferences sp;
    private String token;


    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.infoviewpager);
        radioGroup = (RadioGroup) view.findViewById(R.id.inforadiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_specialtopiclist:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.rb_qalist:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_downloadlist:
                        viewPager.setCurrentItem(2, false);
                        break;
                }
            }
        });
    }

    private void initViewPager() {
        InfoSpecialtopic1List specialtopic1ListFragment = new InfoSpecialtopic1List();
        InfoSpecialtopic2List specialtopic2ListFragment = new InfoSpecialtopic2List();
        InfoSpecialtopic3List specialtopic3ListFragment = new InfoSpecialtopic3List();
        alFragment = new ArrayList<>();
        alFragment.add(specialtopic1ListFragment);
        alFragment.add(specialtopic2ListFragment);
        alFragment.add(specialtopic3ListFragment);

        FragmentManager fragmentManager = getChildFragmentManager();
        ;

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return alFragment.get(position);
            }

            @Override
            public int getCount() {
                return alFragment.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sp.getString("token", "");

        View view = inflater.inflate(R.layout.activity_info, container, false);
        ViewGroup vg = (ViewGroup) container.getParent();

        Button backbutton = (Button) vg.findViewById(R.id.button_backward);

        backbutton.setVisibility(View.INVISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.titlebar_info);

        initView(view);
        initViewPager();

        return view;
    }

}


