package r33.ai.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;

/**
 * Created by Ejdems on 14/12/2016.
 */
public interface ShipPlacer {
    public void placeFleetOnBoard(Fleet fleet, Board board);
}
