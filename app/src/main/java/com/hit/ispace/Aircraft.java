package com.hit.ispace;


public final class Aircraft {
    private String name;
    private String image;

    public Aircraft() {
        //TODO: get my collection of aircraft
        // from DB and set the name and image
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
