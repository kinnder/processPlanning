package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Link;

public class LinkXMLSchema implements XMLSchema<Link> {

	final private static String TAG_name = "name";

	final private static String TAG_objectId1 = "objectId1";

	final private static String TAG_objectId2 = "objectId2";

	final private static String TAG_schema = "link";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	@Override
	public Link parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		String objectId1 = root.getChildText(TAG_objectId1);
		String objectId2 = root.getChildText(TAG_objectId2);
		return new Link(name, objectId1, objectId2);
	}

	@Override
	public Element combine(Link link) {
		Element root = new Element(TAG_schema);
		{
			Element child = new Element(TAG_name);
			child.setText(link.getName());
			root.addContent(child);
		}
		String objectId1 = link.getObjectId1();
		if (objectId1 != null) {
			Element child = new Element(TAG_objectId1);
			child.setText(objectId1);
			root.addContent(child);
		}
		String objectId2 = link.getObjectId2();
		if (objectId2 != null) {
			Element child = new Element(TAG_objectId2);
			child.setText(objectId2);
			root.addContent(child);
		}
		return root;
	}
}
