package r33.ai.placement.heatmap;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.placement.MyBoard;
import r33.ai.placement.ShipPlacer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class HeatMapShipPlacer implements ShipPlacer {
    private ShipPlacementScanner shipPlacementScanner;
    private Random random;
    private MyBoard myBoard;

    public HeatMapShipPlacer(ShipPlacementScanner shipPlacementScanner, MyBoard myBoard) {
        this.shipPlacementScanner = shipPlacementScanner;
        this.myBoard = myBoard;
        random = new Random();
    }

    @Override
    public void placeFleetOnBoard(Fleet fleet, Board board) {
        ArrayList<Position> idealPositions;
        boolean vertical;
        Ship ship;
        Position basePosition;
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            ship = fleet.getShip(i);
            vertical = shipPlacementScanner.willBeVertical(ship);
            idealPositions = shipPlacementScanner.getBestPositionsFromScan();
            System.out.println("heatmap");
            shipPlacementScanner.printGrid();
            if (idealPositions.size() > 1) {
                basePosition = idealPositions.get(random.nextInt(idealPositions.size() - 1));
            } else {
                basePosition = idealPositions.get(0);
            }
            System.out.println(basePosition);
            System.out.println("vertical: "+ vertical);
            board.placeShip(basePosition, ship, vertical);
            myBoard.registerShipPlacement(basePosition, ship, vertical);
        }
    }
}
