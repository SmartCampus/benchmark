package org.smartcampus.benchmark;

import scala.concurrent.duration.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Stores the results of a full benchmark
 */
public class BenchmarkResults {

    private int nbSensors = 0;
    private int sentValues = 0;
    private double receivedRate = 0;
    private Duration totalDuration = Duration.create(0, TimeUnit.SECONDS);
    private Duration meanSendFrequency = Duration.create(0, TimeUnit.SECONDS);
    private List<SimulationResult> details;

    public BenchmarkResults() {
        details = new LinkedList<SimulationResult>();
    }

    public int getNbSensors() {
        return nbSensors;
    }

    public int getSentValues() {
        return sentValues;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public Duration getMeanSendFrequency() {
        return meanSendFrequency;
    }

    public List<SimulationResult> getDetails() {
        return details;
    }

    public void addSimulationResult(SimulationResult res) {
        details.add(res);
    }

    public double getReceivedRate() {
        return receivedRate;
    }

    public void analyzeValues() {
        if (details.isEmpty()) return;
        long startTime = details.get(0).getSimulation().getStart();
        long endTime = details.get(0).getSimulation().getEndTimestamp();
        double received = 0;
        for (SimulationResult r : details) {
            Simulation s = r.getSimulation();
            nbSensors += s.getSensors();
            sentValues += r.getTotalSentValues();
            startTime = Math.min(startTime, s.getStart());
            endTime = Math.max(endTime, s.getEndTimestamp());
            received += (double) r.getGotValues() / r.getAwaitedValues() * r.getTotalSentValues();
        }
        totalDuration = Duration.create(endTime - startTime, TimeUnit.MILLISECONDS);
        if (totalDuration.equals(Duration.create(0, TimeUnit.MILLISECONDS)))
            totalDuration = Duration.create(1, TimeUnit.SECONDS);
        if (sentValues == 0) {
            meanSendFrequency = Duration.create(0, TimeUnit.MILLISECONDS);
            receivedRate = 0;
        } else {
            meanSendFrequency = Duration.create(nbSensors * totalDuration.toMillis() / sentValues, TimeUnit.MILLISECONDS);
            receivedRate = received / sentValues;
        }
    }

}
