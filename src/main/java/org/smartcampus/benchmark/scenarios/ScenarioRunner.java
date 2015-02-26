package org.smartcampus.benchmark.scenarios;

import org.smartcampus.benchmark.Benchmark;
import org.smartcampus.benchmark.BenchmarkResults;
import org.smartcampus.benchmark.ResultsPrinter;
import org.smartcampus.benchmark.Simulation;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ScenarioRunner {

    private static String middleware_ip = "52.16.33.142";
    private Map<SCENARIOS, Benchmark> scenarios;

    public ScenarioRunner() {
        scenarios = new HashMap<SCENARIOS, Benchmark>();
        FiniteDuration duration = Duration.create(5, TimeUnit.MINUTES);

        Benchmark b1 = new Benchmark();
        b1.addSimulation(new Simulation(176, "Parking1", 5000, duration.toMillis(),
                60 * 1000, 1000));
        b1.addSimulation(new Simulation(151, "Parking2", 3000, duration.toMillis(),
                60 * 1000, 1000));
        b1.addSimulation(new Simulation(126, "Batiment1", 0, duration.toMillis(),
                2000, 1000, true, false));
        b1.addSimulation(new Simulation(151, "Batiment2", 1000, duration.toMillis(),
                2000, 1000));
        b1.addSimulation(new Simulation(101, "Couloir", 2000, duration.toMillis(),
                5000, 1000));
        scenarios.put(SCENARIOS.SCENARIO1, b1);
        Benchmark b2 = new Benchmark();
        b2.addSimulation(new Simulation(50, "S2", 1000, 30000,
                1000, 2000, false, false));
        b2.addSimulation(new Simulation(50, "S2", 2000, 30000,
                1000, 2000, true, false));
        b2.addSimulation(new Simulation(50, "S2", 3000, 30000,
                1000, 1000, false, false));
        b2.addSimulation(new Simulation(50, "S2", 4000, 30000,
                1000, 1000, false, false));
        b2.addSimulation(new Simulation(50, "S2", 0, 30000,
                1000, 1000, false, false));
        scenarios.put(SCENARIOS.SCENARIO2, b2);
    }

    public void runScenario(SCENARIOS scenario, String file) {
        System.out.println("BENCHMARK FOR " + scenario.name() + " STARTING");
        Benchmark b = scenarios.get(scenario);
        BenchmarkResults result = b.simulate(middleware_ip);
        ResultsPrinter.printResults(result, file);
        System.out.println("BEANCHMARK FOR " + scenario.name() + " FINISHED");
        System.out.println("Mean Frequency : " + result.getMeanSendFrequency());
        System.out.println("Received : " + result.getReceivedRate() * 100 + "%");
        System.out.println("===============================================");
    }

    public void runAll() {
        for (SCENARIOS s : scenarios.keySet()) {
            runScenario(s, s.name() + ".txt");
        }
    }

    public static void main(String[] args) {
        ScenarioRunner runner = new ScenarioRunner();
        runner.runScenario(SCENARIOS.SCENARIO1, "lol.txt");
    }

}
