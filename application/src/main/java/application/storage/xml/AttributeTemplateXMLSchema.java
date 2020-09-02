package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTemplate;

public class AttributeTemplateXMLSchema implements XMLSchema<AttributeTemplate> {

	final public static String TAG_attributeTemplate = "attributeTemplate";

	final public static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_attributeTemplate;
	}

	public AttributeTemplateXMLSchema() {
		this(new ValueXMLSchema());
	}

	AttributeTemplateXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueXMLSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueXMLSchema;

	@Override
	public AttributeTemplate parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Object value = valueXMLSchema.parse(root.getChild(valueXMLSchema.getSchemaName()));
		if (value == null) {
			return new AttributeTemplate(name);
		}
		return new AttributeTemplate(name, value);
	}

	@Override
	public Element combine(AttributeTemplate attributeTemplate) {
		Element root = new Element(TAG_attributeTemplate);
		Element name = new Element(TAG_name);
		name.setText(attributeTemplate.getName());
		root.addContent(name);
		Object attributeValue = attributeTemplate.getValue();
		if (attributeValue != null) {
			Element value = valueXMLSchema.combine(attributeValue);
			root.addContent(value);
		}
		return root;
	}
}
