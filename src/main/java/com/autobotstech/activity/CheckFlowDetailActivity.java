package com.autobotstech.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.fragment.FlowDetailGistFragment;
import com.autobotstech.activity.fragment.FlowDetailMeasureFragment;
import com.autobotstech.activity.fragment.FlowDetailMethodFragment;
import com.autobotstech.activity.fragment.FlowImageFragment;
import com.autobotstech.activity.fragment.FlowListFragment;

import java.util.ArrayList;


public class CheckFlowDetailActivity extends Fragment {

    private AppGlobals appGlobals;

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton flowmethod,flowmeasure,flowgist;
    private ArrayList<Fragment> alFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_check_flow_list_detail, container, false);
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
        backbutton.setText(R.string.flow);
        backbutton.setVisibility(View.VISIBLE);

        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.flowdetail);

        initView(view);
        initViewPager();


//        WebView webView = (WebView) view.findViewById(R.id.checkflowdetail);
//
//        webView.loadDataWithBaseURL(null,sb.toString(), "text/html", "utf-8", null);

        return view;
    }

    private void initView(View view){
        viewPager=(ViewPager) view.findViewById(R.id.flowdetailviewpager);
        radioGroup=(RadioGroup) view.findViewById(R.id.detailradiogroup);
        flowmethod=(RadioButton) view.findViewById(R.id.rb_method);
        flowmeasure=(RadioButton) view.findViewById(R.id.rb_measure);
        flowgist=(RadioButton) view.findViewById(R.id.rb_gist);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_method:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.rb_measure:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_gist:
                        viewPager.setCurrentItem(2,false);
                        break;
                }
            }
        });
    }

    private void initViewPager(){
        FlowDetailMethodFragment flowDetailMethodFragment=new FlowDetailMethodFragment();
        FlowDetailMeasureFragment flowDetailMeasureFragment=new FlowDetailMeasureFragment();
        FlowDetailGistFragment flowDetailGistFragment=new FlowDetailGistFragment();
        alFragment=new ArrayList<>();
        alFragment.add(flowDetailMethodFragment);
        alFragment.add(flowDetailMeasureFragment);
        alFragment.add(flowDetailGistFragment);

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


}


