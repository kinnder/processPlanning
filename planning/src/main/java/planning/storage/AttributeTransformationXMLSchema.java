package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;

public class AttributeTransformationXMLSchema implements XMLSchema<AttributeTransformation> {

	final private static String TAG_attributeTransformation = "attributeTransformation";

	final private static String TAG_objectId = "objectId";

	final private static String TAG_name = "name";

	final private static String TAG_value = "value";

	@Override
	public String getSchemaName() {
		return TAG_attributeTransformation;
	}

	private ValueXMLSchema valueSchema = new ValueXMLSchema();

	@Override
	public AttributeTransformation parse(Element root) throws DataConversionException {
		String objectId = root.getChildText(TAG_objectId);
		String name = root.getChildText(TAG_name);
		Object value = valueSchema.parse(root.getChild(TAG_value));
		return new AttributeTransformation(objectId, name, value);
	}

	@Override
	public Element combine(AttributeTransformation transformation) {
		Element objectId = new Element(TAG_objectId);
		objectId.setText(transformation.getObjectId());
		Element name = new Element(TAG_name);
		name.setText(transformation.getAttributeName());
		Element value = valueSchema.combine(transformation.getAttributeValue());
		Element root = new Element(TAG_attributeTransformation);
		root.addContent(objectId);
		root.addContent(name);
		root.addContent(value);
		return root;
	}

}
