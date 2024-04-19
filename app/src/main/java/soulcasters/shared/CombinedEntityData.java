package soulcasters.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class CombinedEntityData implements Serializable {
    
    public ArrayList<EntityData> visibleEntities;
    public ArrayList<EntityData> interactableEntities;

    public CombinedEntityData(ArrayList<EntityData> visibleEntities, ArrayList<EntityData> interactableEntities) {
        this.visibleEntities = visibleEntities;
        this.interactableEntities = interactableEntities;
    }

}
