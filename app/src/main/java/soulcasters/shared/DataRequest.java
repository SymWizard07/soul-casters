package soulcasters.shared;

import java.io.Serializable;

public class DataRequest implements Serializable {

    public long sessionToken;
    public String request;

    public DataRequest(long sessionToken, String request) {
        this.sessionToken = sessionToken;
        this.request = request;
    }
    
}
