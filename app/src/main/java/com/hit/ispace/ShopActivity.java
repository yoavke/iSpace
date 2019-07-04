package com.hit.ispace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private RecyclerView recyclerSpaceShipView;
    private ShopAdapter adapter;
    private SharedPreferences settings;
    private RelativeLayout container;
    private boolean isPlayed;

    //private ArrayList<String> mPrice = new ArrayList<>();
    //private ArrayList<Integer> mImageSpaceShip = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mDatabaseHelper = new DatabaseHelper(this);

        iniwtView();
    }

    private void iniwtView(){
        recyclerSpaceShipView = findViewById(R.id.recycler_free_style_score);
        recyclerSpaceShipView.setHasFixedSize(true);
        recyclerSpaceShipView.setLayoutManager(new LinearLayoutManager(this));
        recyclerSpaceShipView.setAdapter(adapter);
        adapter = new ShopAdapter(mDatabaseHelper.getAllSpaceShip());
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




