import battleship.implementations.SimpleFleet;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r33.ai.Field;
import r33.ai.mode.HuntMode;
import r33.ai.mode.TargetMode;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class Main {
    public static void main(String[] args) {
        Field field = new Field(10, 10);
        HuntMode huntMode = new HuntMode(field);
        Fleet fleet = new SimpleFleet(new int[]{5});
        field.setLastShot(huntMode.getShot(fleet));
        field.registerHit(field.getLastShot(), true);
        TargetMode targetMode = new TargetMode(field);
        Position shot = targetMode.getShot(fleet);
    }
}
