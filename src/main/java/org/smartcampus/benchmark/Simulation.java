package org.smartcampus.benchmark;

import scala.concurrent.duration.FiniteDuration;

public class Simulation {

    private int sensors;
    private String name;
    private long start;
    private long duration;
    private long frequency;
    private long objective;
    private boolean virtual;
    private boolean onEvent;

    public Simulation(int sensors, String name, long start, long duration, long frequency,
                      long objective, boolean virtual, boolean onEvent) {
        this.sensors = sensors;
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
        this.objective = objective;
        this.virtual = virtual;
        this.onEvent = onEvent;
    }

    public Simulation(int sensors, String name, long start, long duration, long frequency,
                      long objective) {
        this(sensors, name, start, duration, frequency, objective, false, false);
    }

    public Simulation(int sensors, String s1, long start, FiniteDuration duration, FiniteDuration frequency, FiniteDuration objective) {
        this(sensors, s1, start, duration.toMillis(), frequency.toMillis(), objective.toMillis());
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

    public long getDuration() {
        return duration;
    }

    public long getFrequency() {
        return frequency;
    }

    public long getObjective() {
        return objective;
    }

    public int getSentValues() {
        return (int) (duration / frequency) * sensors;
    }

    public long getEndTimestamp() {
        return start + duration;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isOnEvent() {
        return onEvent;
    }
}
