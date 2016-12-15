package r33.ai.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import r33.ai.Field;
import r33.ai.mode.Shots;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ejdems on 15/12/2016.
 */
public class EnemyShots extends Shots {
    private HashMap<MyShip,ArrayList<Position>> occupiedPositions;
    private Board board;

    public EnemyShots(Field field, Board board) {
        super(field);
        this.board = board;
        occupiedPositions = new HashMap<>();
    }
}
