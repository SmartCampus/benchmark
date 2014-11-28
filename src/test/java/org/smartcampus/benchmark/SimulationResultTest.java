package org.smartcampus.benchmark;

import org.junit.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SimulationResultTest {

    private long now;
    private SimulationResult sim1;
    private SimulationResult sim2;
    private SimulationResult sim3;

    @Before
    public void setUp() throws Exception {
        now = System.currentTimeMillis();
        Simulation simulation1 = new Simulation(1, "S1", now + 1000, Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        Simulation simulation2 = new Simulation(10, "S2", now, Duration.create(15, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        Simulation simulation3 = new Simulation(33, "S2", now + 3000, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(6, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        sim1 = new SimulationResult(simulation1);
        sim2 = new SimulationResult(simulation2);
        sim3 = new SimulationResult(simulation3);
    }

    @org.junit.Test
    public void testSimulationResult() throws Exception {
        assertEquals(1,sim1.getTotalSentValues());
        assertEquals(150,sim2.getTotalSentValues());
        assertEquals(33,sim3.getTotalSentValues());
    }
}