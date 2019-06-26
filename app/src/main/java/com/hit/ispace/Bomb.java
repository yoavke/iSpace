package com.hit.ispace;

import android.util.Log;

public class Bomb extends Obstacle implements IDestroyable {

    private static final String TAG = Bomb.class.getSimpleName();

    @Override
    public void printPower() {
        System.out.println("I'm a bomb");
    }

    public Bomb() {
        this.destroyAnimation = Settings.Animation.BOMB_ANIMATION;
    }

    @Override
    public boolean destroy() {
        //TODO make the destruction of all destroyable objects
        Log.i(TAG, "Destroying all destroyable objects");

        return this.isDestroyable;
    }
}
