package io.github.GRUMv2.EngSim.Server;


import io.github.GRUMv2.EngSim.Server.EventHandler.EventHandler;
import io.github.GRUMv2.EngSim.Server.PopupManager.PopupManager;
import io.github.GRUMv2.EngSim.Server.Simulation.Simulation;
import io.github.GRUMv2.EngSim.broker.Broker;


public class Server extends Thread {
    public final TimeKeeper timeKeeper = new TimeKeeper(1);
    public final PopupManager popupManager = new PopupManager();
    private final int targetTPS;

    private final Simulation simulation;
    private final EventHandler eventHandler;
    private final Broker broker;
    private boolean isRunning = true;
    private boolean isPaused = true;

    public Server(int targetTPS) {
        super("Server");
        this.targetTPS = targetTPS;

        System.out.println("[ SVR ] Server created");

        simulation = new Simulation();
        eventHandler = new EventHandler(this);

        broker = Broker.getInstance();
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
        System.out.println("[ SVR ] Stopping");
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
// Simulation passthroughs
//   Now redundant, broker will handle this
//    // Driven by:
//    // - Location of buildings
//    // - Staff Student Ratio
//    // - Student building ratio
//    // Can also be affected by events
//    public float getStudentSatisfaction() {
//        return simulation.getStudentSatisfaction();
//    }
//
//    // most of the time is the maximum possible given the number of halls,
//    // unless the student satisfaction is too low then will drop as people
//    // drop out.
//    // Can also be affected by events
//    //
//    // Average students per building: 300
//    public int getStudentNumbers() {
//        return simulation.getStudentNumbers();
//    }
//
//    // Driven by:
//    // - Location to car parks
//    // - Staff student satisfaction
//    // - Student turnout (to lectures)
//    //   - Driven by distance to halls
//    // Can also be affected by events
//    public float getStaffSatisfaction() {
//        return simulation.getStaffSatisfaction();
//    }
//
//    // Again mostly driven by the number of offices (that is itself driven by
//    // number of placed buildings) unless staff satisfaction is too low.
//    // Can also be affected by events
//    public int getStaffNumbers() {
//        return simulation.getStaffNumbers();
//    }
//
//    // Driven by
//    // - Income
//    // - User building buildings
//    // - Events can directly add/remove
//    //
//    // Average cost of halls building 20_000_000
//    public long getMoney() {
//        return simulation.getMoney();
//    }
//
//    public void spendMoney(int spent) {
//        simulation.spendMoney(spent);
//    }
//
//    // Driven by
//    // - Number students
//    // - International student ratio
//    // - Staff numbers
//    // - Staff satisfaction
//    //   - Low staff satisfaction numbers will increase their
//    //     wage to prevent them from being fired
//    public int getIncome() {
//        return simulation.getIncome();
//    }
//
//    // TimeHandler passthroughs
//    public long getGameTime() {
//        return this.timeKeeper.currentGameTime();
//    }
//
//    public String getGameTimeFormatted() {
//        return this.timeKeeper.currentGameTimeFormatted();
//    }

    // Calls the tick function, handles isRunning and isPaused
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

    private void updateInternalGridCache() {
    }

    private void tick(double delta) {
        updateInternalGridCache();

        simulation.tick(delta);
        eventHandler.tick(delta);
        popupManager.serverTick(delta);

//        System.out.println((float) this.timeKeeper.currentGameTime());

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
