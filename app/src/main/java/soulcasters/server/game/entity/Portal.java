package soulcasters.server.game.entity;

import java.awt.Color;

import soulcasters.Constants;
import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.units.Lumberjack;

public class Portal extends OwnedEntity {

    private boolean repeatCast = false;

    public Portal(EntityHandler entityHandler, int x, int y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        options = new String[][]{
            {"workerCasts", "SoulCast Worker"},
            {"fighterCasts", "SoulCast Fighter"},
            {"toggleRepeat", "Toggle Repeat Cast"}
        };
        
        width = 32;
        height = 32;
        type = "portal";
    }

    @Override
    public void optionAction(String selectedOption) {
        updateEntity = true;
        switch (selectedOption) {
            case "back":
            options = new String[][]{
                {"workerCasts", "SoulCast Worker"},
                {"fighterCasts", "SoulCast Fighter"},
                {"toggleRepeat", "Toggle Repeat Cast"}
            };
            break;
            case "workerCasts":
            options = new String[][]{
                {"properties", "isMenu"},
                {"lumberjack", "Cast Lumberjack - 10 SP"},
                {"miner", "Cast Miner - 15 SP"},
                {"extractor", "Cast Clay Extractor - 15 SP"},
                {"hauler", "Cast Hauler - 5 SP"},
                {"arcanist", "Cast Arcanist - 30 SP"}
            };
            break;
            case "fighterCasts":
            options = new String[][]{
                {"properties", "isMenu"},
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

        return options;
    }

    @Override
    public void update(double deltaTime) {
        
    }
    
}
