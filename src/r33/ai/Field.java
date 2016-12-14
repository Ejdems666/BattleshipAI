package r33.ai;

/**
 * Created by Ejdems on 14/12/2016.
 */
public class Field {
    private final int x;
    private final int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumberOfCells() {
        return x*y;
    }
}
