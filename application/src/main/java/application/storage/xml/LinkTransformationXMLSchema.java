package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTransformation;

public class LinkTransformationXMLSchema implements XMLSchema<LinkTransformation> {

	final private static String TAG_linkTransformation = "linkTransformation";

	final private static String TAG_id = "id";

	final private static String TAG_name = "name";

	final private static String TAG_oldValue = "oldValue";

	final private static String TAG_newValue = "newValue";

	@Override
	public String getSchemaName() {
		return TAG_linkTransformation;
	}

	@Override
	public LinkTransformation parse(Element root) throws DataConversionException {
		String id = root.getChildText(TAG_id);
		String name = root.getChildText(TAG_name);
		String oldValue = root.getChildText(TAG_oldValue);
		String newValue = root.getChildText(TAG_newValue);
		return new LinkTransformation(id, name, oldValue, newValue);
	}

	@Override
	public Element combine(LinkTransformation transformation) {
		Element root = new Element(TAG_linkTransformation);
		Element id = new Element(TAG_id);
		id.setText(transformation.getId());
		root.addContent(id);
		Element name = new Element(TAG_name);
		name.setText(transformation.getLinkName());
		root.addContent(name);
		String value;
		value = transformation.getLinkObjectId2Old();
		if (value != null) {
			Element element = new Element(TAG_oldValue);
			element.setText(value);
			root.addContent(element);
		}
		value = transformation.getLinkObjectId2New();
		if (value != null) {
			Element element = new Element(TAG_newValue);
			element.setText(value);
			root.addContent(element);
		}
		return root;
	}
}
