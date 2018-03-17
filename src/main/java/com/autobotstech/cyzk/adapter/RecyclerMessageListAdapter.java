package com.autobotstech.cyzk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivityContainer;
import com.autobotstech.cyzk.activity.MessageDetailFragment;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerMessageListAdapter extends RecyclerAdapter {

    boolean isinmine;

    public RecyclerMessageListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals) {
        super(lecturehallList, appGlobals);
    }

    public RecyclerMessageListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals, boolean isinmine) {
        super(lecturehallList, appGlobals);
        this.isinmine = isinmine;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MessageDetailFragment messageDetailFragment = new MessageDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
                messageDetailFragment.setArguments(bundle);

                if (isinmine) {
                    CheckActivityContainer.changeFragment(R.id.minemainpage, messageDetailFragment);
                } else {
                    CheckActivityContainer.changeFragment(R.id.checkmainpage, messageDetailFragment);
                }

            }
        });


        return holder;
    }


}
