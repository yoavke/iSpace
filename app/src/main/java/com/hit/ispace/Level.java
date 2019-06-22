package com.hit.ispace;

import android.util.Log;

import java.util.ArrayList;

public class Level {
    private static final String TAG = Level.class.getSimpleName();
    int levelNumber;
    int distance;
    double speed;
    ArrayList<Obstacle> obstacleCollection;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        switch (this.levelNumber) {
            case 1:
                Log.i(TAG, "Level 1 - no obstacles");
                this.speed = Settings.Speed.SLOW;
                this.distance = 100;
                break;
            case 2:
                Log.i(TAG, "Level 2 - rocks");
                obstacleCollection.add(new Rock());
                this.speed = Settings.Speed.SLOW;
                this.distance = 250;
                break;
            case 3:
                Log.i(TAG, "Level 3 - rocks and bombs");
                obstacleCollection.add(new Rock());
                obstacleCollection.add(new Bomb());
                this.speed = Settings.Speed.REGULAR;
                this.distance = 500;
                break;
            case 4:
                Log.i(TAG, "Level 4");
                obstacleCollection.add(new Rock());
                obstacleCollection.add(new Bomb());
                obstacleCollection.add(new SuprerRock());
                this.speed = Settings.Speed.FAST;
                this.distance = 1000;
                break;
            case 5:
                Log.i(TAG, "Level 5");
                obstacleCollection.add(new Rock());
                obstacleCollection.add(new Bomb());
                obstacleCollection.add(new SuprerRock());
                this.speed = Settings.Speed.FAST;
                this.distance = 2000;
                break;
            default:
                Log.i(TAG, "DEFAULT LEVER WHAT TO DO?");
        }
    }
}