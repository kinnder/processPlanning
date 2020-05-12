package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTemplate;

public class AttributeTemplateXMLSchema implements XMLSchema<AttributeTemplate> {

	private ValueXMLSchema valueSchema = new ValueXMLSchema();

	@Override
	public AttributeTemplate parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Object value = valueSchema.parse(root.getChild("value"));
		if (value == null) {
			return new AttributeTemplate(name);
		}
		return new AttributeTemplate(name, value);
	}

	@Override
	public Element combine(AttributeTemplate attributeTemplate) {
		Element root = new Element("attributeTemplate");
		Element name = new Element("name");
		name.setText(attributeTemplate.getName());
		root.addContent(name);
		Object attributeValue = attributeTemplate.getValue();
		if (attributeValue != null) {
			Element value = valueSchema.combine(attributeValue);
			root.addContent(value);
		}
		return root;
	}
}
