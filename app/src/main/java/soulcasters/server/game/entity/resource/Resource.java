package soulcasters.server.game.entity.resource;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.Entity;
import soulcasters.server.game.entity.units.Unit;

public abstract class Resource extends Entity {

    protected Unit carrier;

    public Resource(EntityHandler entityHandler, double x, double y) {
        super(entityHandler, x, y);
    }

    public void setCarrier(Unit carrier) {
        this.carrier = carrier;
    }

    public Unit getCarrier() {
        return carrier;
    }
    
}
