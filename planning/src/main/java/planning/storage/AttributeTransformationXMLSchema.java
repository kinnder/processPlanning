package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;

public class AttributeTransformationXMLSchema implements XMLSchema<AttributeTransformation> {

	final private static String TAG_schema = "attributeTransformation";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private ValueXMLSchema valueSchema = new ValueXMLSchema();

	@Override
	public AttributeTransformation parse(Element root) throws DataConversionException {
		String objectId = root.getChildText("objectId");
		String name = root.getChildText("name");
		Object value = valueSchema.parse(root.getChild("value"));
		return new AttributeTransformation(objectId, name, value);
	}

	@Override
	public Element combine(AttributeTransformation transformation) {
		Element objectId = new Element("objectId");
		objectId.setText(transformation.getObjectId());
		Element name = new Element("name");
		name.setText(transformation.getAttributeName());
		Element value = valueSchema.combine(transformation.getAttributeValue());
		Element root = new Element("attributeTransformation");
		root.addContent(objectId);
		root.addContent(name);
		root.addContent(value);
		return root;
	}

}
