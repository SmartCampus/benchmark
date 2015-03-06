package org.smartcampus.benchmark.scenarios;

import org.smartcampus.benchmark.Benchmark;
import org.smartcampus.benchmark.BenchmarkResults;
import org.smartcampus.benchmark.Simulation;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Used to define and launch benchmarks
 */
public class ScenarioRunner {

    private Map<SCENARIOS, Benchmark> scenarios;

    public ScenarioRunner() {
        scenarios = new HashMap<SCENARIOS, Benchmark>();

        FiniteDuration duration = Duration.create(5, TimeUnit.MINUTES);
        List<Simulation> simulations = new LinkedList<Simulation>();
        simulations.add(new Simulation(176, "Parking1", 5000, duration.toMillis(), 60 * 1000, 1000, true, false));
        simulations.add(new Simulation(151, "Parking2", 3000, duration.toMillis(), 60 * 1000, 1000));
        simulations.add(new Simulation(127, "Batiment1", 0, duration.toMillis(), 2000, 1000, true, false));
        simulations.add(new Simulation(152, "Batiment2", 1000, duration.toMillis(), 2000, 1000));
        simulations.add(new Simulation(101, "Couloir", 2000, duration.toMillis(), 5000, 1000));
        defineScenario(simulations, SCENARIOS.SCENARIO1);

        duration = Duration.create(2, TimeUnit.MINUTES);
        simulations = new LinkedList<Simulation>();
        simulations.add(new Simulation(52, "Parking1", 5000, duration.toMillis(), 60 * 1000, 1000, true, false));
        simulations.add(new Simulation(52, "Parking2", 3000, duration.toMillis(), 60 * 1000, 1000));
        simulations.add(new Simulation(52, "Batiment1", 0, duration.toMillis(), 2000, 1000, true, false));
        simulations.add(new Simulation(52, "Batiment2", 1000, duration.toMillis(), 2000, 1000));
        simulations.add(new Simulation(52, "Couloir", 2000, duration.toMillis(), 5000, 1000));
        defineScenario(simulations, SCENARIOS.SCENARIO2);

        simulations = new LinkedList<Simulation>();
        duration = Duration.create(1, TimeUnit.SECONDS);
        simulations.add(new Simulation(1, "Parking1", 5000, duration.toMillis(), 60 * 1000, 0, true, false));
        simulations.add(new Simulation(1, "Parking2", 3000, duration.toMillis(), 60 * 1000, 0));
        simulations.add(new Simulation(2, "Batiment1", 0, duration.toMillis(), 2000, 0, true, false));
        simulations.add(new Simulation(1, "Batiment2", 1000, duration.toMillis(), 2000, 0));
        simulations.add(new Simulation(1, "Couloir", 2000, duration.toMillis(), 5000, 0));
        defineScenario(simulations, SCENARIOS.SCENARIO3);

        simulations = new LinkedList<Simulation>();
        duration = Duration.create(5, TimeUnit.MINUTES);
        simulations.add(new Simulation(201, "Parking1", 5000, duration.toMillis(), 10000, 4000, true, false));
        simulations.add(new Simulation(201, "Parking2", 3000, duration.toMillis(), 10000, 4000, true, false));
        simulations.add(new Simulation(202, "Batiment1", 0, duration.toMillis(), 2000, 4000, true, false));
        simulations.add(new Simulation(202, "Batiment2", 1000, duration.toMillis(), 2000, 4000, true, false));
        simulations.add(new Simulation(201, "Couloir", 2000, duration.toMillis(), 5000, 4000, true, false));
        defineScenario(simulations, SCENARIOS.SCENARIO4);
    }

    private void defineScenario(List<Simulation> simulations, SCENARIOS scenario) {
        Benchmark b = new Benchmark();
        for (Simulation s : simulations) {
            if (s != null) b.addSimulation(s);
        }
        scenarios.put(scenario, b);
    }

    public void runScenario(SCENARIOS scenario, String middleware_ip) {
        System.out.println("BENCHMARK FOR " + scenario.name() + " STARTING");
        Benchmark b = scenarios.get(scenario);
        BenchmarkResults result = b.simulate(middleware_ip);
        System.out.println("BEANCHMARK FOR " + scenario.name() + " FINISHED");
        System.out.println("Mean Frequency : " + result.getMeanSendFrequency());
        System.out.println("Received : " + result.getReceivedRate() * 100 + "%");
        System.out.println("===============================================");
    }

    public void runAll(String middleware_ip) {
        for (SCENARIOS s : scenarios.keySet()) {
            runScenario(s, middleware_ip);
        }
    }

    public static void main(String[] args) {
        String middleware_ip = "localhost";
        ScenarioRunner runner = new ScenarioRunner();
        runner.runScenario(SCENARIOS.SCENARIO1, middleware_ip);
    }

}
