package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;

public class AttributeTransformationXMLSchema implements XMLSchema<AttributeTransformation> {

	final private static String TAG_attributeTransformation = "attributeTransformation";

	final private static String TAG_id = "id";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_attributeTransformation;
	}

	public AttributeTransformationXMLSchema() {
		this(new ValueXMLSchema());
	}

	AttributeTransformationXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueXMLSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueXMLSchema;

	@Override
	public AttributeTransformation parse(Element root) throws DataConversionException {
		final String id = root.getChildText(TAG_id);
		final String name = root.getChildText(TAG_name);
		final Object value = valueXMLSchema.parse(root.getChild(valueXMLSchema.getSchemaName()));
		return new AttributeTransformation(id, name, value);
	}

	@Override
	public Element combine(AttributeTransformation transformation) {
		final Element id = new Element(TAG_id);
		id.setText(transformation.getId());
		final Element name = new Element(TAG_name);
		name.setText(transformation.getAttributeName());
		final Element value = valueXMLSchema.combine(transformation.getAttributeValue());
		final Element root = new Element(TAG_attributeTransformation);
		root.addContent(id);
		root.addContent(name);
		root.addContent(value);
		return root;
	}

}
