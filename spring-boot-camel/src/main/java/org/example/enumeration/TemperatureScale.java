package org.example.enumeration;

public enum TemperatureScale {
    IMPERIAL("Grades Fahrenheit"),
    METRIC("Grades Celsius"),
    STANDARD("Grades Kelvin");

    private final String value;

    TemperatureScale(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
