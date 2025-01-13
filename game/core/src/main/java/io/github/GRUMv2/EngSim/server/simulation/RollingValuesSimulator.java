package io.github.GRUMv2.EngSim.server.simulation;


import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.ForegroundEntity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

// Using a rolling value average removes the need to have a 'building' state as it allows us to
// control how quickly the values change. taking a few seconds to update the student numbers removes
// immediate income for example.
public class RollingValuesSimulator {
    private final ArrayList<Integer> studentHousingCapacityList = new ArrayList<>();
    private final ArrayList<Integer> studentStudyCapacityList = new ArrayList<>();
    private final ArrayList<Integer> leisureCapacityList = new ArrayList<>();
    private final ArrayList<Integer> staffOfficeCapacityList = new ArrayList<>();
    private final ArrayList<Integer> monthlyUpkeepCostsList = new ArrayList<>();

    private float studentHousingCapacityMultiplier = 1;
    private float studentStudyCapacityMultiplier = 1;
    private float leisureCapacityMultiplier = 1;
    private float staffOfficeCapacityMultiplier = 1;
    private float monthlyUpkeepCostsMultiplier = 1;

    private void addValue(ArrayList<Integer> list, int value) {
        list.add(value);
        if (list.size() > 1000) list.remove(0);
    }

    public void tick(CopyOnWriteArrayList<ForegroundEntity> entities) {
        int studentHousingCapacityS = 0;
        int studentStudyCapacityS = 0;
        int leisureCapacityS = 0;
        int staffOfficeCapacityS = 0;
        int monthlyUpkeepCostsS = 0;

        for (ForegroundEntity entity : entities) {
            if (!(entity instanceof Building)) continue;

            studentHousingCapacityS += ((Building) entity).getStudentHousingCapacity();
            studentStudyCapacityS += ((Building) entity).getStudentStudyCapacity();
            leisureCapacityS += ((Building) entity).getLeisureCapacity();
            staffOfficeCapacityS += ((Building) entity).getStaffOfficeCapacity();
            monthlyUpkeepCostsS += ((Building) entity).getMonthlyUpkeepCosts();
        }

        this.addValue(studentHousingCapacityList, studentHousingCapacityS);
        this.addValue(studentStudyCapacityList, studentStudyCapacityS);
        this.addValue(leisureCapacityList, leisureCapacityS);
        this.addValue(staffOfficeCapacityList, staffOfficeCapacityS);
        this.addValue(monthlyUpkeepCostsList, monthlyUpkeepCostsS);

        studentHousingCapacityMultiplier = (studentHousingCapacityMultiplier - 1) * 0.999f + 1;
        studentStudyCapacityMultiplier = (studentStudyCapacityMultiplier - 1) * 0.999f + 1;
        leisureCapacityMultiplier = (leisureCapacityMultiplier - 1) * 0.999f + 1;
        staffOfficeCapacityMultiplier = (staffOfficeCapacityMultiplier - 1) * 0.999f + 1;
        monthlyUpkeepCostsMultiplier = (monthlyUpkeepCostsMultiplier - 1) * 0.999f + 1;
    }

    private int getAvg(ArrayList<Integer> of) {
        long t = 0;
        for (int n : of) t += n;
        return (int) Math.floor((double) t / of.size());
    }

    public int studentHousingCapacity() {
        return (int) Math.floor(this.getAvg(this.studentHousingCapacityList) * studentHousingCapacityMultiplier);
    }

    public int studentStudyCapacity() {
        return (int) Math.floor(this.getAvg(this.studentStudyCapacityList) * studentStudyCapacityMultiplier);
    }

    public int leisureCapacity() {
        return (int) Math.floor(this.getAvg(this.leisureCapacityList) * leisureCapacityMultiplier);
    }

    public int staffOfficeCapacity() {
        return (int) Math.floor(this.getAvg(this.staffOfficeCapacityList) * staffOfficeCapacityMultiplier);
    }

    public int monthlyUpkeepCosts() {
        return (int) Math.floor(this.getAvg(this.monthlyUpkeepCostsList) * monthlyUpkeepCostsMultiplier);
    }

    public void setStudentHousingCapacityMultiplier(float to) {
        studentHousingCapacityMultiplier = to;
    }

    public void setStudentStudyCapacityMultiplier(float to) {
        studentStudyCapacityMultiplier = to;
    }

    public void setLeisureCapacityMultiplier(float to) {
        leisureCapacityMultiplier = to;
    }

    public void setStaffOfficeCapacityMultiplier(float to) {
        staffOfficeCapacityMultiplier = to;
    }

    public void setMonthlyUpkeepCostsMultiplier(float to) {
        monthlyUpkeepCostsMultiplier = to;
    }
}
