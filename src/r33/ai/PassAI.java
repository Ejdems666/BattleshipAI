package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class PassAI implements BattleshipsPlayer {
    private int sizeY;
    private int sizeX;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

    }

    @Override
    public void incoming(Position pos) {

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        return null;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
