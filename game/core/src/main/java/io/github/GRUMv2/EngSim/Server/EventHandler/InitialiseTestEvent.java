package io.github.GRUMv2.EngSim.Server.EventHandler;

public class InitialiseTestEvent extends Event {
    public InitialiseTestEvent() {
        System.out.println("[     ] InitialiseTestEvent created");
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        System.out.println("[     ] InitialiseTestEvent ticked");

        return false;
    }
}
