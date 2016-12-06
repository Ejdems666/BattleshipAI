import battleship.implementations.SimpleFleet;
import r33.ai.HuntMode;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class Main {
    public static void main(String[] args) {
        HuntMode huntMode = new HuntMode(10,10);
        huntMode.getShot(new SimpleFleet(new int[]{2,3,3,4,5}));
    }
}
