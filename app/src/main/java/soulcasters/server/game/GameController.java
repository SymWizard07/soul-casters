
package soulcasters.server.game;

import java.awt.Color;
import java.util.ArrayList;

import soulcasters.Constants;
import soulcasters.server.GameServer;
import soulcasters.server.game.entity.*;
import soulcasters.server.game.entity.units.*;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.EntityData;

public class GameController implements Runnable {

    private boolean running = true;
    private PlayerData[] players;
    private EntityHandler entityHandler;
    private TextEntity timerText;
    private double gameTimer;
    private TextEntity scoreText;

    public GameController(GameServer gs) {
        players = new PlayerData[2];
        entityHandler = new EntityHandler(players);
        gameTimer = 480.0;
        setupEntities();
    }

    private void setupEntities() {

        // Portals and Walls
        entityHandler.addEntity(new Wall(entityHandler, -90, -40, 0));
        entityHandler.addEntity(new Portal(entityHandler, 20, 54, 0));
        entityHandler.addEntity(new Wall(entityHandler, 154, -40, 1));
        entityHandler.addEntity(new Portal(entityHandler, 276, 54, 1));

        // Trees
        for (int i = 64; i < 236; i += 16) {
            entityHandler.addEntity(
                    new Tree(entityHandler, i + (int) (Math.random() * 10) - 5, (int) (Math.random() * 20) + 5));
        }

        // Ponds
        for (int i = 40; i < 280; i += 40) {
            entityHandler.addEntity(new Pond(entityHandler, i + (int) (Math.random() * 10) - 5,
                    200 - ((int) (Math.random() * 20) + 30)));
        }

        // Starting Units
        entityHandler.addEntity(new Lumberjack(entityHandler, 40, 68, 0));
        entityHandler.addEntity(new Extractor(entityHandler, 40, 116, 0));
        entityHandler.addEntity(new Lumberjack(entityHandler, 264, 68, 1));
        entityHandler.addEntity(new Extractor(entityHandler, 264, 116, 1));

        timerText = new TextEntity(entityHandler, 140, 20, "", 42, Color.BLACK, -1, -1);
        entityHandler.addEntity(timerText);

        scoreText = new TextEntity(entityHandler, 140, 30, "", 42, Color.BLACK, -1, -1);
        entityHandler.addEntity(scoreText);
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

    public int addPlayer(long networkId, String username) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = new PlayerData(i, networkId, username);
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns the combined entity data of all visible entities, plus the specific
     * player's
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
                    } catch (Exception e2) {
                    }
                }

                return combinedEntityData;
            }
        }

        return null;
    }

    public void recieveSelectedOption(int entityId, String selectedOption) {
        entityHandler.recieveSelectedOption(entityId, selectedOption);
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

            if (players[0] != null && players[1] != null) {
                gameTimer -= deltaTime;
                scoreText.setText(players[0].score + ":" + players[1].score);
            } 
            else {
                scoreText.setText("Waiting on another Player...");
            }
            timerText.setText(Constants.formatTime((int) (gameTimer)));

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