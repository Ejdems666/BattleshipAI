package r33.ai.placement;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;
import r33.ai.mode.Shots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ejdems on 15/12/2016.
 */
public class MyBoard extends Shots {
    private ArrayList<MyShip> ships;
//    private boolean[][] occupied;
    private final Field field;

    public MyBoard(Field field) {
        super(field);
        this.field = field;
//        occupied = new boolean[field.getX()][field.getY()];
        ships = new ArrayList<>();
    }

    public void registerShipPlacement(Position basePosition, Ship ship, boolean vertical) {
        ArrayList<Position> positions = new ArrayList<>();
        if (vertical) {
            for (int l = basePosition.y; l < ship.size() + basePosition.y; l++) {
//                occupied[basePosition.x][l] = true;
                positions.add(new Position(basePosition.x,l));
            }
        } else {
            for (int l = basePosition.x; l < ship.size() + basePosition.x; l++) {
//                occupied[l][basePosition.y] = true;
                positions.add(new Position(l,basePosition.y));
            }
        }
        ships.add(new MyShip(ship,positions));
    }

    public boolean hasShip(Position position) {
        for (MyShip ship : ships) {
            if(ship.isHere(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTargetedShips() {
        for (MyShip ship : ships) {
            if(ship.damaged() && !ship.sunk()) {
                return true;
            }
        }
        return false;
    }

    public void clearBoard() {
//        occupied = new boolean[field.getX()][field.getY()];
        ships = new ArrayList<>();
    }

    @Override
    public void registerHit(boolean hit) {
        super.registerHit(hit);
        if(hit) {
            for (MyShip ship : ships) {
                if(ship.isHere(getLastShot())) {
                    ship.setHit(getLastShot());
                }
            }
        }
    }
}
