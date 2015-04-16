package com.camilstaps.rushhour;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by camilstaps on 16-4-15.
 */
public class Car {

    //private int startCoordinate.getX(), startCoordinate.getY(), endCoordinate.getX(), endCoordinate.getY();
    private Coordinate startCoordinate, endCoordinate;
    private final int colour;

    private static final int SIZE = 50;
    private static final int MARGIN = 5;

    private MoveListener moveListener;

    ImageView iv;

    public Car(Coordinate start, Coordinate end) {
        startCoordinate = start;
        endCoordinate = end;
        this.colour = Color.BLACK;
    }

    public Car(Coordinate start, Coordinate end, int colour) {
        startCoordinate = start;
        endCoordinate = end;
        this.colour = colour;
    }

    public int getColour() { return colour; }

    public void setMoveListener(MoveListener listener) {
        moveListener = listener;
    }

    private int getWidth() {
        return (SIZE + MARGIN) * (endCoordinate.getX() - startCoordinate.getX()) + SIZE;
    }

    private int getHeight() {
        return (SIZE + MARGIN) * (endCoordinate.getY() - startCoordinate.getY()) + SIZE;
    }

    public void setImageViewMargins() {
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(getWidth(), getHeight());
        marginParams.setMargins(startCoordinate.getX() * (SIZE + MARGIN), startCoordinate.getY() * (SIZE + MARGIN), MARGIN, MARGIN);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        iv.setLayoutParams(layoutParams);
    }

    public ImageView getImageView(Context context) {

        iv = new ImageView(context);
        iv.setBackgroundColor(colour);
        int width = getWidth();
        int height = getHeight();
        iv.setMinimumWidth(width);
        iv.setMinimumHeight(height);

        setImageViewMargins();

        final GestureDetector gdt = new GestureDetector(new GestureListener());
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });

        return iv;
    }

    public boolean canMoveHorizontally() {
        return startCoordinate.getY() == endCoordinate.getY();
    }

    public boolean canMoveVertically() {
        return startCoordinate.getX() == endCoordinate.getX();
    }

    public void move(int offset) {
        if (canMoveHorizontally()) {
            moveHorizontally(offset);
        } else {
            moveVertically(offset);
        }
    }

    public void moveHorizontally(int offset) {
        startCoordinate.move(offset, 0);
        endCoordinate.move(offset, 0);

        setImageViewMargins();
    }

    public void moveVertically(int offset) {
        startCoordinate.move(0, offset);
        endCoordinate.move(0, offset);

        setImageViewMargins();
    }

    public Coordinate wouldMoveTo(int offset) {
        Coordinate movedCoordinate;
        if (offset < 0) {
            movedCoordinate = new Coordinate(startCoordinate);
        } else {
            movedCoordinate = new Coordinate(endCoordinate);
        }
        if (canMoveHorizontally()) {
            movedCoordinate.move(offset, 0);
        } else {
            movedCoordinate.move(0, offset);
        }
        return movedCoordinate;
    }

    public boolean occupies(Coordinate c) {
        return c.getX() >= startCoordinate.getX() && c.getX() <= endCoordinate.getX() && c.getY() >= startCoordinate.getY() && c.getY() <= endCoordinate.getY();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = SIZE;
        private static final int SWIPE_THRESHOLD_VELOCITY = SIZE;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && canMoveHorizontally()) {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                    moveListener.onMove(Car.this, -1);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                    moveListener.onMove(Car.this, 1);
                }
            }

            if (Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && canMoveVertically()) {
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                    moveListener.onMove(Car.this, -1);
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                    moveListener.onMove(Car.this, 1);
                }
            }

            return true;
        }
    }

}
