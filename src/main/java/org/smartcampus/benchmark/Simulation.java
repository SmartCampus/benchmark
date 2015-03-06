package org.smartcampus.benchmark;

import scala.concurrent.duration.FiniteDuration;

/**
 * Used to store configuration of the simulations we want to launch
 * Available parameters :
 * - number of sensors
 * - name of the sensors group (sensors named NAME_0, NAME_1, etc. have to be defined in the smartcampus sensors database)
 * - the offset wished at the the start of the simulation
 * - the duration of the simulation
 * - the period at which data is sent
 * - the duration we want to wait after the simulation to query the database
 * - whether we will query for virtual of physical data (if virtual,  NAME_0V, NAME_1V, etc. have to be defined in the SmartCampus sensors database)
 * - whether the sensors send data each period or only when its value changes
 */
public class Simulation {

    private int sensors;
    private String name;
    private long start;
    private long startOffset;
    private long duration;
    private long frequency;
    private long objective;
    private boolean virtual;
    private boolean onEvent;

    public Simulation(int sensors, String name, long startOffset, long duration, long frequency,
                      long objective, boolean virtual, boolean onEvent) {
        this.sensors = sensors;
        this.name = name;
        this.startOffset = startOffset;
        this.duration = duration;
        this.frequency = frequency;
        this.objective = objective;
        this.virtual = virtual;
        this.onEvent = onEvent;
    }

    public Simulation(int sensors, String name, long startOffset, long duration, long frequency,
                      long objective) {
        this(sensors, name, startOffset, duration, frequency, objective, false, false);
    }

    public Simulation(int sensors, String s1, long startOffset, FiniteDuration duration, FiniteDuration frequency, FiniteDuration objective) {
        this(sensors, s1, startOffset, duration, frequency, objective, false, false);
    }

    public Simulation(int sensors, String s1, long startOffset, FiniteDuration duration, FiniteDuration frequency, FiniteDuration objective, boolean virtual, boolean onEvent) {
        this(sensors, s1, startOffset, duration.toMillis(), frequency.toMillis(), objective.toMillis(), virtual, onEvent);
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
        int res = (int) (duration / frequency) * sensors;
        return res > 0 ? res : 1;
    }

    public long getEndTimestamp() {
        if (frequency >= duration) return start;
        long leftTime = duration % frequency + 1000;
        return (start + duration - leftTime) > 0 ? start + duration - leftTime : start + duration;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isOnEvent() {
        return onEvent;
    }

    public void setStart(long start) {
        this.start = (startOffset > 0) ? start + startOffset : start;
    }
}
