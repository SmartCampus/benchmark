package org.smartcampus.benchmark;

import org.json.JSONException;
import org.smartcampus.benchmark.exceptions.BenchmarkException;
import org.smartcampus.benchmark.requests.HttpHelper;
import org.smartcampus.benchmark.requests.JsonTranslator;
import org.smartcampus.benchmark.requests.SensorValues;

public class ResultsAnalyser extends Thread {

    private Simulation simulation;
    private SimulationResult result;

    public ResultsAnalyser(Simulation s) {
        this.simulation = s;
        this.result = new SimulationResult(s);
    }

    @Override
    public void run() {
        System.out.println("THREAD for simulation " + simulation.getName() + " STARTED");
        try {
            sleep(simulation.getDuration().toMillis() + simulation.getObjective().toMillis());
            System.out.println("FINI DODO for simulation " + simulation.getName());
            int j = 0; int received=0;
            for (int i = 0; i < simulation.getSensors(); i += 50) {
                j++;
                String response = HttpHelper.getSensorValues(simulation.getName() + "-" + i, simulation.getStart(),
                        simulation.getEndTimestamp());
                SensorValues v = JsonTranslator.readResults(response);
                received+=v.getNbValues();
                System.out.println("RECU " + v.getNbValues() + " FOR SENSOR " + v.getName());
            }
            result.setAwaitedValues(j*simulation.getSensors());
            result.setGotValues(received);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BenchmarkException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public SimulationResult getResult() {
        return result;
    }

}
