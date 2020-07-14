package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Attribute;

public class AttributeXMLSchema implements XMLSchema<Attribute> {

	final private static String TAG_attribute = "attribute";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_attribute;
	}

	public AttributeXMLSchema() {
		this(new ValueXMLSchema());
	}

	AttributeXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueXMLSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueXMLSchema;

	@Override
	public Attribute parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Object value = valueXMLSchema.parse(root.getChild(valueXMLSchema.getSchemaName()));
		return new Attribute(name, value);
	}

	@Override
	public Element combine(Attribute attribute) {
		Element root = new Element(TAG_attribute);

		Element name = new Element(TAG_name);
		name.setText(attribute.getName());
		root.addContent(name);

		Object attributeValue = attribute.getValue();
		if (attributeValue != null) {
			Element value = valueXMLSchema.combine(attributeValue);
			root.addContent(value);
		}
		return root;
	}
}
