package com.hit.ispace;

import android.util.Log;

import java.util.ArrayList;

public class Level {
    private static final String TAG = Level.class.getSimpleName();
    private int levelType;
    private double speed;
    private boolean levelEnded;
    protected Spaceship spaceship;
    private ArrayList<String> obstacleTypes;
    protected ElementFactory elementFactory;
    private int numCoinsEarned;

    public Level(int levelType) {
        this.levelEnded = false;
        this.levelType = levelType;
        this.obstacleTypes = new ArrayList<>();
        this.spaceship = new Spaceship();
        this.numCoinsEarned = 0;

        for (CSettings.FlyingElements obs : CSettings.FlyingElements.values()) {
            obstacleTypes.add(obs.name());
        }

        this.elementFactory = new ElementFactory(obstacleTypes);

        switch (this.getLevelType()) {
            case 1:
                Log.i(TAG, "Game - Free Style");
                this.speed = CSettings.Speed.REGULAR;
                break;
            case 2:
                Log.i(TAG, "Game - Getting Sick");
                this.speed = CSettings.Speed.REGULAR;
                break;
            case 3:
                Log.i(TAG, "Game - COMING SOON!");
                break;
            default:
                Log.e(TAG, "Level not exists");
        }
    }

    public int getNumCoinsEarned() {
        return numCoinsEarned;
    }

    public void incrementNumCoinsEarned() {
        this.numCoinsEarned =+5;
    }

    public void reduceNumCoins() {
        if (getNumCoinsEarned() <= SuperRock.POWER) {
            this.numCoinsEarned = 0;
        } else {
            this.numCoinsEarned -= SuperRock.POWER;
        }
    }

    public int getLevelType() {
        return levelType;
    }

    public double getSpeed() {
        return speed;
    }

    public ArrayList<String> getObstacleTypes() {
        return obstacleTypes;
    }

    public boolean isLevelEnded() {
        return levelEnded;
    }

    public void setLevelEnded(boolean levelEnded) {
        this.levelEnded = levelEnded;
    }
}