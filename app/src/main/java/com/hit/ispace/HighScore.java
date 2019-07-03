package com.hit.ispace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HighScore extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private RecyclerView recyclerFreeStyleView,recyclerFasterView,recyclerGettingSickView;
    private HighScoreAdapter adapter;
    private SharedPreferences settings;
    private RelativeLayout container;
    private int lastScore;
    private boolean isPlayed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mDatabaseHelper = new DatabaseHelper(this);

        iniwtView();
    }

    private void iniwtView() {

        recyclerFreeStyleView = findViewById(R.id.recycler_free_style_score);
        recyclerFasterView = findViewById(R.id.recycler_faster_score);
        recyclerGettingSickView = findViewById(R.id.recycler_getting_sick_score);
        recyclerFreeStyleView.setHasFixedSize(true);
        recyclerFasterView.setHasFixedSize(true);
        recyclerGettingSickView.setHasFixedSize(true);
        container = findViewById(R.id.container_high_score_activity);
        recyclerFreeStyleView.setLayoutManager(new LinearLayoutManager(this));
        recyclerFasterView.setLayoutManager(new LinearLayoutManager(this));
        recyclerGettingSickView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HighScoreAdapter(mDatabaseHelper.getTopFreeStyle());
        recyclerFreeStyleView.setAdapter(adapter);
        adapter = new HighScoreAdapter(mDatabaseHelper.getTopFaster());
        recyclerFasterView.setAdapter(adapter);
        adapter = new HighScoreAdapter(mDatabaseHelper.getTopGettingSick());
        recyclerGettingSickView.setAdapter(adapter);
        settings = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        lastScore = getIntent().getIntExtra("isInTable", 0);
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
            Intent serviceIntent = new Intent(HighScore.this, BackgroundMusic.class);
            startService(serviceIntent);

        }

        @Override
        protected void onResume () {
            super.onResume();

            BackgroundMusic.pause--;
            Intent serviceIntent = new Intent(HighScore.this, BackgroundMusic.class);
            startService(serviceIntent);
    }
}
