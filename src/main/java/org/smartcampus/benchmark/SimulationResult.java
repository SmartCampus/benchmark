package org.smartcampus.benchmark;

public class SimulationResult {

    private Simulation simulation;
    private int awaitedValues;
    private int gotValues;
    private int totalSentValues;

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
