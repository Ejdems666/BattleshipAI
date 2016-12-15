package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.picker.ParityPicker;
import r33.ai.placement.MyBoard;
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
    private int[] shots;
    private HuntMode huntMode;
    private Mode currentMode;
    private EnemyShotsHeatMap[] enemyShotsHeatMap;
    private EnemyPlacementHeatMap[] enemyPlacementHeatMaps;
    private MyBoard myBoard;
    private Shots[] myShots;
//    private Shots[] enemyShots;
    private ShipPlacer shipPlacer;
    private ShipPlacementScanner shipPlacementScanner;
    private Field field;
    private ParityPicker parityPicker;
    private int currentRound;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        shots = new int[rounds];
        field = new Field(sizeX, sizeY);
        myBoard = new MyBoard(field);
        shipPlacementScanner = new ShipPlacementScanner(field, myBoard);
        myShots = new Shots[rounds];
//        enemyShots = new Shots[rounds];
        enemyShotsHeatMap = new EnemyShotsHeatMap[rounds];
        enemyPlacementHeatMaps = new EnemyPlacementHeatMap[rounds];
        shipPlacer = new StaticShipPlacer(myBoard);
        parityPicker = new ParityPicker(0);
    }

    @Override
    public void startRound(int round) {
        init(--round);
        if(round == 1) {
            shipPlacer = new HeatMapShipPlacer(shipPlacementScanner, myBoard);
        }
    }

    private void init(int round) {
        enemyShotsHeatMap[round] = new EnemyShotsHeatMap(field);
        enemyPlacementHeatMaps[round] = new EnemyPlacementHeatMap(field);
        myShots[round] = new Shots(field);
//        enemyShots[round] = new Shots(field);
        huntMode = new HuntMode(field, myShots[round], parityPicker);
        currentRound = round;
        currentMode = huntMode;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        shipPlacer.placeFleetOnBoard(fleet, board);
    }

    @Override
    public void incoming(Position pos) {
        boolean hit = myBoard.hasShip(pos);
//        myBoard.setLastShot(pos);
//        myBoard.registerHit(hit);
        if(hit) {
            enemyShotsHeatMap[currentRound].registerEnemyHit(pos);
        } else {
            enemyShotsHeatMap[currentRound].registerEnemyMiss(pos);
        }
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        shots[currentRound]++;
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
                    enemyPlacementHeatMaps[currentRound].registerPositions(targetMode.getHitPositions());
                    currentMode = huntMode;
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        myBoard.clearBoard();
        if(currentRound%10 == 0) {
            shipPlacementScanner.clearMergedHeatMaps();
//            parityPicker.setParityMode(0);
        }
//        if(currentRound%5 == 0) {
//            parityPicker.setParityMode(1);
//        }
        shipPlacementScanner.addHeatMap(enemyShotsHeatMap[currentRound]);
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        int totalValue = 0;
        for (int shot : shots) {
            totalValue += shot;
        }
        System.out.println("average: "+(totalValue/shots.length));
    }
}
