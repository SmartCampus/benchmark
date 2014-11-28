package org.smartcampus.benchmark.scenarios;

import org.smartcampus.benchmark.Benchmark;
import org.smartcampus.benchmark.BenchmarkResults;
import org.smartcampus.benchmark.ResultsPrinter;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ScenarioRunner {

    private Map<SCENARIOS,Benchmark> scenarios;

    public ScenarioRunner(){
        scenarios = new HashMap<SCENARIOS, Benchmark>();
        Benchmark b1 = new Benchmark();
        b1.addSimulation(1, "S1", System.currentTimeMillis(), Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS));
        scenarios.put(SCENARIOS.SCENARIO1,b1);
        Benchmark b2 = new Benchmark();
        b2.addSimulation(200, "S2", System.currentTimeMillis(), Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS));
        scenarios.put(SCENARIOS.SCENARIO2,b1);
    }

    public void runScenario(SCENARIOS scenario, String file){
        Benchmark b = scenarios.get(scenario);
        BenchmarkResults result = b.simulate();
        ResultsPrinter.printResults(result, file);
    }

    public void runAll(){
        for(SCENARIOS s : scenarios.keySet()){
            runScenario(s,s.name()+".txt");
        }
    }

    public static void main(String[] args) {
        ScenarioRunner runner = new ScenarioRunner();
        runner.runAll();
    }

}