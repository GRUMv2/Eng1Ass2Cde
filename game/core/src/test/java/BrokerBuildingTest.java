import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.math.Vector2;
import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrokerBuildingTest {
    private BuildingFactory buildingFactory;
    private Broker broker;

    @BeforeEach
    public void setUp() throws Exception {
        java.lang.reflect.Field instanceField = Broker.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        broker = Broker.getInstance();
        buildingFactory = BuildingFactory.getInstance();
    }

    @Test
    public void placeBuilding() {
        Building accomBuilding = buildingFactory.newBuilding(Available.HALLS, new Vector2(0, 0));
        boolean result = broker.placeBuilding(accomBuilding);

        assertTrue(result, "building placed: successful");
        assertEquals(1, broker.getBuildingCount(Available.HALLS), "building count incremented");
    }

    @Test
    public void placeBuildingMultiple() {
        Building accomBuilding = buildingFactory.newBuilding(Available.HALLS, new Vector2(0, 0));
        Building gymBuilding = buildingFactory.newBuilding(Available.GYM, new Vector2(5, 5));
        Building pubBuilding = buildingFactory.newBuilding(Available.PUB, new Vector2(10, 10));
        Building lectureHallBuilding = buildingFactory.newBuilding(Available.LECTURE_HALL, new Vector2(20, 20));
        boolean halls = broker.placeBuilding(accomBuilding);
        boolean gym = broker.placeBuilding(gymBuilding);
        boolean pub = broker.placeBuilding(pubBuilding);
        boolean lectureHall = broker.placeBuilding(lectureHallBuilding);

        assertTrue(halls, "halls placed: successful");
        assertTrue(gym, "gym placed: successful");
        assertTrue(pub, "pub placed: successful");
        assertTrue(lectureHall, "lecture hall placed: successful");
        assertEquals(1, broker.getBuildingCount(Available.HALLS), "halls building count incremented");
        assertEquals(1, broker.getBuildingCount(Available.GYM), "gym building count incremented");
        assertEquals(1, broker.getBuildingCount(Available.PUB), "pub building count incremented");
        assertEquals(1, broker.getBuildingCount(Available.LECTURE_HALL), "lecture hall building count incremented");
    }

    @Test
    public void placeBuildingOutOfBounds() {
        Building accomBuilding = buildingFactory.newBuilding(Available.HALLS, new Vector2(-1, 0));
        Building gymBuilding = buildingFactory.newBuilding(Available.GYM, new Vector2(0, -1));
        Building pubBuilding = buildingFactory.newBuilding(Available.PUB, new Vector2(0, broker.getMapCells() + 1));
        Building lectureHallBuilding = buildingFactory.newBuilding(Available.LECTURE_HALL,
                new Vector2(broker.getMapCells() + 1, 0));
        boolean halls = broker.placeBuilding(accomBuilding);
        boolean gym = broker.placeBuilding(gymBuilding);
        boolean pub = broker.placeBuilding(pubBuilding);
        boolean lectureHall = broker.placeBuilding(lectureHallBuilding);

        assertFalse(halls, "halls placed: unsuccessful");
        assertFalse(gym, "gym placed: unsuccessful");
        assertFalse(pub, "pub placed: unsuccessful");
        assertFalse(lectureHall, "lecture hall placed: unsuccessful");
        assertEquals(0, broker.getBuildingCount(Available.HALLS), "halls building count no change");
        assertEquals(0, broker.getBuildingCount(Available.GYM), "gym building count no change");
        assertEquals(0, broker.getBuildingCount(Available.PUB), "pub building count no change");
        assertEquals(0, broker.getBuildingCount(Available.LECTURE_HALL), "lecture hall building count no change");

    }

    @Test
    public void placeBuildingOverlapping() {
        Building accomBuilding = buildingFactory.newBuilding(Available.HALLS, new Vector2(0, 0));
        Building gymBuilding = buildingFactory.newBuilding(Available.GYM, new Vector2(0, 0));
        Building pubBuilding = buildingFactory.newBuilding(Available.PUB, new Vector2(0, 0));
        boolean halls = broker.placeBuilding(accomBuilding);
        boolean gym = broker.placeBuilding(gymBuilding);
        boolean pub = broker.placeBuilding(pubBuilding);

        assertTrue(halls, "halls placed: successful");
        assertFalse(gym, "gym placed: unsuccessful");
        assertFalse(pub, "pub placed: unsuccessful");
        assertEquals(1, broker.getBuildingCount(Available.HALLS), "halls building count incremented");
        assertEquals(0, broker.getBuildingCount(Available.GYM), "overlapping gym building count no change");
        assertEquals(0, broker.getBuildingCount(Available.PUB), "overlapping pub building count no change");

    }

    @Test
    public void destroyBuilding() {
        Vector2 position = new Vector2(1, 1);
        Building gymBuilding = buildingFactory.newBuilding(Available.GYM, new Vector2(0, 0));
        broker.placeBuilding(gymBuilding);
        Building destroyedBuilding = broker.destroyBuilding(position);

        assertNotNull(destroyedBuilding, "building destroyed + returned");
        assertEquals(gymBuilding, destroyedBuilding, "original building and destroyed building are the same");
        assertEquals(0, broker.getBuildingCount(Available.GYM), "building count decremented");
        assertNull(broker.getGrid().get(new Vector2(0, 0)), "grid cell empty after destroy");
    }

    @Test
    public void destroyBuildingNonExistent() {
        Vector2 position = new Vector2(2, 2);
        Building destroyedBuilding = broker.destroyBuilding(position);

        assertNull(destroyedBuilding, "destroy non-existent building: return null");
    }
}
