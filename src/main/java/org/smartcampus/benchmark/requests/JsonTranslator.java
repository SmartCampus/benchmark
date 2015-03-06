package org.smartcampus.benchmark.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Tool class used to translate json data received from the Data-API
 */
public class JsonTranslator {

    public static SensorValues readResults(String string) throws JSONException {
        if (string.startsWith("Error")) {
            return null;
        }
        JSONObject json = new JSONObject(string);
        SensorValues s = new SensorValues(json.getString("id"));
        JSONArray array = json.getJSONArray("values");
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            s.addValue(Long.parseLong(o.getString("date")), o.getString("value"));
        }
        return s;
    }

}
