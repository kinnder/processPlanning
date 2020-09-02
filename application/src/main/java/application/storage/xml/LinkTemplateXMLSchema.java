package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LinkTemplate;

public class LinkTemplateXMLSchema implements XMLSchema<LinkTemplate> {

	final private static String TAG_name = "name";

	final private static String TAG_objectId1 = "objectId1";

	final private static String TAG_objectId2 = "objectId2";

	final private static String TAG_linkTemplate = "linkTemplate";

	@Override
	public String getSchemaName() {
		return TAG_linkTemplate;
	}

	@Override
	public LinkTemplate parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		String objectId1 = root.getChildText(TAG_objectId1);
		String objectId2 = root.getChildText(TAG_objectId2);
		return new LinkTemplate(name, objectId1, objectId2);
	}

	@Override
	public Element combine(LinkTemplate linkTemplate) {
		Element root = new Element(TAG_linkTemplate);
		{
			Element child = new Element(TAG_name);
			child.setText(linkTemplate.getName());
			root.addContent(child);
		}
		String objectId1 = linkTemplate.getObjectId1();
		if (objectId1 != null) {
			Element child = new Element(TAG_objectId1);
			child.setText(objectId1);
			root.addContent(child);
		}
		String objectId2 = linkTemplate.getObjectId2();
		if (objectId2 != null) {
			Element child = new Element(TAG_objectId2);
			child.setText(objectId2);
			root.addContent(child);
		}
		return root;
	}
}
