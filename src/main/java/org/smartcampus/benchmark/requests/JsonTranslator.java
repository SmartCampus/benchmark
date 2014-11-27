package org.smartcampus.benchmark.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTranslator {

    public static SensorValues readResults(JSONObject json) throws JSONException{
        SensorValues s = new SensorValues(json.getString("id"));
        JSONArray array = json.getJSONArray("values");
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            s.addValue(Long.parseLong(o.getString("date")), o.getString("value"));
        }
        return s;
    }

}
