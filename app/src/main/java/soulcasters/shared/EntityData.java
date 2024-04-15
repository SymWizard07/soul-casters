package soulcasters.shared;

import java.io.Serializable;

/**
 * This class represents a packet of information relating to an {@link soulcasters.server.game.entity.Entity Entity} that is sent to the client.
 * If a client already has an entity in its handler,
 * it will overwrite the information on that entity using its ID.
 * Entity properties may be left null to remain unchanged clientside.
 */
public class EntityData implements Serializable {
    
    public int x, y, width, height;
    public int id;
    public String[][] options;

    public EntityData(int id, int x, int y, int width, int height, String[][] options) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.options = options.clone();
    }

    public EntityData(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public EntityData(int id, String[][] options) {
        this.id = id;
        this.options = options.clone();
    }

}
