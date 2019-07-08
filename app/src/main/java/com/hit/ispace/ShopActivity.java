package com.hit.ispace;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class ShopActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    //private RecyclerView recyclerSpaceShipView;
    private ShopAdapter adapter;
    private SharedPreferences settings;
    private RelativeLayout container;
    private boolean isPlayed;
    private TextView coins_total;
    boolean isFlip = false;
    private ImageView anim_bomb, anim_asteroid;
    private Animation animShakeBomb, animRotateAsteroid;

    //private ArrayList<String> mPrice = new ArrayList<>();
    //private ArrayList<Integer> mImageSpaceShip = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mDatabaseHelper = new DatabaseHelper(this);

        initView();
    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_space_ship);
        recyclerView.setLayoutManager(layoutManager);
        ShopAdapter adapter = new ShopAdapter(this ,mDatabaseHelper.getAllSpaceShip());
        recyclerView.setAdapter(adapter);

        int number_of_coins;
        number_of_coins = mDatabaseHelper.getTotalCoins();
        coins_total = findViewById(R.id.coins_value);
        coins_total.setText(String.valueOf(number_of_coins));

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
        isPlayed = getIntent().getBooleanExtra("isPlayed", false);
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
    protected void onPause () {
        super.onPause();

        BackgroundMusic.pause++;
        Intent serviceIntent = new Intent(ShopActivity.this, BackgroundMusic.class);
        startService(serviceIntent);

    }

    @Override
    protected void onResume () {
        super.onResume();

        BackgroundMusic.pause--;
        Intent serviceIntent = new Intent(ShopActivity.this, BackgroundMusic.class);
        startService(serviceIntent);
    }
}




