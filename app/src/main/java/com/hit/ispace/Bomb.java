package com.hit.ispace;

import android.graphics.Bitmap;
import android.util.Log;

public class Bomb implements IElement {

    private static final String TAG = Bomb.class.getSimpleName();
    private Point topLeft;
    private Point bottomRight;

    private Bitmap bitmapSrc;

    private boolean hit;

    @Override
    public String sayMyName() {
        return "Bomb";
    }

    @Override
    public void setHit() {
        this.hit = true;
    }

    @Override
    public boolean getHit() {
        return this.hit;
    }

    public Bomb(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.hit = false;
    }

    @Override
    public void setBitmapSrc(Bitmap bitmap) {
        this.bitmapSrc = bitmap;
    }

    @Override
    public Bitmap getBitmapSrc() {
        return this.bitmapSrc;
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
}
