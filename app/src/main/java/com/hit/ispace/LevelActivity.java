package com.hit.ispace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class LevelActivity extends AppCompatActivity {

    private static final String TAG = LevelActivity.class.getSimpleName();
    private HandlerThread waitEndGameThread;
    private Handler waitEndGameHandler;
    private Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //get the view of the level
        final LevelView levelView = findViewById(R.id.levelview);

        //set the background of the level view
        levelView.setBackgroundColor(getResources().getColor(R.color.blackColor));

        final int levelType = 1; //getIntent().getIntExtra("levelType", 0);
        this.waitEndGameThread = new HandlerThread("wait for end of game thread");
        this.waitEndGameThread.start();
        this.waitEndGameHandler = new Handler(waitEndGameThread.getLooper());

        //check if the level type chosen exists
        switch (levelType) {
            case CSettings.LevelTypes.FREE_STYLE:
            case CSettings.LevelTypes.GETTING_SICK:
                //instantiate level here so we dont instantiate it for level that doesn't exists
                level = new Level(levelType);
                levelView.startLevel(level);
                break;
            default:
                Log.e(TAG, "No such level type");

                //finish activity with relevant request_code
                setResult(RESULT_CANCELED);
                finish();
                break;
        }

        this.waitEndGameHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "game still playing");

                if (levelView.isGameEnded()) {
                    //stopping threads
                    levelView.animateElementThread.quit();
                    levelView.createElementThread.quit();
                    LevelActivity.this.waitEndGameThread.quit();

                    //end the game
                    finishGame(level.getNumCoinsEarned());
                }
                waitEndGameHandler.postDelayed(this, 20);
            }
        }, 20);

    }

    public void finishGame(int coinsEarned) {
        //TODO add coins to the bank database
        Log.d(TAG, "coins earned in game: " +coinsEarned);
        DatabaseHelper db = new DatabaseHelper(this);
        db.updateCoin(coinsEarned);

        //finish activity with relevant request_code
        //set intent for data (coins earned will be sent to called activity
        Intent data = new Intent();
        data.putExtra("coinsEarned", coinsEarned);

        setResult(RESULT_OK, data);
//        finish();
    }
}