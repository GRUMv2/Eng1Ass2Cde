package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class BrokeAchievement extends Achievement {
    private final Broker broker;

    BrokeAchievement() {
        super();
        broker = Broker.getInstance();
        awardName = "Go Broke!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (broker.getMoney() < 0) {
            this.award();
            return false;
        }
        return true;
    }
}
