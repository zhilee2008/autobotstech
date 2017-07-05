package com.autobotstech.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.autobotstech.AppGlobals;
import com.autobotstech.stickygridheaders.StickyGridAdapter;
import com.autobotstech.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class CheckStructureActivity extends Fragment {

    private AppGlobals appGlobals;

    LinearLayout view1;
    private List mGirdList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        appGlobals = new AppGlobals();

        appGlobals = new AppGlobals();

        ViewGroup vg = (ViewGroup) container.getParent();
        Button backbutton = (Button) vg.findViewById(R.id.button_backward);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
        backbutton.setText(R.string.business);
        backbutton.setVisibility(View.VISIBLE);

        View view = inflater.inflate(R.layout.stickygrid, container, false);

        GridView mGridView = (GridView) view.findViewById(R.id.sgv);

        List<GridItem> nonHeaderIdList = new ArrayList<GridItem>();

        JSONObject structOBJ = Utils.readJSONFromFile(getContext());
        if (structOBJ != null) {
            try {
                JSONArray structArr = structOBJ.getJSONArray("structure");
                for (int i = 0; i < structArr.length(); i++) {
                    GridItem mGridItem = new GridItem(structArr.getJSONObject(i).getString("id"),structArr.getJSONObject(i).getString("name"),R.drawable.ic_home_black_24dp,structArr.getJSONObject(i).getString("parent"),i);
                    nonHeaderIdList.add(mGridItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        List<GridItem> hasHeaderIdList = generateHeaderId(nonHeaderIdList);
        mGridView.setAdapter(new StickyGridAdapter(getContext(), hasHeaderIdList, mGridView));


        return view;
    }

    private List<GridItem> generateHeaderId(List<GridItem> nonHeaderIdList) {
        Map<String, Integer> mHeaderIdMap = new HashMap<String, Integer>();
        List<GridItem> hasHeaderIdList;

        for (ListIterator<GridItem> it = nonHeaderIdList.listIterator(); it.hasNext(); ) {
            GridItem mGridItem = it.next();
            String parent = mGridItem.getParent();
            if (!mHeaderIdMap.containsKey(parent)) {
                mGridItem.setHeaderId(mGridItem.getHeaderId());
                mHeaderIdMap.put(parent, mGridItem.getHeaderId());
            } else {
                mGridItem.setHeaderId(mHeaderIdMap.get(parent));
            }
        }
        hasHeaderIdList = nonHeaderIdList;

        return hasHeaderIdList;
    }


}


