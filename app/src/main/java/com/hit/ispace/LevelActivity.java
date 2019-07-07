package com.hit.ispace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
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
    private TextView msgHighScore;
    private EditText userNameEditText;
    public SharedPreferences settings ;
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

        settings = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        // Checks whether the last time the user played background music
        if (readSetting("music").equals("true")) {
            Intent serviceIntent = new Intent(this, BackgroundMusic.class);
            startService(serviceIntent);
        }
    }

    private String readSetting(String key) {
        String value;
        value = settings.getString(key, "");
        return value;
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
                Log.d(TAG, "coins earned in game: " +LevelActivity.this.coinsEarned);
                mDatabaseHelper = new DatabaseHelper(LevelActivity.this);
                mDatabaseHelper.updateCoin(LevelActivity.this.coinsEarned);
                ArrayList<UserScore> userScores = mDatabaseHelper.checkScore(LevelActivity.this.level.getLevelType(), LevelActivity.this.kmPassed);

                //1st-15th place
                if (userScores.size()<15 || userScores == null) {
                    //open dialog for 1st place
                    AlertDialog.Builder builder = new AlertDialog.Builder(LevelActivity.this);
                    final View myView = getLayoutInflater().inflate(R.layout.dialog_high_score, null);
                    final TextView score = (TextView) myView.findViewById(R.id.user_score);
                    myView.setBackgroundResource(R.color.grayColor);
                    msgHighScore = (TextView) myView.findViewById(R.id.text_msg_score);
                    if(mDatabaseHelper.getHighScore(LevelActivity.this.level.getLevelType()) < LevelActivity.this.kmPassed){
                        msgHighScore.setText(getText(R.string.new_high_score));
                    }
                    else {
                        msgHighScore.setText(getText(R.string.in_top_15));
                    }
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
                                FancyToast.makeText(LevelActivity.this , getString(R.string.msg_error_save_score),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
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

                    btnSharing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent sendIntent = new Intent();
                            try
                            {
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.text_send_whatsapp_1)+" "+ Integer.toString(LevelActivity.this.kmPassed)+getString(R.string.text_send_whatsapp_2));
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("com.whatsapp");
                                startActivity(sendIntent);
                            }
                            catch ( ActivityNotFoundException ex  )
                            {
                                // If Waze is not installed, open it in Google Play:
                                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market //details id=whatsapp" ) );
                                startActivity(intent);
                            }
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

                    btnSharing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent sendIntent = new Intent();
                            try
                            {
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.text_send_whatsapp_1)+" "+ Integer.toString(LevelActivity.this.kmPassed)+getString(R.string.text_send_whatsapp_2));
                                sendIntent.setType("text/plain");
                                sendIntent.setPackage("com.whatsapp");
                                startActivity(sendIntent);
                            }
                            catch ( ActivityNotFoundException ex  )
                            {
                                // If Waze is not installed, open it in Google Play:
                                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market //details id=whatsapp" ) );
                                startActivity(intent);
                            }
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

    public void onBackPressed() {
        new FancyAlertDialog.Builder(LevelActivity.this)

                .setBackgroundColor(Color.parseColor("#D0A29898"))
                .setMessage(getString(R.string.out_game))
                .setNegativeBtnText(getString(R.string.msg_no))
                .setPositiveBtnBackground(Color.parseColor("#FF67C76B"))
                .setPositiveBtnText(getString(R.string.msg_yes))
                .setNegativeBtnBackground(Color.parseColor("#FFD6463F"))
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.icon_exit, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(LevelActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BackgroundMusic.pause++;
        Intent serviceIntent = new Intent(LevelActivity.this, BackgroundMusic.class);
        startService(serviceIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundMusic.pause--;
        Intent serviceIntent = new Intent(LevelActivity.this, BackgroundMusic.class);
        startService(serviceIntent);
    }

}