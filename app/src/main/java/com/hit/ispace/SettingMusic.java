package com.hit.ispace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.suke.widget.SwitchButton;


public class SettingMusic extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private com.suke.widget.SwitchButton setting_sound , setting_music;
    private ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_settings);

        settings = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        editor = settings.edit();
        initView();
    }

    //Initialize all UI elements
    public void initView()
    {
        setting_sound = findViewById(R.id.setting_sfx);
        setting_music = findViewById(R.id.setting_music);


        // Read and set sound last choice from Shared Preferences
        if(readSetting("sound").equals("true"))
        {
            setting_sound.setChecked(true);
        }
        else
        {
            setting_sound.setChecked(false);
        }


        // Read and set background music last choice from Shared Preferences
        if(readSetting("music").equals("true"))
        {
            setting_music.setChecked(true);
        }
        else
        {
            setting_music.setChecked(false);
        }


        // Listener that turn on and off  background music using Service
        setting_music.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if(isChecked)
                {
                    editSetting("music", "true");
                    Intent serviceIntent = new Intent(SettingMusic.this, BackgroundMusic.class);
                    startService(serviceIntent);
                }
                else
                {
                    editSetting("music", "false");
                    Intent serviceIntent = new Intent(SettingMusic.this, BackgroundMusic.class);
                    stopService(serviceIntent);
                }
            }
        });


        // Listener that turn on and off  sound using Service
        setting_sound.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked)
                {
                    editSetting("sound", "true");
                }
                else
                {
                    editSetting("sound", "false");
                }
            }

        });

    }

    //Read setting
    public String readSetting(String key)
    {
        String value;
        value = settings.getString(key, "");
        return value;
    }

    //Edit setting
    public void editSetting(String key, String value)
    {
        editor.putString(key,value);
        editor.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();

        BackgroundMusic.pause++;
        Intent serviceIntent = new Intent(SettingMusic.this, BackgroundMusic.class);
        startService(serviceIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        BackgroundMusic.pause--;
        Intent serviceIntent = new Intent(SettingMusic.this, BackgroundMusic.class);
        startService(serviceIntent);
    }
}