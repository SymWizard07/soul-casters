package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;

public class Wall extends OwnedEntity {

    private int integrity;

    public Wall(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        integrity = 100;
        this.ownerId = ownerId;

        width = 256;
        height = 256;
        if (ownerId == 0) {
            type = "wall1";
        }
        if (ownerId == 1) {
            type = "wall2";
        }
    }

    @Override
    public void update(double deltaTime) {
        
    }

    @Override
    public void optionAction(String selectedOption) {
    }
    
}
