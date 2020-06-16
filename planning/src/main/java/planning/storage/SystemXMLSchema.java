package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class SystemXMLSchema implements XMLSchema<System> {

	final private static String TAG_schema = "system";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private SystemObjectXMLSchema systemObjectSchema = new SystemObjectXMLSchema();

	private LinkXMLSchema linkSchema = new LinkXMLSchema();

	@Override
	public System parse(Element root) throws DataConversionException {
		System system = new System();

		for (Element element : root.getChildren("systemObject")) {
			SystemObject object = systemObjectSchema.parse(element);
			system.addObject(object);
		}

		for (Element element : root.getChildren("link")) {
			Link link = linkSchema.parse(element);
			system.addLink(link);
		}

		return system;
	}

	@Override
	public Element combine(System system) {
		Element root = new Element("system");

		for (SystemObject systemObject : system.getObjects()) {
			Element element = systemObjectSchema.combine(systemObject);
			root.addContent(element);
		}

		for (Link link : system.getLinks()) {
			Element element = linkSchema.combine(link);
			root.addContent(element);
		}

		return root;
	}
}
