package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.System;
import planning.model.SystemObject;

public class SystemXMLSchema implements XMLSchema<System> {

	private SystemObjectXMLSchema systemObjectSchema = new SystemObjectXMLSchema();

	@Override
	public System parse(Element root) throws DataConversionException {
		System system = new System();

		for (Element element : root.getChildren("systemObject")) {
			SystemObject object = systemObjectSchema.parse(element);
			system.addObject(object);
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

		return root;
	}
}
