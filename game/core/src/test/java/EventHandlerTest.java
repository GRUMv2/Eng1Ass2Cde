import io.github.GRUMv2.EngSim.Server.EventHandler.EventHandler;
import io.github.GRUMv2.EngSim.Server.EventHandler.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/*
Testing for:
- Events that should be removed are removed
- Events that should not be removed are not removed
 */

class InitialiseRemoveTestEvent extends Event {
    @Override
    public boolean tick(double delta, EventHandler handler) {
        return false;
    }
}

class InitialiseRemainTestEvent extends Event {
    @Override
    public boolean tick(double delta, EventHandler handler) {
        return true;
    }
}


public class EventHandlerTest {
    private EventHandler eventHandler;

    @BeforeEach
    public void setUp() {
        eventHandler = new EventHandler();
    }

    @Test
    public void initialiseTestEventIsRemovedAfterTick() throws NoSuchFieldException, IllegalAccessException {
        Field activeEventsField = EventHandler.class.getDeclaredField("activeEvents");
        activeEventsField.setAccessible(true);
        List<InitialiseRemoveTestEvent> activeEvents = (List<InitialiseRemoveTestEvent>) activeEventsField.get(eventHandler);

        // Remove all events and add just the test case event
        activeEvents.clear();
        activeEvents.add(new InitialiseRemoveTestEvent());
        assertEquals(1, activeEvents.size());

        eventHandler.tick(0.016);

        assertEquals(0, activeEvents.size());
    }

    @Test
    public void remainEventTestIsNotRemovedAfterTick() throws NoSuchFieldException, IllegalAccessException {
        Field activeEventsField = EventHandler.class.getDeclaredField("activeEvents");
        activeEventsField.setAccessible(true);
        List<InitialiseRemainTestEvent> activeEvents = (List<InitialiseRemainTestEvent>) activeEventsField.get(eventHandler);

        // Remove all events and add just the test case event
        activeEvents.clear();
        activeEvents.add(new InitialiseRemainTestEvent());
        assertEquals(1, activeEvents.size());

        eventHandler.tick(0.016);

        assertEquals(1, activeEvents.size());
    }
}
