package com.hit.ispace;

import android.graphics.Bitmap;

//when implement IElement need to have variable Point topLeft and bottomRight
public interface IElement {
    void setCoordinates(Point topLeft, Point BottomRight);
    Point getLeftTop();
    Point getRightBottom();
    String sayMyName();
    void setBitmapSrc(Bitmap bitmap);
    Bitmap getBitmapSrc();
    void setHit();
    boolean getHit();
}
