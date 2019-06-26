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
        double fingerCoordinateX = event.getX();

        int move;

        //make spaceship move
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.fingerDownX = (int)fingerCoordinateX;
                break;
            case MotionEvent.ACTION_MOVE:
                move = (int)fingerCoordinateX - fingerDownX;
                this.fingerDownX = (int)fingerCoordinateX;

                moveSpaceShip(move);

                //update the screen with the new location of the spaceship
                invalidate();
                break;
        }

        return true;
    }

    private void moveSpaceShip(int move) {
        //the spaceship moving depends on level type
        switch (level.getLevelType())
        {
            //free style - moving in natural way - left goes left and right goes right
            case Settings.LevelTypes.FREE_STYLE:
                if (this.aircraftCoordinateX+move >= 0 && this.aircraftCoordinateX+move <= getWidth()-aircraft.getWidth()) {
                    //set the new coordinate of spaceship
                    this.aircraftCoordinateX = this.aircraftCoordinateX + move;

                    //logs the moves
                    if (move > 0)
                        Log.i(TAG, "spaceship moves " + move + " to right");
                    else if (move < 0)
                        Log.i(TAG, "spaceship moves " + Math.abs(move) + " to Left");
                }
                //move spaceship only if it stays between borders of screen
                else if (this.aircraftCoordinateX+move < 0) {
                    this.aircraftCoordinateX = 0;
                } else if (this.aircraftCoordinateX+move > getWidth()-aircraft.getWidth()) {
                    this.aircraftCoordinateX = getWidth()-aircraft.getWidth();
                }
                break;
            //getting sick - moving in the wrong direction - left goes right and vice versa
            case Settings.LevelTypes.GETTING_SICK:
                if (this.aircraftCoordinateX-move >= 0 && this.aircraftCoordinateX-move <= getWidth()-aircraft.getWidth()) {
                    //set the new coordinate of spaceship
                    this.aircraftCoordinateX = this.aircraftCoordinateX - move;

                    //logs the moves
                    if (move >0)
                        Log.i(TAG, "spaceship moves " + move + " to left");
                    else if (move <0)
                        Log.i(TAG, "spaceship moves " + Math.abs(move) + " to right");
                }
                //move spaceship only if it stays between borders of screen
                else if (this.aircraftCoordinateX+move < 0) {
                    this.aircraftCoordinateX = 0;
                } else if (this.aircraftCoordinateX+move > getWidth()-aircraft.getWidth()) {
                    this.aircraftCoordinateX = getWidth()-aircraft.getWidth();
                }
                break;
            default:
                Log.e(TAG, "no such level type!");
        }
    }

    public void startLevel(Level level) {
        this.level = level;
        Log.i(TAG, "Started playing level #" +level.getLevelType());

        //start animating obstacles and coins.
        //game will end when one obstacle hits the spaceship
    }
}
