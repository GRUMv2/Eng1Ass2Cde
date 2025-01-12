package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.util.Random;

public class PoliticalMovementEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (random.nextInt(0, 100_000) > 5) {
            return true;
        }

        server.popupManager.addPopup("Political Movement!",
            "Some of your students arnt happy with the university's stance\n" +
                "on a political issue, they have started a protest!\n\n");

        server.setStudentHousingCapacityMultiuplyer(0.5f);

        return false;
    }
}
