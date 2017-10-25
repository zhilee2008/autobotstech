package com.autobotstech.cyzk.activity;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.autobotstech.cyzk.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;


public class CheckActivityContainer extends Fragment {

    protected Context mContext;
    protected static FragmentManager fm;
    private RollPagerView mRollViewPager;

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton check,check1,check2;
    private ArrayList<Fragment> alFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fm = getFragmentManager();
        mContext = getContext();
        View view = inflater.inflate(R.layout.activity_check_container, container, false);
        ViewGroup vg = (ViewGroup)container.getParent();
        vg.findViewById(R.id.button_backward).setVisibility(View.INVISIBLE);


        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.title_check);

        Button messageButton = (Button) vg.findViewById(R.id.button_message);
        messageButton.setVisibility(View.VISIBLE);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeFragment(R.id.checkmainpage,new MessageListFragment());
            }
        });

        initView(view);
        initViewPager();

        return view;
    }

    private void initView(View view){
        viewPager=(ViewPager) view.findViewById(R.id.checkviewpager);
        radioGroup=(RadioGroup) view.findViewById(R.id.checkradiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_check:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.rb_check1:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_check2:
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });
    }

    private void initViewPager(){
        CheckActivity checkActivity=new CheckActivity();
        InfoQaList infoQaList=new InfoQaList();
        InfoDownloadListFragment infoDownloadListFragment=new InfoDownloadListFragment();
        InfoSpecialtopic4List nfoSpecialtopic4List=new InfoSpecialtopic4List();
        alFragment=new ArrayList<>();
        alFragment.add(checkActivity);
        alFragment.add(nfoSpecialtopic4List);
        alFragment.add(infoDownloadListFragment);

        FragmentManager fragmentManager= getChildFragmentManager();;

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


    // 切換Fragment 存在于栈中
    public static void changeFragment(int fragemnt,Fragment f){
        changeFragment(fragemnt,f, false);
    }
    // 初始化Fragment(FragmentActivity中呼叫) 不在在于栈中
    public static void initFragment(int fragment,Fragment f){
        changeFragment(fragment,f, true);
    }
    protected static void changeFragment(int fragment,Fragment f, boolean init){
        FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);

        ft.replace(fragment, f);

        if(!init)
            ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

}


