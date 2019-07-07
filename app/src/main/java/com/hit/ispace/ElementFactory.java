package com.hit.ispace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ElementFactory implements Runnable{

    private static final String TAG = ElementFactory.class.getSimpleName();

    //divide the screen to this number.
    private static final int SCREEN_DIVIDOR = 6;

    private ArrayList<String> obstacleTypes;
    private CopyOnWriteArrayList<IElement> elemList = new CopyOnWriteArrayList<>();

    //logical size of the list (since the beginning, even if we remove elements)
    //even when element is destroyed, we dont decrease the size
    //if we depend of the list.size() or decrease listSize, we'll get a bug of % 6
    private int listSize;
    private int collectedSize;

    public CopyOnWriteArrayList<IElement> getElemList() {
        return elemList;
    }

    public int getCollectedSize() {
        return collectedSize;
    }

    public void setCollectedSize() {
        this.collectedSize++;
    }

    public ElementFactory(ArrayList<String> obstacleTypes) {
        this.obstacleTypes = obstacleTypes;
        this.listSize = 0;
        this.collectedSize = 0;
    }

    //called by run method
    public void createNewElements() {
        int i;  // loop counter
        int topX, topY, bottomX, bottomY; // (x,y) for the points
        Point topLeft, bottomRight; // the points of the elements
        IElement tempElem; //a temporary element

        // we force creating at least one space in a row
        int randomInitSpace = (int)(Math.random()*6);
        CSettings.FlyingElements flyingElem;

        for (i=0; i<6; i++) {

            if (i==randomInitSpace) {
                //the "at least" one space
                Log.i(TAG, "creating the 'at least' one space");
                flyingElem = CSettings.FlyingElements.SPACE;
            } else {
                //select a random element
                int selectedByPriority = (int)(Math.random()*100);

                //2% space
                if (selectedByPriority>=0 && selectedByPriority<=1) {
                    flyingElem = CSettings.FlyingElements.SPACE;
                }
                //2% goodbomb
                else if (selectedByPriority>=2 && selectedByPriority<=3) {
                    flyingElem = CSettings.FlyingElements.GOODBOMB;
                }
                //22% bomb
                else if (selectedByPriority>=4 && selectedByPriority<=25) {
                    flyingElem = CSettings.FlyingElements.BOMB;
                }
                //30% rock
                else if (selectedByPriority>=26 && selectedByPriority<=55) {
                    flyingElem = CSettings.FlyingElements.ROCK;
                }
                //30% superrock
                else if (selectedByPriority>=56 && selectedByPriority<=85) {
                    flyingElem = CSettings.FlyingElements.SUPERROCK;
                }
                //15% coin
                else {
                    flyingElem = CSettings.FlyingElements.COIN;
                }
            }

            //create the randomized element
            switch (flyingElem) {
                case SPACE:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Space(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Space element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Space(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Space element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                case COIN:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Coin(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Coin element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Coin(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Coin element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                case GOODBOMB:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Goodbomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: GoodBomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Goodbomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: GoodBomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                case ROCK:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Rock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Rock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");

                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Rock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Rock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                case SUPERROCK:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new SuperRock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: SuperRock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new SuperRock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: SuperRock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                case BOMB:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = CSettings.Margin.TOP;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Bomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Bomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-this.getCollectedSize()-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-this.getCollectedSize()-1).getLeftTop().getY();
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Bomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Bomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                    }
                    break;
                default:
                    Log.e(TAG, "No such element");
            }

            //new element created - increase size of list
            this.listSize++;
        }
    }


    @Override
    public void run() {
        this.createNewElements();
    }
}
