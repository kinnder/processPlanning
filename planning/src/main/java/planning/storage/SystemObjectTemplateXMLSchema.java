package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTemplate;
import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateXMLSchema implements XMLSchema<SystemObjectTemplate> {

	private LinkTemplateXMLSchema linkTemplateSchema = new LinkTemplateXMLSchema();

	private AttributeTemplateXMLSchema attributeTemplateSchema = new AttributeTemplateXMLSchema();

	@Override
	public SystemObjectTemplate parse(Element root) throws DataConversionException {
		String objectId = root.getChildText("objectId");
		SystemObjectTemplate objectTemplate = new SystemObjectTemplate(objectId);
		for (Element element : root.getChildren("attributeTemplate")) {
			AttributeTemplate attributeTemplate = attributeTemplateSchema.parse(element);
			objectTemplate.addAttributeTemplate(attributeTemplate);
		}
		for (Element element : root.getChildren("linkTemplate")) {
			LinkTemplate linkTemplate = linkTemplateSchema.parse(element);
			objectTemplate.addLinkTemplate(linkTemplate);
		}
		return objectTemplate;
	}

	@Override
	public Element combine(SystemObjectTemplate systemObjectTemplate) {
		Element root = new Element("objectTemplate");
		Element objectId = new Element("objectId");
		objectId.setText(systemObjectTemplate.getId());
		root.addContent(objectId);
		for (AttributeTemplate attributeTemplate : systemObjectTemplate.getAttributeTemplates()) {
			Element element = attributeTemplateSchema.combine(attributeTemplate);
			root.addContent(element);
		}
		for (LinkTemplate linkTemplate : systemObjectTemplate.getLinkTemplates()) {
			Element element = linkTemplateSchema.combine(linkTemplate);
			root.addContent(element);
		}
		return root;
	}
}
