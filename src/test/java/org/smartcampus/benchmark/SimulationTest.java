package org.smartcampus.benchmark;

import org.junit.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SimulationTest {

    private long now;
    private Simulation simulation1;
    private Simulation simulation2;
    private Simulation simulation3;

    @Before
    public void setUp() throws Exception {
        now = System.currentTimeMillis();
        simulation1 = new Simulation(1, "S1", now, Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        simulation2 = new Simulation(10, "S2", now, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        simulation3 = new Simulation(10, "S2", now, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(6, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
    }

    @org.junit.Test
    public void testGetSentValues() throws Exception {
        assertEquals(1, simulation1.getSentValues());
        assertEquals(100, simulation2.getSentValues());
        assertEquals(10, simulation3.getSentValues());
    }

    @org.junit.Test
    public void testGetEndTimestamp() throws Exception {
        assertEquals(now + 1000, simulation1.getEndTimestamp());
        assertEquals(now + 10000, simulation2.getEndTimestamp());
        assertEquals(now + 10000, simulation3.getEndTimestamp());
    }
}