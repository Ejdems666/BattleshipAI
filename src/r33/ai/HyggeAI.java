package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class HyggeAI implements BattleshipsPlayer {
    private HuntMode[] huntModes;
    private Mode currentMode;
    private int round;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        huntModes = new HuntMode[rounds];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public void startRound(int round) {
        huntModes[round] = new HuntMode(sizeX, sizeY);
        this.round = round;
        currentMode = huntModes[round];
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            board.placeShip(new Position(0, i), fleet.getShip(i), true);
        }
    }

    @Override
    public void incoming(Position pos) {

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        return currentMode.getShot(enemyShips);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
//        if (hit) {
//            if (currentMode instanceof HuntMode) {
//                currentMode = new TargetMode(enemyShips);
//            } else if (((TargetMode) currentMode).hadSafelySunk(enemyShips)) {
//                currentMode = huntModes[round];
//            }
//        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
