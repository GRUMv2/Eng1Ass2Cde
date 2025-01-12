package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.Server.Server;

import java.util.ArrayList;
import java.util.Iterator;

public class EventHandler {
    ArrayList<Event> activeEvents = new ArrayList<>();
    private final Server server;

    public EventHandler(Server server) {
        System.out.println("[ EVN ] EventHandler created");

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
        activeEvents.add(new PubRiotEvent());
        activeEvents.add(new RosesEvent());
        activeEvents.add(new PoliticalMovementEvent());
        activeEvents.add(new CovidEvent());

        this.server = server;
    }

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
                // avoids 'io.github.GRUMv2.EngSim.Server.EventHandler.InitialiseTestEvent@7d492h2'
                // for 'EventHandler'
            }
        }
    }
}
