package io.github.GRUMv2.EngSim.server.eventHandler;

import io.github.GRUMv2.EngSim.server.Server;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;


public abstract class Event {
    public Server server;
    final Broker broker;

    protected Event() {
        broker = Broker.getInstance();
    }

    /**
     * Tick the event, this is called every server tick and allows events to interact with the game
     *
     * @param delta   time since last tick in milliseconds
     * @param handler the event handler itself
     * @return true if the event needs to be ticked again, false if the event is done
     */
    abstract public boolean tick(double delta, EventHandler handler);

    public int getBuildingNumber(Class<? extends Building> building) {
        return broker.getEntities().stream().filter(building::isInstance).toArray().length;
    }
}
