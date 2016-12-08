package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.mode.HuntMode;
import r33.ai.mode.Mode;
import r33.ai.mode.TargetMode;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class HyggeAI implements BattleshipsPlayer {
    private HuntMode[] huntModes;
    private Mode currentMode;
    private Field field;
    private int currentRound;
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
        init(--round);
    }
    private void init(int round) {
        field = new Field(sizeX,sizeY);
        huntModes[round] = new HuntMode(field);
        currentRound = round;
        currentMode = huntModes[round];
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            board.placeShip(new Position(0, i), fleet.getShip(i), false);
        }
    }

    @Override
    public void incoming(Position pos) {

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
        if (hit) {
            if (currentMode instanceof HuntMode) {
                currentMode = new TargetMode(field);
            } else if (((TargetMode) currentMode).hadSafelySunk(enemyShips)) {
                currentMode = huntModes[currentRound];
            } else {
                ((TargetMode) currentMode).setBaseHit(field.getLastShot());
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
