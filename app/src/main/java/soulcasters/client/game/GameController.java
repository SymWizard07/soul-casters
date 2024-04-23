
package soulcasters.client.game;

import java.awt.Graphics2D;

import soulcasters.Constants;
import soulcasters.client.GamePanelControl;
import soulcasters.shared.CombinedEntityData;
import java.awt.*;

public class GameController implements Runnable {

    private boolean running = true;
    //private EntityHandler entityHandler = new EntityHandler();
    private GamePanelControl gcp;
    private EntityHandler entityHandler;

    public GameController(GamePanelControl gcp) {
        this.gcp = gcp;
        this.entityHandler = new EntityHandler(this);
    }

    public void run() {
        final int FPS = 60;
        final long frameTime = 1000 / FPS;

        long lastTime = System.nanoTime();
        long now;
        long wait;
        long requestTime = 0;
        long requestNow;
        long requestLast = 0;

        while (running) {
            now = System.nanoTime();
            lastTime = now;

            gcp.repaintPanel(); // Call Panel repaint to render graphics

            requestNow = System.currentTimeMillis();
            requestTime += requestNow - requestLast;
            requestLast = requestNow;
            // EntityData requested every 200 ms
            if (requestTime > 200) {
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
        Rectangle clipBounds = g.getClipBounds();

        // Calculate the current aspect ratio
        double currentAspectRatio = (double) clipBounds.width / clipBounds.height;

        // Desired aspect ratio is 16:10, which is 1.6
        double desiredAspectRatio = Constants.ASPECT_RATIO;

        int newWidth, newHeight;
        int x, y;

        if (currentAspectRatio > desiredAspectRatio) {
            // Too wide
            newHeight = clipBounds.height;
            newWidth = (int) (newHeight * desiredAspectRatio);
            x = (clipBounds.width - newWidth) / 2;
            y = 0;
        } else {
            // Too tall
            newWidth = clipBounds.width;
            newHeight = (int) (newWidth / desiredAspectRatio);
            x = 0;
            y = (clipBounds.height - newHeight) / 2;
        }

        g.setColor(new Color(121, 217, 84));
        g.fillRect(x, y, newWidth, newHeight);
        entityHandler.render(g, x, y, (double)(newWidth) / Constants.GAME_WIDTH);

        // Fill the background with black bars
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, clipBounds.width, y); // Top bar
        g.fillRect(0, y + newHeight, clipBounds.width, clipBounds.height - (y + newHeight)); // Bottom bar
        g.fillRect(0, 0, x, clipBounds.height); // Left bar
        g.fillRect(x + newWidth, 0, clipBounds.width - (x + newWidth), clipBounds.height); // Right bar
    }

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        gcp.addOptionsPanel(optionsPanel);
    }

    public void removeOptionsPanel(OptionsDisplay optionsPanel) {
        gcp.removeOptionsPanel(optionsPanel);
    }

    public void checkClick(int mouseX, int mouseY) {
        entityHandler.checkClick(mouseX, mouseY);
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

    public void sendSelectedOption(int entityId, String selectedOption) {
        gcp.sendSelectedOption(entityId, selectedOption);
    }

    public void setPlayerId(int playerId) {
        entityHandler.setPlayerId(playerId);
    }
}