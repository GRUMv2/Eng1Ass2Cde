import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.GRUMv2.EngSim.broker.Broker;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.concurrent.CopyOnWriteArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class BrokerLeaderboardTest {
    private Broker broker;

    @BeforeEach
    public void setUp() throws Exception {
        java.lang.reflect.Field instanceField = Broker.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        broker = Broker.getInstance();
    }

    @Test
    public void updateLeaderboardValidScore() {
        assertTrue(broker.updateLeaderboard("Alice", 555), "add to leaderboard");
        assertEquals(1, broker.getLeaderboard().size(), "leaderboard size: 1");
        assertEquals("Alice", broker.getLeaderboard().get(0).getKey(), "alice on top");
        assertEquals(555, broker.getLeaderboard().get(0).getValue(), "top score: 555");
    }

    @Test
    public void updateLeaderboardInvalidScore() {
        assertFalse(broker.updateLeaderboard("Bob", -111), "negative scores invalid entries");
        assertEquals(0, broker.getLeaderboard().size(), "leaderboard size: 0 after invalid score.");
    }

    @Test
    public void leaderboardMaintainsOrder() {
        broker.updateLeaderboard("Alice", 1000);
        broker.updateLeaderboard("Bob", 20000);
        broker.updateLeaderboard("Chris", 15000);

        CopyOnWriteArrayList<SimpleImmutableEntry<String, Integer>> leaderboard = broker.getLeaderboard();
        assertEquals("Bob", leaderboard.get(0).getKey(), "bob on top");
        assertEquals(20000, leaderboard.get(0).getValue(), "top score: 20000");
        assertEquals("Chris", leaderboard.get(1).getKey(), "chris second");
        assertEquals(15000, leaderboard.get(1).getValue(), "second score: 15000");
        assertEquals("Alice", leaderboard.get(2).getKey(), "alice third");
        assertEquals(1000, leaderboard.get(2).getValue(), "third score: 1000");
    }

    @Test
    public void getTopScore() {
        broker.updateLeaderboard("Alice", 9999);
        broker.updateLeaderboard("Bob", 10000);
        broker.updateLeaderboard("Chris", 1555);

        assertEquals(10000, broker.getHighScore(), "top score: 10000");
    }
}
