package r33.ai;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class TargetMode implements Mode {
    private Fleet enemyShips;

    public TargetMode(Fleet enemyShips) {
        this.enemyShips = enemyShips;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        return null;
    }

    public boolean hadSafelySunk(Fleet enemyShips) {
        // TODO: check for touching ships strategy ("L" or "T" shape)
        this.enemyShips = enemyShips; // this after the check so it could be used for next time
        return true;
    }
}
