package soulcasters.shared;

import java.io.Serializable;

public class SelectedOptionData implements Serializable {
    
    public long sessionToken;
    public int entityId;
    public String selectedOption;

    public SelectedOptionData(long sessionToken, int entityId, String selectedOption) {
        this.sessionToken = sessionToken;
        this.entityId = entityId;
        this.selectedOption = selectedOption;
    }

}
