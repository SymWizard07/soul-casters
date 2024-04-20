
package soulcasters.server.game;

import java.util.ArrayList;

import soulcasters.server.GameServer;
import soulcasters.server.game.entity.units.Lumberjack;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.EntityData;

public class GameController implements Runnable {

    private GameServer gs;
    private boolean running = true;
    private EntityHandler entityHandler = new EntityHandler();

    private PlayerData[] players = new PlayerData[2];

    public GameController(GameServer gs) {
        this.gs = gs;
        entityHandler.addEntity(new Lumberjack(entityHandler, 0, 0, 0));
    }

    public int getPlayerCount() {

        int playerCount = 0;

        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                playerCount++;
            }
        }

        return playerCount;
    }

    public boolean addPlayer(long networkId, String username) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = new PlayerData(i, networkId, username);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the combined entity data of all visible entities, plus the specific player's
     * interactable entities and their options.
     * 
     * @param networkId
     * @return An Array containing 2 ArrayLists of EntityData to update clientside.
     */
    public CombinedEntityData retrieveEntityData(long networkId) {
        
        ArrayList<ArrayList<EntityData>> dataQueues = entityHandler.getEntityQueues();
        
        for (int i = 0; i < players.length; i++) {
            if (players[i].networkId == networkId) {
                if (dataQueues == null) {
                    return null;
                }
                CombinedEntityData combinedEntityData = new CombinedEntityData(null, null);
                try {
                    combinedEntityData = new CombinedEntityData(dataQueues.get(0), dataQueues.get(i + 1));
                } catch (Exception e) {
                    try {
                        combinedEntityData = new CombinedEntityData(dataQueues.get(0), null);
                    } catch (Exception e2) {}
                }
                
                return combinedEntityData;
            }
        }

        return null;
    }

    public void run() {
        final int FPS = 30;
        final long frameTime = 1000 / FPS;
    
        long lastTime = System.nanoTime();
        long now;
        double deltaTime;
    
        while (running) {
            now = System.nanoTime();
            deltaTime = (now - lastTime) / 1000000000.0; // Convert nanoseconds to seconds
            lastTime = now;
    
            update(deltaTime);
    
            long wait = (lastTime - System.nanoTime() + frameTime) / 1000000;
    
            if (wait < 0) {
                wait = 5;
            }
    
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update(double deltaTime) {
        entityHandler.update(deltaTime);
    }

    public void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
    }
}