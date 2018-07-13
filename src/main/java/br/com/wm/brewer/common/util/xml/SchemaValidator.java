package br.com.wm.brewer.common.util.xml;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaValidator implements ErrorHandler {
	private List<String> errors = new ArrayList<String>();

	public List<String> validate(String xml, String fileSchema)  {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", fileSchema);
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(this);
			
			builder.parse(new ByteArrayInputStream(normalizeXML(xml).getBytes()));
			return this.getListaComErrosDeValidacao();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao validar Schema do xml", e);
		}
	}

	private String normalizeXML(String xml) {
		xml = xml.replace("\r", "");
		xml = xml.replace("\n", "");
		xml = xml.replace(" standalone=\"no\"", "");
		return xml;
	}

	public void error(SAXParseException exception) throws SAXException {
		if (isError(exception)) {
			errors.add(tratamentoRetorno(exception.getMessage()));
		}
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		errors.add(tratamentoRetorno(exception.getMessage()));
	}

	public void warning(SAXParseException exception) throws SAXException {
		errors.add(tratamentoRetorno(exception.getMessage()));
	}

	/**
	 * Método para o tratamento do retorno do XML
	 * @param message
	 * @return
	 */
	private String tratamentoRetorno(String message) {
		return message.replace("cvc-type.3.1.3:", "")
					  .replace("cvc-complex-type.2.4.a:", "")
					  .replace("cvc-complex-type.2.4.b:", "")
					  .replace("\\{", "")
					  .replace("\\}", "")
					  .replace("\"", "")
					  .replace("http://www.portalfiscal.inf.br/nfe:", "")
					  .replace("'", "\"")
					  .replace("The value", "O valor")
					  .replace("The content", "O conteúdo")
					  .replace("is not complete", "não está completo")
					  .replace("of element", "do elemento")
					  .replace("is not valid", "não é valido")
					  .replace("Invalid content was found starting with element", "Conteúdo inválido foi encontrado começando com elemento")
					  .replace("One of", "O(s) campo(s)")
					  .replace("is expected", "era esperado");

	}

	public List<String> getListaComErrosDeValidacao() {
		return errors;
	}

	private boolean isError(SAXParseException exception) {
		if (exception.getMessage().startsWith("cvc-pattern-valid") || exception.getMessage().startsWith("cvc-maxLength-valid") || exception.getMessage().startsWith("cvc-datatype")) {
			return false;
		}
		return true;
	}
}
