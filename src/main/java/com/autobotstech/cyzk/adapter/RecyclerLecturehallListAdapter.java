package com.autobotstech.cyzk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivity;
import com.autobotstech.cyzk.activity.CheckFlowListDetailActivity;
import com.autobotstech.cyzk.activity.LecturehallDetailFragment;
import com.autobotstech.cyzk.activity.fragment.BaseFragement;
import com.autobotstech.cyzk.activity.fragment.LecturehallFragment;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerLecturehallListAdapter extends RecyclerAdapter {


    public RecyclerLecturehallListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals) {
        super(lecturehallList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lecturehall_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LecturehallDetailFragment lecturehallDetailFragment = new LecturehallDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
                lecturehallDetailFragment.setArguments(bundle);
                CheckActivity.changeFragment(R.id.lecturehallmainpage, lecturehallDetailFragment);
            }
        });


        return holder;
    }


}
