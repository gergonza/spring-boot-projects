package org.example.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class WeatherProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String json = exchange.getIn().getBody(String.class);
        JSONObject obj = new JSONObject(json);
        JSONObject main = obj.getJSONObject("main");
        double dayTemp = main.getDouble("temp");

        long timestampLong = obj.getLong("dt");
        Date date = new Date(timestampLong * 1000);
        String dateString = new SimpleDateFormat(exchange.getProperty("dateFormat", String.class)).format(date);

        exchange.getMessage().setBody(dateString + ", " + dayTemp + " " + exchange.getProperty("temperatureScale", String.class), String.class);
    }
}
