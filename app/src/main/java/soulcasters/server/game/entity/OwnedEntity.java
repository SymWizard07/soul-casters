package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;
import soulcasters.shared.EntityData;
import soulcasters.shared.OwnedEntityData;

public abstract class OwnedEntity extends Entity {

    protected int ownerId;

    public OwnedEntity(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y);
        this.ownerId = ownerId;
    }
    
    public int getOwnerId() {
        return ownerId;
    }

    public EntityData convertToEntityData() {
        return new OwnedEntityData(id, ownerId, (int)x, (int)y, width, height, options, type);
    }
}
