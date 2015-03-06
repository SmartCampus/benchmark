package org.smartcampus.benchmark;

/**
 * Represents the result of a finished simulation
 */
public class SimulationResult {

    private Simulation simulation;
    private int awaitedValues; //number of values we tried to get
    private int gotValues; // number of value we effectively got
    private int totalSentValues; //total number of sent values over the course of the simulation

    public SimulationResult(Simulation simulation) {
        this.simulation = simulation;
        this.totalSentValues = simulation.getSentValues();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public int getGotValues() {
        return gotValues;
    }

    public void setGotValues(int gotValues) {
        this.gotValues = gotValues;
    }

    public void setAwaitedValues(int awaitedValues) {
        this.awaitedValues = awaitedValues;
    }

    public int getAwaitedValues() {
        return awaitedValues;
    }

    public int getTotalSentValues() {
        return totalSentValues;
    }

}
