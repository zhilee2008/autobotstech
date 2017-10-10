package com.autobotstech.cyzk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivity;
import com.autobotstech.cyzk.activity.CheckActivityContainer;
import com.autobotstech.cyzk.activity.LecturehallDetail;
import com.autobotstech.cyzk.activity.MessageDetail;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerMessageListAdapter extends RecyclerAdapter {


    public RecyclerMessageListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals) {
        super(lecturehallList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MessageDetail messageDetailFragment = new MessageDetail();
                Bundle bundle = new Bundle();
                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
                messageDetailFragment.setArguments(bundle);
                CheckActivityContainer.changeFragment(R.id.checkmainpage, messageDetailFragment);
            }
        });


        return holder;
    }



}
