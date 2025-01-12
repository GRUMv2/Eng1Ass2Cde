import io.github.GRUMv2.EngSim.Server.PopupManager.PopupManager;
import io.github.GRUMv2.EngSim.broker.Broker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PopupManagerTest {
    private PopupManager popupManager;
    private Broker broker;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        popupManager = new PopupManager();
        java.lang.reflect.Field instanceField = Broker.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        broker = Broker.getInstance();
    }

    @Test
    public void addPopup() {
        assertEquals(0, popupManager.getPopups().size());

        popupManager.addPopup("TestName", "Test");

        assertEquals(1, popupManager.getPopups().size());
        assertEquals("TestName", popupManager.getPopups().get(0).getName());
        assertEquals("Test", popupManager.getPopups().get(0).getDescription());
    }

    //@Test
    //public void popupsRemovedAuto() {
    //    assertEquals(0, popupManager.getPopups().size());
    //
    //    popupManager.addPopup("TestName", "Test");
    //    assertFalse(broker.getPendingPopups() == null);
    //
    //    assertEquals(1, popupManager.getPopups().size());
    //
    //    try {
    //        TimeUnit.SECONDS.sleep(2);
    //    } catch (InterruptedException e) {
    //        throw new RuntimeException(e);
    //    }
    //
    //    assertFalse(broker.getPendingPopups() == null);
    //    if (broker.getPendingPopups() != null) {
    //        broker.getPendingPopups().dismiss(0);
    //    }
    //    broker.resolvePopup();
    //    popupManager.serverTick(2);
    //
    //    assertEquals(0, popupManager.getPopups().size());
    //}
}
