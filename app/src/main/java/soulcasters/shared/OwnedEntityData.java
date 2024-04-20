package soulcasters.shared;

public class OwnedEntityData extends EntityData {
    
    public int x, y, width, height;
    public int id;
    public String[][] options;
    public transient int ownerId;

    public OwnedEntityData(int id, int ownerId, int x, int y, int width, int height, String[][] options, String type) {
        super(id, x, y, width, height, options, type);
        this.ownerId = ownerId;
    }

    public OwnedEntityData(int id, int ownerId, int x, int y) {
        super(id, x, y);
        this.ownerId = ownerId;
    }

    public OwnedEntityData(int id, int ownerId, String[][] options) {
        super(id, options);
        this.ownerId = ownerId;
    }

    public OwnedEntityData(int id, int ownerId, String type) {
        super(id, type);
        this.ownerId = ownerId;
    }

}
