package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTemplate;

public class LinkTemplateXMLSchema implements XMLSchema<LinkTemplate> {

	@Override
	public LinkTemplate parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		String value = root.getChildText("value");
		return new LinkTemplate(name, value, null);
	}

	@Override
	public Element combine(LinkTemplate linkTemplate) {
		Element root = new Element("linkTemplate");
		Element name = new Element("name");
		name.setText(linkTemplate.getName());
		root.addContent(name);
		String linkValue = linkTemplate.getObjectId();
		if (linkValue != null) {
			Element value = new Element("value");
			value.setText(linkValue);
			root.addContent(value);
		}
		return root;
	}
}
