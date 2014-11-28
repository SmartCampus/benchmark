package org.smartcampus.benchmark;

import org.smartcampus.simulation.framework.fluentapi.StartImpl;
import org.smartcampus.simulation.framework.fluentapi.simulation.SimulationLawWrapper1;
import org.smartcampus.simulation.smartcampus.simulation.ParkingSimulation;
import org.smartcampus.simulation.stdlib.sensors.RandomSensorTransformation;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Benchmark {

    protected List<Simulation> simulations;

    public Benchmark() {
        simulations = new ArrayList<Simulation>();
    }

    public void addSimulation(int sensors, String name, long start, FiniteDuration duration, FiniteDuration frequency,
                              FiniteDuration objective) {
        simulations.add(new Simulation(sensors, name, start, duration, frequency, objective));
    }

    private void launchSimulation(Simulation s) {
        SimulationLawWrapper1 simulation;
        if (s instanceof EventSimulation) {
            simulation = new StartImpl()
                    .createSimulation(s.getName(), ParkingSimulation.class)
                    .withSensors(s.getSensors(), new RandomSensorTransformation());
        } else {
            simulation = new StartImpl()
                    .createSimulation(s.getName(), ParkingSimulation.class)
                    .withSensors(s.getSensors(), new RandomSensorTransformation());
        }
        simulation
                .withLaw(null)
                .setOutput("http://54.229.14.230:8080/collector/value")
                .startAt(s.getStart())
                .duration(s.getDuration())
                .frequency(s.getFrequency()).startRealTimeSimulationAt(s.getStart());
    }

    private void getResults(Simulation s) {

    }

    protected BenchmarkResults simulate() {
        for (Simulation s : simulations) {
            launchSimulation(s);
        }
        List<ResultsAnalyser> analysers = new LinkedList<ResultsAnalyser>();
        for (Simulation s : simulations) {
            ResultsAnalyser a = new ResultsAnalyser(s);
            analysers.add(a);
            a.start(); // start thread
        }
        BenchmarkResults res = new BenchmarkResults();
        for (ResultsAnalyser a : analysers) {
            try {
                a.join();
                System.out.println("THREAD for " + a.getSimulation().getName() + "finished");
                res.addSimulationResult(a.getResult());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ALL THREADS FINISHED");
        res.analyzeValues();
        return res;
    }

    public static void main(String[] args) {
        Benchmark t = new Benchmark();
        t.addSimulation(200, "S1", System.currentTimeMillis(), Duration.create(10, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS));
        BenchmarkResults res = t.simulate();
        ResultsPrinter.printResults(res);
    }
}
