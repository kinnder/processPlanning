package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Link;

public class LinkXMLSchema implements XMLSchema<Link> {

	final private static String TAG_name = "name";

	final private static String TAG_id1 = "id1";

	final private static String TAG_id2 = "id2";

	final private static String TAG_link = "link";

	@Override
	public String getSchemaName() {
		return TAG_link;
	}

	@Override
	public Link parse(Element root) throws DataConversionException {
		final String name = root.getChildText(TAG_name);
		final String id1 = root.getChildText(TAG_id1);
		final String id2 = root.getChildText(TAG_id2);
		return new Link(name, id1, id2);
	}

	@Override
	public Element combine(Link link) {
		final Element root = new Element(TAG_link);
		{
			final Element child = new Element(TAG_name);
			child.setText(link.getName());
			root.addContent(child);
		}
		final String id1 = link.getId1();
		if (id1 != null) {
			final Element child = new Element(TAG_id1);
			child.setText(id1);
			root.addContent(child);
		}
		final String id2 = link.getId2();
		if (id2 != null) {
			final Element child = new Element(TAG_id2);
			child.setText(id2);
			root.addContent(child);
		}
		return root;
	}
}
