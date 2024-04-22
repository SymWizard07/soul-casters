package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;

public class Wall extends OwnedEntity {

    public Wall(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
    }

    @Override
    public void optionAction(String selectedOption) {
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
