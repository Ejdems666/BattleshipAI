package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.MyShots;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode extends BestShotCalculator implements Mode {
    public HuntMode(MyShots myShots, ParityCalculator parityCalculator) {
        super(parityCalculator);
        this.myShots = myShots;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        grid = new int[myShots.getX()][myShots.getY()];
        scanGrid(enemyShips);
        return getBestShot();
    }

    private void scanGrid(Fleet enemyShips) {
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            calculateShipsProbabilityInGrid(enemyShips.getShip(i));
        }
    }
    private void calculateShipsProbabilityInGrid(Ship ship) {
        for (int x = 0; x < myShots.getX(); x++) {
            for (int y = 0; y < myShots.getY(); y++) {
                if (canPlaceShipVertically(ship, x, y)) {
                    stampShipsProbabilityInGridVertically(ship, x, y);
                }if (canPlaceShipHorizontally(ship, x, y)) {
                    stampShipsProbabilityInGridHorizontally(ship, x, y);
                }
            }
        }
    }
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > myShots.getY()) {
            return false;
        }
        for (int l = y; l < ship.size() + y; l++) {
            if (wasShot(x,l)) {
                return false;
            }
        }
        return true;
    }
    private boolean wasShot(int x, int y) {
        return myShots.getHit(x,y) != MyShots.NO_HIT;
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > myShots.getX()) {
            return false;
        }
        for (int l = x; l < ship.size() + x; l++) {
            if (wasShot(l,y)) {
                return false;
            }
        }
        return true;
    }
    private void stampShipsProbabilityInGridVertically(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            grid[x][l] += 1;
        }
    }
    private void stampShipsProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            grid[l][y] += 1;
        }
    }

}