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

public class LevelView extends View {

    private static final String TAG = LevelView.class.getSimpleName();
    private Bitmap aircraft;
    private int screenMaxHeight;

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "LevelView created");

        //TODO: Select the aircraft from user's shop
        this.aircraft = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.rocket_ship),
                100, 100, false);
        this.screenMaxHeight = getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(aircraft,((getWidth()/2)-(aircraft.getWidth()/2)),((getHeight())-(aircraft.getHeight()+50)),null);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        double x = event.getX();
        double y = event.getY();
        if (x<0 || y<0) {
            Log.e(TAG, "touched out of the screen (full height: " + screenMaxHeight + ")" + " x: " + x + " y: " + y);
        } else {
            Log.i(TAG, "touched the screen (full height: " + screenMaxHeight + ")" + " x: " + x + " y: " + y);
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
