package soulcasters.shared;

import java.io.Serializable;

public class ClientToken implements Serializable {
    
    public long sessionToken;

    public ClientToken(long sessionToken) {
        this.sessionToken = sessionToken;
    }

}
