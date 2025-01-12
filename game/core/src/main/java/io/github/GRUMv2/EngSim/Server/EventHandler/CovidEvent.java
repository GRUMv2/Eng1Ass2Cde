package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class CovidEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 100_000) > 3) {
            return true;
        }
        server.popupManager.addPopup("Uh oh!",
            "A new strain of Covid has been discovered in your city.\n" +
                "The university has been forced to spend 10k per building to\n" +
                "upgrade their safety measures.");

        broker.spendMoney(10_000 * broker.getTotalBuildings());
        return false;
    }
}
