package application.storage.xml;

import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

public class TransformationsXMLSchema implements XMLSchema<Transformations> {

	final private static String TAG_transformations = "transformations";

	final private static String TAG_id = "id";

	final private static String TAG_transformation = "transformation";

	@Override
	public String getSchemaName() {
		return TAG_transformations;
	}

	public TransformationsXMLSchema() {
		this(new AttributeTransformationXMLSchema(), new LinkTransformationXMLSchema());
	}

	TransformationsXMLSchema(AttributeTransformationXMLSchema attributeTransformationXMLSchema, LinkTransformationXMLSchema linkTransformationXMLSchema) {
		this.attributeTransformationSchema = attributeTransformationXMLSchema;
		this.linkTransformationSchema = linkTransformationXMLSchema;
	}

	private AttributeTransformationXMLSchema attributeTransformationSchema;

	private LinkTransformationXMLSchema linkTransformationSchema;

	@Override
	public Transformations parse(Element root) throws DataConversionException {
		Transformations transformations = new Transformations();
		List<Element> elements = root.getChildren(linkTransformationSchema.getSchemaName());
		for (Element element : elements) {
			transformations.add(linkTransformationSchema.parse(element));
		}
		elements = root.getChildren(attributeTransformationSchema.getSchemaName());
		for (Element element : elements) {
			transformations.add(attributeTransformationSchema.parse(element));
		}
		return transformations;
	}

	@Override
	public Element combine(Transformations transformations) {
		Element root = new Element(TAG_transformations);
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
		Element id = new Element(TAG_id);
		id.setText(transformation.getId());
		Element root = new Element(TAG_transformation);
		root.addContent(id);
		return root;
	}
}
