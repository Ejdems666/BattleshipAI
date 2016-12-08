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
        Fleet fleet = new SimpleFleet(new int[]{2,2,3,4,5});
        for (int i = 0; i < 20; i++) {
            field.setLastShot(huntMode.getShot(fleet));
            field.registerHit(false);
        }
//        field.setLastShot(huntMode.getShot(fleet));
//        field.registerHit(true);
//        TargetMode targetMode = new TargetMode(field);
//        for (int i = 0; i < 5; i++) {
//            Position shot = targetMode.getShot(fleet);
//            field.setLastShot(shot);
//            field.registerHit(false);
//            field.printGrid();
//        }
        System.out.println("\n_____________________________________________________________________\n");
        field = new Field(10, 10);
        huntMode = new HuntMode(field);
        for (int i = 0; i < 20; i++) {
            field.setLastShot(huntMode.getShot(fleet));
            field.registerHit(false);
        }

    }
}
