package com.autobotstech.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.autobotstech.activity.fragment.AuditoriumFragment;
import com.autobotstech.activity.fragment.CheckFragment;
import com.autobotstech.activity.fragment.InfoFragment;
import com.autobotstech.activity.fragment.MineFragment;
import com.autobotstech.activity.widget.NoSlidingViewPaper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoSlidingViewPaper mViewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_check:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_info:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_auditorium:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_mine:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        /*初始化显示内容*/
        mViewPager = (NoSlidingViewPaper) findViewById(R.id.vp_main_container);
        final ArrayList<Fragment> fgLists = new ArrayList<>(4);
        fgLists.add(new CheckFragment());
        fgLists.add(new InfoFragment());
        fgLists.add(new AuditoriumFragment());
        fgLists.add(new MineFragment());
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3); //预加载剩下两页

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*给底部导航栏菜单项添加点击事件*/
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
