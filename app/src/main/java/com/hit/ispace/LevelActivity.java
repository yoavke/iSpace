package com.hit.ispace;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    AnimationDrawable backgroundAnim;
    private int coinsEarned;
    private int kmPassed;
    private TextView msgHighScore,score,coin;
    private EditText userNameEditText;
    public SharedPreferences settings ;
    private ImageButton btnStartAgain, btnSharing, btnHome;
    private Button btnSave;
    private boolean doubleBackToExitPressedOnce = false;
    boolean isFlip = false;
    private boolean isPlayed;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //get the view of the level
        levelView = findViewById(R.id.levelview);
        this.scoreTxt = findViewById(R.id.score_value);
        this.coinsTxt = findViewById(R.id.coins_value);

        //ImageView imageView = (ImageView) findViewById(R.id.image);
        //imageView.setBackgroundResource(R.drawable.frame_background);
        //backgroundAnim = (AnimationDrawable) imageView.getBackground();

        //set the background of the level view
        levelView.setBackground(getResources().getDrawable(R.drawable.background_level2));

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

                levelView.start(level);
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


        //id of ImageView of coin
        final ImageView coinIv = findViewById(R.id.icon_animation_coin);

        final ObjectAnimator invisibleAnim = ObjectAnimator.ofFloat(coinIv,"scaleX",1f,0f).setDuration(1000);
        final ObjectAnimator visibleAnim = ObjectAnimator.ofFloat(coinIv,"scaleX",0f,1f).setDuration(1000);

        invisibleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                isFlip  = !isFlip;

                if(isFlip)
                    coinIv.setImageResource(R.drawable.icon_coin);
                else
                    coinIv.setImageResource(R.drawable.icon_coin);
                visibleAnim.start();
            }
        });

        visibleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                invisibleAnim.start();
            }
        });
        invisibleAnim.start();



        settings = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        // Checks whether the last time the user played background music
        if (readSetting("music").equals("true")) {
            Intent serviceIntent = new Intent(this, BackgroundMusic.class);
            startService(serviceIntent);
        }
        isPlayed = getIntent().getBooleanExtra("isPlayed", false);
        playSound(R.raw.start_level);
    }

    //@Override
    //public void onWindowFocusChanged(boolean hasFocus) {
    //   super.onWindowFocusChanged(hasFocus);
    //    backgroundAnim.start();
    //}

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
        playSound(R.raw.fail_level);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "coins earned in game: " +LevelActivity.this.coinsEarned);
                mDatabaseHelper = new DatabaseHelper(LevelActivity.this);
                mDatabaseHelper.updateCoin(LevelActivity.this.coinsEarned);
                ArrayList<UserScore> userScores = mDatabaseHelper.checkScore(LevelActivity.this.level.getLevelType(), LevelActivity.this.kmPassed);
                dialog = new Dialog(LevelActivity.this);
                //1st-15th place
                if (userScores.size()<15 || userScores == null) {
                    //open dialog for 1st place
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_high_score);
                    btnStartAgain = (ImageButton) dialog.findViewById(R.id.btn_start_again);
                    btnSharing = (ImageButton) dialog.findViewById(R.id.btn_sharing_in_whatsapps);
                    btnHome = (ImageButton) dialog.findViewById(R.id.btn_home);
                    score = (TextView) dialog.findViewById(R.id.user_score);
                    coin = (TextView) dialog.findViewById(R.id.user_coin);
                    msgHighScore = (TextView) dialog.findViewById(R.id.text_msg_score);
                    if(mDatabaseHelper.getHighScore(LevelActivity.this.level.getLevelType()) < LevelActivity.this.kmPassed){
                        msgHighScore.setText(getText(R.string.new_high_score));
                    }
                    else {
                        msgHighScore.setText(getText(R.string.in_top_15));
                    }

                    userNameEditText = (EditText) dialog.findViewById(R.id.user_name);
                    score.setText(Integer.toString(LevelActivity.this.kmPassed));
                    coin.setText(Integer.toString(LevelActivity.this.coinsEarned));
                    btnSave = (Button) dialog.findViewById(R.id.btn_save);

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int level = LevelActivity.this.level.getLevelType();
                            String newUser = userNameEditText.getText().toString();
                            int score = LevelActivity.this.kmPassed;
                            if(newUser.length() != 0)
                            {
                                AddData(level,newUser,score);
                                Intent intent = new Intent(LevelActivity.this, MainMenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else {
                                FancyToast.makeText(LevelActivity.this , getString(R.string.msg_error_save_score),FancyToast.LENGTH_SHORT, FancyToast.ERROR,true).show();
                            }
                        }
                    });

                    btnHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LevelActivity.this, MainMenuActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });

                    btnStartAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(LevelActivity.this, LevelActivity.class);
                            intent.putExtra("levelType", LevelActivity.this.level.getLevelType());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_score);
                    btnStartAgain = (ImageButton) dialog.findViewById(R.id.btn_start_again);
                    btnSharing = (ImageButton) dialog.findViewById(R.id.btn_sharing_in_whatsapps);
                    btnHome = (ImageButton) dialog.findViewById(R.id.btn_home);
                    score = (TextView) dialog.findViewById(R.id.user_score);
                    coin = (TextView) dialog.findViewById(R.id.user_coin);
                    score.setText(Integer.toString(LevelActivity.this.kmPassed));
                    coin.setText(Integer.toString(LevelActivity.this.coinsEarned));

                    btnHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LevelActivity.this, MainMenuActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);                        }
                    });

                    btnStartAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            intent = new Intent(LevelActivity.this, LevelActivity.class);
                            intent.putExtra("levelType", LevelActivity.this.level.getLevelType());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            FancyToast.makeText(this , getString(R.string.success_adding),FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,true).show();
        } else {
            FancyToast.makeText(this , getString(R.string.no_success),FancyToast.LENGTH_SHORT, FancyToast.ERROR,true).show();
        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.levelView.createElementThread.quit();
            this.levelView.animateElementThread.quit();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        String message = getResources().getString(R.string.doubleClickExit);
        FancyToast.makeText(this,message,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1500);
    }

    public String readSetting (String key)
    {
        String value;
        value = settings.getString(key, "");
        return value;
    }


    // Play the Sound
    private void playSound ( int idOfSound){

        if (readSetting("sound").equals("true")) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), idOfSound);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }

                assert mediaPlayer != null;
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(@NonNull MediaPlayer mp) {
                        if (mp.isPlaying()) {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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