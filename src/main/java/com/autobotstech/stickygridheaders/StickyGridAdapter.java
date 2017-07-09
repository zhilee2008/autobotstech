package com.autobotstech.stickygridheaders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.CheckActivity;
import com.autobotstech.activity.CheckStandarActivity;
import com.autobotstech.activity.R;
import com.autobotstech.model.StructureGridItem;

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
        ImageView iv = (ImageView)convertView.findViewById(R.id.stickyitemimage);
        iv.setImageResource(R.drawable.ic_dashboard_black_24dp);
        TextView tv = (TextView) convertView.findViewById(R.id.stickyitemtext);
        tv.setText(hasHeaderIdList.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appGlobals.setVehicleType(hasHeaderIdList.get(position).getId());

                CheckStandarActivity checkStandarActivityFragment = new CheckStandarActivity();
                Bundle bundle = new Bundle();
                bundle.putString("standar", hasHeaderIdList.get(position).getStandar().toString());
                checkStandarActivityFragment.setArguments(bundle);
                CheckActivity.changeFragment(R.id.checkmainpage, checkStandarActivityFragment);
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