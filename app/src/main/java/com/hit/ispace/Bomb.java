package com.hit.ispace;

import android.util.Log;

public class Bomb extends Obstacle implements IDestroyable {

    private static final String TAG = Bomb.class.getSimpleName();

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
