package io.github.GRUMv2.EngSim.server.eventHandler;

import io.github.GRUMv2.EngSim.server.Server;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manages the events that are active
 */
public class EventHandler {
    private final Server server;
    final ArrayList<Event> activeEvents = new ArrayList<>();

    public EventHandler(Server server) {
        System.out.println("[ EVN ] eventHandler created");

        // achievements
        activeEvents.add(new BrokeAchievement());
        activeEvents.add(new FromNothingAchievement());
        activeEvents.add(new CityCreatorAchievement());
        activeEvents.add(new BusyBeeAchievement());
        activeEvents.add(new MassivePopulationAchievement());
        activeEvents.add(new QuitTheGameAchievement());
        activeEvents.add(new StartTheGameAchievement());

        // events
        activeEvents.add(new LotteryEvent());
        activeEvents.add(new RishiSunacEvent());
        activeEvents.add(new GovernmentGrantEvent());
        activeEvents.add(new PubRiotEvent()); // Pubs
        activeEvents.add(new RosesEvent()); // Gyms
        activeEvents.add(new PoliticalMovementEvent());
        activeEvents.add(new CovidEvent());
        activeEvents.add(new InactivityEvent());

        this.server = server;
    }

    /**
     * Tick all active events
     *
     * @param delta time since last tick
     */
    public void tick(double delta) {
        Iterator<Event> iterator = activeEvents.iterator();

        while (iterator.hasNext()) {
            Event event = iterator.next();

            if (event.server == null) {
                event.server = server;
            }

            boolean resp = event.tick(delta, this);

            if (!resp) {
                iterator.remove();
                System.out.println("[ EVN ] '" + event.getClass().getSimpleName() + "' events removed");
                // avoids 'io.github.GRUMv2.EngSim.server.eventHandler.InitialiseTestEvent@7d492h2'
                // for 'eventHandler'
            }
        }
    }
}
