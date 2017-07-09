package com.autobotstech.activity;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.fragment.FlowImageFragment;
import com.autobotstech.activity.fragment.FlowListFragment;

import java.util.ArrayList;


public class CheckFlowActivity extends Fragment{

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton flowlist,flowimage;
    private ArrayList<Fragment> alFragment;

    SharedPreferences sp;
    private String token;

    private AppGlobals appGlobals;

    private void initView(View view){
        viewPager=(ViewPager) view.findViewById(R.id.flowviewpager);
        radioGroup=(RadioGroup) view.findViewById(R.id.flowradiogroup);
        flowlist=(RadioButton) view.findViewById(R.id.rb_flowlist);
        flowimage=(RadioButton) view.findViewById(R.id.rb_flowimage);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_flowlist:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.rb_flowimage:
                        viewPager.setCurrentItem(1,false);
                        break;
                }
            }
        });
    }

    private void initViewPager(){
        FlowListFragment flowListFragment=new FlowListFragment();
        FlowImageFragment flowImageFragment=new FlowImageFragment();
        alFragment=new ArrayList<>();
        alFragment.add(flowListFragment);
        alFragment.add(flowImageFragment);

        FragmentManager fragmentManager= getChildFragmentManager();;
//        if(appGlobals.isChildFragment){
//            fragmentManager = getChildFragmentManager();
//        }else{
//            fragmentManager =getFragmentManager();
//            appGlobals.isChildFragment=true;
//        }

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

        appGlobals = (AppGlobals)getActivity().getApplication();

        View view = inflater.inflate(R.layout.activity_check_flow, container, false);
        ViewGroup vg=(ViewGroup) container.getParent();
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
        backbutton.setText(R.string.usage);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.flow);

        initView(view);
        initViewPager();

        return view;
    }

}


