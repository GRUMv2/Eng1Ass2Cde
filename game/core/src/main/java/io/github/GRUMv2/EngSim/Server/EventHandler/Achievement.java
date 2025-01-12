package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class Achievement extends Event {
    String awardName = "default award";

    /**
     * gives standard way for achievements to be shown to the user
     */
    void award() {
        Broker.getInstance().achievementAwarded(this.awardName);
    }

    /**
     * only overwritten incase we forget lol
     */
    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}
