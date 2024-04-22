package soulcasters.shared;

import java.io.Serializable;

/**
 * This class represents a packet of information relating to an {@link soulcasters.server.game.entity.Entity Entity} that is sent to the client.
 * If a client already has an entity in its handler,
 * it will overwrite the information on that entity using its ID.
 * Entity properties may be left null to remain unchanged clientside.
 */
public class EntityData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Integer x, y, width, height;
    public Integer id;
    public String type;
    public boolean remove;

    public EntityData(int id, int x, int y, int width, int height, String type) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.remove = false;
    }

}
