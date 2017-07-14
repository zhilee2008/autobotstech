package com.autobotstech.cyzk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivity;
import com.autobotstech.cyzk.activity.CheckFlowListDetailActivity;
import com.autobotstech.cyzk.model.RecyclerItem;

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
                CheckFlowListDetailActivity checkFlowDetailActivity = new CheckFlowListDetailActivity();
//                Bundle bundle = new Bundle();
//                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
//                checkFlowDetailActivity.setArguments(bundle);
                appGlobals.setCurrentFlowId(mRecyclerList.get(holder.getAdapterPosition()).getId());
                CheckActivity.changeFragment(R.id.checkmainpage, checkFlowDetailActivity);
            }
        });


        return holder;
    }


}
