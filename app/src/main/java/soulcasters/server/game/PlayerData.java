package soulcasters.server.game;

import java.util.ArrayList;

import soulcasters.shared.EntityData;

public class PlayerData {
    
    public int id;
    public long networkId;
    public String username;
    public int score;
    public int wood, ore, clay;
    public ArrayList<EntityData> entityDataQueue;

    public PlayerData(int id, long networkId, String username) {
        this.id = id;
        this.networkId = networkId;
        this.username = username;

        entityDataQueue = new ArrayList<>();
    }

}
