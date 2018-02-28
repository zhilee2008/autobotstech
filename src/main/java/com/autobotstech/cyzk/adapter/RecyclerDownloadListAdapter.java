package com.autobotstech.cyzk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.activity.PreviewOfficeOnline;
import com.autobotstech.cyzk.model.RecyclerItem;
import com.autobotstech.cyzk.util.FileUtils;

import java.util.List;

/**
 * Created by zhi on 06/07/2017.
 */

public class RecyclerDownloadListAdapter extends RecyclerAdapter {


    public RecyclerDownloadListAdapter(List<RecyclerItem> flowList, AppGlobals appGlobals) {
        super(flowList, appGlobals);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_download_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

//        ImageView recycleritem = (ImageView) view.findViewById(R.id.downloadbutton);
//
//        recycleritem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                String fileName = mRecyclerList.get(holder.getAdapterPosition()).getName();
//                String filePath = mRecyclerList.get(holder.getAdapterPosition()).getFilePath();
//                FileUtils.downloadFile(view.getContext(), filePath, fileName, new FileUtils.SaveResultCallback() {
//                    @Override
//                    public void onSavedSuccess() {
//                        Activity activityDownload = (Activity) view.getContext();
//                        activityDownload.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(view.getContext(), "保存成功", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onSavedFailed() {
//                        Activity activityDownload = (Activity) view.getContext();
//                        activityDownload.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(view.getContext(), "保存失败", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//            }
//        });

        ImageView previewbutton = (ImageView) view.findViewById(R.id.previewbutton);

        previewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String fileName = mRecyclerList.get(holder.getAdapterPosition()).getName();
                String filePath = mRecyclerList.get(holder.getAdapterPosition()).getFilePath();
                Intent intent = new Intent(view.getContext(), PreviewOfficeOnline.class);
                intent.putExtra("documentURL", filePath);
                view.getContext().startActivity(intent);

            }
        });


        return holder;
    }


}
