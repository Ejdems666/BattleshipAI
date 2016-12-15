package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.picker.ParityPicker;
import r33.ai.placement.ShipPlacer;
import r33.ai.placement.heatmap.ShipPlacementScanner;
import r33.ai.placement.heatmap.EnemyShotsHeatMap;
import r33.ai.mode.*;
import r33.ai.placement.StaticShipPlacer;
import r33.ai.placement.heatmap.HeatMapShipPlacer;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class HyggeAI implements BattleshipsPlayer {
    private HuntMode huntMode;
    private Mode currentMode;
    private EnemyShotsHeatMap[] enemyShotsHeatMap;
    private MyShots[] myShots;
    private ShipPlacer shipPlacer;
    private ShipPlacementScanner shipPlacementScanner;
    private Field field;
    private ParityPicker parityPicker;
    private int currentRound;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        field = new Field(sizeX, sizeY);
        shipPlacementScanner = new ShipPlacementScanner(field);
        myShots = new MyShots[rounds];
        enemyShotsHeatMap = new EnemyShotsHeatMap[rounds];
        shipPlacer = new StaticShipPlacer();
        parityPicker = new ParityPicker(0);
    }

    @Override
    public void startRound(int round) {
        init(--round);
        if(round == 1) {
            shipPlacer = new HeatMapShipPlacer(shipPlacementScanner);
        }
    }

    private void init(int round) {
        enemyShotsHeatMap[round] = new EnemyShotsHeatMap(field);
        myShots[round] = new MyShots(field);
        huntMode = new HuntMode(field, myShots[round], parityPicker);
        currentRound = round;
        currentMode = huntMode;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        try {
            shipPlacer.placeFleetOnBoard(fleet, board);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incoming(Position pos) {
        enemyShotsHeatMap[currentRound].registerEnemyShot(pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = currentMode.getShot(enemyShips);
        myShots[currentRound].setLastShot(shot);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        myShots[currentRound].registerHit(hit);
//        ((ProbabilityPicker) currentMode).printGrid();
        if (hit) {
            if (currentMode instanceof HuntMode) {
                currentMode = new TargetMode(field, myShots[currentRound], parityPicker);
            } else {
                TargetMode targetMode = ((TargetMode) currentMode);
                targetMode.registerHit(myShots[currentRound].getLastShot());
                if (targetMode.hadSafelySunk(enemyShips)) {
                    myShots[currentRound].reStampSunkPositions(targetMode.getHitPositions());
                    currentMode = huntMode;
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        if(currentRound%10 == 0) {
            shipPlacementScanner = new ShipPlacementScanner(field);
            shipPlacer = new HeatMapShipPlacer(shipPlacementScanner);
        }
        shipPlacementScanner.addHeatMap(enemyShotsHeatMap[currentRound]);
    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
