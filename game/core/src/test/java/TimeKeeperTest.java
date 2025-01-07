import io.github.GRUMv2.EngSim.Server.TimeKeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TimeKeeperTest {
    private TimeKeeper timeKeeper;

    @BeforeEach
    public void setUp() {
        timeKeeper = new TimeKeeper(1);
    }

    @Test
    public void beforeStartZeroTime() {
        assertEquals(0, timeKeeper.currentGameTime());
        assertEquals("Jan 2024", timeKeeper.currentGameTimeFormatted());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, timeKeeper.currentGameTime());
        assertEquals("Jan 2024", timeKeeper.currentGameTimeFormatted());
    }

    @Test
    public void roughlyOneSecondPassed() {
        timeKeeper.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(() -> 950 < timeKeeper.currentGameTime() && timeKeeper.currentGameTime() < 1050);
    }

    @Test
    public void pausePausesAndUnpauses() {
        timeKeeper.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        timeKeeper.pause();

        long pausedTime = timeKeeper.currentGameTime();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(pausedTime, timeKeeper.currentGameTime());

        timeKeeper.unpause();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertNotEquals(pausedTime, timeKeeper.currentGameTime());
    }

    @Test
    public void timeFormatted() {
        assertEquals("Jan 2024", timeKeeper.timeFormatted(0));
        // at 1 year per minute 100_000 microseconds should be 1 year and 8 months
        assertEquals("Sep 2025", timeKeeper.timeFormatted(100_000));
        assertEquals("Mar 2028", timeKeeper.timeFormatted(250_000));
    }
}
