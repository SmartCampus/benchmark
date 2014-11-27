package org.smartcampus.benchmark;

import scala.concurrent.duration.FiniteDuration;

public class Simulation {

    private int sensors;
    private String name;
    private long start;
    private FiniteDuration duration;
    private FiniteDuration frequency;
    private FiniteDuration objective;

    public Simulation(int sensors, String name, long start, FiniteDuration duration, FiniteDuration frequency,
                      FiniteDuration objective) {
        this.sensors = sensors;
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
        this.objective = objective;
    }

    public int getSensors() {
        return sensors;
    }

    public String getName() {
        return name;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public FiniteDuration getDuration() {
        return duration;
    }

    public FiniteDuration getFrequency() {
        return frequency;
    }

    public FiniteDuration getObjective() {
        return objective;
    }

    public int getSentValues() {
        return (int) (duration.div(frequency)) * sensors;
    }

    public long getEndTimestamp() {
        return start + duration.toMillis();
    }
}
