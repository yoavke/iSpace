package com.hit.ispace;

import android.graphics.Bitmap;

public class Spaceship implements IElement {

    private Point topLeft;
    private Point bottomRight;
    private Bitmap bitmapSrc;
    private int spaceshipWidth;
    private int SpaceshipHeight;
    private boolean hit;

    @Override
    public void setHit() {
        this.hit=true;
    }

    @Override
    public boolean getHit() {
        return this.hit;
    }

    public Spaceship() {
        this.spaceshipWidth = CSettings.Dimension.SPACESHIP_SIZE;
        this.SpaceshipHeight = CSettings.Dimension.SPACESHIP_SIZE;
        this.hit = false;
    }

    @Override
    public void setCoordinates(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public Point getLeftTop() {
        return this.topLeft;
    }

    @Override
    public Point getRightBottom() {
        return this.bottomRight;
    }

    @Override
    public String sayMyName() {
        return "Spaceship";
    }

    public Bitmap getBitmapSrc() {
        return bitmapSrc;
    }

    public void setBitmapSrc(Bitmap bitmapSrc) {
        this.bitmapSrc = bitmapSrc;
    }

    public int getSpaceshipWidth() {
        return spaceshipWidth;
    }

    public int getSpaceshipHeight() {
        return SpaceshipHeight;
    }
}
