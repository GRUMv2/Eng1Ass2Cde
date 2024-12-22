package io.github.GRUMv2.EngSim.Server;

public class TimeKeeper {
    private long startTime = 0;
    private long pausedTime = 0;
    private boolean isPaused = false;

    public void start() {
        this.startTime = System.currentTimeMillis();

        System.out.println("[ TME ] GameTimer started at " + this.startTime);
    }

    public void pause() {
        if (isPaused) return;

        this.pausedTime = System.currentTimeMillis();
        this.isPaused = true;

        System.out.println("[ TME ] GameTimer paused at " + this.pausedTime);
    }

    public void unpause() {
        if (!isPaused) return;

        this.startTime += System.currentTimeMillis() - pausedTime;
        this.isPaused = false;

        System.out.println("[ TME ] GameTimer unpaused, starttime adjusted to " +
            this.startTime + " after " + (System.currentTimeMillis() - pausedTime));
    }

    // Game time since start in milliseconds
    public long currentGameTime() {
        if (isPaused) return pausedTime - startTime;

        return System.currentTimeMillis() - startTime;
    }

    public String currentGameTimeFormatted() {
        return "" + this.currentGameTime();
        // yeah this works but (String) x does not
        // TODO: Calculate date given time elapsed
    }
}
