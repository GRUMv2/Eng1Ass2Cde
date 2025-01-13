package io.github.GRUMv2.EngSim.server;


import io.github.GRUMv2.EngSim.server.eventHandler.EventHandler;
import io.github.GRUMv2.EngSim.server.popupManager.PopupManager;
import io.github.GRUMv2.EngSim.server.simulation.Simulation;
import io.github.GRUMv2.EngSim.broker.Broker;


/**
 * The server class is the main class that runs the simulation. It is responsible for
 * running the simulation, handling events, and managing the popup system.
 */
public class Server extends Thread {
    public final TimeKeeper timeKeeper = new TimeKeeper(1);
    public final PopupManager popupManager = new PopupManager();
    private final int targetTPS;

    private final Simulation simulation;
    private final EventHandler eventHandler;
    private final Broker broker;
    private boolean isRunning = true;
    private boolean isPaused = true;


    /**
     * Creates a new server with the given target ticks per second.
     *
     * @param targetTPS The target ticks per second.
     */
    public Server(int targetTPS) {
        super("server");
        this.targetTPS = targetTPS;

        System.out.println("[ SVR ] server created");

        simulation = new Simulation();
        eventHandler = new EventHandler(this);

        broker = Broker.getInstance();
    }

    /**
     * Pauses the server, events, and simulation.
     */
    public void Pause() {
        isPaused = true;

        this.timeKeeper.pause();
    }

    /**
     * Resumes the server, events, and simulation.
     */
    public void Resume() {
        isPaused = false;

        this.timeKeeper.unpause();
    }

    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Stops the server, events, and simulation.
     */
    public void Stop() {
        System.out.println("[ SVR ] Stopping");
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    // called by threads
    @SuppressWarnings("BusyWait")
    // Not sure how else to implement this, it kind of has to be busy waiting
    // to keep the server running at a consistent tick rate.
    public void run() {
        System.out.println("[ SVR ] Running in thread " + Thread.currentThread().getName());

        long processTime = 0;

        while (isRunning) {
            // kill the client if the server is dead
            broker.setServerHeartbeat(System.currentTimeMillis());

            // kill the server if the client is dead
            if (broker.serverSuicide()) {
                System.out.println("[ HTM ] The whitman has been called- dun dun dun! (server)");
                isRunning = false;
            }

            // Time how long the tick takes ...
            long lastTime = System.nanoTime();

            if (!isPaused) {
                double deltaMillis = processTime / 1_000_000.0;
                tick(deltaMillis);
            }

            processTime = System.nanoTime() - lastTime;

            // check if iteration ran over time budget
            if (processTime > 1_000_000_000 / targetTPS) {
                System.out.println("[ SVR ] Tick took too long: " + processTime / 1_000_000.0 + "ms");
            }

            long sleepFor = Math.max(0, (1_000_000_000 / targetTPS) - processTime);

            // ... and account for that in the sleep time
            try {
                Thread.sleep(sleepFor / 1_000_000, (int) (sleepFor % 1_000_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("[ SVR ] Stopped.");
    }

    /**
     * Ticks the simulation, events, and popup system.
     *
     * @param delta The time since the last tick in milliseconds.
     */
    private void tick(double delta) {
        simulation.tick(delta);
        eventHandler.tick(delta);
        popupManager.serverTick(delta);

        if (broker.getPendingSpendMoney() != 0) {
            simulation.spendMoney(broker.reconcileFunds());
        }

        broker.serverPush(
            (float) this.timeKeeper.currentGameTime() / 1000,
            this.timeKeeper.currentGameTimeFormatted(),
            simulation.getStudentSatisfaction(),
            simulation.getStudentNumbers(),
            simulation.getStaffSatisfaction(),
            simulation.getStaffNumbers(),
            simulation.getMoney(),
            simulation.getIncome()
        );
    }

    public void setStudentHousingCapacityMultiplier(float to) {
        this.simulation.setStudentHousingCapacityMultiplier(to);
    }

    public void setStudentStudyCapacityMultiplier(float to) {
        this.simulation.setStudentStudyCapacityMultiplier(to);
    }

    public void setLeisureCapacityMultiplier(float to) {
        this.simulation.setLeisureCapacityMultiplier(to);
    }

    public void setStaffOfficeCapacityMultiplier(float to) {
        this.simulation.setStaffOfficeCapacityMultiplier(to);
    }

    public void setMonthlyUpkeepCostsMultiplier(float to) {
        this.simulation.setMonthlyUpkeepCostsMultiplier(to);
    }
}
