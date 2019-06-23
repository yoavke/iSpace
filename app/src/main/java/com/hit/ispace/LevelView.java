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

    private static final String TAG = LevelView.class.getSimpleName();
    private Bitmap aircraft;
    private int screenMaxHeight=0;
    private int aircraftCoordinateX=-1;
    private int aircraftCoordinateY=-1;

    private int aircraftDistanceDownX;
    private int fingerDistanceDownX;

    private int aircraftDistanceMoveX;
    private int fingerDistanceMoveX;

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
        double x = event.getX();
        double y = event.getY();
        if (x<0 || y<0 || x>getWidth() || y>this.screenMaxHeight) {
            Log.i(TAG, "touched out of the screen (full height: " + screenMaxHeight + ")" + " x: " + x + " y: " + y);
        } else {
            Log.i(TAG, "touched the screen (full height: " + screenMaxHeight + ")" + " x: " + x + " y: " + y);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.fingerDistanceDownX = (int)x;
                break;
            case MotionEvent.ACTION_MOVE:
                this.fingerDistanceMoveX = (int)x;
                if (this.fingerDistanceMoveX-this.fingerDistanceDownX>0) {
                    this.aircraftCoordinateX = this.aircraftCoordinateX+(this.fingerDistanceMoveX-this.fingerDistanceDownX);
                } else {
                    this.aircraftCoordinateX = this.aircraftCoordinateX-(Math.abs(this.fingerDistanceMoveX-this.fingerDistanceDownX));
                }
                if (this.aircraftCoordinateX<0) {
                    this.aircraftCoordinateX=0;
                    
                }
                if (this.aircraftCoordinateX > getWidth()-100) {
                    this.aircraftCoordinateX = getWidth()-100;
                }
                Log.e(TAG, "MOVE: fingerDistanceMoveX: " + this.fingerDistanceMoveX + " aircraftCoordinateX:" + this.aircraftCoordinateX + " aircraftCoordinateY:" + this.aircraftCoordinateY);
                invalidate();
                break;
        }

        return true;
    }
}
