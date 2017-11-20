package com.autobotstech.cyzk.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.InfoQaDetail;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerQaListAdapter extends RecyclerAdapter {


    public RecyclerQaListAdapter(List<RecyclerItem> lecturehallList, AppGlobals appGlobals) {
        super(lecturehallList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_info_qa_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                InfoQaDetail infoQaDetailFragment = new InfoQaDetail();
                Bundle bundle = new Bundle();
                bundle.putString("detail", mRecyclerList.get(holder.getAdapterPosition()).getId());
                Intent intent = new Intent();
                intent.setClass(view.getContext(), InfoQaDetail.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
//                infoQaDetailFragment.setArguments(bundle);
//                CheckActivityContainer.changeFragment(R.id.checkmainpage, infoQaDetailFragment);
            }
        });


        return holder;
    }


}
