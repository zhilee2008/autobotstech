package com.autobotstech.activity;


public class GridItem {

    private String id;

    private String name;

    private int image;

    private String parent;

    private int headerId;

    public GridItem(String id,String name, int image,String parent,int headerId) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
        this.headerId = headerId;
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

