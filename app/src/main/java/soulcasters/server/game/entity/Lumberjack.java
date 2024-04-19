package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;

public class Lumberjack extends OwnedEntity {

    public Lumberjack(EntityHandler entityHandler, int x, int y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        width = 64;
        height = 64;
        type = "lumberjack";
        //TODO Auto-generated constructor stub
    }

    @Override
    public void optionAction(String selectedOption) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'optionAction'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
