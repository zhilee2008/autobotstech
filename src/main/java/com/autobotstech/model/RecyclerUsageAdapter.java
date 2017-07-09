package com.autobotstech.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.CheckActivity;
import com.autobotstech.activity.CheckFlowActivity;
import com.autobotstech.activity.R;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerUsageAdapter extends RecyclerAdapter {


    public RecyclerUsageAdapter(List<RecyclerItem> usageList, AppGlobals appGlobals) {
        super(usageList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_check_usage_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String usageId = mRecyclerList.get(holder.getAdapterPosition()).getId();
                appGlobals.setUseProperty(usageId);
                CheckActivity.changeFragment(R.id.checkmainpage, new CheckFlowActivity());
//                Log.d("TAG", "+++++++conditions: "+appGlobals.getBusinessType()+":"+appGlobals.getVehicleType()+":"+appGlobals.getCarStandard()+":"+usageId);

            }
        });


        return holder;
    }


}
