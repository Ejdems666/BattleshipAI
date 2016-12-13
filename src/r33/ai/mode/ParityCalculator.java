package r33.ai.mode;

import battleship.interfaces.Position;

import java.util.ArrayList;

/**
 * Created by Ejdems on 11/12/2016.
 */
public class ParityCalculator {
    private int parityCheck;

    public ParityCalculator(int parityCheck) {
        this.parityCheck = parityCheck;
    }

    public void setParityCheck(int parityCheck) {
        this.parityCheck = parityCheck;
    }

    public Position getBestShotPosition(ArrayList<Position> bestShotPositions) {
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
        return parityCheck >= 0;
    }
    // Position is odd or even, depending on parityCheck settings
    private boolean positionIsInParity(Position bestShotPosition) {
        return Math.abs(bestShotPosition.x - bestShotPosition.y) % 2 == parityCheck;
    }
}
