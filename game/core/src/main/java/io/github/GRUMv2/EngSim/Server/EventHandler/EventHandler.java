package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manages the events that are active
 */
public class EventHandler {
    ArrayList<Event> activeEvents = new ArrayList<>();

    public EventHandler() {
        System.out.println("[ EVN ] EventHandler created");

        // achievements
        activeEvents.add(new BrokeAchievement());
        activeEvents.add(new FromNothingAchievement());
        activeEvents.add(new LotteryAchievement());
    }

    /**
     * Tick all active events
     * @param delta time since last tick
     */
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
