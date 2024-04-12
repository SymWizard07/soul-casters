package soulcasters.server.game.entity;

import java.awt.Color;

import soulcasters.Constants;
import soulcasters.server.game.EntityHandler;

public class SoulCaster extends OwnedEntity {

    private boolean repeatCast = false;

    public SoulCaster(EntityHandler entityHandler, int x, int y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        options = new String[][]{
            {"workerCasts", "SoulCast Worker"},
            {"fighterCasts", "SoulCast Fighter"},
            {"toggleRepeat", "Toggle Repeat Cast\nAttempts to continuously cast the next chosen unit.\nOnly works when you have the resources!"}
        };
    }

    @Override
    public void optionAction(String selectedOption) {
        switch (selectedOption) {
            case "workerCasts":
            options = new String[][]{
                {"lumberjack", "Cast Lumberjack - 10 SP"},
                {"miner", "Cast Miner - 15 SP"},
                {"extractor", "Cast Clay Extractor - 15 SP"},
                {"hauler", "Cast Hauler - 5 SP"},
                {"arcanist", "Cast Arcanist - 30 SP"}
            };
            break;
            case "fighterCasts":
            options = new String[][]{
                {"scout", "Cast Scout - 5 SP"},
                {"defender", "Cast Defender - 20 SP"},
                {"knight", "Cast Knight - 15 SP"},
                {"breacher", "Cast Breacher - 15 SP"},
                {"medic", "Cast Medic - 10 SP"}
            };
            case "toggleRepeat":
            if (!repeatCast) {
                entityHandler.addEntity(new TextEntity(entityHandler, x, y, "Repeat On", 8, new Color(Constants.COLOR_STATUS_SUCCESS), ownerId, 750));
                repeatCast = false;
            }
            else {
                entityHandler.addEntity(new TextEntity(entityHandler, x, y, "Repeat Off", 8, new Color(Constants.COLOR_STATUS_FAILURE), ownerId, 750));
                repeatCast = true;
            }
            break;
            case "lumberjack":
            entityHandler.addEntity(new Lumberjack(entityHandler, x, y, ownerId));
            break;
        }
    }

    public String[][] getOptions() {
        options = new String[][]{
            {"workerCasts", "SoulCast Worker"},
            {"fighterCasts", "SoulCast Fighter"},
            {"toggleRepeat", "Toggle Repeat Cast\nAttempts to continuously cast the next chosen unit.\nOnly works when you have the resources!"}
        };

        return options;
    }

    @Override
    public void update() {
        
    }
    
}
