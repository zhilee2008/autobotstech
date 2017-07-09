package com.autobotstech.model;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.CheckActivity;
import com.autobotstech.activity.CheckFlowActivity;
import com.autobotstech.activity.CheckFlowDetailActivity;
import com.autobotstech.activity.CheckStandarActivity;
import com.autobotstech.activity.CheckUsageActivity;
import com.autobotstech.activity.R;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerFlowListAdapter extends RecyclerAdapter {


    public RecyclerFlowListAdapter(List<RecyclerItem> usageList, AppGlobals appGlobals) {
        super(usageList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_check_flow_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CheckFlowDetailActivity checkFlowDetailActivity = new CheckFlowDetailActivity();
//                Bundle bundle = new Bundle();
//                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
//                checkFlowDetailActivity.setArguments(bundle);
                CheckActivity.changeFragment(R.id.checkmainpage, checkFlowDetailActivity);
            }
        });


        return holder;
    }


}
