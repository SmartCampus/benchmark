package org.smartcampus.benchmark.requests;

import org.junit.Test;

import static org.junit.Assert.*;

public class SensorValuesTest {

    @Test
    public void testAddValue() throws Exception {
        SensorValues v = new SensorValues("Sensor1");
        assertEquals(0,v.getNbValues());
        v.addValue(11000000,"true");
        v.addValue(22000000,"true");
        assertEquals(2,v.getNbValues());
        assertEquals("true",v.getValues().get(new Long(22000000)));
        v.addValue(22000000,"false");
        assertEquals(2,v.getNbValues());
        assertEquals("false",v.getValues().get(new Long(22000000)));
    }
}