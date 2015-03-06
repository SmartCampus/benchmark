package org.smartcampus.benchmark;

import org.json.JSONException;
import org.smartcampus.benchmark.exceptions.BenchmarkException;
import org.smartcampus.benchmark.requests.HttpHelper;
import org.smartcampus.benchmark.requests.JsonTranslator;
import org.smartcampus.benchmark.requests.SensorValues;

/**
 * Thread used to query SmartCampus Data-API for data sent during a given simulation
 * The thread is launched when the request is sent to the simulator, and waits until the simulation is
 * finished to query the Data API. It then stores the results of the suery
 */
public class ResultsAnalyser extends Thread {

    private String middleware_ip;
    private Simulation simulation;
    private SimulationResult result;

    public ResultsAnalyser(Simulation s, String middleware_ip) {
        this.simulation = s;
        this.result = new SimulationResult(s);
        this.middleware_ip = middleware_ip;
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
                        simulation.getEndTimestamp(), middleware_ip);
                SensorValues v = JsonTranslator.readResults(response);
                if (v != null) {
                    received += v.getNbValues();
                    System.out.println("RECU " + v.getNbValues() + "/" + simulation.getSentValues() / simulation.getSensors() + " FOR SENSOR " + v.getName());
                } else {
                    System.out.println("Invalid Sensor " + sensor);
                }
            }
            long now = System.currentTimeMillis();
            System.out.println("GOT RESPONSES FOR " + simulation.getName() + " IN " + (now - then) + "ms");
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
