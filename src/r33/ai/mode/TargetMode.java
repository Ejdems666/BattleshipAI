package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;
import r33.ai.mode.comparator.HorizontalPositionComparator;
import r33.ai.mode.comparator.VerticalPositionComparator;

import java.util.*;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class TargetMode extends BestShotCalculator implements Mode {
    private ArrayList<Ship> previousShips;
    private TreeSet<Position> targetedShipHits;
    private Comparator<Position> comparator;
    private Position baseHit;
    private ArrayList<Position> otherShipsHitFragments = new ArrayList<>();

    public TargetMode(Field field, ParityCalculator parityCalculator, Position baseHit) {
        super(parityCalculator);
        this.field = field;
        this.baseHit = baseHit;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        previousShips = copyFleet(enemyShips);
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return getBestShot();
    }

    private ArrayList<Ship> copyFleet(Fleet enemyShips) {
        ArrayList<Ship> fleet = new ArrayList<>();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            fleet.add(enemyShips.getShip(i));
        }
        return fleet;
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            if (targetedShipHits == null) {
                placeShipAroundBaseHit(ship, baseHit);
            } else {
                placeShipAroundinLineHitPositionsAsBaseHits(ship);
                placeShipThroughAllInlineHitPositions(ship);
            }
        }
    }
    private void placeShipAroundBaseHit(Ship ship, Position baseHit) {
        for (int y = getStartCoordinate(baseHit.y, ship); y <= baseHit.y; y++) {
            if (canPlaceShipVertically(ship, baseHit.x, y)) {
                stampShipsProbabilityInGridVertically(ship, baseHit.x, y);
            }
        }
        for (int x = getStartCoordinate(baseHit.x, ship); x <= baseHit.x; x++) {
            if (canPlaceShipHorizontally(ship, x, baseHit.y)) {
                stampShipsProbabilityInGridHorizontally(ship, x, baseHit.y);
            }
        }
    }
    private void placeShipAroundinLineHitPositionsAsBaseHits(Ship ship) {
        for (Position baseHit : targetedShipHits) {
            placeShipAroundBaseHit(ship, baseHit);
        }
    }
    private int getStartCoordinate(int baseCoordinate, Ship ship) {
        int index = baseCoordinate - ship.size() + 1;
        if (index < 0) {
            return 0;
        }
        return index;
    }
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > field.getY()) {
            return false;
        }
        for (int l = y; l < ship.size() + y; l++) {
            if (wasShotBefore(x,l)) {
                return false;
            }
        }
        return true;
    }
    private boolean wasShotBefore(int x, int y) {
        return field.getHit(x,y) == Field.MISS || field.getHit(x,y) == Field.HIT_SUNK;
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > field.getX()) {
            return false;
        }
        for (int l = x; l < ship.size() + x; l++) {
            if (wasShotBefore(l,y)) {
                return false;
            }
        }
        return true;
    }
    private void stampShipsProbabilityInGridVertically(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            if(wasNoHit(x,l)) {
                grid[x][l] += 1;
            }
        }
    }
    private boolean wasNoHit(int x, int y) {
        return field.getHit(x,y) == Field.NO_HIT;
    }
    private void stampShipsProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            if(wasNoHit(l,y)) {
                grid[l][y] += 1;
            }
        }
    }

    private void placeShipThroughAllInlineHitPositions(Ship ship) {
        if(ship.size() <= targetedShipHits.size()) return;
        if (targetIsPlacedVertically()) {
            for (int y = getStartCoordinate(targetedShipHits.last().y, ship);
                 y <= targetedShipHits.first().y;
                 y++) {
                if(canPlaceShipVertically(ship,baseHit.x,y)) {
                    stampShipsProbabilityInGridVertically(ship,baseHit.x,y);
                }
            }
        } else {
            for (int x = getStartCoordinate(targetedShipHits.last().x, ship);
                 x <= targetedShipHits.first().x;
                 x++) {
                if(canPlaceShipHorizontally(ship,x,baseHit.y)) {
                    stampShipsProbabilityInGridHorizontally(ship, x, baseHit.y);
                }
            }
        }
    }

    private boolean targetIsPlacedVertically() {
        return comparator instanceof VerticalPositionComparator;
    }

    public boolean hadSunk(Fleet enemyShips) {
        return enemyShips.getNumberOfShips() < previousShips.size();
    }

    public boolean hadSafelySunk(Fleet enemyShips) {
        Ship shotShip = null;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            if (previousShips.indexOf(enemyShips.getShip(i)) < 0) {
                shotShip = enemyShips.getShip(i);
            }
        }
        if (shotShip == null) {
            return false;
        }
        return true;
//        if(targetedShipHits.size() != shotShip.size()) {
//            Position lastShot = targetedShipHits.get(shotShip.size()-1);
//        }
    }

    public void setBaseHit(Position baseHit) {
        this.baseHit = baseHit;
    }

    public void registerHit(Position newHit) {
        if (targetedShipHits == null) {
            createInLineHitPositions(newHit);
        }
        if (isInLine(newHit)) {
            targetedShipHits.add(newHit);
        } else {
            saveDifferentShipBaseHit(newHit);
        }
    }
    private void createInLineHitPositions(Position newHit) {
        if (newHit.x == baseHit.x) {
            comparator = new VerticalPositionComparator();
        } else if (newHit.y == baseHit.y) {
            comparator = new HorizontalPositionComparator();
        }
        targetedShipHits = new TreeSet<>(comparator);
        targetedShipHits.add(baseHit);
    }
    private boolean isInLine(Position newHit) {
        if (targetedShipHits == null) return false;
        if(targetIsPlacedVertically()) {
            return newHit.x == baseHit.x;
        } else {
            return newHit.y == baseHit.y;
        }
    }
    private void saveDifferentShipBaseHit(Position baseHit) {
        otherShipsHitFragments.add(baseHit);
    }

    public boolean didShotDifferentShips(Fleet enemyShips) {
        Ship sunkShip = getSunkShip(enemyShips);
        if(targetedShipHits.size() > sunkShip.size()) {
            addInlineFragment(sunkShip);
        }
        field.reStampSunkPositions(targetedShipHits);
        return otherShipsHitFragments.size() > 0;
    }
    private Ship getSunkShip(Fleet enemyShips) {
        previousShips.removeAll(copyFleet(enemyShips));
        return previousShips.get(0);
    }
    private void addInlineFragment(Ship sunkShip) {
        Position lastHit = field.getLastShot();
        Iterator<Position> iterator;
        if(lastHit == targetedShipHits.last()) {
            iterator = targetedShipHits.iterator();
        } else if(lastHit == targetedShipHits.first()) {
            iterator = targetedShipHits.descendingIterator();
        } else {
            return;
        }
        Position fragment = findInlineFragment(sunkShip, iterator);
        saveDifferentShipBaseHit(fragment);
        targetedShipHits.remove(fragment);
    }

    private Position findInlineFragment(Ship sunkShip, Iterator<Position> iterator) {
        int i = 0;
        while(iterator.hasNext()) {
            if(++i > sunkShip.size()) {
                return iterator.next();
            }
        }
        return null;
    }

    public ArrayList<Position> getOtherShipsHitFragments() {
        return otherShipsHitFragments;
    }

//    private void updateFragmentsValidity() {
//        for (Position fragmentPosition : otherShipsHitFragments) {
//            if(field.getHit(fragmentPosition.x,fragmentPosition.y) == Field.HIT_SUNK) {
//                otherShipsHitFragments.remove(fragmentPosition);
//            }
//        }
//    }
}
