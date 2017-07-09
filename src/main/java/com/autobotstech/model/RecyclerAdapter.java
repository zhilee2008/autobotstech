package com.autobotstech.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobotstech.AppGlobals;
import com.autobotstech.activity.R;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    protected List<RecyclerItem> mRecyclerList;
    protected AppGlobals appGlobals;

    public RecyclerAdapter(List<RecyclerItem> usageList,AppGlobals appGlobals){
        this.appGlobals = appGlobals;
        mRecyclerList = usageList;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecyclerItem recyclerItem = mRecyclerList.get(position);
        holder.image.setImageResource(recyclerItem.getImage());
        holder.name.setText(recyclerItem.getName());
        holder.tag.setTag(recyclerItem.getId());
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        LinearLayout tag;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.recycleritemimageview);
            name = (TextView)view.findViewById(R.id.recycleritemtextview);
            tag=(LinearLayout)view.findViewById(R.id.recycleritemtag);
        }
    }


}
