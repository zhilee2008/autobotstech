package com.autobotstech.cyzk.model;


import org.json.JSONArray;

public class StructureGridItem {

    private String id;

    private String name;

    private int image;

    private String parent;

    private int headerId;

    public JSONArray getStandar() {
        return standar;
    }

    public void setStandar(JSONArray standar) {
        this.standar = standar;
    }

    private JSONArray standar;

    public StructureGridItem(String id, String name, int image, String parent, JSONArray standar, int headerId) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
        this.headerId = headerId;
        this.standar = standar;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

