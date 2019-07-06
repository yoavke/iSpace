package com.hit.ispace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView logoIspace;
    private Button btnFreeStyle,btnFaster,btnGettingSick;
    private Button btnSettings;
    private Button btnScore;
    private Button btnShop;
    private Button btnInfo;
    public SharedPreferences settings ;
    private Animation animShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Resources res = getResources();
        int resID = res.getIdentifier("spaceship_1" , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        Toast.makeText(this,"int: " + resID,Toast.LENGTH_LONG).show();
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
        btnFreeStyle = findViewById(R.id.btn_freestyle);
        btnFaster = findViewById(R.id.btn_faster);
        btnGettingSick = findViewById(R.id.btn_getting_sick);
        btnSettings = findViewById(R.id.btn_settings);
        btnScore = findViewById(R.id.btn_score);
        btnShop = findViewById(R.id.btn_shop);
        btnInfo = findViewById(R.id.btn_info);


        // Set animation for buttons
        StartSmartAnimation.startAnimation(findViewById(R.id.logo_img) , AnimationType.BounceInDown, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_freestyle) , AnimationType.ZoomInDown, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_faster) , AnimationType.ZoomInLeft, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_getting_sick) , AnimationType.ZoomInRight, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_settings) , AnimationType.BounceInLeft, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_score) , AnimationType.BounceInLeft, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_shop) , AnimationType.BounceInRight, 3000 , 0 , true );
        StartSmartAnimation.startAnimation(findViewById(R.id.btn_info) , AnimationType.BounceInRight, 3000 , 0 , true );


        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        btnFreeStyle.startAnimation(animShake);
        btnFaster.startAnimation(animShake);
        btnGettingSick.startAnimation(animShake);

        btnFreeStyle.setOnClickListener(this);
        btnFaster.setOnClickListener(this);
        btnGettingSick.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnScore.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

        // Checks whether the last time the user played background music
        if (readSetting("music").equals("true")) {
            Intent serviceIntent = new Intent(this, BackgroundMusic.class);
            startService(serviceIntent);
        }

    }

    //Handle onClick events
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_freestyle:
                intent = new Intent(this, LevelActivity.class);
                intent.putExtra("levelType", 1);
                startActivity(intent);
                break;
            case R.id.btn_faster:
                intent = new Intent(this, LevelActivity.class);
                intent.putExtra("levelType", 2);
                startActivity(intent);
                break;
            case R.id.btn_getting_sick:
                intent = new Intent(this, LevelActivity.class);
                intent.putExtra("levelType", 3);
                startActivity(intent);
                break;
            case R.id.btn_settings:
                startActivity(new Intent(this, SettingMusicActivity.class));
                break;

            case R.id.btn_score:
                startActivity(new Intent(this, HighScoreActivity.class));
                break;

            case R.id.btn_shop:
                startActivity(new Intent(this, ShopActivity.class));
                break;
            case R.id.btn_info:
                InfoClick(v);
            default:
                break;
        }
    }

    // Set information button
    public void InfoClick(View view) {
        com.geniusforapp.fancydialog.FancyAlertDialog.Builder alert = new com.geniusforapp.fancydialog.FancyAlertDialog.Builder(MainMenuActivity.this)
                .setimageResource(R.drawable.icon_app)
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
        BackgroundMusic.pause++;
        Intent serviceIntent = new Intent(MainMenuActivity.this, BackgroundMusic.class);
        startService(serviceIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundMusic.pause--;
        Intent serviceIntent = new Intent(MainMenuActivity.this, BackgroundMusic.class);
        startService(serviceIntent);
    }
}

