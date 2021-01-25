package utilities.gamecreation;

import main.Main;

import java.util.concurrent.atomic.AtomicBoolean;

public class Timer implements Runnable {

    private final long startTime;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread thread;
    private Game game;
    private int timeElapsed = 0;

    public Timer(Game game) {
        startTime = System.currentTimeMillis();
        this.game = game;

    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running.set(false);
    }

    public int getTimeLeft(long currentTime) {
        timeElapsed = (int) ((currentTime - startTime)/1000);
        System.out.println("time elapsed: " + timeElapsed);
        int gameTime = (int) (Main.getTimeLimit() * 60);
        return gameTime - timeElapsed;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, failed to complete operation.");
            }

            game.setTimeLeft(getTimeLeft(System.currentTimeMillis()));
        }
    }

}
