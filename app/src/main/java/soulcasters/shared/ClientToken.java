package soulcasters.shared;

import java.io.Serializable;

public class ClientToken implements Serializable {
    
    public long sessionToken;
    public int playerId;

    public ClientToken(long sessionToken, int playerId) {
        this.sessionToken = sessionToken;
        this.playerId = playerId;
    }

}
