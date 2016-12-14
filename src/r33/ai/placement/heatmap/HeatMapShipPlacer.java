package r33.ai.placement.heatmap;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.placement.ShipPlacer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class HeatMapShipPlacer implements ShipPlacer {
    private ShipPlacementScanner shipPlacementScanner;
    private Random random;

    public HeatMapShipPlacer(ShipPlacementScanner shipPlacementScanner) {
        this.shipPlacementScanner = shipPlacementScanner;
        random = new Random();
    }

    @Override
    public void placeFleetOnBoard(Fleet fleet, Board board) {
        ArrayList<Position> idealPositions;
        boolean horizontal;
        Ship ship;
        Position basePosition;
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            ship = fleet.getShip(i);
            horizontal = random.nextBoolean();
            try {
                idealPositions = shipPlacementScanner.getBestPositionsForShip(ship, horizontal);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            shipPlacementScanner.printGrid();
            basePosition = idealPositions.get(random.nextInt(idealPositions.size()-1));
            board.placeShip(basePosition,ship,horizontal);
            shipPlacementScanner.registerShipPlacement(basePosition,ship,horizontal);
        }
    }
}
