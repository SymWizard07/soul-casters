
package soulcasters.client.game;

import java.awt.Graphics2D;

import soulcasters.client.GamePanelControl;
import soulcasters.shared.CombinedEntityData;

public class GameController implements Runnable {

    private boolean running = true;
    //private EntityHandler entityHandler = new EntityHandler();
    private GamePanelControl gcp;
    private EntityHandler entityHandler;

    public GameController(GamePanelControl gcp) {
        this.gcp = gcp;
        this.entityHandler = new EntityHandler();
    }

    public void run() {
        final int FPS = 60;
        final long frameTime = 1000 / FPS;

        long lastTime = System.nanoTime();
        long now;
        long updateTime;
        long wait;
        long requestTime = 0;
        long requestNow;
        long requestLast = 0;

        while (running) {
            now = System.nanoTime();
            updateTime = now - lastTime;
            lastTime = now;

            gcp.repaintPanel(); // Call Panel repaint to render graphics

            requestNow = System.currentTimeMillis();
            requestTime += requestNow - requestLast;
            requestLast = requestNow;
            if (requestTime > 1000) {
                gcp.requestEntityData();
                requestTime = 0;
            }

            wait = (lastTime - System.nanoTime() + frameTime) / 1000000;

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

    public void render(Graphics2D g) {
        entityHandler.render(g);
    }

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        gcp.addOptionsPanel(optionsPanel);
    }

    public void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
    }

    public void updateEntityData(CombinedEntityData combinedEntityData) {
        entityHandler.updateList(combinedEntityData);
    }

    public void sendSelectedOption(int entityId, int optionIndex) {
        gcp.sendSelectedOption(entityId, optionIndex);
    }
}