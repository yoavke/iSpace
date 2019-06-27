package com.hit.ispace;

//when implement IElement need to have variable Point topLeft and bottomRight
public interface IElement {
    void setCoordinates(Point topLeft, Point BottomRight);
    Point getLeftTop();
    Point getRightBottom();
}
