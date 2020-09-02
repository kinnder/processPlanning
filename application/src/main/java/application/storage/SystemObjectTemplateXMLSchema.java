package application.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateXMLSchema implements XMLSchema<SystemObjectTemplate> {

	final private static String TAG_objectTemplate = "objectTemplate";

	final private static String TAG_objectId = "objectId";

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
		String objectId = root.getChildText(TAG_objectId);
		SystemObjectTemplate objectTemplate = new SystemObjectTemplate(objectId);
		for (Element element : root.getChildren(attributeTemplateXMLSchema.getSchemaName())) {
			AttributeTemplate attributeTemplate = attributeTemplateXMLSchema.parse(element);
			objectTemplate.addAttributeTemplate(attributeTemplate);
		}
		return objectTemplate;
	}

	@Override
	public Element combine(SystemObjectTemplate systemObjectTemplate) {
		Element root = new Element(TAG_objectTemplate);
		Element objectId = new Element(TAG_objectId);
		objectId.setText(systemObjectTemplate.getId());
		root.addContent(objectId);
		for (AttributeTemplate attributeTemplate : systemObjectTemplate.getAttributeTemplates()) {
			Element element = attributeTemplateXMLSchema.combine(attributeTemplate);
			root.addContent(element);
		}
		return root;
	}
}
