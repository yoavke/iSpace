package com.hit.ispace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LevelActivity extends AppCompatActivity {

    private static final String TAG = LevelActivity.class.getSimpleName();

    private Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //get the view of the level
        LevelView levelView = findViewById(R.id.levelview);

        int levelType = getIntent().getIntExtra("levelType", 0);

        //check if the level type chosen exists
        switch (levelType) {
            case Settings.LevelTypes.FREE_STYLE:
            case Settings.LevelTypes.GETTING_SICK:
                //instantiate level here so we dont instantiate it for level that doesn't exists
                level = new Level(levelType);
                levelView.startLevel(level);
            default:
                Log.e(TAG, "No such level type");

                //finish activity with relevant request_code
                finishActivity(Settings.RequestCodes.LEVEL_NOT_EXIST);

        }


        //loop until the game ends
        while (!level.isLevelEnded()) {}

        //finish activity with relevant request_code
        finishActivity(Settings.RequestCodes.GAME_ENDED);
    }
}