package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class BusyBeeAchievement extends Achievement{

    private final Broker broker;

    BusyBeeAchievement(){
        super();
        broker = Broker.getInstance();
        awardName = "Busy Bee!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler){
        if(broker.getGamePausedNumber()>= 5){
           this.award();
           return false;
        }
        return true;

    }

}
