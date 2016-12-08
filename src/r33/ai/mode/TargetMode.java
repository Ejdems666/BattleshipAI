package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class TargetMode extends FieldScanner implements Mode {
    private int enemyFleetSize;
    private Position baseHit;

    public TargetMode(Field field) {
        this.field = field;
        baseHit = field.getLastShot();
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        // TODO: copy for L and T shape check
        this.enemyFleetSize = enemyShips.getNumberOfShips();
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return getBestShot();
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int y = getStartIndex(baseHit.y, ship); y <= baseHit.y; y++) {
                if (canPlaceShipVertically(ship, baseHit.x, y)) {
                    stampShipProbabilityInGridVertically(ship, baseHit.x, y);
                }
            }
            for (int x = getStartIndex(baseHit.x, ship); x <= baseHit.x; x++) {
                if (canPlaceShipHorizontally(ship, x, baseHit.y)) {
                    stampShipProbabilityInGridHorizontally(ship, x, baseHit.y);
                }
            }
        }
    }
    private int getStartIndex(int baseIndex, Ship ship) {
        int index = baseIndex - ship.size() + 1;
        if (index < 0) {
            return 0;
        }
        return index;
    }
    private void stampShipProbabilityInGridVertically(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            if(notWhereShipWasHit(x,l)) {
                grid[x][l] += 1;
            }
        }
    }
    private void stampShipProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            if (notWhereShipWasHit(l,y)) {
                grid[l][y] += 1;
            }
        }
    }
    private boolean notWhereShipWasHit(int x, int y) {
        return field.getHit(x,y) == Field.NO_HIT;
    }

    public boolean hadSafelySunk(Fleet enemyShips) {
        return enemyShips.getNumberOfShips() < this.enemyFleetSize;
        // TODO: check for touching ships strategy ("L" or "T" shape)
//        return true;
    }

    @Override
    protected boolean isHit(int x, int y) {
        return field.getHit(x, y) == Field.HIT;
    }

    public void setBaseHit(Position baseHit) {
        this.baseHit = baseHit;
    }
}
