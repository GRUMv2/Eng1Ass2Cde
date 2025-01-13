import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.entities.ForegroundEntity;
import io.github.GRUMv2.EngSim.entities.Water;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Testing for:
 *  - Correctly identifying coordinates out of bounds (cellInBounds)
 *  - Correctly reporting eligibility of objects placed at a given position (checkCoordsFree)
 */

public class BrokerCoordinateTest {
    private Broker broker;

    @BeforeEach
    public void setUp() {
        broker = Broker.getInstance();
    }

    @Test
    public void testCellBounds() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method cellInBounds = Broker.class.getDeclaredMethod("cellInBounds", Vector2.class);
        cellInBounds.setAccessible(true);
        Vector2 oobSmall = new Vector2(-1, 0);
        Vector2 oobSmall2 = new Vector2(0, -1);
        Vector2 oobBig = new Vector2(broker.getMapCells() + 1, 0);
        Vector2 oobBig2 = new Vector2(0, broker.getMapCells() + 1);
        Vector2 notOob = new Vector2(1,1);
        // All should fail as OOB
        assertFalse((Boolean) cellInBounds.invoke(broker, oobSmall));
        assertFalse((Boolean) cellInBounds.invoke(broker, oobSmall2));
        assertFalse((Boolean) cellInBounds.invoke(broker, oobBig));
        assertFalse((Boolean) cellInBounds.invoke(broker, oobBig2));

        // Should succeed as in bounds
        assertTrue((Boolean) cellInBounds.invoke(broker, notOob));
    }

    @Test
    public void checkCheckCoordsFree() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // checkCoordsFree(Vector2[] interlinked)
        Method checkCoordsFree = Broker.class.getDeclaredMethod("checkCoordsFree", Vector2[].class);
        checkCoordsFree.setAccessible(true);

        Vector2[] baseCheck = new Vector2[]{ new Vector2(0,0) };

        // Should succeed as grid is empty
        assertTrue((Boolean) checkCoordsFree.invoke(broker, (Object) /* no idea */ baseCheck));

        Water testEntity = new Water(new Vector2(0, 0), new Vector2[]{ new Vector2(0,0), new Vector2(1,0), new Vector2(0, 1), new Vector2(1, 1) });
        broker.placeObstacle(testEntity);

        // Should now fail, as testEntity is at this position
        assertFalse((Boolean) checkCoordsFree.invoke(broker, (Object) /* no idea */ baseCheck));

        // checkCoordsFree(Vector2[] interlinked, ForegroundEntity self)
        Method checkCoordsFreeEntity = Broker.class.getDeclaredMethod("checkCoordsFree", Vector2[].class, ForegroundEntity.class);
        checkCoordsFreeEntity.setAccessible(true);

        // Should fail as testEntity is at this position
        assertFalse((Boolean) checkCoordsFreeEntity.invoke(broker, baseCheck, new Water(new Vector2(10, 10), new Vector2[]{ new Vector2(0,0) } )));

        // Should succeed as object to be checked is testEntity,
        // which is the object at that map position
        assertTrue((Boolean) checkCoordsFreeEntity.invoke(broker, baseCheck, testEntity));

    }

}
