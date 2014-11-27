package org.smartcampus.benchmark.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
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
    static String url = "http://54.229.14.230:8081/data-api/sensors/";

    static {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static SensorValues getSensorValues(String sensorId, long start, long end) throws BenchmarkException {
        HttpClient client = new DefaultHttpClient();
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        String dates = sdf.format(getDateFromTimestamp(start)) + "/" + sdf.format(getDateFromTimestamp
                (end));
        System.out.println("START "+sdf.format(getDateFromTimestamp(start)));
        System.out.println("END "+end);
        params.add(new BasicNameValuePair("date", dates));
        String p = URLEncodedUtils.format(params, "utf-8");
        HttpGet request = new HttpGet(url + sensorId+ "-0/data?"+p);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String text = "";
            String line;
            while ((line = rd.readLine()) != null) {
                text +=line;
            }
            System.out.println(text);
            return JsonTranslator.readResults(new JSONObject(text));
        } catch (IOException e) {
            //TODO create good exception
            throw new BenchmarkException();
        } catch (JSONException e) {
            //TODO create good exception
            throw new BenchmarkException();
        }
    }

    public static Date getDateFromTimestamp(long timestamp) {
        return new Date(timestamp*1000);
    }

}
