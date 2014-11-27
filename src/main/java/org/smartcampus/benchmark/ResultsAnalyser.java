package org.smartcampus.benchmark;

import org.smartcampus.benchmark.exceptions.BenchmarkException;
import org.smartcampus.benchmark.requests.HttpHelper;

public class ResultsAnalyser extends Thread {

    private Simulation simulation;
    private Results result;

    public ResultsAnalyser(Simulation s){
        this.simulation = s;
        this.result = new Results();
    }

    @Override
    public void run() {
        System.out.println("THREAD for simulation " + simulation.getName()+ " STARTED");
        try {
            sleep(simulation.getDuration().toMillis()+simulation.getObjective().toMillis());
            System.out.println("FINI DODO for simulation " + simulation.getName());
            HttpHelper.getSensorValues(simulation.getName(),simulation.getStart(),simulation.getEndTimestamp());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BenchmarkException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public Results getResult() {
        return result;
    }

}
