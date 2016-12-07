package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class TargetMode extends FieldScanner implements Mode {
    private Fleet enemyShips;

    public TargetMode(Field field) {
        this.field = field;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        this.enemyShips = enemyShips;
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        printGrid();
        return getBestShot();
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        Position base = field.getLastShot();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int y = getStartIndex(base.y,ship); y <= base.y; y++) {
                if (ship.size() + y <= field.getY()) {
                    placeShipVerticaly(ship, base.x, y);
                }
            }
            for (int x = getStartIndex(base.x,ship); x <= base.x; x++) {
                if (ship.size() + x <= field.getX()) {
                    placeShipHorizontaly(ship, base.y, x);
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

    public boolean hadSafelySunk(Fleet enemyShips) {
        return enemyShips.getNumberOfShips() < this.enemyShips.getNumberOfShips();
        // TODO: check for touching ships strategy ("L" or "T" shape)
//        return true;
    }

    @Override
    protected boolean isHit(int x, int y) {
        return field.getHit(x,y) == Field.HIT;
    }

    @Override
    protected int getBestShotValue() {
        int bestShotValue = 0;
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (grid[x][y] > bestShotValue && notBasePosition(x, y)) {
                    bestShotValue = grid[x][y];
                }
            }
        }
        return bestShotValue;
    }

    private boolean notBasePosition(int x, int y) {
        return field.getLastShot().compareTo(new Position(x,y)) != 0;
    }
}
