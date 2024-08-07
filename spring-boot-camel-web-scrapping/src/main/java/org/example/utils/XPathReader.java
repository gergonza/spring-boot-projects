package org.example.utils;

import static javax.xml.xpath.XPathFactory.newInstance;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;

/**
 *
 * @author Santosh Joshi
 *
 * XPATH parser out of a html/xml document
 *
 */
public class XPathReader {

    public static XPath xPath;

    static {
        xPath = newInstance().newXPath();
    }

    public static Object evaluateXPath(String expression, Node xmlDocument, QName returnType) {
        try {
            XPathExpression xPathExpression = xPath.compile(expression);
            return xPathExpression.evaluate(xmlDocument, returnType);
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static XPath getxPath() {
        return xPath;
    }

    public static void setxPath(XPath xPath) {
        XPathReader.xPath = xPath;
    }
}
