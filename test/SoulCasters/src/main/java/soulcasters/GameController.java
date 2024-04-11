
package soulcasters;

public class GameController implements Runnable {

    private boolean running = true;

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