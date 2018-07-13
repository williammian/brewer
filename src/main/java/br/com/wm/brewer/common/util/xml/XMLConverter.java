package br.com.wm.brewer.common.util.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLConverter {
	private static DocumentBuilder builder;
	private static Transformer transformer;
	
	static {
		try {
			synchronized (XMLConverter.class) {
				if(builder == null) {
					DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);  
					factory.setValidating(false); 
					factory.setFeature("http://xml.org/sax/features/namespaces", false);
					factory.setFeature("http://xml.org/sax/features/validation", false);
					factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
					factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
					factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
					factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
					
					builder = factory.newDocumentBuilder();
					builder.setErrorHandler(new NoActionHandler());
					
					TransformerFactory transFactory = TransformerFactory.newInstance();
					transformer = transFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar Builder para parser de XML", e);
		}
	}
	public static ElementXml createElement(String name) {
		Document document = null;
		synchronized (builder) {
			document = builder.newDocument();	
		}
		Element root = document.createElement(name);
		return new ElementXml(root);
	}
	public static ElementXml createElement(String name, String namespace) {
		Document document = null;
		synchronized (builder) {
			document = builder.newDocument();	
		}
		Element root = document.createElementNS(namespace, name);
		return new ElementXml(root);
	}
	public static ElementXml string2Element(String xml) {
		synchronized (builder) {
			try {
				return new ElementXml(builder.parse(new InputSource(new StringReader(xml))));	
			} catch (IOException | SAXException e) {
				throw new RuntimeException("XML converter error", e);
			}
		}
	}
	public static String element2String(ElementXml element) {
		try {
			StringWriter buffer = new StringWriter();
			synchronized (transformer) {
				transformer.transform(new DOMSource(element.getDomElement()), new StreamResult(buffer));
			}
			
			return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException("XML converter error", e);
		}
	}
	public static String element2String(ElementXml element, Transformer transformer) {
		try {
			StringWriter buffer = new StringWriter();
			transformer.transform(new DOMSource(element.getDomElement()), new StreamResult(buffer));
			
			return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException("XML converter error", e);
		}
	}
}
