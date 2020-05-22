package domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations implements MaterialPoints {

	public static SystemTransformation moveRight() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		systemTemplate.addObjectTemplate(object);

		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		systemTemplate.addObjectTemplate(point_A);

		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);
		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		systemTemplate.addObjectTemplate(point_B);

		systemTemplate.addLinkTemplate(object, LINK_POSITION, point_A);

		systemTemplate.addLinkTemplate(point_A, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_B);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_RIGHT);

		return new SystemTransformation(ELEMENT_MOVE_RIGHT, action, systemTemplate, transformations);
	}

	public static SystemTransformation moveLeft() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		systemTemplate.addObjectTemplate(object);

		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		systemTemplate.addObjectTemplate(point_A);

		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);
		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		systemTemplate.addObjectTemplate(point_B);

		systemTemplate.addLinkTemplate(object, LINK_POSITION, point_B);

		systemTemplate.addLinkTemplate(point_A, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_B);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_B, ID_TEMPLATE_POINT_A),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, true),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, false) };

		final Action action = new Action(OPERATION_MOVE_LEFT);

		return new SystemTransformation(ELEMENT_MOVE_LEFT, action, systemTemplate, transformations);
	}

	public static SystemTransformation moveTop() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		systemTemplate.addObjectTemplate(object);

		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		systemTemplate.addObjectTemplate(point_A);

		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);
		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		systemTemplate.addObjectTemplate(point_B);

		systemTemplate.addLinkTemplate(object, LINK_POSITION, point_A);

		systemTemplate.addLinkTemplate(point_A, LINK_NEIGHBOR_TOP, LINK_NEIGHBOR_BOTTOM, point_B);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_TOP);

		return new SystemTransformation(ELEMENT_MOVE_TOP, action, systemTemplate, transformations);
	}

	public static SystemTransformation moveBottom() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate object = new SystemObjectTemplate(ID_TEMPLATE_OBJECT);
		systemTemplate.addObjectTemplate(object);

		final SystemObjectTemplate point_A = new SystemObjectTemplate(ID_TEMPLATE_POINT_A);
		point_A.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		systemTemplate.addObjectTemplate(point_A);

		final SystemObjectTemplate point_B = new SystemObjectTemplate(ID_TEMPLATE_POINT_B);
		point_B.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		systemTemplate.addObjectTemplate(point_B);

		systemTemplate.addLinkTemplate(object, LINK_POSITION, point_A);

		systemTemplate.addLinkTemplate(point_A, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_B);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B),
				new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null),
				new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT),
				new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true) };

		final Action action = new Action(OPERATION_MOVE_BOTTOM);

		return new SystemTransformation(ELEMENT_MOVE_BOTTOM, action, systemTemplate, transformations);
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
