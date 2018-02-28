package com.autobotstech.cyzk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.model.RecyclerItem;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    protected List<RecyclerItem> mRecyclerList;
    protected AppGlobals appGlobals;

    public RecyclerAdapter(List<RecyclerItem> usageList, AppGlobals appGlobals) {
        this.appGlobals = appGlobals;
        mRecyclerList = usageList;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecyclerItem recyclerItem = mRecyclerList.get(position);
        if (holder.image != null) {
            holder.image.setImageDrawable(recyclerItem.getImage());
        }

        if (holder.name != null) {
            holder.name.setText(recyclerItem.getName());
        }

        if (holder.tag != null) {
            holder.tag.setTag(recyclerItem.getId());
        }

        if (holder.createTime != null) {
            holder.createTime.setText(recyclerItem.getCreateTime());
        }

        if (holder.keyword != null) {
            holder.keyword.setText(recyclerItem.getKeyword());
        }

        if (holder.author != null) {
            holder.author.setText(recyclerItem.getAuthor());
        }

        if (holder.checkComment != null) {
            holder.checkComment.setText(recyclerItem.getCheckComment());
        }


    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        LinearLayout tag;
        TextView createTime;
        TextView keyword;
        TextView author;
        TextView checkComment;

        public ViewHolder(View view) {
            super(view);
            if (null != view.findViewById(R.id.recycleritemimageview)) {
                image = (ImageView) view.findViewById(R.id.recycleritemimageview);
            }

            if (null != view.findViewById(R.id.recycleritemtextview)) {
                name = (TextView) view.findViewById(R.id.recycleritemtextview);
            }

            if (null != view.findViewById(R.id.recycleritemtag)) {
                tag = (LinearLayout) view.findViewById(R.id.recycleritemtag);
            }

            if (null != view.findViewById(R.id.recycleritemcreatetime)) {
                createTime = (TextView) view.findViewById(R.id.recycleritemcreatetime);
            }

            if (null != view.findViewById(R.id.recycleritemkeyword)) {
                keyword = (TextView) view.findViewById(R.id.recycleritemkeyword);
            }

            if (null != view.findViewById(R.id.recycleritemauthor)) {
                author = (TextView) view.findViewById(R.id.recycleritemauthor);
            }

            if (null != view.findViewById(R.id.checkComment)) {
                checkComment = (TextView) view.findViewById(R.id.checkComment);
            }

        }
    }


}
