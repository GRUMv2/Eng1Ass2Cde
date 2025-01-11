package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class CityCreatorAchievement extends Achievement{
    
    private final Broker broker;

    CityCreatorAchievement(){
        super();
        broker = Broker.getInstance();
        awardName = "Wow What a Massive Campus!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler){
        if(broker.getTotalBuildings()>= 10){
           this.award();
           return false;
        }
        return true;
    }


}
