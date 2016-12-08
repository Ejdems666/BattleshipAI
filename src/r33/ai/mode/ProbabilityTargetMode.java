package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;
import java.util.ArrayList;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class ProbabilityTargetMode extends FieldScanner implements Mode {
    private ArrayList<Ship> previousShips;
    private ArrayList<Position> shotPositions;
    private Position baseHit;

    public ProbabilityTargetMode(Field field) {
        this.field = field;
        baseHit = field.getLastShot();
        shotPositions = new ArrayList<>();
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        storeFleetData(enemyShips);
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return getBestShot();
    }
    private void storeFleetData(Fleet enemyShips) {
        previousShips = new ArrayList<>();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            previousShips.add(enemyShips.getShip(i));
        }
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
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > field.getY()) {
            return false;
        }
        for (int l = y; l < ship.size() + y; l++) {
            if (isHit(x,l)) {
                return false;
            }
        }
        return true;
    }
    private boolean isHit(int x, int y) {
        return field.getHit(x, y) == Field.HIT;
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > field.getX()) {
            return false;
        }
        for (int l = x; l < ship.size() + x; l++) {
            if (isHit(l,y)) {
                return false;
            }
        }
        return true;
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

    public boolean hadSunk(Fleet enemyShips) {
        return enemyShips.getNumberOfShips() < previousShips.size();
    }
    public boolean hadSafelySunk(Fleet enemyShips) {
        Ship shotShip = null;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            if(previousShips.indexOf(enemyShips.getShip(i)) < 0) {
                shotShip = enemyShips.getShip(i);
            }
        }
        if (shotShip == null) {
            return false;
        }
        return true;
//        if(shotPositions.size() != shotShip.size()) {
//            Position lastShot = shotPositions.get(shotShip.size()-1);
//        }
    }

    public void setBaseHit(Position baseHit) {
        this.baseHit = baseHit;
    }

    public void registerHit(Position position) {
        if(isInLineWithBase(position)) {

        }
        shotPositions.add(position);
    }

    private boolean isInLineWithBase(Position position) {
        return position.x == baseHit.x || position.y == baseHit.y;
    }
}
