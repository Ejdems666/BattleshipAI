package r33.ai.learning;

import battleship.interfaces.Position;

/**
 * Created by Ejdems on 13/12/2016.
 */
public class EnemyShots {
    private int[][] heatMap;
    private int value;

    public EnemyShots(int x, int y) {
        heatMap = new int[x][y];
        value = x*y;
    }

    public void registerEnemyShot(Position position) {
        heatMap[position.x][position.y] = value--;
    }

    public int getCellValue(int x, int y) {
        return heatMap[x][y];
    }
}
