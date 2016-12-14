package r33.ai.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class StaticShipPlacer implements ShipPlacer {
    @Override
    public void placeFleetOnBoard(Fleet fleet, Board board) {
        board.placeShip(new Position(4, 9), fleet.getShip(0), false);
        board.placeShip(new Position(1, 4), fleet.getShip(1), false);
        board.placeShip(new Position(4, 4), fleet.getShip(2), false);
        board.placeShip(new Position(3, 5), fleet.getShip(3), false);
        board.placeShip(new Position(9, 0), fleet.getShip(4), true);
    }
}
