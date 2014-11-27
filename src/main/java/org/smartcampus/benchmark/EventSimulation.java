package org.smartcampus.benchmark;

import scala.concurrent.duration.FiniteDuration;

public class EventSimulation extends Simulation {

    public EventSimulation(int sensors, String name, long start, FiniteDuration duration, FiniteDuration frequency, FiniteDuration objective) {
        super(sensors, name, start, duration, frequency, objective);
    }

}
