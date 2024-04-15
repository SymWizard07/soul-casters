
package soulcasters.server.game;

public class GameController implements Runnable {

    private boolean running = true;
    private EntityHandler entityHandler = new EntityHandler();

    private PlayerData p1 = new PlayerData(0, 1);
    private PlayerData p2 = new PlayerData(1, 2);

    public void run() {
        final int FPS = 60;
        final long frameTime = 1000 / FPS;

        long lastTime = System.nanoTime();
        long now;
        long updateTime;
        long wait;

        while (running) {
            now = System.nanoTime();
            updateTime = now - lastTime;
            lastTime = now;

            update(); // Update game state
            render(); // Render to the screen

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

    private void update() {
        entityHandler.update();
    }

    private void render() {

    }

    public void start() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        GameController gc = new GameController();
        gc.start();
    }

}