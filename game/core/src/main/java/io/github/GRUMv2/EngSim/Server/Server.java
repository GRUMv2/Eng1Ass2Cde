package io.github.GRUMv2.EngSim.Server;


import io.github.GRUMv2.EngSim.Server.Simulation.Simulation;
import io.github.GRUMv2.EngSim.Server.EventHandler.EventHandler;


public class Server extends Thread {
    private boolean isRunning = true;
    private boolean isPaused = false;
    private final int targetTPS;

    private final Simulation simulation = new Simulation();
    private final EventHandler eventHandler = new EventHandler();
    private final TimeKeeper timeKeeper = new TimeKeeper(1);

    public Server(int targetTPS) {
        super("Server");
        this.targetTPS = targetTPS;

        System.out.println("[ SVR ] Server created");
    }

    // Pause's the server, events, and simulation
    public void Pause() {
        isPaused = true;

        this.timeKeeper.pause();
    }

    // Unpauses the above
    public void Resume() {
        isPaused = false;

        this.timeKeeper.unpause();
    }

    public boolean isPaused() {
        return isPaused;
    }

    // Entirely stops and disposes the server, this call cannot be undone
    public void Stop() {
        isRunning = false;
    }

    // Driven by:
    // - Location of buildings
    // - Staff Student Ratio
    // - Student building ratio
    // Can also be affected by events
    public float getStudentSatisfaction() {
        return simulation.getStudentSatisfaction();
    }

    // most of the time is the maximum possible given the number of halls,
    // unless the student satisfaction is too low then will drop as people
    // drop out.
    // Can also be affected by events
    //
    // Average students per building: 300
    public int getStudentNumbers() {
        return simulation.getStudentNumbers();
    }

    // Driven by:
    // - Location to car parks
    // - Staff student satisfaction
    // - Student turnout (to lectures)
    //   - Driven by distance to halls
    // Can also be affected by events
    public float getStaffSatisfaction() {
        return simulation.getStaffSatisfaction();
    }

    // Again mostly driven by the number of offices (that is itself driven by
    // number of placed buildings) unless staff satisfaction is too low.
    // Can also be affected by events
    public int getStaffNumbers() {
        return simulation.getStaffNumbers();
    }

    // Driven by
    // - Income
    // - User building buildings
    // - Events can directly add/remove
    //
    // Average cost of halls building 20_000_000
    public int getMoney() {
        return simulation.getMoney();
    }

    // Driven by
    // - Number students
    // - International student ratio
    // - Staff numbers
    // - Staff satisfaction
    //   - Low staff satisfaction numbers will increase their
    //     wage to prevent them from being fired
    public int getIncome() {
        return simulation.getIncome();
    }

    public long getGameTime() {
        return this.timeKeeper.currentGameTime();
    }

    public String getGameTimeFormatted() {
        return this.timeKeeper.currentGameTimeFormatted();
    }


    // Calls the tick function, handles isRunning and isPaused
    public void run() {
        System.out.println("[ SVR ] Running in thread " + Thread.currentThread().getName());
        this.timeKeeper.start();

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

    private void updateInternalGridCache() {}

    private void tick(double delta) {
        updateInternalGridCache();

        simulation.tick(delta);
        eventHandler.tick(delta);
    }
}
