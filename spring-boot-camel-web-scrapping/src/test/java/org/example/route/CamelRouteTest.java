package org.example.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("file:output")
class CamelRouteTest {

    @EndpointInject(value = "mock:direct:scrap")
    protected MockEndpoint resultEndpoint;

    @Produce(value = "direct:scrap")
    protected ProducerTemplate template;

    @Test
    void testSendMessage() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        template.sendBody(null);
        resultEndpoint.assertIsSatisfied();
    }
}
