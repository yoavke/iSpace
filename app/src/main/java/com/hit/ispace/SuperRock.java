package com.hit.ispace;

import android.util.Log;

public class SuperRock extends Obstacle implements IDestroyable{

    private static final String TAG = SuperRock.class.getSimpleName();

    Point topLeft;
    Point bottomRight;

    private boolean isDestroyable = false;

    //TODO image to point to the path of the image
    String image = "";

    public SuperRock(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public boolean destroy() {
        return this.isDestroyable;
    }

    @Override
    public void setCoordinates(Point topLeft, Point BottomRight) {
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
