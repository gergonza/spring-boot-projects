package org.example.processor;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.nio.file.Path;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

class ScrapProcessorTest extends CamelTestSupport {

    @EndpointInject(value = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(value = "direct:start")
    protected ProducerTemplate template;

    @Override
    public boolean isDumpRouteCoverage() {
        return true;
    }

    @Test
    void testSendMessage() throws Exception {
        // Get Json Request from Resources Folder
        Path resourceDir = of(EMPTY, "src/test/resources");
        Path file = resourceDir.resolve("response.txt");
        String body = readString(file);

        // Set Expected Response
        String expectedBody = "Links [https://www.iana.org/domains/example]";

        // Execute Test
        resultEndpoint.expectedBodiesReceived(expectedBody);
        template.send(exchange -> {
            exchange.setProperty("searchExpression", "/html/body/div/p/a");
            exchange.setProperty("askedProperty", "href");
            exchange.getIn().setBody(body);
        });
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").routeId("testScrap").process(new ScrapProcessor()).to("mock:result");
            }
        };
    }
}
