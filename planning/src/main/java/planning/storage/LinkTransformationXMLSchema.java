package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTransformation;

public class LinkTransformationXMLSchema implements XMLSchema<LinkTransformation> {

	@Override
	public LinkTransformation parse(Element root) throws DataConversionException {
		String objectId = root.getChildText("objectId");
		String name = root.getChildText("name");
		String oldValue = root.getChildText("oldValue");
		String newValue = root.getChildText("newValue");
		return new LinkTransformation(objectId, name, oldValue, newValue);
	}

	@Override
	public Element combine(LinkTransformation transformation) {
		Element root = new Element("linkTransformation");
		Element objectId = new Element("objectId");
		objectId.setText(transformation.getObjectId());
		root.addContent(objectId);
		Element name = new Element("name");
		name.setText(transformation.getLinkName());
		root.addContent(name);
		String value;
		value = transformation.getLinkOldValue();
		if (value != null) {
			Element element = new Element("oldValue");
			element.setText(value);
			root.addContent(element);
		}
		value = transformation.getLinkNewValue();
		if (value != null) {
			Element element = new Element("newValue");
			element.setText(value);
			root.addContent(element);
		}
		return root;
	}
}
