package soulcasters.shared;

import java.io.Serializable;

public class SelectedOptionData implements Serializable {
    
    public long sessionToken;
    public int entityId;
    public int optionIndex;

    public SelectedOptionData(long sessionToken, int entityId, int optionIndex) {
        this.sessionToken = sessionToken;
        this.entityId = entityId;
        this.optionIndex = optionIndex;
    }

}
