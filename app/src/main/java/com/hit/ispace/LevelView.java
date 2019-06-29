package com.hit.ispace;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class LevelView extends View {

    //TAG
    private static final String TAG = LevelView.class.getSimpleName();

    private Level level=null;

    private int screenWidth=0;
    private int screenHeight=0;

    //coordinates of finger
    private int fingerDownX;

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "LevelView created");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.screenWidth==0) {
            this.screenWidth = getWidth();
            this.screenHeight = getHeight();
        }

        //draw the avatar on the screen in the right coordinate after touching the screen
        if(level!=null) {
            canvas.drawBitmap(this.level.spaceship.getSpaceshipBitmap(), this.level.spaceship.getLeftTop().getX(), this.level.spaceship.getLeftTop().getY(), null);
        }
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
            case CSettings.LevelTypes.FREE_STYLE:
                //if spaceship will remain in border of game after moving, make the move
                if (this.level.spaceship.getLeftTop().getX()+move >= 0 && this.level.spaceship.getLeftTop().getX()+move <= getWidth()-level.spaceship.getSpaceshipBitmap().getWidth()) {

                    //set the new coordinate of spaceship
                    this.level.spaceship.getLeftTop().setX(this.level.spaceship.getLeftTop().getX() + move);
                    this.level.spaceship.getRightBottom().setX(this.level.spaceship.getLeftTop().getX() + this.level.spaceship.getSpaceshipWidth());

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");

                    //logs the moves
                    if (move > 0)
                        Log.i(TAG, "spaceship moves " + move + " to right");
                    else if (move < 0)
                        Log.i(TAG, "spaceship moves " + Math.abs(move) + " to Left");

                }
                //force spaceship stay in border of game

                else if (this.level.spaceship.getLeftTop().getX()+move < 0) {
                    //set the new coordinate of spaceship. setting the X coordinate, Y remain the same
                    this.level.spaceship.getLeftTop().setX(0);
                    this.level.spaceship.getRightBottom().setX(CSettings.Dimension.SPACESHIP_SIZE);

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");


                } else if (this.level.spaceship.getLeftTop().getX()+move > getWidth()-this.level.spaceship.getSpaceshipBitmap().getWidth()) {

                    this.level.spaceship.getLeftTop().setX(getWidth()-this.level.spaceship.getSpaceshipBitmap().getWidth());
                    this.level.spaceship.getRightBottom().setX(this.level.spaceship.getLeftTop().getX()+100);

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");


                }
                break;
            //getting sick - moving in the wrong direction - left goes right and vice versa
            case CSettings.LevelTypes.GETTING_SICK:
                //if spaceship will remain in border of game after moving, make the move
                if (this.level.spaceship.getLeftTop().getX() - move >= 0 && this.level.spaceship.getLeftTop().getX() - move <= getWidth()-level.spaceship.getSpaceshipBitmap().getWidth()) {

                    //set the new coordinate of spaceship
                    this.level.spaceship.getLeftTop().setX(this.level.spaceship.getLeftTop().getX() - move);
                    this.level.spaceship.getRightBottom().setX(this.level.spaceship.getLeftTop().getX() - (move + this.level.spaceship.getSpaceshipWidth()));

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");


                    //logs the moves
                    if (move > 0)
                        Log.i(TAG, "spaceship moves " + move + " to right");
                    else if (move < 0)
                        Log.i(TAG, "spaceship moves " + Math.abs(move) + " to Left");

                }
                //force spaceship stay in border of game

                else if (this.level.spaceship.getLeftTop().getX()+move < 0) {
                    //set the new coordinate of spaceship. setting the X coordinate, Y remain the same
                    this.level.spaceship.getLeftTop().setX(0);
                    this.level.spaceship.getRightBottom().setX(CSettings.Dimension.SPACESHIP_SIZE);

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");


                } else if (this.level.spaceship.getLeftTop().getX()+move > getWidth()-this.level.spaceship.getSpaceshipBitmap().getWidth()) {

                    this.level.spaceship.getLeftTop().setX(getWidth()-this.level.spaceship.getSpaceshipBitmap().getWidth());
                    this.level.spaceship.getRightBottom().setX(this.level.spaceship.getLeftTop().getX()+100);

                    Log.e(TAG, "REMOVETHIS: Coordinates: TOP LEFT(" + this.level.spaceship.getLeftTop().getX() + "," + this.level.spaceship.getLeftTop().getY() + ") BOTTOM RIGHT(" + this.level.spaceship.getRightBottom().getX() + "," + this.level.spaceship.getRightBottom().getY() +")");

                }
                break;
            default:
                Log.e(TAG, "no such level type!");
        }
    }

    public void startLevel(Level level) {
        this.level = level;
        Log.i(TAG, "Painting spaceship on the screen");
        invalidate();
        //TODO: Select the aircraft from user's shop and change hardcoded 100 to class variable
        this.level.spaceship.setSpaceshipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rocket_ship),100, 100, false));

        //TODO remove 2 lines of code of width and height
        this.screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Point topLeft = new Point((this.screenWidth / 2) - (this.level.spaceship.getSpaceshipBitmap().getWidth() / 2),this.screenHeight-200);
        //TODO change hardcoded 100 to class variable in Dimension class
        Point bottomRight = new Point(topLeft.getX()+100, topLeft.getY()+100);
        // initialize avatar on the screen when start the level
        this.level.spaceship.setCoordinates(topLeft,bottomRight);

        invalidate();

        Log.i(TAG, "Started playing level #" +level.getLevelType());

        //start animating obstacles and coins.
        Timer timer = new Timer();
        Timer timer2 = new Timer();
        TimerTask animator = new TimerTask() {
            @Override
            public void run() {
                for (IElement elem : LevelView.this.level.elementFactory.getElemList()) {
                    Point newTopLeft = new Point(elem.getLeftTop().getX(),elem.getLeftTop().getY()+1);
                    Point newBottomRight = new Point(elem.getRightBottom().getX(),elem.getRightBottom().getY()+1);

                    elem.setCoordinates(newTopLeft,newBottomRight);
                    Log.e(TAG, "Animated " + elem.sayMyName() + " new coordinates: ("+elem.getLeftTop().getX()+","+elem.getLeftTop().getY()+")("+elem.getRightBottom().getX()+","+elem.getRightBottom().getY()+")");
                }
            }
        };
        timer.schedule(this.level.elementFactory, 1500, 3000);
        timer2.schedule(animator,1500,300);
        invalidate();

        //game will end when one obstacle hits the spaceship
    }
}
