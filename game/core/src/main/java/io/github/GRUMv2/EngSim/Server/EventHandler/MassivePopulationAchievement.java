package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class MassivePopulationAchievement extends Achievement{

    private final Broker broker;

    MassivePopulationAchievement(){
        super();
        broker = Broker.getInstance();
        awardName = "Massive Population!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler){
        if(broker.getStudentNumbers()>= 15000){
           this.award();
           return false;
        }
        return true;

    }



}
