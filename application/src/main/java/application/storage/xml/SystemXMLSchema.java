package application.storage.xml;

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
		this.systemObjectXMLSchema = systemObjectXMLSchema;
		this.linkXMLSchema = linkXMLSchema;
	}

	private SystemObjectXMLSchema systemObjectXMLSchema = new SystemObjectXMLSchema();

	private LinkXMLSchema linkXMLSchema = new LinkXMLSchema();

	@Override
	public System parse(Element root) throws DataConversionException {
		final System system = new System();

		for (Element element : root.getChildren(systemObjectXMLSchema.getSchemaName())) {
			final SystemObject object = systemObjectXMLSchema.parse(element);
			system.addObject(object);
		}

		for (Element element : root.getChildren(linkXMLSchema.getSchemaName())) {
			final Link link = linkXMLSchema.parse(element);
			system.addLink(link);
		}

		return system;
	}

	@Override
	public Element combine(System system) {
		final Element root = new Element(TAG_system);

		for (SystemObject systemObject : system.getObjects()) {
			final Element element = systemObjectXMLSchema.combine(systemObject);
			root.addContent(element);
		}

		for (Link link : system.getLinks()) {
			final Element element = linkXMLSchema.combine(link);
			root.addContent(element);
		}

		return root;
	}
}
