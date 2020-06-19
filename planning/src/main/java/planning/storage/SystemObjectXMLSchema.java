package planning.storage;

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
		this.attributeSchema = attributeXMLSchema;
	}

	private AttributeXMLSchema attributeSchema;

	@Override
	public SystemObject parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		String id = root.getChildText(TAG_id);

		SystemObject object = new SystemObject(name, id);

		for (Element element : root.getChildren(attributeSchema.getSchemaName())) {
			Attribute attribute = attributeSchema.parse(element);
			object.addAttribute(attribute);
		}

		return object;
	}

	@Override
	public Element combine(SystemObject systemObject) {
		Element root = new Element(TAG_systemObject);

		Element name = new Element(TAG_name);
		name.setText(systemObject.getName());
		root.addContent(name);

		Element id = new Element(TAG_id);
		id.setText(systemObject.getId());
		root.addContent(id);

		for (Attribute attribute : systemObject.getAttributes()) {
			Element element = attributeSchema.combine(attribute);
			root.addContent(element);
		}

		return root;
	}
}
