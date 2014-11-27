package org.smartcampus.benchmark;

public class SimulationResult {

    private Simulation simulation;
    private int awaitedValues;
    private int gottenValues;

    public SimulationResult(Simulation simulation) {
        this.simulation = simulation;
        this.awaitedValues = simulation.getSentValues();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
