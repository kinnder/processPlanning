package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateXMLSchema implements XMLSchema<SystemTemplate> {

	private SystemObjectTemplateXMLSchema systemObjectTemplateSchema = new SystemObjectTemplateXMLSchema();

	@Override
	public SystemTemplate parse(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren("objectTemplate");
		SystemTemplate systemTemplate = new SystemTemplate();
		for (Element element : elements) {
			SystemObjectTemplate systemObjectTemplate = systemObjectTemplateSchema.parse(element);
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		}
		return systemTemplate;
	}

	@Override
	public Element combine(SystemTemplate systemTemplate) {
		Element root = new Element("systemTemplate");
		List<Element> elements = new ArrayList<>();
		for (SystemObjectTemplate systemObjectTemplate : systemTemplate.getObjectTemplates()) {
			Element element = systemObjectTemplateSchema.combine(systemObjectTemplate);
			elements.add(element);
		}
		root.addContent(elements);
		return root;
	}
}
