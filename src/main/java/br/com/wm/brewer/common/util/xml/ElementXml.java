package br.com.wm.brewer.common.util.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementXml implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Element element;
	public ElementXml(Document document) {
		this.element = document.getDocumentElement();
	}
	public ElementXml(Element element) {
		this.element = element;
	}
	
	/*=============================================*/
	/*=============METODOS PARA ESCRITA============*/

	public ElementXml addNode(String tagName) {
		Element child = element.getOwnerDocument().createElement(tagName.trim());
		element.appendChild(child);
		return new ElementXml(child);
	}
	
	public ElementXml addNode(String tagName, Object value) {
		Element child = element.getOwnerDocument().createElement(tagName.trim());
		element.appendChild(child);
		ElementXml elementXml = new ElementXml(child);
		
		if(value != null)elementXml.setNodeValue(value.toString());
		return elementXml;
	}

	public ElementXml setNodeValue(String value) {
		NodeList childs = element.getChildNodes();
		if(childs != null && childs.getLength() > 0) {
			for(int i = 0;i<childs.getLength(); i++) {
				element.removeChild(childs.item(i));
			}
		}
		element.appendChild(element.getOwnerDocument().createTextNode(value));
		return this;
	}
	
	public ElementXml setAttribute(String name, String value) {
		element.setAttribute(name, value);
		return this;
	}
	public ElementXml removeAttribute(String attributeName) {
		element.removeAttribute(attributeName);
		return this;
	}
	
	/*=============================================*/
	/*=============METODOS PARA LEITURA============*/
	public ElementXml getChildNode(String name) {
		if(name.contains("."))throw new ElementXmlException("Não é permitido utilizar . no método getChildNode, utilize o findChildNode para localizar elementos");
		List<ElementXml> childNodes = getChildNodes(name);
		if(childNodes == null)return null;
		
		if(childNodes.size() > 1)throw new ElementXmlException("A busca no XML pelo elemento de nome '" + name + "' retornou mais de uma ocorrência, utilize a busca especifica para listas");
		return childNodes.get(0);
	}
	public List<ElementXml> getChildNodes(String name) {
		if(name.contains("."))throw new ElementXmlException("Não é permitido utilizar . no método getChildNodes, utilize o findChildNodes para localizar elementos");
		return getDirectChildNodesOnThisElement(name);
	}
	public String getChildValue(String name) {
		if(name.contains("."))throw new ElementXmlException("Não é permitido utilizar . no método getChildValue, utilize o findChildValue para localizar elementos");
		ElementXml element = getChildNode(name);
		if(element == null)return null;
		else return element.getValue();
	}
	public ElementXml findChildNode(String query) {
		List<ElementXml> nodes = findChildNodes(query);
		if(nodes == null)return null;
		
		if(nodes.size() > 1)throw new ElementXmlException("A busca no XML pela consulta '" + query + "' retornou mais de uma ocorrência, utilize a busca especifica para listas");
		return nodes.get(0);
	}
	public String findChildValue(String query) {
		ElementXml child = findChildNode(query);
		if(child == null)return null;
		return child.getValue();
	}
	public String findFirstChildValue(String ... queries) {
		for(String query : queries) {
			ElementXml child = findChildNode(query);
			if(child != null)return child.getValue();
		}
		return null;
	}

	public List<ElementXml> findChildNodes(String query) {
		String[] names = query.split("\\.");
		if(names.length == 1) {
			return findChildNodesOnThisElement(names[0]);
		}else {
			List<ElementXml> children = findChildNodesOnThisElement(names[0]);
			for(int i = 1; i < names.length; i++) {
				children = getChildNodesForAllElementsByName(children, names[i]);
			}
			return children.size() == 0 ? null : children;
		}
	}
	
	private List<ElementXml> getDirectChildNodesOnThisElement(String name){
		name = name.trim();
		final NodeList list = this.element.getChildNodes();

		return createListElementsByNodeList(name, list);
	}
	private List<ElementXml> findChildNodesOnThisElement(String name){
		final NodeList list = this.element.getElementsByTagName(name);
		
		return createListElementsByNodeList(name, list);
	}
	
	private List<ElementXml> getChildNodesForAllElementsByName(List<ElementXml> elements, String childName){
		List<ElementXml> retorno = new ArrayList<>();
		if(elements != null) {
			for(ElementXml xml : elements) {
				List<ElementXml> childNodes = xml.getChildNodes(childName);
				if(childNodes != null) {
					retorno.addAll(childNodes);
				}
			}
		}
		return retorno;
	}
	
	private List<ElementXml> createListElementsByNodeList(String name, final NodeList list) {
		List<ElementXml> elements = new ArrayList<ElementXml>();
		for(int i = 0; i < list.getLength(); i++) {
			if(list.item(i).getNodeType() != Node.ELEMENT_NODE)continue;
			if(list.item(i).getNodeName().equals(name))elements.add(new ElementXml((Element)list.item(i)));
		}
		
		return elements.size() == 0 ? null : elements;
	}
	
	public String getValue() {
		return this.element.getTextContent();
	}
	public String getAttValue(String name) {
		NamedNodeMap atts = this.element.getAttributes();
		if(atts == null || atts.getLength() == 0)return null;
		for(int i = 0; i < atts.getLength(); i++) {
			if(atts.item(i).getNodeName().equalsIgnoreCase(name))return atts.item(i).getNodeValue();
		}
		throw new AttributeInvalidException("O Atributo '" + name + "' não existe no elemento '" + element.getNodeName() + "'");
	}
	
	
	
	public Element getDomElement() {
		return this.element;
	}
	public String getNodeName() {
		return this.element.getNodeName();
	}
}
