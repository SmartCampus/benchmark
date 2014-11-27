package org.smartcampus.benchmark;

import org.smartcampus.simulation.framework.fluentapi.StartImpl;
import org.smartcampus.simulation.framework.fluentapi.simulation.SimulationLawWrapper1;
import org.smartcampus.simulation.smartcampus.simulation.ParkingSimulation;
import org.smartcampus.simulation.stdlib.sensors.RandomSensorTransformation;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test {

    protected List<Simulation> simulations;

    public Test() {
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

    protected Result simulate() {
        for (Simulation s : simulations) {
            launchSimulation(s);
        }
        List<ResultsAnalyser> analysers = new LinkedList<ResultsAnalyser>();
        for (Simulation s : simulations) {
            ResultsAnalyser a = new ResultsAnalyser(s);
            analysers.add(a);
            a.start(); // start thread
        }
        for (ResultsAnalyser a : analysers) {
            try {
                a.join();
                System.out.println("THREAD for " + a.getSimulation().getName() + "finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("DONE");
        return null;
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.addSimulation(1, "S1", System.currentTimeMillis(), Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), Duration.create(2, TimeUnit.SECONDS));
        t.simulate();
    }
}
