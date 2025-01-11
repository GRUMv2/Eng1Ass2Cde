package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.Server.Server;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;


public abstract class Event {
    Broker broker;
    public Server server;

    protected Event() {
        broker = Broker.getInstance();
    }

    abstract public boolean tick(double delta, EventHandler handler);

    public int getBuildingNumber(Class<? extends Building> building) {
        return broker.getEntities().stream().filter(building::isInstance).toArray().length;
    }
}
