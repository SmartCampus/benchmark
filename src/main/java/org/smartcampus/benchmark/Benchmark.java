package org.smartcampus.benchmark;

import org.smartcampus.benchmark.requests.HttpHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Benchmark {

    private static String[] simulationServicesIps = {"52.16.84.68", "52.16.14.213", "52.16.91.166", "52.16.35.130", "52.16.85.114"};
    protected List<Simulation> simulations;
    private int i = 0;

    public Benchmark() {
        simulations = new ArrayList<Simulation>();
    }

    public void addSimulation(Simulation simulation) {
        this.simulations.add(simulation);
    }

    public BenchmarkResults simulate(String middleware_ip) {
        List<ResultsAnalyser> analysers = new LinkedList<ResultsAnalyser>();
        for (Simulation s : simulations) {
            if (HttpHelper.launchSimulation(s, simulationServicesIps[(i++ % simulationServicesIps.length)], middleware_ip)) {
                ResultsAnalyser a = new ResultsAnalyser(s,middleware_ip);
                analysers.add(a);
                a.start(); //start thread
            } else {
                System.out.println("ERREUR LAUNCHING SIMULATION FOR " + s.getName() + (s.isVirtual() ? "(Virtual)" : ""));
            }
        }
        BenchmarkResults res = new BenchmarkResults();
        for (ResultsAnalyser a : analysers) {
            try {
                a.join();
                System.out.println("THREAD for " + a.getSimulation().getName() + " finished");
                res.addSimulationResult(a.getResult());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ALL THREADS FINISHED");
        res.analyzeValues();
        return res;
    }

}
