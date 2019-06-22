package com.hit.ispace;

import android.util.Log;

public class Rock extends Obstacle implements IDestroyable{

    private static final String TAG = Obstacle.class.getSimpleName();

    @Override
    public void destroy() {
        //TODO make the destruction of all destroyable objects
        Log.i(TAG, "Destroying all destroyable objects");
    }
}
