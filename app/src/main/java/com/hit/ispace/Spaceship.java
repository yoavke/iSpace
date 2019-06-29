package com.hit.ispace;

import android.graphics.Bitmap;

public class Spaceship implements IElement {

    Point topLeft;
    Point bottomRight;
    Bitmap bitmapSrc;
    int spaceshipWidth;
    int SpaceshipHeight;

    public Spaceship() {
        this.spaceshipWidth = CSettings.Dimension.SPACESHIP_SIZE;
        this.SpaceshipHeight = CSettings.Dimension.SPACESHIP_SIZE;
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
