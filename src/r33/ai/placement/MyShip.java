package r33.ai.placement;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Ejdems on 15/12/2016.
 */
public class MyShip implements Ship {
    private final Ship ship;
    private HashMap<Position, Boolean> hits;

    public MyShip(Ship ship, List<Position> positions) {
        this.ship = ship;
        hits = new HashMap<>();
        for (Position position : positions) {
            hits.put(position,false);
        }
    }

    @Override
    public int size() {
        return ship.size();
    }

    public boolean sunk() {
        for (Boolean hit : hits.values()) {
            if(!hit) {
                return false;
            }
        }
        return true;
    }

    public boolean damaged() {
        for (Boolean hit : hits.values()) {
            if(hit) {
                return true;
            }
        }
        return false;
    }

    public void setHit(Position position) {
        hits.replace(position,true);
    }

    public boolean isHere(Position position) {
        return hits.containsKey(position);
    }
}
