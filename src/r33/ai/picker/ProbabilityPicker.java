package r33.ai.picker;

import battleship.interfaces.Position;
import r33.ai.Field;

import java.util.ArrayList;

/**
 * Created by Ejdems on 14/12/2016.
 */
public abstract class ProbabilityPicker {
    protected final Field field;
    protected int[][] scannedGrid;

    public ProbabilityPicker(Field field) {
        this.field = field;
    }

    protected ArrayList<Position> pickBestPositionsFromScannedGrid() {
        int idealValue = getIdealValue();
        return getPositionsWithSameValue(idealValue);
    }

    protected int getIdealValue() {
        int highestValue = 0;
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (scannedGrid[x][y] > highestValue) {
                    highestValue = scannedGrid[x][y];
                }
            }
        }
        return highestValue;
    }

    protected ArrayList<Position> getPositionsWithSameValue(int value) {
        ArrayList<Position> bestShotPositions = new ArrayList<>();
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (scannedGrid[x][y] == value) {
                    bestShotPositions.add(new Position(x, y));
                }
            }
        }
        return bestShotPositions;
    }

    public void printGrid() {
        for (int y = field.getY()-1; y >= 0; y--) {
            System.out.print(y + " ");
            for (int x = 0; x < field.getX(); x++) {
                if (scannedGrid[x][y] < 10) {
                    System.out.print(" |" + scannedGrid[x][y] + "  | ");
                } else if (scannedGrid[x][y] < 100) {
                    System.out.print(" |" + scannedGrid[x][y] + " | ");
                } else {
                    System.out.print(" |" + scannedGrid[x][y] + "| ");
                }
            }
            System.out.println("");
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.print("   ");
        for (int x = 0; x < field.getX(); x++) {
            System.out.print("  "+x+"    ");
        }
        System.out.println("\n----------------------------------------\n");
    }
}
