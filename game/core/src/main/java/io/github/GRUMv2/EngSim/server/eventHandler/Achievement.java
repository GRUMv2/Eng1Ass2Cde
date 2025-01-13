package io.github.GRUMv2.EngSim.server.eventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class Achievement extends Event {
    String awardName = "default award";

    /**
     * gives standard way for achievements to be shown to the user
     */
    void award() {
        Broker.getInstance().achievementAwarded(this.awardName);
        server.popupManager.addPopup("Achievement Unlocked: " + this.awardName);
        System.out.println("[ ACH ] '" + this.awardName + "' awarded");
    }

    /**
     * only overwritten in case we forget lol
     */
    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}
