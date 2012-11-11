package com.serlib.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.StrUtils;
import org.apache.solr.handler.XmlUpdateRequestHandler;

public class SolrTool {
	public static Collection<SolrInputDocument> readDocs(XMLStreamReader parser) throws XMLStreamException{
		 Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		 while (true) {
				int event = parser.next();
				switch (event) {
				case XMLStreamConstants.END_DOCUMENT:
					parser.close();
					return docs;
				case XMLStreamConstants.START_ELEMENT:
					String currTag = parser.getLocalName();
					if ("doc".equals(currTag)) {
						docs.add(readDoc(parser));
					}
					break;
				}
			}
	}
	
	private static SolrInputDocument readDoc(XMLStreamReader parser)
			throws XMLStreamException {
		SolrInputDocument doc = new SolrInputDocument();

		String attrName = "";
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			attrName = parser.getAttributeLocalName(i);
			if ("boost".equals(attrName)) {
				doc.setDocumentBoost(Float.parseFloat(parser
						.getAttributeValue(i)));
			} else {
				XmlUpdateRequestHandler.log.warn("Unknown attribute doc/@"
						+ attrName);
			}
		}

		StringBuilder text = new StringBuilder();
		String name = null;
		float boost = 1.0f;
		boolean isNull = false;
		while (true) {
			int event = parser.next();
			switch (event) {
			// Add everything to the text
			case XMLStreamConstants.SPACE:
			case XMLStreamConstants.CDATA:
			case XMLStreamConstants.CHARACTERS:
				text.append(parser.getText());
				break;

			case XMLStreamConstants.END_ELEMENT:
				if ("doc".equals(parser.getLocalName())) {
					return doc;
				} else if ("field".equals(parser.getLocalName())) {
					if (!isNull) {
						doc.addField(name, text.toString(), boost);
						boost = 1.0f;
					}
				}
				break;

			case XMLStreamConstants.START_ELEMENT:
				text.setLength(0);
				String localName = parser.getLocalName();
				if (!"field".equals(localName)) {
					XmlUpdateRequestHandler.log.warn("unexpected XML tag doc/"
							+ localName);
					throw new SolrException(
							SolrException.ErrorCode.BAD_REQUEST,
							"unexpected XML tag doc/" + localName);
				}
				boost = 1.0f;
				String attrVal = "";
				for (int i = 0; i < parser.getAttributeCount(); i++) {
					attrName = parser.getAttributeLocalName(i);
					attrVal = parser.getAttributeValue(i);
					if ("name".equals(attrName)) {
						name = attrVal;
					} else if ("boost".equals(attrName)) {
						boost = Float.parseFloat(attrVal);
					} else if ("null".equals(attrName)) {
						isNull = StrUtils.parseBoolean(attrVal);
					} else {
						XmlUpdateRequestHandler.log
								.warn("Unknown attribute doc/field/@"
										+ attrName);
					}
				}
				break;
			}
		}
	}
}
