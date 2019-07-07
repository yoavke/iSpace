package com.hit.ispace;

import android.graphics.Bitmap;
import android.util.Log;

public class SuperRock extends Obstacle implements IDestroyable{

    private static final String TAG = SuperRock.class.getSimpleName();

    //num of coins superrock reduces from current level bank
    protected static final int POWER = 3;

    private Point topLeft;
    private Point bottomRight;
    private boolean isDestroyable = false;
    private boolean hit;
    private Bitmap bitmapSrc;

    @Override
    public void setHit() {
        this.hit=true;
    }

    @Override
    public boolean getHit() {
        return this.hit;
    }

    @Override
    public void setBitmapSrc(Bitmap bitmap) {
        this.bitmapSrc = bitmap;
    }

    @Override
    public Bitmap getBitmapSrc() {
        return this.bitmapSrc;
    }

    public SuperRock(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.hit = false;
    }

    @Override
    public String sayMyName() {
        return "SuperRock";
    }

    @Override
    public boolean destroy() {
        return this.isDestroyable;
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
