package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class SystemXMLSchema implements XMLSchema<System> {

	final private static String TAG_system = "system";

	@Override
	public String getSchemaName() {
		return TAG_system;
	}

	public SystemXMLSchema() {
		this(new SystemObjectXMLSchema(), new LinkXMLSchema());
	}

	SystemXMLSchema(SystemObjectXMLSchema systemObjectXMLSchema, LinkXMLSchema linkXMLSchema) {
		this.systemObjectSchema = systemObjectXMLSchema;
		this.linkSchema = linkXMLSchema;
	}

	private SystemObjectXMLSchema systemObjectSchema = new SystemObjectXMLSchema();

	private LinkXMLSchema linkSchema = new LinkXMLSchema();

	@Override
	public System parse(Element root) throws DataConversionException {
		System system = new System();

		for (Element element : root.getChildren(systemObjectSchema.getSchemaName())) {
			SystemObject object = systemObjectSchema.parse(element);
			system.addObject(object);
		}

		for (Element element : root.getChildren(linkSchema.getSchemaName())) {
			Link link = linkSchema.parse(element);
			system.addLink(link);
		}

		return system;
	}

	@Override
	public Element combine(System system) {
		Element root = new Element(TAG_system);

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
