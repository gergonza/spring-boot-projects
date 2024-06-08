package org.example.route;

import static org.example.enumeration.TemperatureScale.STANDARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    @EndpointInject(value = "mock:direct:trigger")
    protected MockEndpoint resultEndpoint;

    @Produce(value = "direct:trigger")
    protected ProducerTemplate template;

    @Test
    void testSendMessage() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        template.sendBody(null);
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    void testInvalidTemperatureUnit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CamelRoute obj = new CamelRoute();
        Method privateMethod = CamelRoute.class.getDeclaredMethod("getTemperatureUnit", String.class);
        privateMethod.setAccessible(true);
        String returnValue = (String) privateMethod.invoke(obj, "TEST");
        assertEquals(returnValue, STANDARD.name());
    }
}
