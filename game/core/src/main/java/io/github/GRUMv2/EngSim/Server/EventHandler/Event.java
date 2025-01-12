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

    /**
    * Tick the event, this is called every server tick and allows events to interact with the game
    * @param delta time since last tick in miliseconds
    * @param handler the event handler itself
    * @return true if the event needs to be ticked again, false if the event is done
    */
    abstract public boolean tick(double delta, EventHandler handler);

    public int getBuildingNumber(Class<? extends Building> building) {
        return broker.getEntities().stream().filter(building::isInstance).toArray().length;
    }
}
