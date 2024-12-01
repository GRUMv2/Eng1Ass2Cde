package io.github.GRUMv2.EngSim.Server;


public class Server extends Thread {
    private boolean isRunning = true;
    private boolean isPaused = false;
    private final int targetTPS;

    public Server(int targetTPS) {
        super("Server");
        this.targetTPS = targetTPS;
        System.out.println("[ SVR ] Server created");
    }

    public void Pause() {
        isPaused = true;
    }

    public void Resume() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void Stop() {
        isRunning = false;
    }

    // Calls the tick function, handles isRunning and isPaused
    public void run() {
        System.out.println("[ SVR ] Running in thread " + Thread.currentThread().getName());

        long processTime = 0;

        while (isRunning) {
            // Time how long the tick takes ...
            long lastTime = System.nanoTime();

            if (!isPaused) {
                double deltaMillis = processTime / 1_000_000.0;
                tick(deltaMillis);
            }

            processTime = System.nanoTime() - lastTime;
            long sleepFor = Math.max(0, (1_000_000_000 / targetTPS) - processTime);

            // ... and account for that in the sleep time
            try {
                Thread.sleep(sleepFor / 1_000_000, (int) (sleepFor % 1_000_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void tick(double delta) {

    }
}
