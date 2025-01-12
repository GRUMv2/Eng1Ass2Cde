package io.github.GRUMv2.EngSim.Server;


import io.github.GRUMv2.EngSim.Server.EventHandler.EventHandler;
import io.github.GRUMv2.EngSim.Server.PopupManager.PopupManager;
import io.github.GRUMv2.EngSim.Server.Simulation.Simulation;
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
     * @param targetTPS The target ticks per second.
     */
    public Server(int targetTPS) {
        super("Server");
        this.targetTPS = targetTPS;

        System.out.println("[ SVR ] Server created");

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
    public void run() {
        System.out.println("[ SVR ] Running in thread " + Thread.currentThread().getName());

        long processTime = 0;

        while (isRunning) {
            // kill the client if the server is dead
            broker.setServerHeartbeat(System.currentTimeMillis());

            // kill the server if the client is dead
            if (broker.serverSuicide()) {
                System.out.println("[ HTM ] The hitman has been called- dun dun duuuun (server)");
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

    public void setStudentHousingCapacityMultiuplyer(float to) {
        this.simulation.setStudentHousingCapacityMultiuplyer(to);
    }

    public void setStudentStudyCapacityMultiuplyer(float to) {
        this.simulation.setStudentStudyCapacityMultiuplyer(to);
    }

    public void setLeisureCapacityMultiuplyer(float to) {
        this.simulation.setLeisureCapacityMultiuplyer(to);
    }

    public void setStaffOfficeCapacityMultiuplyer(float to) {
        this.simulation.setStaffOfficeCapacityMultiuplyer(to);
    }

    public void setMonthlyUpkeepCostsMultiuplyer(float to) {
        this.simulation.setMonthlyUpkeepCostsMultiuplyer(to);
    }
}
