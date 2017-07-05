package com.autobotstech.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zhi on 02/07/2017.
 */

public class Utils {

    public static JSONObject readJSONFromFile(Context context) {
        JSONObject jsonOBJ=new JSONObject();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("structure.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            jsonOBJ = new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonOBJ;
    }
}

