package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.ArrayList;
import java.util.Iterator;

public class EventHandler {
    ArrayList<Event> activeEvents = new ArrayList<>();;

    public EventHandler() {
        System.out.println("[ EVN ] EventHandler created");

//        // All events to be added here
//        activeEvents.add(new InitialiseTestEvent());
//        System.out.println("[ EVN ] 'InitialiseTestEvent' events created");
//        // TODO: look into way of automating this, gradle perhaps?
    }

    void getGrid(int x, int y) {
        // TODO
    }

    void setGrid(int x, int y) {
        // TODO: this, remember to update both internal cache and synchronously update the global grid
    }

    public void tick(double delta) {
        Iterator<Event> iterator = activeEvents.iterator();

        while (iterator.hasNext()) {
            Event event = iterator.next();
            boolean resp = event.tick(delta, this);

            if (!resp) {
                iterator.remove();
                System.out.println("[ EVN ] '" + event.getClass().getSimpleName() + "' events removed");
                // avoids 'io.github.GRUMv2.EngSim.Server.EventHandler.InitialiseTestEvent@7d492h2'
                // for 'EventHandler'
            }
        }
    }
}
