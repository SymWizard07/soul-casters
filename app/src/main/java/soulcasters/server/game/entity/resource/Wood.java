package soulcasters.server.game.entity.resource;

import soulcasters.server.game.EntityHandler;

public class Wood extends Resource {

    public Wood(EntityHandler entityHandler, double x, double y) {
        super(entityHandler, x, y);
        width = 16;
        height = 16;
        type = "wood";
    }

    @Override
    public void update(double deltaTime) {
        if (carrier != null) {
            x = carrier.getX();
            y = carrier.getY() - 4;
            if (carrier.inFortress()) {
                carrier.dropResource();
                entityHandler.getOwnerData(carrier.getOwnerId()).wood++;
                remove();
            }
            updateEntity = true;
        }
    }
    
}
