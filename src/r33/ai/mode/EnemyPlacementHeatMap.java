package r33.ai.mode;

import battleship.interfaces.Position;
import r33.ai.Field;
import r33.ai.ShotsGrid;

import java.util.List;

/**
 * Created by Ejdems on 15/12/2016.
 */
public class EnemyPlacementHeatMap implements ShotsGrid {
    private int[][] heatMap;

    public EnemyPlacementHeatMap(Field field) {
        heatMap = new int[field.getX()][field.getY()];
    }

    public void registerPositions(List<Position> positions) {
        for (Position position : positions) {
            heatMap[position.x][position.y] += 1;
        }
    }

    @Override
    public int getCell(int x, int y) {
        return heatMap[x][y];
    }
}
