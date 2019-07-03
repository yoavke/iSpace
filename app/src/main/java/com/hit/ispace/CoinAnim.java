package com.hit.ispace;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;


public class CoinAnim extends AppCompatActivity {

    boolean isFlip = false;
    private ImageView anim_bomb, anim_asteroid;
    private Animation animShakeBomb, animRotateAsteroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //id of ImageView of coin
        final ImageView coinIv = findViewById(R.id.icon_animation_stars);//this bad name !!!

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
    }
}