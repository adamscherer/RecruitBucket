package com.annconia.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * @author Adam Scherer
 *
 */
public class XmlUtils {

	protected static final Log logger = LogFactory.getLog(XmlUtils.class);

	public static String name(Node node) {

		if (StringUtils.isNotEmpty(node.getNodeName())) {
			return node.getNodeName();
		}

		if (StringUtils.isNotEmpty(node.getLocalName())) {
			return node.getLocalName();
		}

		return null;
	}

	public static Map<String, Object> fromXmlToMap(Node node) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		try {
			loadMap(node, map);
		} catch (Throwable ex) {
			logger.error("Error converting XML to Map: " + ex);
		}

		return map;
	}

	public static Document fromXmlStringToDocument(String xmlString) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setExpandEntityReferences(true);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		factory.setXIncludeAware(false);

		DocumentBuilder db = factory.newDocumentBuilder();
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));

		return db.parse(inStream);
	}

	public static Map<String, Object> fromXmlStringToMap(String xmlString) {
		try {
			Document doc = fromXmlStringToDocument(xmlString);
			return fromXmlToMap(doc);

		} catch (Throwable ex) {
			return new HashMap<String, Object>();
		}
	}

	protected static void loadMap(Node node, Map<String, Object> map) {

		if (!node.hasChildNodes()) {
			return;
		}

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if (!child.hasChildNodes()) {
				continue;
			}

			if (loadTextValue(child, map)) {
				continue;
			}

			String name = XmlUtils.name(child);
			if (StringUtils.isNotEmpty(name)) {
				Map<String, Object> subMap = new LinkedHashMap<String, Object>();
				loadMap(child, subMap);
				addToMap(name.toUpperCase(), subMap, map);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected static void addToMap(String name, Object value, Map<String, Object> map) {
		String normalizedKey = StringUtils.upperCase(name);
		if (map.containsKey(normalizedKey)) {
			Object currentValue = map.get(normalizedKey);
			if (currentValue instanceof List) {
				List<Object> values = (List<Object>) currentValue;
				values.add(value);
			} else {
				List<Object> values = new ArrayList<Object>();
				values.add(currentValue);
				values.add(value);
				map.put(normalizedKey, values);
			}
		} else {
			map.put(normalizedKey, value);
		}
	}

	protected static boolean loadTextValue(Node child, Map<String, Object> map) {
		NodeList childList = child.getChildNodes();
		if (childList.getLength() > 1)
			return false;

		Node textNode = childList.item(0);
		if (textNode.getNodeType() == Node.TEXT_NODE) {
			String name = XmlUtils.name(child);
			if (StringUtils.isNotEmpty(name)) {
				addToMap(name.toUpperCase(), textNode.getNodeValue(), map);
			}

			return true;
		}

		return false;
	}

}
