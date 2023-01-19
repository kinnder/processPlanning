package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTemplate;

public class LinkTemplateXMLSchema implements XMLSchema<LinkTemplate> {

	final private static String TAG_name = "name";

	final private static String TAG_id1 = "id1";

	final private static String TAG_id2 = "id2";

	final private static String TAG_linkTemplate = "linkTemplate";

	@Override
	public String getSchemaName() {
		return TAG_linkTemplate;
	}

	@Override
	public LinkTemplate parse(Element root) throws DataConversionException {
		final String name = root.getChildText(TAG_name);
		final String id1 = root.getChildText(TAG_id1);
		final String id2 = root.getChildText(TAG_id2);
		return new LinkTemplate(name, id1, id2);
	}

	@Override
	public Element combine(LinkTemplate linkTemplate) {
		final Element root = new Element(TAG_linkTemplate);
		{
			final Element child = new Element(TAG_name);
			child.setText(linkTemplate.getName());
			root.addContent(child);
		}
		final String id1 = linkTemplate.getId1();
		if (id1 != null) {
			final Element child = new Element(TAG_id1);
			child.setText(id1);
			root.addContent(child);
		}
		final String id2 = linkTemplate.getId2();
		if (id2 != null) {
			final Element child = new Element(TAG_id2);
			child.setText(id2);
			root.addContent(child);
		}
		return root;
	}
}
