package com.hit.ispace;

import android.util.Log;

public class Level {
    private static final String TAG = Level.class.getSimpleName();
    int levelNumber;
    int distance;
    int speed;
    Obstacle[] obstacleCollection;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        switch (this.levelNumber) {
            case 1:
                Log.i(TAG, "Level 1");
                break;
            case 2:
                Log.i(TAG, "Level 2");
                break;
            case 3:
                Log.i(TAG, "Level 3");
                break;
            case 4:
                Log.i(TAG, "Level 4");
                break;
            case 5:
                Log.i(TAG, "Level 5");
                break;
            default:
                Log.i(TAG, "DEFAULT LEVER WHAT TO DO???");
        }
    }
}
