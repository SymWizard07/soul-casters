package soulcasters.shared;

public class OwnedEntityData extends EntityData {
    
    public String[][] options;
    public int ownerId;

    public OwnedEntityData(int id, int ownerId, int x, int y, int width, int height, String[][] options, String type) {
        super(id, x, y, width, height, type);
        this.options = options;
        this.ownerId = ownerId;
    }

}
