package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.learning.HeatMap;
import r33.ai.mode.*;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class HyggeAI implements BattleshipsPlayer {
    private HuntMode[] huntModes;
    private Mode currentMode;
    private HeatMap[] heatMaps;
    private Field field;
    private ParityCalculator parityCalculator;
    private int currentRound;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        huntModes = new HuntMode[rounds];
        heatMaps = new HeatMap[rounds];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        parityCalculator = new ParityCalculator(0);
    }

    @Override
    public void startRound(int round) {
        init(--round);
    }
    private void init(int round) {
        heatMaps[round] = new HeatMap(sizeX,sizeY);
        field = new Field(sizeX,sizeY);
        huntModes[round] = new HuntMode(field,parityCalculator);
        currentRound = round;
        currentMode = huntModes[round];
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        board.placeShip(new Position(4,9),fleet.getShip(0),false);
        board.placeShip(new Position(1,4),fleet.getShip(1),false);
        board.placeShip(new Position(4,4),fleet.getShip(2),false);
        board.placeShip(new Position(3,5),fleet.getShip(3),false);
        board.placeShip(new Position(9,0),fleet.getShip(4),true);
    }

    @Override
    public void incoming(Position pos) {
        heatMaps[currentRound].registerEnemyShot(pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = currentMode.getShot(enemyShips);
        field.setLastShot(shot);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        field.registerHit(hit);
//        ((BestShotCalculator) currentMode).printGrid();
        if (hit) {
            if (currentMode instanceof HuntMode) {
                currentMode = new TargetMode(field,parityCalculator,field.getLastShot());
            } else {
                TargetMode targetMode = ((TargetMode) currentMode);
                targetMode.registerHit(field.getLastShot());
                if (targetMode.hadSafelySunk(enemyShips)) {
                    field.reStampSunkPositions(targetMode.getHitPositions());
                    currentMode = huntModes[currentRound];
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
