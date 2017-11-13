package com.autobotstech.cyzk.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.CheckActivityContainer;
import com.autobotstech.cyzk.activity.InfoSpecial1Detail;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerYLJXListAdapter extends RecyclerAdapter {


    public RecyclerYLJXListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals) {
        super(lecturehallList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_info_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                InfoSpecial1Detail infoSpecialDetailFragment = new InfoSpecial1Detail();
                Bundle bundle = new Bundle();
                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
                infoSpecialDetailFragment.setArguments(bundle);

                CheckActivityContainer.changeFragment(R.id.checkmainpage, infoSpecialDetailFragment);

            }
        });


        return holder;
    }


}
