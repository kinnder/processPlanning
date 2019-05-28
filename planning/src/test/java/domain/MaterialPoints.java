package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import planning.method.Planner;
import planning.model.Attribute;
import planning.model.Element;
import planning.model.Link;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemVariant;
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
		point_A.addLink(new Link(LINK_POSITION, object_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_LEFT, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new LinkTransformation(point_A_id, LINK_POSITION, null),
				new LinkTransformation(point_B_id, LINK_POSITION, object_id),
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
		point_B.addLink(new Link(LINK_POSITION, object_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_A_id),
				new LinkTransformation(point_A_id, LINK_POSITION, object_id),
				new LinkTransformation(point_B_id, LINK_POSITION, null),
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
		point_A.addLink(new Link(LINK_POSITION, object_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new LinkTransformation(point_A_id, LINK_POSITION, null),
				new LinkTransformation(point_B_id, LINK_POSITION, object_id),
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
		point_A.addLink(new Link(LINK_POSITION, object_id));

		point_B.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new Link(LINK_NEIGHBOR_TOP, point_A_id));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] {
				new LinkTransformation(object_id, LINK_POSITION, point_B_id),
				new LinkTransformation(point_A_id, LINK_POSITION, null),
				new LinkTransformation(point_B_id, LINK_POSITION, object_id),
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
		initial_point_1.addLink(new Link(LINK_POSITION, initial_object_id));

		initial_point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_1_id));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_BOTTOM, initial_point_4_id));
		initial_point_2.addLink(new Link(LINK_POSITION, null));

		initial_point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, initial_point_4_id));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_TOP, initial_point_1_id));
		initial_point_3.addLink(new Link(LINK_POSITION, null));

		initial_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_3_id));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_TOP, initial_point_2_id));
		initial_point_4.addLink(new Link(LINK_POSITION, null));

		initial_system.addObject(initial_object);
		initial_system.addObject(initial_point_1);
		initial_system.addObject(initial_point_2);
		initial_system.addObject(initial_point_3);
		initial_system.addObject(initial_point_4);

		System expected_system;
		System actual_system = initial_system.clone();
		Element element;
		SystemVariant[] systemVariants;

		expected_system = actual_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_2_id);
		expected_system.getObjectById(initial_point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_1_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(initial_point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(initial_point_2_id).getLink(LINK_POSITION).setObjectId(initial_object_id);

		element = moveRight();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_4_id);
		expected_system.getObjectById(initial_point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_2_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(initial_point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(initial_point_4_id).getLink(LINK_POSITION).setObjectId(initial_object_id);

		element = moveBottom();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_3_id);
		expected_system.getObjectById(initial_point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_4_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(initial_point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(initial_point_3_id).getLink(LINK_POSITION).setObjectId(initial_object_id);

		element = moveLeft();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_1_id);
		expected_system.getObjectById(initial_point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(initial_point_3_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(initial_point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(initial_point_1_id).getLink(LINK_POSITION).setObjectId(initial_object_id);

		element = moveTop();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));
	}

	@Test
	public void movingOnStraightLine() {
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
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_LEFT, null));
		initial_point_1.addLink(new Link(LINK_POSITION, initial_object_id));

		initial_point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_RIGHT, initial_point_3_id));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_1_id));
		initial_point_2.addLink(new Link(LINK_POSITION, null));

		initial_point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, initial_point_4_id));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_2_id));
		initial_point_3.addLink(new Link(LINK_POSITION, null));

		initial_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_RIGHT, null));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, initial_point_3_id));
		initial_point_4.addLink(new Link(LINK_POSITION, null));

		initial_system.addObject(initial_object);
		initial_system.addObject(initial_point_1);
		initial_system.addObject(initial_point_2);
		initial_system.addObject(initial_point_3);
		initial_system.addObject(initial_point_4);

		SystemVariant[] systemVariants;
		systemVariants = initial_system.matchIds(moveRight().getTemplate());
		assertEquals(1, systemVariants.length);

		systemVariants = initial_system.matchIds(moveLeft().getTemplate());
		assertEquals(0, systemVariants.length);

		systemVariants = initial_system.matchIds(moveTop().getTemplate());
		assertEquals(0, systemVariants.length);

		systemVariants = initial_system.matchIds(moveBottom().getTemplate());
		assertEquals(0, systemVariants.length);

		System final_system = new System();
		SystemObject final_object = (SystemObject) initial_object.clone();
		SystemObject final_point_4 = (SystemObject) initial_point_4.clone();
		final_system.addObject(final_object);
		final_system.addObject(final_point_4);

		final_system.getObjectById(initial_object_id).getLink(LINK_POSITION).setObjectId(initial_point_4_id);
		final_system.getObjectById(initial_point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		final_system.getObjectById(initial_point_4_id).getLink(LINK_POSITION).setObjectId(initial_object_id);
		final_system.getObjectById(initial_point_4_id).removeLink(LINK_NEIGHBOR_RIGHT);
		final_system.getObjectById(initial_point_4_id).removeLink(LINK_NEIGHBOR_LEFT);

		Element[] elements = new Element[] { moveLeft(), moveRight(), moveTop(), moveBottom() };

		Planner planner = new Planner(initial_system, final_system, elements);
		planner.plan();

		List<String> operations = planner.getShortestPlan();
		assertEquals(3, operations.size());
		assertEquals("Движение вправо", operations.get(0));
		assertEquals("Движение вправо", operations.get(1));
		assertEquals("Движение вправо", operations.get(2));
	}
}
