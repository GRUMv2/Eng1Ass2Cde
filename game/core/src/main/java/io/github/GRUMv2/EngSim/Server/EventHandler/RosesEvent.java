package io.github.GRUMv2.EngSim.Server.EventHandler;

import io.github.GRUMv2.EngSim.entities.Gym;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

        server.setStudentHousingCapacityMultiuplyer(2f);
        server.setMonthlyUpkeepCostsMultiuplyer(2f);

        return false;
    }
}
