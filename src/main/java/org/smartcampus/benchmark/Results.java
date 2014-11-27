package org.smartcampus.benchmark;

import scala.concurrent.duration.Duration;

import java.util.List;

public class Results {

    private int nbSensors;
    private int sentValues;
    private Duration totalDuration;
    private Duration meanSendFrequency;
    private List<SimulationResult> details;

}
