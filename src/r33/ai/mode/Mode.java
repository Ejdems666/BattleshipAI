package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 06/12/2016.
 */
public interface Mode {
    public Position getShot(Fleet enemyShips);
}
