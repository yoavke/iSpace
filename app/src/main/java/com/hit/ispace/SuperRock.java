package com.hit.ispace;

import android.graphics.Bitmap;
import android.util.Log;

public class SuperRock extends Obstacle implements IDestroyable{

    private static final String TAG = SuperRock.class.getSimpleName();

    private Point topLeft;
    private Point bottomRight;
    private boolean isDestroyable = false;

    private Bitmap bitmapSrc;

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
