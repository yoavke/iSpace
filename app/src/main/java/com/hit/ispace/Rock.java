package com.hit.ispace;

import android.util.Log;

public class Rock extends Obstacle implements IDestroyable{

    private static final String TAG = Obstacle.class.getSimpleName();

    public Rock() {
        this.destroyAnimation = Settings.Animation.ROCK_ANIMATION;
    }

    @Override
    public boolean destroy() {
        //TODO make the destruction of all destroyable objects
        Log.i(TAG, "Destroying all destroyable objects");

        return this.isDestroyable;
    }
}
