package org.example.route;

import static org.example.enumeration.TemperatureScale.STANDARD;
import static org.example.enumeration.TemperatureScale.valueOf;

import org.apache.camel.builder.RouteBuilder;
import org.example.processor.WeatherProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    // We can use spring dependency injection
    @Autowired
    private WeatherProcessor weatherProcessor;

    @Value("${timer.period}")
    private String period;

    @Value("${timer.delay}")
    private String delay;

    @Value("${weather.location}")
    private String location;

    @Value("${weather.unit}")
    private String unit;

    @Value("${weather.appId}")
    private String appId;

    @Value("${weather.destination.route}")
    private String route;

    @Value("${weather.destination.file}")
    private String file;

    @Value("${weather.date.format}")
    private String dateFormat;

    @Override
    public void configure() {
        // Make sure of manage a valid Temperature Scale
        String validUnit = getTemperatureUnit(unit);

        // start from a timer
        from("timer:fetch?period=" + period + "&delay=" + delay)
                .routeId("timer")
                // and print it to system out via stream component
                .to("direct:trigger");

        // start from a service
        from("direct:trigger")
                .routeId("trigger")
                // and print it to system out via stream component
                .to("weather:foo?location=" + location + "&units=" + validUnit + "&appid=" + appId)
                .setProperty("dateFormat", constant(dateFormat))
                .setProperty("temperatureScale", constant(getTemperatureScale(unit)))
                .process(weatherProcessor)
                .to("log:org.example?level=DEBUG")
                .to(route + "?fileName=" + file);
    }

    private String getTemperatureUnit(String unit) {
        try {
            return valueOf(unit.toUpperCase()).name();
        } catch (IllegalArgumentException | NullPointerException ex) {
            log.debug("The Unit of Temperature is invalid and not supported: {}. Standard value will be used", unit);
            return STANDARD.name();
        }
    }

    private String getTemperatureScale(String scale) {
        return valueOf(scale.toUpperCase()).getValue();
    }
}
