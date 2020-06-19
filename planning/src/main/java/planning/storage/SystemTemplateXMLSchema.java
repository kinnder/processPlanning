package planning.storage;

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
		this.systemObjectTemplateSchema = systemObjectTemplateXMLSchema;
		this.linkTemplateSchema = linkTemplateXMLSchema;
	}

	private SystemObjectTemplateXMLSchema systemObjectTemplateSchema;

	private LinkTemplateXMLSchema linkTemplateSchema;

	@Override
	public SystemTemplate parse(Element root) throws DataConversionException {
		SystemTemplate systemTemplate = new SystemTemplate();

		for (Element element : root.getChildren(systemObjectTemplateSchema.getSchemaName())) {
			SystemObjectTemplate systemObjectTemplate = systemObjectTemplateSchema.parse(element);
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		}

		for (Element element : root.getChildren(linkTemplateSchema.getSchemaName())) {
			LinkTemplate linkTemplate = linkTemplateSchema.parse(element);
			systemTemplate.addLinkTemplate(linkTemplate);
		}

		return systemTemplate;
	}

	@Override
	public Element combine(SystemTemplate systemTemplate) {
		Element root = new Element(TAG_systemTemplate);

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
