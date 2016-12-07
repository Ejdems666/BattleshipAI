import battleship.implementations.SimpleFleet;
import battleship.interfaces.Fleet;
import r33.ai.Field;
import r33.ai.HuntMode;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class Main {
    public static void main(String[] args) {
        HuntMode huntMode = new HuntMode(new Field(10,10));
        Fleet fleet = new SimpleFleet(new int[]{2,3,3,4,5});
        huntMode.getShot(fleet);
        huntMode.getShot(fleet);
        huntMode.getShot(fleet);
    }
}
