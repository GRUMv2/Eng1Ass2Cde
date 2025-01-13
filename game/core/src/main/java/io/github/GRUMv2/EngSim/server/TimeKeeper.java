package io.github.GRUMv2.EngSim.server;

/**
 * This class is responsible for managing the game time.
 */
public class TimeKeeper {
    private final long yearsPerMinute;
    private long startTime = -1;
    private long pausedTime = 0;
    private boolean isPaused = false;


    /**
     * Constructs a TimeKeeper with the specified years per minute.
     *
     * @param yearsPerMinute the number of years that pass per minute of real time
     */
    public TimeKeeper(long yearsPerMinute) {
        this.yearsPerMinute = yearsPerMinute;
    }


    /**
     * Starts the game timer.
     */
    public void start() {
        this.startTime = System.currentTimeMillis();

        System.out.println("[ TME ] GameTimer started at " + this.startTime);
    }

    /**
     * Pauses the game timer.
     */
    public void pause() {
        if (isPaused) return;

        this.pausedTime = System.currentTimeMillis();
        this.isPaused = true;

        System.out.println("[ TME ] GameTimer paused at " + this.pausedTime);
    }

    /**
     * Unpauses the game timer.
     */
    public void unpause() {
        if (!isPaused) return;

        this.startTime += System.currentTimeMillis() - pausedTime;
        this.isPaused = false;

        System.out.println("[ TME ] GameTimer un-paused, start time adjusted to " +
            this.startTime + " after " + (System.currentTimeMillis() - pausedTime));
    }

    /**
     * Returns the game time since start in milliseconds.
     *
     * @return the game time in milliseconds
     */
    public long currentGameTime() {
        if (startTime == -1) return 0;

        if (isPaused) return pausedTime - startTime;

        return System.currentTimeMillis() - startTime;
    }

    /**
     * Formats the inputted time (milliseconds) into a string.
     *
     * @param time the time in milliseconds
     * @return the formatted time string
     */
    public String timeFormatted(double time) {
        double minutes = (time / 60) / 1000;
        double years = minutes * yearsPerMinute;
        int yearFormatted = (int) Math.floor(years) + 2024;
        int month = (int) Math.floor((years - Math.floor(years)) * 12);
        String monthFormatted = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}[month];
        return monthFormatted + " " + yearFormatted;
    }

    /**
     * Returns the current game time in a formatted string.
     * The format is "Month Year", where Month is the abbreviated month name
     * and Year is the calculated year based on the game time.
     *
     * @return the formatted game time string
     */
    public String currentGameTimeFormatted() {
        return timeFormatted(currentGameTime());
    }
}












