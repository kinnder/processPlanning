package domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import planning.model.Attribute;
import planning.model.Element;
import planning.model.Link;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.Transformation;
import planning.model.AttributeTransformation;

public class MaterialPoints {

	private static final String LINK_POSITION = "position";

	private static final String LINK_NEIGHBOR_RIGHT = "neighborOnTheRight";

	private static final String LINK_NEIGHBOR_LEFT = "neighborOnTheLeft";

	private static final String LINK_NEIGHBOR_TOP = "neighborOnTheTop";

	private static final String LINK_NEIGHBOR_BOTTOM = "neighborOnTheBottom";

	private static final String ATTRIBUTE_OCCUPIED = "occupied";

	public static Element moveRight() {
		System template = new System();
		SystemObject object = new SystemObject("материальная точка", "#ID-1");
		SystemObject point_A = new SystemObject("точка", "#ID-2");
		SystemObject point_B = new SystemObject("точка", "#ID-3");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_A_id));

		point_A.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_B_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_LEFT, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new AttributeTransformation(point_A_id, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(point_B_id, ATTRIBUTE_OCCUPIED, true) };

		return new Element("Движение вправо", template, transformations);
	}

	public static Element moveLeft() {
		System template = new System();
		SystemObject object = new SystemObject("материальная точка", "#ID-1");
		SystemObject point_A = new SystemObject("точка", "#ID-2");
		SystemObject point_B = new SystemObject("точка", "#ID-3");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_B_id));

		point_A.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_A.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_B_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_B.addLink(new Link(LINK_NEIGHBOR_LEFT, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_A_id),
				new AttributeTransformation(point_A_id, ATTRIBUTE_OCCUPIED, true),
				new AttributeTransformation(point_B_id, ATTRIBUTE_OCCUPIED, false) };

		return new Element("Движение влево", template, transformations);
	}

	public static Element moveTop() {
		System template = new System();
		SystemObject object = new SystemObject("материальная точка", "#ID-1");
		SystemObject point_A = new SystemObject("точка", "#ID-2");
		SystemObject point_B = new SystemObject("точка", "#ID-3");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_A_id));

		point_A.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new Link(LINK_NEIGHBOR_TOP, point_B_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new AttributeTransformation(point_A_id, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(point_B_id, ATTRIBUTE_OCCUPIED, true) };

		return new Element("Движение вверх", template, transformations);
	}

	public static Element moveBottom() {
		System template = new System();
		SystemObject object = new SystemObject("материальная точка", "#ID-1");
		SystemObject point_A = new SystemObject("точка", "#ID-2");
		SystemObject point_B = new SystemObject("точка", "#ID-3");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_A_id));

		point_A.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_B_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_TOP, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new AttributeTransformation(point_A_id, ATTRIBUTE_OCCUPIED, false),
				new AttributeTransformation(point_B_id, ATTRIBUTE_OCCUPIED, true) };

		return new Element("Движение вниз", template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final System initial_system = new System();
		final SystemObject initial_object = new SystemObject("материальная точка");
		final SystemObject initial_point_1 = new SystemObject("точка-1");
		final SystemObject initial_point_2 = new SystemObject("точка-2");
		final SystemObject initial_point_3 = new SystemObject("точка-3");
		final SystemObject initial_point_4 = new SystemObject("точка-4");

		final String initial_object_id = initial_object.getObjectId();
		final String initial_point_1_id = initial_point_1.getObjectId();
		final String initial_point_2_id = initial_point_2.getObjectId();
		final String initial_point_3_id = initial_point_3.getObjectId();
		final String initial_point_4_id = initial_point_4.getObjectId();

		initial_object.addLink(new Link(LINK_POSITION, initial_point_1_id));

		initial_point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, initial_point_2_id));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_BOTTOM, initial_point_3_id));

		initial_point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_1_id));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_BOTTOM, initial_point_4_id));

		initial_point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, initial_point_4_id));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_TOP, initial_point_1_id));

		initial_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_3_id));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_TOP, initial_point_2_id));

		initial_system.addObject(initial_object);
		initial_system.addObject(initial_point_1);
		initial_system.addObject(initial_point_2);
		initial_system.addObject(initial_point_3);
		initial_system.addObject(initial_point_4);

		System expected_system = initial_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_2_id);
		expected_system.getObjectById(initial_point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);

		System actual_system = moveRight().applyTo(initial_system);
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_4_id);
		expected_system.getObjectById(initial_point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);

		actual_system = moveBottom().applyTo(actual_system);
		assertTrue(expected_system.equals(actual_system));
	}
}
