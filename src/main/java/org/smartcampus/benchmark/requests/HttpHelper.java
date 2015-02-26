package org.smartcampus.benchmark.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.smartcampus.benchmark.Simulation;
import org.smartcampus.benchmark.exceptions.BenchmarkException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class HttpHelper {

    static SimpleDateFormat sdf;
    static String dataApiUrl = ":8081/data-api/sensors/";
    static String simulationServiceUrl = ":8080/smartcampus/simulations/simulation";
    static String simulationServiceEventUrl = ":8080/smartcampus/simulations/eventsimulation";

    static {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Get sensors results from SmartCampus DATA API
     */
    public static String getSensorValues(String sensorId, long start, long end, String ip) throws BenchmarkException {
        HttpClient client = new DefaultHttpClient();
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        String dates = sdf.format(getDateFromTimestamp(start)) + "/" + sdf.format(getDateFromTimestamp
                (end));
        params.add(new BasicNameValuePair("date", dates));
        String p = URLEncodedUtils.format(params, "utf-8");
        HttpGet request = new HttpGet("http://" + ip + dataApiUrl + sensorId + "/data?" + p);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String text = "";
            String line;
            while ((line = rd.readLine()) != null) {
                text += line;
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            //TODO create good exception
            throw new BenchmarkException();
        }
    }

    /**
     * Launch simulation at given ip
     */
    public static boolean launchSimulation(Simulation s, String ip, String middleware_ip) {
        long timeStamp = System.currentTimeMillis();
        s.setStart(timeStamp + 3000);
        System.out.println("LAUNCHING SIMULATION " + s.getName() + " AT SERVER " + ip + " (time " + timeStamp + ")");
        JSONObject json = new JSONObject();
        json.put("name", s.getName());
        json.put("duration", s.getDuration());
        json.put("frequency", s.getFrequency());
        json.put("start", s.getStart());
        json.put("sensors", s.getSensors());
        json.put("virtual", s.isVirtual());
        json.put("ip", middleware_ip);

        String url = s.isOnEvent() ? simulationServiceEventUrl : simulationServiceUrl;

        HttpClient client = new DefaultHttpClient();
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("simulationParams", json.toString()));
        String p = URLEncodedUtils.format(params, "utf-8");
        HttpPost request = new HttpPost("http://" + ip + url + "?" + p);
        try {
            client.execute(request);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Date getDateFromTimestamp(long timestamp) {
        return new Date(timestamp);
    }

}
