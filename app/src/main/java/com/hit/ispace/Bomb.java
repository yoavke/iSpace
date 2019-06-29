package com.hit.ispace;

import android.util.Log;

public class Bomb extends Obstacle implements IDestroyable {

    private static final String TAG = Bomb.class.getSimpleName();
    Point topLeft;
    Point bottomRight;


    @Override
    public String sayMyName() {
        return "Bomb";
    }

    public Bomb(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public boolean destroy() {
        //TODO make the destruction of all destroyable objects
        Log.i(TAG, "Destroying all destroyable objects");

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
