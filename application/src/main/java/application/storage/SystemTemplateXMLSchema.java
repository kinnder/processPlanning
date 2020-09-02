package application.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateXMLSchema implements XMLSchema<SystemTemplate> {

	final private static String TAG_systemTemplate = "systemTemplate";

	@Override
	public String getSchemaName() {
		return TAG_systemTemplate;
	}

	public SystemTemplateXMLSchema() {
		this(new SystemObjectTemplateXMLSchema(), new LinkTemplateXMLSchema());
	}

	public SystemTemplateXMLSchema(SystemObjectTemplateXMLSchema systemObjectTemplateXMLSchema,
			LinkTemplateXMLSchema linkTemplateXMLSchema) {
		this.systemObjectTemplateXMLSchema = systemObjectTemplateXMLSchema;
		this.linkTemplateXMLSchema = linkTemplateXMLSchema;
	}

	private SystemObjectTemplateXMLSchema systemObjectTemplateXMLSchema;

	private LinkTemplateXMLSchema linkTemplateXMLSchema;

	@Override
	public SystemTemplate parse(Element root) throws DataConversionException {
		SystemTemplate systemTemplate = new SystemTemplate();

		for (Element element : root.getChildren(systemObjectTemplateXMLSchema.getSchemaName())) {
			SystemObjectTemplate systemObjectTemplate = systemObjectTemplateXMLSchema.parse(element);
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		}

		for (Element element : root.getChildren(linkTemplateXMLSchema.getSchemaName())) {
			LinkTemplate linkTemplate = linkTemplateXMLSchema.parse(element);
			systemTemplate.addLinkTemplate(linkTemplate);
		}

		return systemTemplate;
	}

	@Override
	public Element combine(SystemTemplate systemTemplate) {
		Element root = new Element(TAG_systemTemplate);

		for (SystemObjectTemplate systemObjectTemplate : systemTemplate.getObjectTemplates()) {
			Element element = systemObjectTemplateXMLSchema.combine(systemObjectTemplate);
			root.addContent(element);
		}

		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			Element element = linkTemplateXMLSchema.combine(linkTemplate);
			root.addContent(element);
		}

		return root;
	}
}
