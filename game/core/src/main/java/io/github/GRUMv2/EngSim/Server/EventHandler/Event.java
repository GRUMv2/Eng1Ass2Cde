package io.github.GRUMv2.EngSim.Server.EventHandler;


public abstract class Event {
    abstract public boolean tick(double delta, EventHandler handler);
}
