package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateXMLSchema implements XMLSchema<SystemTemplate> {

	private SystemObjectTemplateXMLSchema systemObjectTemplateSchema = new SystemObjectTemplateXMLSchema();

	private LinkTemplateXMLSchema linkTemplateSchema = new LinkTemplateXMLSchema();

	// TODO : Добавить тэги

	@Override
	public SystemTemplate parse(Element root) throws DataConversionException {
		SystemTemplate systemTemplate = new SystemTemplate();

		for (Element element : root.getChildren("objectTemplate")) {
			SystemObjectTemplate systemObjectTemplate = systemObjectTemplateSchema.parse(element);
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		}

		for (Element element : root.getChildren("linkTemplate")) {
			LinkTemplate linkTemplate = linkTemplateSchema.parse(element);
			systemTemplate.addLinkTemplate(linkTemplate);
		}

		return systemTemplate;
	}

	@Override
	public Element combine(SystemTemplate systemTemplate) {
		Element root = new Element("systemTemplate");

		for (SystemObjectTemplate systemObjectTemplate : systemTemplate.getObjectTemplates()) {
			Element element = systemObjectTemplateSchema.combine(systemObjectTemplate);
			root.addContent(element);
		}

		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			Element element = linkTemplateSchema.combine(linkTemplate);
			root.addContent(element);
		}

		return root;
	}
}
