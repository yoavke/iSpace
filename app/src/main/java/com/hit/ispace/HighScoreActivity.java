package com.hit.ispace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class HighScoreActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private RecyclerView recyclerView;
    private HighScoreAdapter adapter;
    private SharedPreferences settings;
    private MediaPlayer high_score;
    private RelativeLayout container;
    private boolean isPlayed;
    private KonfettiView konfetti;
    private TextView nameTopDialog;
    private Button btnTopFreeStyle ,btnTopFaster , btnTopFreeGettingSick ;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mDatabaseHelper = new DatabaseHelper(this);
        konfetti = findViewById(R.id.viewKonfetti);
        konfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2500L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 10))
                .setPosition(-50f, konfetti.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
        initView();
    }

    private void initView() {

        container = findViewById(R.id.container_high_score_activity);
        high_score= MediaPlayer.create(this, R.raw.high_score);
        dialog = new Dialog(this);

        btnTopFreeStyle = (Button) findViewById(R.id.btn_top_free_style);
        btnTopFreeStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.dialog_top_high_score);
                nameTopDialog = (TextView) dialog.findViewById(R.id.name_top_high_score);
                nameTopDialog.setText(getText(R.string.free_style));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();

                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_high_score);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(HighScoreActivity.this));

                adapter = new HighScoreAdapter(mDatabaseHelper.getTopHighScore(1));
                recyclerView.setAdapter(adapter);
            }
        });

        btnTopFaster = (Button) findViewById(R.id.btn_top_faster);
        btnTopFaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.dialog_top_high_score);
                nameTopDialog = (TextView) dialog.findViewById(R.id.name_top_high_score);
                nameTopDialog.setText(getText(R.string.faster));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();

                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_high_score);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(HighScoreActivity.this));

                adapter = new HighScoreAdapter(mDatabaseHelper.getTopHighScore(2));
                recyclerView.setAdapter(adapter);
            }
        });

        btnTopFreeGettingSick = (Button) findViewById(R.id.btn_top_getting_sick);
        btnTopFreeGettingSick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.dialog_top_high_score);
                nameTopDialog = (TextView) dialog.findViewById(R.id.name_top_high_score);
                nameTopDialog.setText(getText(R.string.getting_sick));
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();

                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_high_score);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(HighScoreActivity.this));

                adapter = new HighScoreAdapter(mDatabaseHelper.getTopHighScore(3));
                recyclerView.setAdapter(adapter);
            }
        });

        settings = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        //lastScore = getIntent().getIntExtra("isInTable", 0);
        isPlayed = getIntent().getBooleanExtra("isPlayed", false);
        playSound(R.raw.high_score);

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
            Intent serviceIntent = new Intent(HighScoreActivity.this, BackgroundMusic.class);
            startService(serviceIntent);

        }

        @Override
        protected void onResume () {
            super.onResume();

            BackgroundMusic.pause--;
            Intent serviceIntent = new Intent(HighScoreActivity.this, BackgroundMusic.class);
            startService(serviceIntent);
    }

}
