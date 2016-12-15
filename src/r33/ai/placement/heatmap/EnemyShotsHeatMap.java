package r33.ai.placement.heatmap;

import battleship.interfaces.Position;
import r33.ai.Field;
import r33.ai.ShotsGrid;

/**
 * Created by Ejdems on 13/12/2016.
 */
public class EnemyShotsHeatMap implements ShotsGrid {
    private int[][] heatMap;
    private int shotValue;
    private final Field field;

    public EnemyShotsHeatMap(Field field) {
        this.field = field;
        heatMap = new int[field.getX()][field.getY()];
        shotValue = 1;
    }

    public void registerEnemyMiss(Position position) {
        heatMap[position.x][position.y] = shotValue++;
    }

    public void registerEnemyHit(Position position) {
        heatMap[position.x][position.y] = shotValue+field.getNumberOfCells()/2;
        shotValue++;
    }

    @Override
    public int getCell(int x, int y) {
        return heatMap[x][y];
    }
}
