package com.hit.ispace;

import android.util.Log;
import java.util.ArrayList;
import java.util.TimerTask;

public final class ElementFactory extends TimerTask {

    private static final String TAG = ElementFactory.class.getSimpleName();

    //divide the screen to this number.
    private static final int SCREEN_DIVIDOR = 6;

    private ArrayList<String> obstacleTypes;
    private ArrayList<IElement> elemList = new ArrayList<>();

    public ElementFactory(ArrayList<String> obstacleTypes) {
        this.obstacleTypes = obstacleTypes;
    }

    //will be called every few seconds by Timer
    @Override
    public void run() {
        this.createNewElements();
    }

    //called by run method
    public void createNewElements() {
        int i;  // loop counter
        int listSize; // number of elements in the list
        int topX, topY, bottomX, bottomY; // (x,y) for the points
        Point topLeft, bottomRight; // the points of the elements
        IElement tempElem; //a temporary element

        for (i=0; i<6; i++) {
            //used to know if need to create new row of elements (points at top of the screen)
            listSize = elemList.size();

            //select a random element
            CSettings.FlyingElements flyingElem = CSettings.FlyingElements.values()[(int) (Math.random() * CSettings.FlyingElements.values().length)];

            //create the randomized element
            switch (flyingElem) {
                case SPACE:
                    //no elements in list:
                    if (elemList.isEmpty() || (listSize % 6 == 0)) {
                        topX = 25;
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Space(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Space element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Coin(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Coin element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Goodbomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: GoodBomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Rock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Rock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");

                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new SuperRock(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: SuperRock element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
                        topY = 100;
                        bottomX = topX + CSettings.Dimension.ELEMENT_SIZE;
                        bottomY = topY + CSettings.Dimension.ELEMENT_SIZE;

                        topLeft = new Point(topX, topY);
                        bottomRight = new Point(bottomX, bottomY);

                        tempElem = new Bomb(topLeft, bottomRight);
                        elemList.add(tempElem);
                        Log.e(TAG, "FACTORY: Bomb element created at ("+topLeft.getX()+","+topLeft.getY()+") & ("+bottomRight.getX()+","+bottomRight.getY()+")");
                        //list is not empty
                    } else {
                        topX = elemList.get(listSize-1).getRightBottom().getX()+50;
                        topY = elemList.get(listSize-1).getLeftTop().getY();
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
        }
    }


}
