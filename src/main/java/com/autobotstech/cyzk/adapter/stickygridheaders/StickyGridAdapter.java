package com.autobotstech.cyzk.adapter.stickygridheaders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivityContainer;
import com.autobotstech.cyzk.activity.CheckStandarActivity;
import com.autobotstech.cyzk.model.StructureGridItem;

import java.util.List;

/**
 * Created by zhi on 04/07/2017.
 */


public class StickyGridAdapter extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {

    private List<StructureGridItem> hasHeaderIdList;
    private LayoutInflater mInflater;
    private GridView mGridView;
    private AppGlobals appGlobals;

    public StickyGridAdapter(Context context, List<StructureGridItem> hasHeaderIdList,
                             GridView mGridView, AppGlobals appGlobals) {
        this.appGlobals = appGlobals;
        mInflater = LayoutInflater.from(context);
        this.mGridView = mGridView;
        this.hasHeaderIdList = hasHeaderIdList;
    }


    @Override
    public int getCount() {
        return hasHeaderIdList.size();
    }

    @Override
    public Object getItem(int position) {
        return hasHeaderIdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.stickygrid_items, parent, false);
        //TODO add click
        ImageView iv = (ImageView) convertView.findViewById(R.id.stickyitemimage);
        iv.setImageDrawable(hasHeaderIdList.get(position).getImage());
        TextView tv = (TextView) convertView.findViewById(R.id.stickyitemtext);
        tv.setText(hasHeaderIdList.get(position).getName());
        LinearLayout lc = (LinearLayout) convertView.findViewById(R.id.stickyitemimagecontainer);
//        lc.setBackgroundColor(Color.parseColor(hasHeaderIdList.get(position).getBgcolor()));
        GradientDrawable drawable = (GradientDrawable) lc.getBackground();
        drawable.setColor(Color.parseColor(hasHeaderIdList.get(position).getBgcolor()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appGlobals.setVehicleType(hasHeaderIdList.get(position).getId());

                CheckStandarActivity checkStandarActivityFragment = new CheckStandarActivity();
                Bundle bundle = new Bundle();
                bundle.putString("standar", hasHeaderIdList.get(position).getStandar().toString());
                checkStandarActivityFragment.setArguments(bundle);
                CheckActivityContainer.changeFragment(R.id.checkmainpage, checkStandarActivityFragment);
            }
        });

        return convertView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.stickygrid_header, parent, false);
        TextView tv = (TextView) convertView.findViewById(R.id.structure_header);
        tv.setText(hasHeaderIdList.get(position).getParent());
        return convertView;
    }

    /**
     * 获取HeaderId, 只要HeaderId不相等就添加一个Header
     */
    @Override
    public long getHeaderId(int position) {
        return hasHeaderIdList.get(position).getHeaderId();
    }


}