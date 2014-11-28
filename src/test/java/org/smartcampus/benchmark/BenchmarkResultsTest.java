package org.smartcampus.benchmark;

import org.junit.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BenchmarkResultsTest {

    private long now;
    private SimulationResult sim1;
    private SimulationResult sim2;
    private SimulationResult sim3;
    private BenchmarkResults res1;
    private BenchmarkResults res2;

    @Before
    public void setUp() throws Exception {
        now = System.currentTimeMillis();
        Simulation simulation1 = new Simulation(1, "S1", now + 1000, Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        Simulation simulation2 = new Simulation(100, "S2", now + 1000, Duration.create(15, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        Simulation simulation3 = new Simulation(230, "S3", now, Duration.create(10, TimeUnit.SECONDS),
                Duration.create(6, TimeUnit.SECONDS), Duration.create(5, TimeUnit.SECONDS));
        sim1 = new SimulationResult(simulation1);
        sim1.setAwaitedValues(1);
        sim1.setGotValues(1);
        sim2 = new SimulationResult(simulation2);
        sim2.setAwaitedValues(2*15);
        sim2.setGotValues(1*15);
        sim3 = new SimulationResult(simulation3);
        sim3.setAwaitedValues(4);
        sim3.setGotValues(3);

        res1 = new BenchmarkResults();
        res1.addSimulationResult(sim1);
        res2 = new BenchmarkResults();
        res2.addSimulationResult(sim1);
        res2.addSimulationResult(sim2);
        res2.addSimulationResult(sim3);
    }

    @org.junit.Test
    public void testAddSimulation() throws Exception {
        assertEquals(1, res1.getDetails().size());
        assertEquals(3, res2.getDetails().size());
        BenchmarkResults r = new BenchmarkResults();
        assertTrue(r.getDetails().isEmpty());
        r.addSimulationResult(sim1);
        r.addSimulationResult(sim2);
        assertEquals(2, r.getDetails().size());
    }

    @org.junit.Test
    public void testAnalyzeValues() throws Exception {
        // bench 1
        res1.analyzeValues();
        assertEquals(1,res1.getSentValues());
        assertEquals(1,res1.getNbSensors());
        assertEquals(Duration.create(1,TimeUnit.SECONDS),res1.getTotalDuration());
        assertEquals(Duration.create(1,TimeUnit.SECONDS),res1.getMeanSendFrequency());
        assertEquals(1,res1.getReceivedRate(),0.0001);
        // bench 2
        res2.analyzeValues();
        assertEquals(1+1500+230,res2.getSentValues());
        assertEquals(331,res2.getNbSensors());
        assertEquals(Duration.create(16,TimeUnit.SECONDS),res2.getTotalDuration());
        assertEquals(Duration.create(3,TimeUnit.SECONDS).toMillis(),res2.getMeanSendFrequency().toMillis(),100);
        assertEquals(0.53,res2.getReceivedRate(),0.01);
    }
}