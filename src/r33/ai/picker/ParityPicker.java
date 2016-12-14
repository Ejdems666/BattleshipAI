package r33.ai.picker;

import battleship.interfaces.Position;

import java.util.ArrayList;

/**
 * Created by Ejdems on 11/12/2016.
 */
public class ParityPicker {
    private int parityMode;

    public ParityPicker(int parityMode) {
        this.parityMode = parityMode;
    }

    public void setParityMode(int parityMode) {
        this.parityMode = parityMode;
    }

    public Position getFirstValidParityPosition(ArrayList<Position> bestShotPositions) {
        if (bestShotPositions.size() > 1 && implementParity()) {
            for (Position bestShotPosition : bestShotPositions) {
                if (positionIsInParity(bestShotPosition)) {
                    return bestShotPosition;
                }
            }
        }
        return bestShotPositions.get(0);
    }
    private boolean implementParity() {
        return parityMode >= 0;
    }
    // Position is odd or even, depending on parityMode settings
    private boolean positionIsInParity(Position bestShotPosition) {
        return Math.abs(bestShotPosition.x - bestShotPosition.y) % 2 == parityMode;
    }
}
