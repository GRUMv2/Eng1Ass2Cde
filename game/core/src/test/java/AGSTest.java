import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.GRUMv2.EngSim.client.AbstractGameScreen;
import io.github.GRUMv2.EngSim.client.InputHandler;
import io.github.GRUMv2.EngSim.client.Renderer;
import io.github.GRUMv2.EngSim.client.Screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Testing for:
 *  - changeEvent correctly sets, updates and runs
 */

class FakeScreen extends AbstractGameScreen {

    private int testValue = 0;

    public FakeScreen(Renderer renderer, InputHandler handler) {
        // Pass null values here as renderering can/will not be tested
        super(null, null);
    }

    @Override
    public void update(Renderer renderer, InputHandler inputHandler) {
        // do nothing
    }

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

}

public class AGSTest {
    private FakeScreen screen;

    @BeforeEach
    public void setUp() {
        // Pass null values here as renderering can/will not be tested
        screen = new FakeScreen(null, null);

    }

    @Test
    public void testChangeEvent() {
        screen.setChangeEvent(Screens.GAME, () -> screen.setTestValue(1337));
        // Check only executed on correct screen update and no error thrown
        screen.changeEvent(Screens.END);
        assertEquals(0, screen.getTestValue());
        // Check correctly executed on correct screen update
        screen.changeEvent(Screens.GAME);
        assertEquals(1337, screen.getTestValue());

        // Check multiple executions
        screen.setChangeEvent(Screens.GAME, () -> screen.setTestValue(-1337));
        screen.changeEvent(Screens.GAME);
        assertEquals(-1337, screen.getTestValue());
        screen.changeEvent(Screens.GAME);
        assertEquals(-1337, screen.getTestValue());

    }
}
