package r33.ai.mode;
import battleship.interfaces.Ship;
import r33.ai.Field;

/**
 * Created by Ejdems on 07/12/2016.
 */
public abstract class FieldScanner {
    protected int[][] grid;
    protected Field field;

    protected void placeShipVerticaly(Ship ship, int x, int y, int allowedHit) {
        for (int l = y; l < ship.size() + y; l++) {
            if (field.isHit(x,l,allowedHit)) {
                return;
            }
        }
        for (int l = y; l < ship.size() + y; l++) {
            grid[x][l] += 1;
        }
    }

    protected void placeShipHorizontaly(Ship ship, int y, int x, int allowedHit) {
        for (int l = x; l < ship.size() + x; l++) {
            if (field.isHit(l,y,allowedHit)) {
                return;
            }
        }
        for (int l = x; l < ship.size() + x; l++) {
            grid[l][y] += 1;
        }
    }

    protected void printGrid() {
        for (int xx = 0; xx < field.getX(); xx++) {
            System.out.println("");
            for (int yy = 0; yy < field.getY(); yy++) {
                if (grid[xx][yy] < 10) {
                    System.out.print(" |" + grid[xx][yy] + " | ");
                } else {
                    System.out.print(" |" + grid[xx][yy] + "| ");
                }
            }
        }
        System.out.println("\n----------------------------------------\n");
    }
}
