package io.github.GRUMv2.EngSim.server.eventHandler;

import io.github.GRUMv2.EngSim.entities.Gym;

import java.util.Random;

public class RosesEvent extends Event {
    private final Random random = new Random();

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (getBuildingNumber(Gym.class) < 2) {
            return true;
        }

        if (random.nextInt(0, 10_000) > 5) {
            return true;
        }

        server.popupManager.addPopup("Its that time of year!", "Roses has started!\n" +
                "things are a little more expensive but the students are loving it!");

        server.setStudentHousingCapacityMultiplier(2f);
        server.setMonthlyUpkeepCostsMultiplier(4f);

        return false;
    }
}
