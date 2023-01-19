package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTransformation;

public class LinkTransformationXMLSchema implements XMLSchema<LinkTransformation> {

	final private static String TAG_linkTransformation = "linkTransformation";

	final private static String TAG_id = "id";

	final private static String TAG_name = "name";

	final private static String TAG_id2Old = "id2Old";

	final private static String TAG_id2New = "id2New";

	@Override
	public String getSchemaName() {
		return TAG_linkTransformation;
	}

	@Override
	public LinkTransformation parse(Element root) throws DataConversionException {
		final String id = root.getChildText(TAG_id);
		final String name = root.getChildText(TAG_name);
		final String id2Old = root.getChildText(TAG_id2Old);
		final String id2New = root.getChildText(TAG_id2New);
		return new LinkTransformation(id, name, id2Old, id2New);
	}

	@Override
	public Element combine(LinkTransformation transformation) {
		final Element root = new Element(TAG_linkTransformation);
		final Element id = new Element(TAG_id);
		id.setText(transformation.getId());
		root.addContent(id);
		final Element name = new Element(TAG_name);
		name.setText(transformation.getLinkName());
		root.addContent(name);
		String value;
		value = transformation.getId2Old();
		if (value != null) {
			final Element element = new Element(TAG_id2Old);
			element.setText(value);
			root.addContent(element);
		}
		value = transformation.getId2New();
		if (value != null) {
			final Element element = new Element(TAG_id2New);
			element.setText(value);
			root.addContent(element);
		}
		return root;
	}
}
