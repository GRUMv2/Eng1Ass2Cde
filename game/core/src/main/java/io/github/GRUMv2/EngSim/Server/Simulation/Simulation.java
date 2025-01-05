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

    private long currentMoney = 50_000_000; // You'd have to be pretty good to go over 2bil but just in case

    public Simulation() {
        System.out.println("[ SIM ] Simulation started");

        this.broker = Broker.getInstance();
        studentWalkSimulation = new StudentWalkSimulation();
        rollingValuesSimulator = new RollingValuesSimulator();
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

    public long getMoney() {
        return this.currentMoney;
    }

    public void spendMoney(int spent) {
        this.currentMoney -= spent;
    }

    public int getIncome() {
        return 700_000;
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

        System.out.println("Walk score: " + studentWalkSimulation.getScore());
    }
}
