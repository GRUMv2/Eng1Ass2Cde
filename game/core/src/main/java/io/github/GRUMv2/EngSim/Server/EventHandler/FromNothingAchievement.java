package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class FromNothingAchievement extends Achievement {
    private final Broker broker;
    private boolean beenNegative = false;

    FromNothingAchievement() {
        super();
        broker = Broker.getInstance();
        awardName = "Build a fortune from nothing!";
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        double money = broker.getMoney();

        if (money < 0) {
            beenNegative = true;
        }
        if (beenNegative && money > 1_000_000) {
            this.award();
            return false;
        }

        return true;
    }
}
