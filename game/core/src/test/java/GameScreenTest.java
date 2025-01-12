import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.GRUMv2.EngSim.client.GameScreen;
import io.github.GRUMv2.EngSim.client.GameScreen.Modes;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing for:
 *  - setBuildingToPlace correctly setting buildingToPlace
 *  - toggleMode
 */

public class GameScreenTest {
    private GameScreen screen;

    @BeforeEach
    public void setUp() {
        // Pass null values here as renderering can/will not be tested
        screen = new GameScreen(null, null);

    }

    @Test
    public void setBuildingToPlaceSetsBuildingToPlace() {
        // Check building type set correctly
        screen.setBuildingToPlace(Available.PUB);
        assertEquals(Available.PUB, screen.getBuildingToPlace());

        // Check building type updates correctly
        screen.setBuildingToPlace(Available.GYM);
        assertNotEquals(Available.PUB, screen.getBuildingToPlace());

        // Check mode correctly changed to NORMAL ("building") mode
        assertEquals(Modes.NORMAL, screen.getMode());
    }

    @Test
    public void testToggleMode() {
        // Sanity test: GameScreen should init to Modes.NORMAL
        assertEquals(Modes.NORMAL, screen.getMode());

        // Check mode correctly updates
        screen.toggleMode(Modes.MOVE);
        assertEquals(Modes.MOVE, screen.getMode());

        // Check mode correctly toggles
        screen.toggleMode(Modes.MOVE);
        assertEquals(Modes.NORMAL, screen.getMode());

        // Check buildingToPlace is unset
        screen.setBuildingToPlace(Available.PUB);
        screen.toggleMode(Modes.DESTROY);
        assertNull(screen.getBuildingToPlace());

    }
}
