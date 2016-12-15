package r33.ai.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class StaticShipPlacer implements ShipPlacer {
    private MyBoard myBoard;

    public StaticShipPlacer(MyBoard myBoard) {
        this.myBoard = myBoard;
    }

    @Override
    public void placeFleetOnBoard(Fleet fleet, Board board) {
        board.placeShip(new Position(0, 9), fleet.getShip(0), false);
        myBoard.registerShipPlacement(new Position(0, 9), fleet.getShip(0), false);
        board.placeShip(new Position(4, 9), fleet.getShip(1), false);
        myBoard.registerShipPlacement(new Position(4, 9), fleet.getShip(1), false);
        board.placeShip(new Position(0, 0), fleet.getShip(2), false);
        myBoard.registerShipPlacement(new Position(0, 0), fleet.getShip(2), false);
        board.placeShip(new Position(4, 0), fleet.getShip(3), false);
        myBoard.registerShipPlacement(new Position(4, 0), fleet.getShip(3), false);
        board.placeShip(new Position(0, 2), fleet.getShip(4), true);
        myBoard.registerShipPlacement(new Position(0, 2), fleet.getShip(4), true);
    }
}
