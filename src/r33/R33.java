package r33;

import battleship.interfaces.BattleshipsPlayer;
import r33.ai.PassAI;
import tournament.player.PlayerFactory;

/**
 * Created by Ejdems on 05/12/2016.
 */
public class R33 implements PlayerFactory<BattleshipsPlayer> {
    @Override
    public BattleshipsPlayer getNewInstance() {
        return new PassAI();
    }

    @Override
    public String getID() {
        return "R33";
    }

    @Override
    public String getName() {
        return "just killer";
    }

    @Override
    public String[] getAuthors() {
        return new String[] {"Adam Bečvář", "Farkas István"};
    }
}
