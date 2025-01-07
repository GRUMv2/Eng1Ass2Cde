import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.GRUMv2.EngSim.broker.Broker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Testing for:
- BuildingFactory exhibits correct singleton behaviour
- Buildings are created by BuildingFactory
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
