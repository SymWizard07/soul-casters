package soulcasters.server.game.entity.units;

import soulcasters.server.game.EntityHandler;

public class Lumberjack extends Unit {

    public Lumberjack(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        width = 32;
        height = 32;
        type = "lumberjack";

        if (ownerId == 0) {
            //jobs.add(new Job(280, 30, "woodcutting"));
        }

        options = new String[][]{
            {"chop","Chop Wood"},
            {"return","Return to Fortress"}
        };
    }

    @Override
    public void optionAction(String selectedOption) {
    }

    @Override
    public void update(double deltaTime) {
        x += deltaTime * 1;
        y += deltaTime * 1;
        updateEntity = true;
    }
    
}
