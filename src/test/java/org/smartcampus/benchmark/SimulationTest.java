package org.smartcampus.benchmark;

import org.junit.Before;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SimulationTest {

    private long now;
    private Simulation simulation1;
    private Simulation simulation2;
    private Simulation simulation3;

    @Before
    public void setUp() throws Exception {
        now = System.currentTimeMillis();
        simulation1 = new Simulation(1, "S1", 1000, Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        simulation2 = new Simulation(10, "S2", 10000, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        simulation3 = new Simulation(10, "S3", 10000, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(6, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        simulation1.setStart(now);
        simulation2.setStart(now);
        simulation3.setStart(now);
    }

    @org.junit.Test
    public void testGetSentValues() throws Exception {
        assertEquals(1, simulation1.getSentValues());
        assertEquals(100, simulation2.getSentValues());
        assertEquals(10, simulation3.getSentValues());
    }

    @org.junit.Test
    public void testGetEndTimestamp() throws Exception {
        System.out.println(now);
        assertEquals(now + 1000, simulation1.getEndTimestamp());
        assertEquals(now + 19000, simulation2.getEndTimestamp());
        assertEquals(now + 15000, simulation3.getEndTimestamp());
    }
}