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
        long wait = simulation.getEndTimestamp() + simulation.getObjective() - System.currentTimeMillis();
        try {
            sleep(wait > 0 ? wait : 0);
            System.out.println("FINI DODO for simulation " + simulation.getName() + " (time " + System.currentTimeMillis() + ")");
            int j = 0;
            int received = 0;
            //query answers
            long then = System.currentTimeMillis();
            for (int i = 0; i < simulation.getSensors(); i += 25) {
                j++;
                String sensor = simulation.getName() + "_" + i;
                if (simulation.isVirtual()) sensor += "V";
                String response = HttpHelper.getSensorValues(sensor, simulation.getStart(),
                        simulation.getEndTimestamp());
                SensorValues v = JsonTranslator.readResults(response);
                if (v != null) {
                    received += v.getNbValues();
                    System.out.println("RECU " + v.getNbValues() + "/" + simulation.getSentValues() / simulation.getSensors() + " FOR SENSOR " + v.getName());
                } else {
                    System.out.println("Invalid Sensor " + sensor);
                }
            }
            long now = System.currentTimeMillis();
            System.out.println("GOT RESPONSES FOR IN " + (now - then) + " ms (" + simulation.getName() + ")");
            //answers got
            result.setAwaitedValues(j * (int) (simulation.getDuration() / simulation.getFrequency()));
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
