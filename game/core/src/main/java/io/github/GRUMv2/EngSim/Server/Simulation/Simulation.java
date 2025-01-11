package io.github.GRUMv2.EngSim.Server.Simulation;

import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.ForegroundEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Simulation {
    private final Broker broker;
    private final StudentWalkSimulation studentWalkSimulation;
    private final RollingValuesSimulator rollingValuesSimulator;

    private long currentMoney = 250_000; // You'd have to be pretty good to go over 2bil but just in case

    public Simulation() {
        System.out.println("[ SIM ] Simulation started");

        this.broker = Broker.getInstance();
        studentWalkSimulation = new StudentWalkSimulation();
        rollingValuesSimulator = new RollingValuesSimulator();
    }

    public float getStudentSatisfaction() {
        float studentStaffRatio = (float) rollingValuesSimulator.studentHousingCapacity() / (float) rollingValuesSimulator.staffOfficeCapacity();
        float studentStaffRatioPenalty = Math.abs(studentStaffRatio - 100f) / 1000;

        return (float) Math.max(0, studentWalkSimulation.getScore() - studentStaffRatioPenalty);
    }

    public int getStudentNumbers() {
        return (int) Math.floor(rollingValuesSimulator.studentHousingCapacity() * Math.max(0.5f, getStudentSatisfaction()));
    }

    public float getStaffSatisfaction() {
        float studentStaffRatio = (float) rollingValuesSimulator.studentHousingCapacity() / (float) rollingValuesSimulator.staffOfficeCapacity();
        float studentStaffRatioPenalty = Math.abs(studentStaffRatio - 100f) / 1000;

        return 1 - Math.max(0f, Math.min(1f, studentStaffRatioPenalty));
    }

    public int getStaffNumbers() {
        return rollingValuesSimulator.staffOfficeCapacity();
    }

    public long getMoney() {
        return this.currentMoney;
    }

    public void spendMoney(int spent) {
        this.currentMoney -= spent;
    }

    private int getIncomeI() {
        // no international students here :(
        // /12 /300

        long studentIncome = (long) getStudentNumbers() * 9250;
        long staffWages = (long) getStaffNumbers() * 44_000;
        long buildingUpkeep = (long) this.rollingValuesSimulator.monthlyUpkeepCosts() * 12;

        long totalIncome = studentIncome - staffWages - buildingUpkeep;
        long totalIncomePerMonth = totalIncome / 12;
        long totalIncomePerTick = totalIncomePerMonth / 300;

        return (int) totalIncomePerTick;
    }

    public int getIncome() {
        return this.getIncomeI() * 300;
    }

    // minor architecture mishaps
    private HashMap<Class<? extends ForegroundEntity>, ArrayList<Vector2>> generateIntermediaryBuildingMappings(ConcurrentHashMap<Vector2, ForegroundEntity> t_grid) {

        HashMap<Class<? extends ForegroundEntity>, ArrayList<Vector2>> buildings = new HashMap<>();

        for (Map.Entry<Vector2, ForegroundEntity> entry : t_grid.entrySet()) {
            Vector2 entryKey = entry.getKey();
            ForegroundEntity entryValue = entry.getValue();

            Class<? extends ForegroundEntity> entityClass = entryValue.getClass();
            buildings.computeIfAbsent(entityClass, k -> new ArrayList<>());
            buildings.get(entityClass).add(entryKey);
        }

        return buildings;
    }

    public void tick(double delta) {
        ConcurrentHashMap<Vector2, ForegroundEntity> grid = this.broker.getGrid();
        CopyOnWriteArrayList<ForegroundEntity> entities = this.broker.getEntities();
        HashMap<Class<? extends ForegroundEntity>, ArrayList<Vector2>> map = this.generateIntermediaryBuildingMappings(grid);

        studentWalkSimulation.tick(grid, map);
        rollingValuesSimulator.tick(entities);

        this.currentMoney += this.getIncomeI();
    }

    public void setStudentHousingCapacityMultiuplyer(float to) {
        this.rollingValuesSimulator.setStudentHousingCapacityMultiuplyer(to);
    }

    public void setStudentStudyCapacityMultiuplyer(float to) {
        this.rollingValuesSimulator.setStudentStudyCapacityMultiuplyer(to);
    }

    public void setLeisureCapacityMultiuplyer(float to) {
        this.rollingValuesSimulator.setLeisureCapacityMultiuplyer(to);
    }

    public void setStaffOfficeCapacityMultiuplyer(float to) {
        this.rollingValuesSimulator.setStaffOfficeCapacityMultiuplyer(to);
    }

    public void setMonthlyUpkeepCostsMultiuplyer(float to) {
        this.rollingValuesSimulator.setMonthlyUpkeepCostsMultiuplyer(to);
    }

}
