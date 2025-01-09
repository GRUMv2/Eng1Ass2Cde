package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.broker.Broker;

public class BrokeAcheivement extends Acheivement{
    private final Broker broker;

    BrokeAcheivement() {
        super();
        broker = Broker.getInstance();
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        System.out.println("BrokeAcheivement tick " + broker.getMoney());

        if (broker.getMoney() < 0) {
            this.award();
            return false;
        }
        return true;
    }
}
