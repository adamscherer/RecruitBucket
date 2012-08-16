package com.annconia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlUtilsTest {

	@Test
	public void name() throws Exception {
		Node node = getXmlNode();

		assertNotNull(node);
		assertEquals(XmlUtils.name(node), "autnresponse");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void fromXmlToMap() throws Exception {
		Node node = getXmlNode();
		assertNotNull(node);

		Map<String, Object> map = XmlUtils.fromXmlToMap(node);
		assertNotNull(map);
		assertEquals(map.keySet().size(), 4);
		assertEquals(map.get("TEST") instanceof List, true);

		Map<String, Object> response = (Map<String, Object>)map.get("RESPONSEDATA");
		assertNotNull(response);
		List<Object> hits = (List<Object> )response.get("AUTN:HIT");
		assertEquals(hits.size(), 4);
	}
	
	protected Node getXmlNode() throws Exception {
		Resource resource = new ClassPathResource("landing-page.xml");
		String response = FileUtils.readFileToString(resource.getFile());
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(response));

		Document doc = db.parse(inStream);
		return doc.getElementsByTagName("autnresponse").item(0);
	}
	

	
}
