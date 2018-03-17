package com.autobotstech.cyzk.model;


import android.graphics.drawable.Drawable;

import org.json.JSONArray;

public class StructureGridItem {

    private String id;

    private String name;

    private Drawable image;

    private String parent;

    private int headerId;

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    private String bgcolor;

    public JSONArray getStandar() {
        return standar;
    }

    public void setStandar(JSONArray standar) {
        this.standar = standar;
    }

    private JSONArray standar;

    public StructureGridItem(String id, String name, Drawable image, String parent, JSONArray standar, int headerId, String bgcolor) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
        this.headerId = headerId;
        this.standar = standar;
        this.bgcolor = bgcolor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }


}

