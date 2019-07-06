package com.hit.ispace;

import android.graphics.Bitmap;
import android.util.Log;

public class Rock extends Obstacle implements IDestroyable{

    private static final String TAG = Obstacle.class.getSimpleName();

    private Point topLeft;
    private Point bottomRight;
    private Bitmap bitmapSrc;
    private boolean hit;

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

    @Override
    public String sayMyName() {
        return "Rock";
    }

    public Rock() {
        this.destroyAnimation = CSettings.Animation.ROCK_ANIMATION;
    }

    public Rock(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.hit = false;
    }

    @Override
    public boolean destroy() {
        //TODO make the destruction of all destroyable objects

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
