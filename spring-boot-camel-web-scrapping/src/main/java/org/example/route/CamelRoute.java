package org.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.example.processor.ScrapProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    // We can use spring dependency injection
    @Autowired
    private ScrapProcessor scrapProcessor;

    @Value("${web-scraping.count}")
    private String count;

    @Value("${web-scraping.web}")
    private String web;

    @Value("${web-scraping.search-expression}")
    private String searchExpression;

    @Value("${web-scraping.asked-property}")
    private String askedProperty;

    @Value("${web-scraping.destination.route}")
    private String route;

    @Value("${web-scraping.destination.file}")
    private String file;

    @Override
    public void configure() {
        // start from a timer
        from("timer:getHtml?repeatCount=" + count)
                .to("direct:scrap");

        // start from a service
        from("direct:scrap")
                .routeId("scrap")
                .to(web)
                .setProperty("searchExpression", constant(searchExpression))
                .setProperty("askedProperty", constant(askedProperty))
                .process(scrapProcessor)
                .to("log:org.example?level=DEBUG")
                .to(route + "?fileName=" + file);
    }
}
