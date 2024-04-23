package soulcasters.server.game.entity.resource;

import soulcasters.server.game.EntityHandler;

public class Clay extends Resource {

    public Clay(EntityHandler entityHandler, double x, double y) {
        super(entityHandler, x, y);
        width = 16;
        height = 16;
        type = "clay";
    }

    @Override
    public void update(double deltaTime) {
        if (carrier != null) {
            x = carrier.getX();
            y = carrier.getY() - 4;
            if (carrier.inFortress()) {
                carrier.dropResource();
                entityHandler.getOwnerData(carrier.getOwnerId()).clay++;
                remove();
            }
            updateEntity = true;
        }
    }
    
}
