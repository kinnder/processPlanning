package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsXMLSchema implements XMLSchema<Transformation[]> {

	final private static String TAG_schema = "transformations";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private AttributeTransformationXMLSchema attributeTransformationSchema = new AttributeTransformationXMLSchema();

	private LinkTransformationXMLSchema linkTransformationSchema = new LinkTransformationXMLSchema();

	@Override
	public Transformation[] parse(Element root) throws DataConversionException {
		List<Transformation> transformations = new ArrayList<>();
		List<Element> elements = root.getChildren("linkTransformation");
		for (Element element : elements) {
			transformations.add(linkTransformationSchema.parse(element));
		}
		elements = root.getChildren("attributeTransformation");
		for (Element element : elements) {
			transformations.add(attributeTransformationSchema.parse(element));
		}
		return transformations.toArray(new Transformation[0]);
	}

	@Override
	public Element combine(Transformation[] transformations) {
		Element root = new Element("transformations");
		for (Transformation transformation : transformations) {
			Element element;
			if (transformation instanceof AttributeTransformation) {
				element = attributeTransformationSchema.combine((AttributeTransformation) transformation);
			} else if (transformation instanceof LinkTransformation) {
				element = linkTransformationSchema.combine((LinkTransformation) transformation);
			} else {
				element = combineTransformation(transformation);
			}
			root.addContent(element);
		}
		return root;
	}

	// TODO : remove this or update systemTransformations.xsd
	public Element combineTransformation(Transformation transformation) {
		Element objectId = new Element("objectId");
		objectId.setText(transformation.getObjectId());
		Element root = new Element("transformation");
		root.addContent(objectId);
		return root;
	}
}
