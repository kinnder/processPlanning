package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Attribute;
import planning.model.SystemObject;

public class SystemObjectXMLSchema implements XMLSchema<SystemObject> {

	final private static String TAG_systemObject = "systemObject";

	final private static String TAG_name = "name";

	final private static String TAG_id = "id";

	@Override
	public String getSchemaName() {
		return TAG_systemObject;
	}

	public SystemObjectXMLSchema() {
		this(new AttributeXMLSchema());
	}

	SystemObjectXMLSchema(AttributeXMLSchema attributeXMLSchema) {
		this.attributeXMLSchema = attributeXMLSchema;
	}

	private AttributeXMLSchema attributeXMLSchema;

	@Override
	public SystemObject parse(Element root) throws DataConversionException {
		final String name = root.getChildText(TAG_name);
		final String id = root.getChildText(TAG_id);

		final SystemObject object = new SystemObject(name, id);

		for (Element element : root.getChildren(attributeXMLSchema.getSchemaName())) {
			final Attribute attribute = attributeXMLSchema.parse(element);
			object.addAttribute(attribute);
		}

		return object;
	}

	@Override
	public Element combine(SystemObject systemObject) {
		final Element root = new Element(TAG_systemObject);

		final Element name = new Element(TAG_name);
		name.setText(systemObject.getName());
		root.addContent(name);

		final Element id = new Element(TAG_id);
		id.setText(systemObject.getId());
		root.addContent(id);

		for (Attribute attribute : systemObject.getAttributes()) {
			final Element element = attributeXMLSchema.combine(attribute);
			root.addContent(element);
		}

		return root;
	}
}
