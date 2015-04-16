package com.camilstaps.rushhour;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by camilstaps on 16-4-15.
 */
public class Board {

    Set<Car> cars = new HashSet<>();

    public static final int DIMENSION = 6;

    private MoveListener moveListener = new MoveListener() {
        @Override
        public void onMove(Car car, int offset) {
            Coordinate newC = car.wouldMoveTo(offset);
            if (newC.getY() > DIMENSION - 1 || newC.getX() > DIMENSION - 1 || newC.getX() < 0 || newC.getY() < 0) return;
            for (Car iter : cars) {
                if (iter.occupies(newC)) {
                    Log.d("Board", "Can't move");
                    return;
                }
            }
            car.move(offset);
        }
    };

    public Board() {
        this(new HashSet<Car>());
    }

    public Board(Set<Car> cars) {
        for (Car car : cars) {
            add(car);
        }
    }

    public void add(Car car) {
        car.setMoveListener(moveListener);
        cars.add(car);
    }

    public void addToLayout(Context context, RelativeLayout layout) {
        for (Car car : cars) {
            layout.addView(car.getImageView(context));
        }
    }

}
