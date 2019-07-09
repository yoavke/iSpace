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
        if (this.getLevelType()==CSettings.LevelTypes.FREE_STYLE)
            this.numCoinsEarned += 3;
        else if (this.getLevelType()==CSettings.LevelTypes.FASTER)
            this.numCoinsEarned += 5;
        else if (this.getLevelType()==CSettings.LevelTypes.GETTING_SICK)
            this.numCoinsEarned += 7;
        else
            this.numCoinsEarned += 1;
    }

    public boolean reduceNumCoins(boolean fullStrength) {
        if (fullStrength) {
            if ((this.numCoinsEarned % 2)==1) {
                this.numCoinsEarned = (int)(this.numCoinsEarned * CSettings.Strength.HALF) + 1;
            } else {
                this.numCoinsEarned = (int)(this.numCoinsEarned * CSettings.Strength.HALF);
            }
            return true;
        } else {
            if (getNumCoinsEarned() - SuperRock.POWER < 0) {
                return false;
            } else {
                this.numCoinsEarned -= SuperRock.POWER;
                return true;
            }
        }
    }

    public int getLevelType() {
        return levelType;
    }
}