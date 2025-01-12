package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.entities.Pub;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PubRiotEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (getBuildingNumber(Pub.class) == 0) {
            return true;
        }

        if (random.nextInt(0, 10_000) > 5) {
            return true;
        }

        server.popupManager.addPopup("Pub Riot!", "The students have rioted in the pub!\n" +
            "The pub has been destroyed and will need to be rebuilt.");

        List<Pub> pubs = broker.getEntities().stream()
            .filter(entity -> entity instanceof Pub)
            .map(entity -> (Pub) entity)
            .collect(Collectors.toList());

        broker.destroyBuilding(pubs.get(random.nextInt(0, pubs.size())).getMapPos());

        return false;
    }
}
