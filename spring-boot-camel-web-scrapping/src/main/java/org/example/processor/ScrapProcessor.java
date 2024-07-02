package org.example.processor;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static javax.xml.xpath.XPathConstants.NODESET;
import static org.example.utils.XPathReader.evaluateXPath;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class ScrapProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws IOException, SAXException, ParserConfigurationException {
        // Builder Document Factory
        DocumentBuilderFactory builderFactory = newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        // Exchange Properties
        String searchExpression = exchange.getProperty("searchExpression", String.class);
        String askedProperty = exchange.getProperty("askedProperty", String.class);

        // Get the Asked Node from the HTML Page
        String body = exchange.getIn().getBody(String.class);
        StringReader stringReader = new StringReader(body.replace("doctype", "DOCTYPE"));
        InputSource inputSource = new InputSource(stringReader);
        Document document = builder.parse(inputSource);
        NodeList nodeList= (NodeList) evaluateXPath(searchExpression, document, NODESET);

        List<String> uris = new ArrayList<>();

        if (nodeList != null) {

            for (int index = 0; index < nodeList.getLength(); index++) {
                Node aNode = nodeList.item(index);
                NodeList childNodes = aNode.getChildNodes();

                NamedNodeMap attributes = aNode.getAttributes();
                String link = attributes.getNamedItem(askedProperty).getNodeValue();

                if (childNodes.item(0) != null) {
                    uris.add(link);
                }
            }

        }

        exchange.getMessage().setBody("Links " + uris, String.class);
    }
}
