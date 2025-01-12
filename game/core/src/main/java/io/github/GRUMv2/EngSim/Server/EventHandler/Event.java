package io.github.GRUMv2.EngSim.Server.EventHandler;


public abstract class Event {
    /**
     * Tick the event, this is called every server tick and allows events to interact with the game
     * @param delta time since last tick in miliseconds
     * @param handler the event handler itself
     * @return true if the event needs to be ticked again, false if the event is done
     */
    abstract public boolean tick(double delta, EventHandler handler);
}
