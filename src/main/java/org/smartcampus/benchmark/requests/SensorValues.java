package org.smartcampus.benchmark.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores values gotten from the data api for a given sensor
 */
public class SensorValues {

    private String name; //sensor name
    private Map<Long, String> values; //associates timestamp with the value

    public SensorValues(String name) {
        this.name = name;
        this.values = new HashMap<Long, String>();
    }

    public void addValue(long timeStamp, String value){
        values.put(timeStamp,value);
    }

    public String getName() {
        return name;
    }

    public Map<Long, String> getValues() {
        return values;
    }

    public int getNbValues(){
        return values.size();
    }
}
