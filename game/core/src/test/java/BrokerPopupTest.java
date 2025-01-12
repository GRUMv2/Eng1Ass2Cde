import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.GRUMv2.EngSim.broker.Broker;
import io.github.GRUMv2.EngSim.broker.PopupTicket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing for:
 *  - Broker exhibits correct singleton behaviour
 */

public class BrokerPopupTest {
    private Broker broker;

    @BeforeEach
    public void setUp() throws IllegalAccessException, NoSuchFieldException {
        java.lang.reflect.Field instanceField = Broker.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        broker = Broker.getInstance();
    }

    @Test
    public void testTransientPendingPopups() {
        // Queue should start empty
        assertTrue(broker.getPendingPopups() == null);

        // Queue should increment on popup add
        PopupTicket p = new PopupTicket("");
        broker.queuePopup(p);
        assertFalse(broker.getPendingPopups() == null);

        // Queue should contain queued popup
        assertEquals(broker.getPendingPopups(), p);

        // Queue should be empty after transient is resolved
        broker.resolvePopup();
        assertTrue(broker.getPendingPopups() == null);

        // Queue should return first queued popup
        p = new PopupTicket("");
        PopupTicket p2 = new PopupTicket("");
        broker.queuePopup(p);
        broker.queuePopup(p2);
        assertEquals(broker.getPendingPopups(), p);

        // Queue should return second queued popup after first is resolved
        broker.resolvePopup();
        assertEquals(broker.getPendingPopups(), p2);
    }

    @Test
    public void testInteractivePendingPopups() {
        // Queue should start empty
        assertTrue(broker.getPendingPopups() == null);

        // Queue should increment on popup add
        PopupTicket p = new PopupTicket("", "", new String[]{""});
        broker.queuePopup(p);
        assertFalse(broker.getPendingPopups() == null);

        // Queue should contain queued popup
        assertEquals(broker.getPendingPopups(), p);

        // Queue should not be empty until interactive is dismissed
        broker.resolvePopup();
        assertFalse(broker.getPendingPopups() == null);

        // Queue should be empty after interactive is dismissed and resolved
        broker.getPendingPopups().dismiss(0);
        broker.resolvePopup();
        assertTrue(broker.getPendingPopups() == null);

        // Queue should return first queued popup
        p = new PopupTicket("", "", new String[]{});
        PopupTicket p2 = new PopupTicket("", "", new String[]{});
        broker.queuePopup(p);
        broker.queuePopup(p2);
        assertEquals(broker.getPendingPopups(), p);

        // Queue should return second queued popup after first is resolved
        broker.getPendingPopups().dismiss(0);
        broker.resolvePopup();
        assertEquals(broker.getPendingPopups(), p2);
    }

    @Test
    public void testResolvePopups() {
        // Queue should start empty
        assertTrue(broker.getPendingPopups() == null);

        // resolvePopup should return false if no popups to resolve
        assertFalse(broker.resolvePopup());

        // resolvePopup should return true if popup resolved
        PopupTicket p = new PopupTicket("");
        broker.queuePopup(p);
        assertTrue(broker.resolvePopup());

        // resolvePopup should return false if attempted to resolve undismissed popup
        p = new PopupTicket("", "", new String[]{""});
        broker.queuePopup(p);
        assertFalse(broker.resolvePopup());

        // resolvePopup should return true if to resolved dismissed popup
        broker.getPendingPopups().dismiss(0);
        assertTrue(broker.resolvePopup());
    }

    @Test
    public void testResolvedPopupsQueue() {
        // Queue should start empty
        assertTrue(broker.getResolvedPopups() == null);

        // Queue should not increment on unresolved popup add
        PopupTicket p = new PopupTicket("", "", new String[]{""});
        broker.queuePopup(p);
        assertTrue(broker.getResolvedPopups() == null);

        // Queue should increment on resolved popup
        broker.getPendingPopups().dismiss(0);
        broker.resolvePopup();
        assertFalse(broker.getResolvedPopups() == null);

        // Queue should empty after retrieval
        assertTrue(broker.getResolvedPopups() == null);

        // Queue should not increment on resolved transient
        p = new PopupTicket("");
        broker.queuePopup(p);
        broker.resolvePopup();
        assertTrue(broker.getResolvedPopups() == null);
    }

}
