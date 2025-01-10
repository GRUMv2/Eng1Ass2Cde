import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.Building;
import io.github.GRUMv2.EngSim.entities.BuildingFactory;
import io.github.GRUMv2.EngSim.entities.BuildingFactory.Available;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.Vector2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrokerBuildingCountTest {
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
    public void testGetTotalBuildingWithNoBuilding(){
        int total = broker.getTotalBuildings();
        assertEquals(0, total);
    }

    @Test
    public void testGetTotalBuildingWithSingleBuildingType(){
        Building accomBuilding = buildingFactory.newBuilding(Available.ACCOMMODATION, new Vector2(0, 0));
        broker.placeBuilding(accomBuilding);

        int total = broker.getTotalBuildings();
        assertEquals(1, total, "Total Building should be 2 when only 2 building has been added.");
    }

    @Test
    public void testGetTotalBuildingWithDifferentType(){
        Building accomBuilding = buildingFactory.newBuilding(Available.ACCOMMODATION, new Vector2(0, 0));
        Building gymBuilding = buildingFactory.newBuilding(Available.GYM, new Vector2(5, 5));
   
        broker.placeBuilding(accomBuilding);
        broker.placeBuilding(gymBuilding);


        int total = broker.getTotalBuildings();
        assertEquals(2, total, "It does not match the total number of buildings");
    }

    @Test
    public void testGetBuildingCount_NoBuildings() {
    
        Building accomBuilding = buildingFactory.newBuilding(Available.ACCOMMODATION, new Vector2(0, 0));
        assertEquals(0, broker.getBuildingCount(accomBuilding));
    }
}
