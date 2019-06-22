package com.hit.ispace;


public final class Aircraft {
    private String name;
    private String image;
    private int xCoordinate;
    private int distanceCovered;

    public Aircraft() {
        //TODO: get my collection of aircraft
        // from DB and set the name and image

         this.xCoordinate = 1;
         this.distanceCovered = 0;
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

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(int distanceCovered) {
        this.distanceCovered = distanceCovered;
    }
}
