package br.com.wm.brewer.common.util.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class NoActionHandler implements ErrorHandler {
	public void error(SAXParseException exception) {
	}
	public void fatalError(SAXParseException exception) {
	}
	public void warning(SAXParseException exception) {
	}

}
