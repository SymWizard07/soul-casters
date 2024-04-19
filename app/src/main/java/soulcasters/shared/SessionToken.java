package soulcasters.shared;

import java.io.Serializable;

import soulcasters.server.game.GameController;

public class SessionToken implements Serializable {
    
    public long networkId;
    public long sessionToken;
    public GameController game;

    public SessionToken(long networkId, long sessionToken, GameController game) {
        this.networkId = networkId;
        this.sessionToken = sessionToken;
        this.game = game;
    }
    
}
