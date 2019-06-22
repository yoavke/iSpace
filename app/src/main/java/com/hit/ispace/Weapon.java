package com.hit.ispace;

import android.util.Log;

public class Weapon {
    private static final String TAG = Weapon.class.getSimpleName();

    private String name;
    private int amount;
    private String image;

    private final String GOODBOMB = "Good bomb";
    private final String GUN = "Gun";

    public Weapon(String name, int amount) {
        this.name = name;
        this.amount = amount;
        if (this.name == GOODBOMB) {
            //TODO set the image path
            this.image = "";
            this.name = "Good bomb";
        } else {
            Log.i(TAG, "Not a GOOD BOMB!");
        }
    }
}
