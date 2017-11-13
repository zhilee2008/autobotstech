package com.autobotstech.cyzk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivityContainer;
import com.autobotstech.cyzk.activity.CheckFlowActivity;
import com.autobotstech.cyzk.model.RecyclerItem;

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
                CheckActivityContainer.changeFragment(R.id.checkmainpage, new CheckFlowActivity());
//                Log.d("TAG", "+++++++conditions: "+appGlobals.getBusinessType()+":"+appGlobals.getVehicleType()+":"+appGlobals.getCarStandard()+":"+usageId);

            }
        });


        return holder;
    }


}
