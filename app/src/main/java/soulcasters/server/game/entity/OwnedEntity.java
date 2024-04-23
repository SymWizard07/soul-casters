package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;
import soulcasters.shared.EntityData;
import soulcasters.shared.OwnedEntityData;

public abstract class OwnedEntity extends Entity {

    protected int ownerId;
    // Each option has a string identifier, and a text description that is displayed to the user.
    protected String[][] options;

    public OwnedEntity(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y);
        this.ownerId = ownerId;
    }
    
    public int getOwnerId() {
        return ownerId;
    }

    public String[][] getOptions() {
        if (options == null) {
            return null;
        }
        return options.clone();
    }

    public EntityData convertToEntityData() {
        return new OwnedEntityData(id, ownerId, (int)x, (int)y, width, height, getOptions(), type);
    }

    public abstract void optionAction(String selectedOption);
}
