package com.autobotstech.cyzk.activity;

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
import android.widget.TextView;

import com.autobotstech.cyzk.AppGlobals;
import com.autobotstech.cyzk.R;
import com.autobotstech.cyzk.adapter.stickygridheaders.StickyGridAdapter;
import com.autobotstech.cyzk.model.StructureGridItem;
import com.autobotstech.cyzk.util.Utils;

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

        appGlobals = (AppGlobals) getActivity().getApplication();

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


        TextView titlebar = (TextView) vg.findViewById(R.id.text_title);
        titlebar.setText(R.string.structure);

        View view = inflater.inflate(R.layout.activity_check_structure, container, false);

        GridView mGridView = (GridView) view.findViewById(R.id.sgv);

        List<StructureGridItem> nonHeaderIdList = new ArrayList<StructureGridItem>();

        JSONObject structOBJ = Utils.readJSONFromFile(getContext(), "structure.json");
        if (structOBJ != null) {
            try {
                JSONArray structArr = structOBJ.getJSONArray("structure");
                for (int i = 0; i < structArr.length(); i++) {
                    StructureGridItem mGridItem = new StructureGridItem(structArr.getJSONObject(i).getString("id"), structArr.getJSONObject(i).getString("name"), Utils.getImageID(this.getContext(), structArr.getJSONObject(i).getString("img")), structArr.getJSONObject(i).getString("parent"), structArr.getJSONObject(i).getJSONArray("standar"), i);
                    nonHeaderIdList.add(mGridItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        List<StructureGridItem> hasHeaderIdList = generateHeaderId(nonHeaderIdList);
        mGridView.setAdapter(new StickyGridAdapter(getContext(), hasHeaderIdList, mGridView, appGlobals));


        return view;
    }

    private List<StructureGridItem> generateHeaderId(List<StructureGridItem> nonHeaderIdList) {
        Map<String, Integer> mHeaderIdMap = new HashMap<String, Integer>();
        List<StructureGridItem> hasHeaderIdList;

        for (ListIterator<StructureGridItem> it = nonHeaderIdList.listIterator(); it.hasNext(); ) {
            StructureGridItem mGridItem = it.next();
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


