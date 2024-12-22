package io.github.GRUMv2.EngSim.Server.Simulation;

public class Simulation {
    public Simulation() {
        System.out.println("[ SIM ] Simulation started");
    }

    public float getStudentSatisfaction() {
        return 0.5f;
    }

    public int getStudentNumbers() {
        return 1_000;
    }

    public float getStaffSatisfaction() {
        return 0.5f;
    }

    public int getStaffNumbers() {
        return 5;
    }

    public int getMoney() {
        return 50_000_000;
    }

    public int getIncome() {
        return 700_000;
    }

    public void tick(double delta) {

    }
}
