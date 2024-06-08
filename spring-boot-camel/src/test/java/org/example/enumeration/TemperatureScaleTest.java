package org.example.enumeration;

import static org.example.enumeration.TemperatureScale.IMPERIAL;
import static org.example.enumeration.TemperatureScale.METRIC;
import static org.example.enumeration.TemperatureScale.STANDARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TemperatureScaleTest {

    @Test
    void testImperialUnit() {
        assertEquals("IMPERIAL", IMPERIAL.name());
    }

    @Test
    void testMetricUnit() {
        assertEquals("METRIC", METRIC.name());
    }

    @Test
    void testStandardUnit() {
        assertEquals("STANDARD", STANDARD.name());
    }

    @Test
    void testImperialScale() {
        assertEquals("Grades Fahrenheit", IMPERIAL.getValue());
    }

    @Test
    void testMetricScale() {
        assertEquals("Grades Celsius", METRIC.getValue());
    }

    @Test
    void testStandardScale() {
        assertEquals("Grades Kelvin", STANDARD.getValue());
    }
}
