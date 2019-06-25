package com.hit.ispace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LevelView extends View {

    //TAG
    private static final String TAG = LevelView.class.getSimpleName();

    //Level vars
    private Bitmap aircraft;

    private Level level;

    //the screen height
    private int screenMaxHeight=0;

    //coordinates of the spaceship
    private int aircraftCoordinateX=-1;
    private int aircraftCoordinateY=-1;


    //coordinates of finger
    private int fingerDownX;
    private int move;

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "LevelView created");

        //TODO: Select the aircraft from user's shop
        this.aircraft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rocket_ship),
                100, 100, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // initialize avatar on the screen when start the level
        if (this.aircraftCoordinateX==-1) {
            this.aircraftCoordinateX = (getWidth() / 2) - (aircraft.getWidth() / 2);
            this.aircraftCoordinateY = ((getHeight()) - (aircraft.getHeight() + 50));
            Toast.makeText(getContext(),aircraftCoordinateX + " " + aircraftCoordinateY, Toast.LENGTH_LONG).show();
        }

        // set the screen height
        if (this.screenMaxHeight == 0) {
            this.screenMaxHeight = getHeight()-(aircraft.getHeight());
        }

        //draw the avatar on the screen in the right coordinate after touching the screen
        canvas.drawBitmap(aircraft,this.aircraftCoordinateX,this.aircraftCoordinateY,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //save the finger coordinate
        double fingerCoordindateX = event.getX();

        //make spaceship move
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.fingerDownX = (int)fingerCoordindateX;
                break;
            case MotionEvent.ACTION_MOVE:
                this.move = (int)fingerCoordindateX - fingerDownX;
                this.fingerDownX = (int)fingerCoordindateX;

                //move spaceship only if it stays between borders of screen
                if (this.aircraftCoordinateX+this.move >0 && this.aircraftCoordinateX+this.move <getWidth()-aircraft.getWidth()) {
                    this.aircraftCoordinateX = this.aircraftCoordinateX - this.move;

                    //logs the moves
                    if (this.move >0)
                        Log.i(TAG, "spaceship moves " + this.move + " to right");
                    else if (this.move <0)
                        Log.i(TAG, "spaceship moves " + Math.abs(this.move) + " to Left");
                }

                //update the screen with the new location of the spaceship
                invalidate();
                break;
        }

        return true;
    }

    public void startLevel(Level level) {
        this.level = level;
        Log.i(TAG, "Started playing level #" +level.getLevelNumber());

        //start animating obstacles and coins.
        //game will end when one obstacle hits the spaceship
    }
}
