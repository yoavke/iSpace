package com.hit.ispace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LevelActivity extends AppCompatActivity {

    private Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //get the view of the level
        LevelView levelView = findViewById(R.id.levelview);

        //play 5 levels
        for (int i=1; i<=5; i++) {
            level = new Level(i);
            levelView.startLevel(level);

            //loop until the game ends (finished or failed)
            while (!level.isLevelEnded()) {}
        }
    }
}