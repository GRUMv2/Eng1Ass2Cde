package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.Server.Server;
import io.github.GRUMv2.EngSim.broker.Broker;

public class Achievement extends Event {
    String awardName = "default award";

    Achievement() {
        super();
    }

    void award() {
        Broker.getInstance().achievementAwarded(this.awardName);
        server.popupManager.addPopup("Achievement Unlocked", this.awardName);
        System.out.println("[ ACH ] '" + this.awardName + "' awarded");
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}
