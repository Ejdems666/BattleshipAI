package r33.ai;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.learning.EnemyShots;
import r33.ai.mode.*;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class HyggeAI implements BattleshipsPlayer {
    private HuntMode huntMode;
    private Mode currentMode;
    private EnemyShots[] EnemyShots;
    private MyShots[] myShots;
    private ParityCalculator parityCalculator;
    private int currentRound;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        myShots = new MyShots[rounds];
        EnemyShots = new EnemyShots[rounds];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        parityCalculator = new ParityCalculator(0);
    }

    @Override
    public void startRound(int round) {
        EnemyShots[round] = new EnemyShots(sizeX,sizeY);
        myShots[round] = new MyShots(sizeX,sizeY);
        huntMode = new HuntMode(myShots[round],parityCalculator);
        currentRound = round;
        currentMode = huntMode;
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
        EnemyShots[currentRound].registerEnemyShot(pos);
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
//        ((BestShotCalculator) currentMode).printGrid();
        if (hit) {
            if (currentMode instanceof HuntMode) {
                currentMode = new TargetMode(myShots[currentRound],parityCalculator);
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
    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
