package com.hit.ispace;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundMusic extends Service implements MediaPlayer.OnErrorListener{

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    public static int pause = 0;
    private SharedPreferences music;
    private Float MUSIC_VOLUME = 0.2f;

    public BackgroundMusic() { }

    public class ServiceBinder extends Binder {
        BackgroundMusic getService()
        {
            return BackgroundMusic.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return mBinder;}

    @Override
    public void onCreate (){
        super.onCreate();

        mPlayer = MediaPlayer.create(this, R.raw.background_music);
        mPlayer.setOnErrorListener(this);

        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(MUSIC_VOLUME , MUSIC_VOLUME);
        }

        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra){

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        music = getSharedPreferences("PREFERENCES",MODE_PRIVATE);

        if (pause == 0) {

            pauseMusic();
        }
        else if(readSetting("music").equals("true")){
            mPlayer.start();}

        return START_STICKY;
    }

    public void pauseMusic()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
            length=mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic()
    {
        if(mPlayer.isPlaying()==false)
        {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic()
    {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
        return false;
    }
    public String readSetting(String key)
    {

        String value;
        value = music.getString(key, "Error");
        return value;
    }
}