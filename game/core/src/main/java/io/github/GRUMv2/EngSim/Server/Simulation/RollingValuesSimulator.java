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

    private float studentHousingCapacityMultiuplyer = 1;
    private float studentStudyCapacityMultiuplyer = 1;
    private float leisureCapacityMultiuplyer = 1;
    private float staffOfficeCapacityMultiuplyer = 1;
    private float monthlyUpkeepCostsMultiuplyer = 1;

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

        studentHousingCapacityMultiuplyer = (studentHousingCapacityMultiuplyer - 1) * 0.999f + 1;
        studentStudyCapacityMultiuplyer = (studentStudyCapacityMultiuplyer - 1) * 0.999f + 1;
        leisureCapacityMultiuplyer = (leisureCapacityMultiuplyer - 1) * 0.999f + 1;
        staffOfficeCapacityMultiuplyer = (staffOfficeCapacityMultiuplyer - 1) * 0.999f + 1;
        monthlyUpkeepCostsMultiuplyer = (monthlyUpkeepCostsMultiuplyer - 1) * 0.999f + 1;

//        System.out.println("[ ROL ] studentHousingCapacity: " + this.studentHousingCapacity());
//        System.out.println("[ ROL ] studentStudyCapacity: " + this.studentStudyCapacity());
//        System.out.println("[ ROL ] leisureCapacity: " + this.leisureCapacity());
//        System.out.println("[ ROL ] staffOfficeCapacity: " + this.staffOfficeCapacity());
//        System.out.println("[ ROL ] monthlyUpkeepCosts: " + this.monthlyUpkeepCosts());
    }

    private int getAvg(ArrayList<Integer> of) {
        long t = 0;
        for (int n : of) t += n;
        return (int) Math.floor((double) t / of.size());
    }

    public int studentHousingCapacity() {
        return (int) Math.floor(this.getAvg(this.studentHousingCapacityList) * studentHousingCapacityMultiuplyer);
    }

    public int studentStudyCapacity() {
        return (int) Math.floor(this.getAvg(this.studentStudyCapacityList) * studentStudyCapacityMultiuplyer);
    }

    public int leisureCapacity() {
        return (int) Math.floor(this.getAvg(this.leisureCapacityList) * leisureCapacityMultiuplyer);
    }

    public int staffOfficeCapacity() {
        return (int) Math.floor(this.getAvg(this.staffOfficeCapacityList) * staffOfficeCapacityMultiuplyer);
    }

    public int monthlyUpkeepCosts() {
        return (int) Math.floor(this.getAvg(this.monthlyUpkeepCostsList) * monthlyUpkeepCostsMultiuplyer);
    }

    public void setStudentHousingCapacityMultiuplyer(float to) {
        studentHousingCapacityMultiuplyer = to;
    }

    public void setStudentStudyCapacityMultiuplyer(float to) {
        studentStudyCapacityMultiuplyer = to;
    }

    public void setLeisureCapacityMultiuplyer(float to) {
        leisureCapacityMultiuplyer = to;
    }

    public void setStaffOfficeCapacityMultiuplyer(float to) {
        staffOfficeCapacityMultiuplyer = to;
    }

    public void setMonthlyUpkeepCostsMultiuplyer(float to) {
        monthlyUpkeepCostsMultiuplyer = to;
    }
}
