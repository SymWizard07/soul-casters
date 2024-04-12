package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;

public abstract class OwnedEntity extends Entity {

    protected int ownerId;

    public OwnedEntity(EntityHandler entityHandler, int x, int y, int ownerId) {
        super(entityHandler, x, y);
        this.ownerId = ownerId;
    }
    
    public int getOwnerId() {
        return ownerId;
    }
}
