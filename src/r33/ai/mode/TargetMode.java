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
        return null;
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        Position base = field.getLastShot();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int y = getStartIndex(base.y,ship); y < getEndIndex(base.y,ship); y++) {
                placeShipVerticaly(ship,base.x,y,Field.SHIP_HIT);
            }
            for (int x = getStartIndex(base.x,ship); x < getEndIndex(base.x,ship); x++) {
                placeShipHorizontaly(ship,base.y,x,Field.SHIP_HIT);
            }
        }
    }

    private double getEndIndex(int baseIndex, Ship ship) {
        int index = baseIndex + ship.size() - 1;
        if(index > field.getY()) {
            return field.getY();
        }
        return index;
    }

    private int getStartIndex(int baseIndex, Ship ship) {
        int index = baseIndex - ship.size() + 1;
        if (index < 0) {
            return 0;
        }
        return index;
    }

    public boolean hadSafelySunk(Fleet enemyShips) {
        // TODO: check for touching ships strategy ("L" or "T" shape)
        return true;
    }
}
