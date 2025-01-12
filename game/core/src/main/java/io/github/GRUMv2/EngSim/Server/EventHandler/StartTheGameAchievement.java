package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class StartTheGameAchievement extends Achievement {

    StartTheGameAchievement() {
        super();
        awardName = "Start the game!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        this.award();
        return false;
    }
}
