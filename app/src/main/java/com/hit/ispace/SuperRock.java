package com.hit.ispace;

import android.util.Log;

public class SuperRock extends Obstacle implements IDestroyable{

    private static final String TAG = SuperRock.class.getSimpleName();

    private boolean isDestroyable = false;

    //TODO image to point to the path of the image
    String image = "";

    public SuperRock() {
        Log.i(TAG,"SuperRock created");
    }

    @Override
    public void printPower() {
        System.out.println("I'm a superrock. I cannot get destroyed");
    }

    @Override
    public boolean destroy() {
        return this.isDestroyable;
    }
}
