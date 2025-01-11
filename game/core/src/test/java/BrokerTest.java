import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.GRUMv2.EngSim.broker.Broker;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing for:
 *  - Broker exhibits correct singleton behaviour
 */

public class BrokerTest {
    private Broker broker;

    @BeforeEach
    public void setUp() {
        broker = Broker.getInstance();
    }

    @Test
    public void brokerIsValidSingleton() {
        Broker testBroker = Broker.getInstance();
        assertEquals(this.broker, testBroker);
    }
}
