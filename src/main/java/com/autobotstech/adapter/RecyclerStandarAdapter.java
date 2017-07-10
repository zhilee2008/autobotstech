package com.autobotstech.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.CheckActivity;
import com.autobotstech.activity.CheckUsageActivity;
import com.autobotstech.activity.R;
import com.autobotstech.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerStandarAdapter extends RecyclerAdapter {

    public RecyclerStandarAdapter(List<RecyclerItem> standarList, AppGlobals appGlobals) {
        super(standarList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_check_standar_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String standarId = mRecyclerList.get(holder.getAdapterPosition()).getId();
                appGlobals.setCarStandard(standarId);
                CheckActivity.changeFragment(R.id.checkmainpage, new CheckUsageActivity());

            }
        });


        return holder;
    }



}
