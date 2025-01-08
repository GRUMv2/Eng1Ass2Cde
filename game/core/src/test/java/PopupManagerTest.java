import io.github.GRUMv2.EngSim.Server.PopupManager.PopupManager;
import io.github.GRUMv2.EngSim.Server.TimeKeeper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PopupManagerTest {
    private PopupManager popupManager;

    @BeforeEach
    public void setUp() {
        TimeKeeper timeKeeper = new TimeKeeper(1);
        popupManager = new PopupManager(timeKeeper);
    }

    @Test
    public void addPopup() {
        assertEquals(0, popupManager.getPopups().size());

        popupManager.addPopup("TestName", "Test");

        assertEquals(1, popupManager.getPopups().size());
        assertEquals("TestName", popupManager.getPopups().get(0).getName());
        assertEquals("Test", popupManager.getPopups().get(0).getDescription());
    }

    @Test
    public void popupsRemovedAuto() {
        assertEquals(0, popupManager.getPopups().size());

        popupManager.addPopup("TestName", "Test", 1);

        assertEquals(1, popupManager.getPopups().size());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        popupManager.serverTick(2);

        assertEquals(0, popupManager.getPopups().size());
    }

    @Test
    public void popupsRemovedManual() {
        assertEquals(0, popupManager.getPopups().size());

        popupManager.addPopup("TestName", "Test");

        assertEquals(1, popupManager.getPopups().size());

        popupManager.removePopup(popupManager.getPopups().get(0));

        assertEquals(0, popupManager.getPopups().size());
    }
}
