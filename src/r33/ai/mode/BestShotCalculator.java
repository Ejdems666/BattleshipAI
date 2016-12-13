package r33.ai.mode;
import battleship.interfaces.Position;
import r33.ai.MyShots;

import java.util.ArrayList;

/**
 * Created by Ejdems on 07/12/2016.
 */
public abstract class BestShotCalculator {
    protected int[][] grid;
    protected MyShots myShots;
    protected final ParityCalculator parityCalculator;

    public BestShotCalculator(ParityCalculator parityCalculator) {
        this.parityCalculator = parityCalculator;
    }

    public void printGrid() {
        for (int yy = myShots.getY()-1; yy >= 0; yy--) {
            System.out.print(yy + " ");
            for (int xx = 0; xx < myShots.getX(); xx++) {
                if (grid[xx][yy] < 10) {
                    System.out.print(" |" + grid[xx][yy] + " | ");
                } else {
                    System.out.print(" |" + grid[xx][yy] + "| ");
                }
            }
            System.out.println("");
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.print("   ");
        for (int xx = 0; xx < myShots.getX(); xx++) {
            System.out.print(" "+xx+"    ");
        }
        System.out.println("\n----------------------------------------\n");
    }

    protected Position getBestShot() {
        int bestProbabilityValue = getBestProbabilityValue();
        ArrayList<Position> bestShotPositions = getBestShotPositions(bestProbabilityValue);
        return parityCalculator.getBestShotPosition(bestShotPositions);
    }
    private int getBestProbabilityValue() {
        int bestShotValue = 0;
        for (int x = 0; x < myShots.getX(); x++) {
            for (int y = 0; y < myShots.getY(); y++) {
                if (grid[x][y] > bestShotValue) {
                    bestShotValue = grid[x][y];
                }
            }
        }
        return bestShotValue;
    }

    private ArrayList<Position> getBestShotPositions(int bestProbabilityValue) {
        ArrayList<Position> bestShotPositions = new ArrayList<>();
        for (int x = 0; x < myShots.getX(); x++) {
            for (int y = 0; y < myShots.getY(); y++) {
                if (grid[x][y] == bestProbabilityValue) {
                    bestShotPositions.add(new Position(x, y));
                }
            }
        }
        return bestShotPositions;
    }
}
