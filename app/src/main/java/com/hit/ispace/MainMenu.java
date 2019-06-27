package com.hit.ispace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private ImageView logoIspace;
    private Button btnStart;
    private Button btnSettings;
    private Button btnScore;
    private Button btnShop;
    private Button btnInfo;
    public SharedPreferences settings ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initView();
    }

    private String readSetting(String key) {
       String value;
        value = settings.getString(key, "");
       return value;
    }


    //Initialize all UI elements
    private void initView() {
        settings = getSharedPreferences("PREFERENCES", MODE_PRIVATE);

        logoIspace = findViewById(R.id.logo_img);
        btnStart = findViewById(R.id.btn_play);
        btnSettings = findViewById(R.id.btn_settings);
        btnScore = findViewById(R.id.btn_score);
        btnShop = findViewById(R.id.btn_shop);
        btnInfo = findViewById(R.id.btn_info);


        // Set animation for buttons
        StartSmartAnimation.startAnimation(findViewById(R.id.logo_img) , AnimationType.BounceInDown, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_play) , AnimationType.ZoomInDown, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_settings) , AnimationType.BounceInLeft, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_score) , AnimationType.BounceInLeft, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_shop) , AnimationType.BounceInRight, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_info) , AnimationType.BounceInRight, 3000 , 0 , true );


        btnStart.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnScore.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

        // Checks whether the last time the user played background music
        if (readSetting("music").equals("true")) {
            Intent serviceIntent = new Intent(this, MusicService.class);
            startService(serviceIntent);
        }

    }

    //Handle onClick events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                startActivity(new Intent(this, LevelActivity.class));
                break;

            case R.id.btn_settings:
                startActivity(new Intent(this, CSettings.class));
                break;

            case R.id.btn_score:
                startActivity(new Intent(this, HighScore.class));
                break;

            case R.id.btn_shop:
                startActivity(new Intent(this, Shop.class));
                break;

            default:
                break;
        }
    }

    // Set information button
    public void InfoClick(View view) {

        com.geniusforapp.fancydialog.FancyAlertDialog.Builder alert = new com.geniusforapp.fancydialog.FancyAlertDialog.Builder(MainMenu.this)
                .setimageResource(R.drawable.icon_milky_way)
                .setTextTitle(getString(R.string.app_name))

                .setBody(getString(R.string.info))
                .setNegativeColor(R.color.redColor)



                .setPositiveButtonText(getString(R.string.ok))
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new com.geniusforapp.fancydialog.FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                }).setButtonsGravity(com.geniusforapp.fancydialog.FancyAlertDialog.PanelGravity.CENTER)
                .setBodyGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(com.geniusforapp.fancydialog.FancyAlertDialog.TextGravity.CENTER)

                .setCancelable(true)
                .build();
        alert.show();
    }



    @Override
    protected void onPause() {
        super.onPause();
        //MusicService.pause++;
        Intent serviceIntent = new Intent(MainMenu.this, MusicService.class);
        startService(serviceIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //MusicService.pause--;
        Intent serviceIntent = new Intent(MainMenu.this, MusicService.class);
        startService(serviceIntent);
    }
}

