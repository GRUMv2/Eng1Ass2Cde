package io.github.GRUMv2.EngSim.Server.Simulation;


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


    RollingValuesSimulator () {

    }

    private void addValue (ArrayList<Integer> list, int value) {
        list.add(value);
        if (list.size() > 1000) list.remove(0);
    }

    public void tick (CopyOnWriteArrayList<ForegroundEntity> entities) {
        int studentHousingCapacityS = 0;
        int studentStudyCapacityS = 0;
        int leisureCapacityS = 0;
        int staffOfficeCapacityS = 0;
        int monthlyUpkeepCostsS = 0;

        for (ForegroundEntity  entity : entities) {
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


//        System.out.println("[ ROL ] studentHousingCapacity: " + this.studentHousingCapacity());
//        System.out.println("[ ROL ] studentStudyCapacity: " + this.studentStudyCapacity());
//        System.out.println("[ ROL ] leisureCapacity: " + this.leisureCapacity());
//        System.out.println("[ ROL ] staffOfficeCapacity: " + this.staffOfficeCapacity());
//        System.out.println("[ ROL ] monthlyUpkeepCosts: " + this.monthlyUpkeepCosts());
    }

    private int getAvg(ArrayList<Integer> of) {
        long t = 0;
        for (int n:of) t += n;
        return (int) Math.floor((double) t / of.size());
    }

    public int studentHousingCapacity() {
        return this.getAvg(this.studentHousingCapacityList);
    }

    public int studentStudyCapacity() {
        return this.getAvg(this.studentStudyCapacityList);
    }

    public int leisureCapacity() {
        return this.getAvg(this.leisureCapacityList);
    }

    public int staffOfficeCapacity() {
        return this.getAvg(this.staffOfficeCapacityList);
    }

    public int monthlyUpkeepCosts() {
        return this.getAvg(this.monthlyUpkeepCostsList);
    }
}
