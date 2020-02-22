package domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations implements MaterialPoints {

	public static SystemTransformation moveRight() {
		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(object);
		template.addObjectTemplate(point_A);
		template.addObjectTemplate(point_B);

		object.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_POINT_A));

		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_RIGHT, ID_TEMPLATE_POINT_B));
		point_A.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_OBJECT));

		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_LEFT, ID_TEMPLATE_POINT_A));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_RIGHT);

		return new SystemTransformation(ELEMENT_MOVE_RIGHT, action, template, transformations);
	}

	public static SystemTransformation moveLeft() {
		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(object);
		template.addObjectTemplate(point_A);
		template.addObjectTemplate(point_B);

		object.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_POINT_B));

		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_A.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_RIGHT, ID_TEMPLATE_POINT_B));

		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_B.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_LEFT, ID_TEMPLATE_POINT_A));
		point_B.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_OBJECT));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_B, ID_TEMPLATE_POINT_A),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, true),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, false) };

		final Action action = new Action(OPERATION_MOVE_LEFT);

		return new SystemTransformation(ELEMENT_MOVE_LEFT, action, template, transformations);
	}

	public static SystemTransformation moveTop() {
		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(object);
		template.addObjectTemplate(point_A);
		template.addObjectTemplate(point_B);

		object.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_POINT_A));

		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_TOP, ID_TEMPLATE_POINT_B));
		point_A.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_OBJECT));

		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_BOTTOM, ID_TEMPLATE_POINT_A));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_TOP);

		return new SystemTransformation(ELEMENT_MOVE_TOP, action, template, transformations);
	}

	public static SystemTransformation moveBottom() {
		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(object);
		template.addObjectTemplate(point_A);
		template.addObjectTemplate(point_B);

		object.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_POINT_A));

		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_BOTTOM, ID_TEMPLATE_POINT_B));
		point_A.addLinkTemplate(new LinkTemplate(LINK_POSITION, ID_TEMPLATE_OBJECT));

		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLinkTemplate(new LinkTemplate(LINK_NEIGHBOR_TOP, ID_TEMPLATE_POINT_A));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_BOTTOM);

		return new SystemTransformation(ELEMENT_MOVE_BOTTOM, action, template, transformations);
	}

	public static void main(String args[]) {
		SystemTransformations materialPointsTransformations = new SystemTransformations();
		materialPointsTransformations.add(moveRight());
		materialPointsTransformations.add(moveLeft());
		materialPointsTransformations.add(moveTop());
		materialPointsTransformations.add(moveBottom());

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(materialPointsTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/materialPoints/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
