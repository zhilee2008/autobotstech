package com.autobotstech.cyzk.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.EmptyActivity;
import com.autobotstech.cyzk.activity.PreviewOfficeOnline;
import com.autobotstech.cyzk.activity.PreviewVideo;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lecturehall_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        LinearLayout recycleritem = (LinearLayout) view.findViewById(R.id.recycleritemtag);
        recycleritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String filePath = mRecyclerList.get(holder.getAdapterPosition()).getFilePath();
                if(filePath==null){
//                    Toast.makeText(view.getContext(),"当先条目无内容",Toast.LENGTH_SHORT).show();
//                    return;
                    Intent intent = new Intent(view.getContext(), EmptyActivity.class);
                    view.getContext().startActivity(intent);
                }else{
                    if(filePath.indexOf(".PDF")>=0
                            || filePath.indexOf(".pdf")>=0
                            || filePath.indexOf(".ppt")>=0
                            || filePath.indexOf(".PPT")>=0
                            || filePath.indexOf(".PPTX")>=0
                            || filePath.indexOf(".pptx")>=0){
                        Intent intent = new Intent(view.getContext(), PreviewOfficeOnline.class);
                        intent.putExtra("documentURL", filePath);
                        view.getContext().startActivity(intent);
                    }else{
                        Intent intent = new Intent(view.getContext(), PreviewVideo.class);
                        intent.putExtra("documentURL", filePath);
                        view.getContext().startActivity(intent);

                    }
                }

            }
        });


        return holder;
    }


}
