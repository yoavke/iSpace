package com.hit.ispace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity {

    private static final String TAG = LevelActivity.class.getSimpleName();
    private HandlerThread waitEndGameThread;
    private Handler waitEndGameHandler;
    private Level level;
    private TextView scoreTxt, coinsTxt;
    private LevelView levelView;
    DatabaseHelper mDatabaseHelper;

    private int coinsEarned;
    private int kmPassed;
    private EditText userNameEditText;
    private Button btnSave, btnStartAgain, btnSharing, btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //get the view of the level
        levelView = findViewById(R.id.levelview);
        this.scoreTxt = findViewById(R.id.score_value);
        this.coinsTxt = findViewById(R.id.coins_value);

        //set the background of the level view
        levelView.setBackgroundColor(getResources().getColor(R.color.blackColor));

        final int levelType = getIntent().getIntExtra("levelType", 0);
        this.waitEndGameThread = new HandlerThread("wait for end of game thread");
        this.waitEndGameThread.start();
        this.waitEndGameHandler = new Handler(waitEndGameThread.getLooper());

        //check if the level type chosen exists
        switch (levelType) {
            case CSettings.LevelTypes.FREE_STYLE:
            case CSettings.LevelTypes.FASTER:
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
                LevelActivity.this.setValues();
                if (levelView.isGameEnded()) {
                    //stopping threads
                    levelView.animateElementThread.quit();
                    levelView.createElementThread.quit();
                    //end the game
                    finishGame(level.getNumCoinsEarned(), (int)(LevelActivity.this.levelView.getKm()/100));
                    LevelActivity.this.waitEndGameThread.quit();
                } else {
                    waitEndGameHandler.postDelayed(this, 10);
                }
            }
        }, 1000);

    }

    public void setValues() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int coinsValue, kmValue;
                coinsValue = LevelActivity.this.level.getNumCoinsEarned();
                kmValue = (int)(LevelActivity.this.levelView.getKm()/100);

                LevelActivity.this.coinsTxt.setText(Integer.toString(coinsValue));
                LevelActivity.this.scoreTxt.setText(Integer.toString(kmValue));
            }
        });
    }

    public void finishGame(int coinsEarned, int kmPassed) {
        this.coinsEarned = coinsEarned;
        this.kmPassed = kmPassed;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO add coins to the bank database
                Log.d(TAG, "coins earned in game: " +LevelActivity.this.coinsEarned);
                mDatabaseHelper = new DatabaseHelper(LevelActivity.this);
                mDatabaseHelper.updateCoin(LevelActivity.this.coinsEarned);
                ArrayList<UserScore> userScores = mDatabaseHelper.checkScore(LevelActivity.this.level.getLevelType(), LevelActivity.this.kmPassed);

                //1st-5th place
                if (userScores.size()<5 || userScores == null) {
                    //open dialog for 1st place

                    AlertDialog.Builder builder = new AlertDialog.Builder(LevelActivity.this);
                    final View myView = getLayoutInflater().inflate(R.layout.dialog_high_score, null);

                    final TextView score = (TextView) myView.findViewById(R.id.user_score);
                    userNameEditText = (EditText) myView.findViewById(R.id.user_name);
                    builder.setView(myView);
                    score.setText(Integer.toString(LevelActivity.this.kmPassed));
                    btnSave = (Button) myView.findViewById(R.id.save_button);
                    btnStartAgain = (Button) myView.findViewById(R.id.btn_start_again);
                    btnSharing = (Button) myView.findViewById(R.id.btn_sharing_in_whatsaps);
                    btnHome = (Button) myView.findViewById(R.id.btn_home);
                    final AlertDialog dialog = builder.create();

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int level = LevelActivity.this.level.getLevelType();
                            String newUser = userNameEditText.getText().toString();
                            int score = LevelActivity.this.kmPassed;
                            if(newUser.length() != 0)
                            {
                                AddData(level,newUser,score);
                                dialog.dismiss();
                            }
                            else {
                                FancyToast.makeText(LevelActivity.this , "You must put something in the text field!",FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
                            }
                        }
                    });

                    btnHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LevelActivity.this, MainMenuActivity.class));
                        }
                    });

                    btnStartAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(LevelActivity.this, LevelActivity.class);
                            intent.putExtra("levelType", LevelActivity.this.level.getLevelType());
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                }
                //not in top 5
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LevelActivity.this);
                    final View myView = getLayoutInflater().inflate(R.layout.dialog_score, null);

                    final TextView score = (TextView) myView.findViewById(R.id.user_score);
                    userNameEditText = (EditText) myView.findViewById(R.id.user_name);
                    builder.setView(myView);
                    score.setText(Integer.toString(LevelActivity.this.kmPassed));
                    btnStartAgain = (Button) myView.findViewById(R.id.btn_start_again);
                    btnSharing = (Button) myView.findViewById(R.id.btn_sharing_in_whatsaps);
                    btnHome = (Button) myView.findViewById(R.id.btn_home);
                    final AlertDialog dialog = builder.create();

                    btnHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LevelActivity.this, MainMenuActivity.class));
                        }
                    });

                    btnStartAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(LevelActivity.this, LevelActivity.class);
                            intent.putExtra("levelType", LevelActivity.this.level.getLevelType());
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                }
            }
        });

        //finish activity with relevant request_code
        //set intent for data (coins earned will be sent to called activity
        Intent data = new Intent();
        data.putExtra("coinsEarned", coinsEarned);

        setResult(RESULT_OK, data);
//        finish();
    }

    public void AddData(int level, String name, int score) {
        boolean insertData = mDatabaseHelper.addNewRecord(level,name,score);
        if (insertData) {
            FancyToast.makeText(this , getString(R.string.success_adding),FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
        } else {
            FancyToast.makeText(this , getString(R.string.no_success),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
        }
    }
}