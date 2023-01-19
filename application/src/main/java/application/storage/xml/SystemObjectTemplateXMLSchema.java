package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateXMLSchema implements XMLSchema<SystemObjectTemplate> {

	final private static String TAG_objectTemplate = "objectTemplate";

	final private static String TAG_id = "id";

	@Override
	public String getSchemaName() {
		return TAG_objectTemplate;
	}

	public SystemObjectTemplateXMLSchema() {
		this(new AttributeTemplateXMLSchema());
	}

	SystemObjectTemplateXMLSchema(AttributeTemplateXMLSchema attributeTemplateXMLSchema) {
		this.attributeTemplateXMLSchema = attributeTemplateXMLSchema;
	}

	private AttributeTemplateXMLSchema attributeTemplateXMLSchema;

	@Override
	public SystemObjectTemplate parse(Element root) throws DataConversionException {
		final String id = root.getChildText(TAG_id);
		final SystemObjectTemplate objectTemplate = new SystemObjectTemplate(id);
		for (Element element : root.getChildren(attributeTemplateXMLSchema.getSchemaName())) {
			final AttributeTemplate attributeTemplate = attributeTemplateXMLSchema.parse(element);
			objectTemplate.addAttributeTemplate(attributeTemplate);
		}
		return objectTemplate;
	}

	@Override
	public Element combine(SystemObjectTemplate systemObjectTemplate) {
		final Element root = new Element(TAG_objectTemplate);
		final Element id = new Element(TAG_id);
		id.setText(systemObjectTemplate.getId());
		root.addContent(id);
		for (AttributeTemplate attributeTemplate : systemObjectTemplate.getAttributeTemplates()) {
			final Element element = attributeTemplateXMLSchema.combine(attributeTemplate);
			root.addContent(element);
		}
		return root;
	}
}
