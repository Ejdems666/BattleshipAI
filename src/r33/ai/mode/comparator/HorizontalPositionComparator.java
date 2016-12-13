package r33.ai.mode.comparator;

import battleship.interfaces.Position;

import java.util.Comparator;

/**
 * Created by Ejdems on 11/12/2016.
 */
public class HorizontalPositionComparator implements Comparator<Position> {
    @Override
    public int compare(Position o1, Position o2) {
        return o1.x - o2.x;
    }
}
