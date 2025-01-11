import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory;
import io.github.GRUMv2.EngSim.entities.Halls;
import io.github.GRUMv2.EngSim.entities.Pub;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Testing for:
- BuildingFactory exhibits correct singleton behaviour
- Buildings are created by BuildingFactory
 */

public class BuildingFactoryTest {
    private BuildingFactory buildingFactory;

    @BeforeEach
    public void setUp() {
        buildingFactory = BuildingFactory.getInstance();
    }

    @Test
    public void buildingFactoryIsValidSingleton() {
        BuildingFactory testFactory = BuildingFactory.getInstance();
        assertEquals(testFactory, buildingFactory);
    }

    @Test
    public void buildingFactoryProducesValidBuilding() {
        Building f = buildingFactory.newBuilding(Available.HALLS, new Vector2(0,0));
        assertTrue(() -> f instanceof Halls);

        Building g = buildingFactory.newBuilding(Available.PUB, new Vector2(0,0));
        assertTrue(() -> g instanceof Pub);

        assertNotEquals(f, g);
    }
}
