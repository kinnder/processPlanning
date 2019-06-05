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

	private static final String OBJECT_MATERIAL_POINT = "материальная точка";

	private static final String OBJECT_POINT = "точка";

	private static final String OPERATION_MOVE_RIGHT = "Движение вправо";

	private static final String OPERATION_MOVE_LEFT = "Движение влево";

	private static final String OPERATION_MOVE_BOTTOM = "Движение вниз";

	private static final String OPERATION_MOVE_TOP = "Движение вверх";

	public static Element moveRight() {
		System template = new System();
		SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, "#ID-1");
		SystemObject point_A = new SystemObject(OBJECT_POINT, "#ID-2");
		SystemObject point_B = new SystemObject(OBJECT_POINT, "#ID-3");

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

		return new Element(OPERATION_MOVE_RIGHT, template, transformations);
	}

	public static Element moveLeft() {
		System template = new System();
		SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, "#ID-1");
		SystemObject point_A = new SystemObject(OBJECT_POINT, "#ID-2");
		SystemObject point_B = new SystemObject(OBJECT_POINT, "#ID-3");

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

		return new Element(OPERATION_MOVE_LEFT, template, transformations);
	}

	public static Element moveTop() {
		System template = new System();
		SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, "#ID-1");
		SystemObject point_A = new SystemObject(OBJECT_POINT, "#ID-2");
		SystemObject point_B = new SystemObject(OBJECT_POINT, "#ID-3");

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

		return new Element(OPERATION_MOVE_TOP, template, transformations);
	}

	public static Element moveBottom() {
		System template = new System();
		SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, "#ID-1");
		SystemObject point_A = new SystemObject(OBJECT_POINT, "#ID-2");
		SystemObject point_B = new SystemObject(OBJECT_POINT, "#ID-3");

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

		return new Element(OPERATION_MOVE_BOTTOM, template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final System initial_system = new System();
		final SystemObject initial_object = new SystemObject("материальная точка");
		final SystemObject initial_point_1 = new SystemObject("точка-1");
		final SystemObject initial_point_2 = new SystemObject("точка-2");
		final SystemObject initial_point_3 = new SystemObject("точка-3");
		final SystemObject initial_point_4 = new SystemObject("точка-4");

		final String object_id = initial_object.getObjectId();
		final String point_1_id = initial_point_1.getObjectId();
		final String point_2_id = initial_point_2.getObjectId();
		final String point_3_id = initial_point_3.getObjectId();
		final String point_4_id = initial_point_4.getObjectId();

		initial_object.addLink(new Link(LINK_POSITION, point_1_id));

		initial_point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_2_id));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_3_id));
		initial_point_1.addLink(new Link(LINK_POSITION, object_id));

		initial_point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, point_1_id));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_4_id));
		initial_point_2.addLink(new Link(LINK_POSITION, null));

		initial_point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_4_id));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_TOP, point_1_id));
		initial_point_3.addLink(new Link(LINK_POSITION, null));

		initial_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, point_3_id));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_TOP, point_2_id));
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
		expected_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_2_id);
		expected_system.getObjectById(point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(point_1_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(point_2_id).getLink(LINK_POSITION).setObjectId(object_id);

		element = moveRight();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_4_id);
		expected_system.getObjectById(point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(point_2_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(point_4_id).getLink(LINK_POSITION).setObjectId(object_id);

		element = moveBottom();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_3_id);
		expected_system.getObjectById(point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(point_4_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(point_3_id).getLink(LINK_POSITION).setObjectId(object_id);

		element = moveLeft();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		expected_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_1_id);
		expected_system.getObjectById(point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(false);
		expected_system.getObjectById(point_3_id).getLink(LINK_POSITION).setObjectId(null);
		expected_system.getObjectById(point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		expected_system.getObjectById(point_1_id).getLink(LINK_POSITION).setObjectId(object_id);

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

		final String object_id = initial_object.getObjectId();
		final String point_1_id = initial_point_1.getObjectId();
		final String point_2_id = initial_point_2.getObjectId();
		final String point_3_id = initial_point_3.getObjectId();
		final String point_4_id = initial_point_4.getObjectId();

		initial_object.addLink(new Link(LINK_POSITION, point_1_id));

		initial_point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_2_id));
		initial_point_1.addLink(new Link(LINK_NEIGHBOR_LEFT, null));
		initial_point_1.addLink(new Link(LINK_POSITION, object_id));

		initial_point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_3_id));
		initial_point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, point_1_id));
		initial_point_2.addLink(new Link(LINK_POSITION, null));

		initial_point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_4_id));
		initial_point_3.addLink(new Link(LINK_NEIGHBOR_LEFT, point_2_id));
		initial_point_3.addLink(new Link(LINK_POSITION, null));

		initial_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_RIGHT, null));
		initial_point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, point_3_id));
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

		final System final_system = new System();
		final SystemObject final_object = (SystemObject) initial_object.clone();
		final SystemObject final_point_4 = (SystemObject) initial_point_4.clone();
		final_system.addObject(final_object);
		final_system.addObject(final_point_4);

		final_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_4_id);
		final_system.getObjectById(point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		final_system.getObjectById(point_4_id).getLink(LINK_POSITION).setObjectId(object_id);
		final_system.getObjectById(point_4_id).removeLink(LINK_NEIGHBOR_RIGHT);
		final_system.getObjectById(point_4_id).removeLink(LINK_NEIGHBOR_LEFT);

		final Element[] elements = new Element[] { moveLeft(), moveRight(), moveTop(), moveBottom() };

		Planner planner = new Planner(initial_system, final_system, elements);
		planner.plan();

		List<String> operations = planner.getShortestPlan();
		assertEquals(3, operations.size());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(0));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(1));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(2));
	}

	@Test
	public void movingInTheField() {
		// y
		// 6 + - - - - - - - - - +
		// 5 | . - - + - - - + . |
		// 4 | A . . | . . . | . |
		// 3 | . | . | . | . | . |
		// 2 | . | . . . | . . B |
		// 1 | . + - - - + - - . |
		// 0 + - - - - - - - - - +
		// 0 0 1 2 3 4 5 6 7 8 9 A x

		final System initial_system = new System();
		final SystemObject initial_object = new SystemObject("материальная точка");
		final SystemObject initial_point_15 = new SystemObject("точка-15");
		final SystemObject initial_point_14 = new SystemObject("точка-14");
		final SystemObject initial_point_13 = new SystemObject("точка-13");
		final SystemObject initial_point_12 = new SystemObject("точка-12");
		final SystemObject initial_point_11 = new SystemObject("точка-11");
		final SystemObject initial_point_24 = new SystemObject("точка-24");
		final SystemObject initial_point_34 = new SystemObject("точка-34");
		final SystemObject initial_point_33 = new SystemObject("точка-33");
		final SystemObject initial_point_32 = new SystemObject("точка-32");
		final SystemObject initial_point_42 = new SystemObject("точка-42");
		final SystemObject initial_point_54 = new SystemObject("точка-54");
		final SystemObject initial_point_53 = new SystemObject("точка-53");
		final SystemObject initial_point_52 = new SystemObject("точка-52");
		final SystemObject initial_point_64 = new SystemObject("точка-64");
		final SystemObject initial_point_74 = new SystemObject("точка-74");
		final SystemObject initial_point_73 = new SystemObject("точка-73");
		final SystemObject initial_point_72 = new SystemObject("точка-72");
		final SystemObject initial_point_82 = new SystemObject("точка-82");
		final SystemObject initial_point_95 = new SystemObject("точка-95");
		final SystemObject initial_point_94 = new SystemObject("точка-94");
		final SystemObject initial_point_93 = new SystemObject("точка-93");
		final SystemObject initial_point_92 = new SystemObject("точка-92");
		final SystemObject initial_point_91 = new SystemObject("точка-91");

		final String object_id = initial_object.getObjectId();
		final String point_15_id = initial_point_15.getObjectId();
		final String point_14_id = initial_point_14.getObjectId();
		final String point_13_id = initial_point_13.getObjectId();
		final String point_12_id = initial_point_12.getObjectId();
		final String point_11_id = initial_point_11.getObjectId();
		final String point_24_id = initial_point_24.getObjectId();
		final String point_34_id = initial_point_34.getObjectId();
		final String point_33_id = initial_point_33.getObjectId();
		final String point_32_id = initial_point_32.getObjectId();
		final String point_42_id = initial_point_42.getObjectId();
		final String point_54_id = initial_point_54.getObjectId();
		final String point_53_id = initial_point_53.getObjectId();
		final String point_52_id = initial_point_52.getObjectId();
		final String point_64_id = initial_point_64.getObjectId();
		final String point_74_id = initial_point_74.getObjectId();
		final String point_73_id = initial_point_73.getObjectId();
		final String point_72_id = initial_point_72.getObjectId();
		final String point_82_id = initial_point_82.getObjectId();
		final String point_95_id = initial_point_95.getObjectId();
		final String point_94_id = initial_point_94.getObjectId();
		final String point_93_id = initial_point_93.getObjectId();
		final String point_92_id = initial_point_92.getObjectId();
		final String point_91_id = initial_point_91.getObjectId();

		initial_object.addLink(new Link(LINK_POSITION, point_14_id));

		initial_point_15.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_15.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_14_id));
		initial_point_15.addLink(new Link(LINK_POSITION, null));

		initial_point_14.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		initial_point_14.addLink(new Link(LINK_NEIGHBOR_TOP, point_15_id));
		initial_point_14.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_24_id));
		initial_point_14.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_13_id));
		initial_point_14.addLink(new Link(LINK_POSITION, object_id));

		initial_point_13.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_13.addLink(new Link(LINK_NEIGHBOR_TOP, point_14_id));
		initial_point_13.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_12_id));
		initial_point_13.addLink(new Link(LINK_POSITION, null));

		initial_point_12.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_12.addLink(new Link(LINK_NEIGHBOR_TOP, point_13_id));
		initial_point_12.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_11_id));
		initial_point_12.addLink(new Link(LINK_POSITION, null));

		initial_point_11.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_11.addLink(new Link(LINK_NEIGHBOR_TOP, point_12_id));
		initial_point_11.addLink(new Link(LINK_POSITION, null));

		initial_point_24.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_24.addLink(new Link(LINK_NEIGHBOR_LEFT, point_14_id));
		initial_point_24.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_34_id));
		initial_point_24.addLink(new Link(LINK_POSITION, null));

		initial_point_34.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_34.addLink(new Link(LINK_NEIGHBOR_LEFT, point_24_id));
		initial_point_34.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_33_id));
		initial_point_34.addLink(new Link(LINK_POSITION, null));

		initial_point_33.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_33.addLink(new Link(LINK_NEIGHBOR_TOP, point_34_id));
		initial_point_33.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_32_id));
		initial_point_33.addLink(new Link(LINK_POSITION, null));

		initial_point_32.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_32.addLink(new Link(LINK_NEIGHBOR_TOP, point_33_id));
		initial_point_32.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_42_id));
		initial_point_32.addLink(new Link(LINK_POSITION, null));

		initial_point_42.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_42.addLink(new Link(LINK_NEIGHBOR_LEFT, point_32_id));
		initial_point_42.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_52_id));
		initial_point_42.addLink(new Link(LINK_POSITION, null));

		initial_point_52.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_52.addLink(new Link(LINK_NEIGHBOR_TOP, point_53_id));
		initial_point_52.addLink(new Link(LINK_NEIGHBOR_LEFT, point_42_id));
		initial_point_52.addLink(new Link(LINK_POSITION, null));

		initial_point_53.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_53.addLink(new Link(LINK_NEIGHBOR_TOP, point_54_id));
		initial_point_53.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_52_id));
		initial_point_53.addLink(new Link(LINK_POSITION, null));

		initial_point_54.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_54.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_64_id));
		initial_point_54.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_53_id));
		initial_point_54.addLink(new Link(LINK_POSITION, null));

		initial_point_64.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_64.addLink(new Link(LINK_NEIGHBOR_LEFT, point_54_id));
		initial_point_64.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_74_id));
		initial_point_64.addLink(new Link(LINK_POSITION, null));

		initial_point_74.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_74.addLink(new Link(LINK_NEIGHBOR_LEFT, point_64_id));
		initial_point_74.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_73_id));
		initial_point_74.addLink(new Link(LINK_POSITION, null));

		initial_point_73.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_73.addLink(new Link(LINK_NEIGHBOR_TOP, point_74_id));
		initial_point_73.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_72_id));
		initial_point_73.addLink(new Link(LINK_POSITION, null));

		initial_point_72.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_72.addLink(new Link(LINK_NEIGHBOR_TOP, point_73_id));
		initial_point_72.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_82_id));
		initial_point_72.addLink(new Link(LINK_POSITION, null));

		initial_point_82.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_82.addLink(new Link(LINK_NEIGHBOR_LEFT, point_72_id));
		initial_point_82.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_92_id));
		initial_point_82.addLink(new Link(LINK_POSITION, null));

		initial_point_95.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_95.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_94_id));
		initial_point_95.addLink(new Link(LINK_POSITION, null));

		initial_point_94.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_94.addLink(new Link(LINK_NEIGHBOR_TOP, point_95_id));
		initial_point_94.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_93_id));
		initial_point_94.addLink(new Link(LINK_POSITION, null));

		initial_point_93.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_93.addLink(new Link(LINK_NEIGHBOR_TOP, point_94_id));
		initial_point_93.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_92_id));
		initial_point_93.addLink(new Link(LINK_POSITION, null));

		initial_point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_92.addLink(new Link(LINK_NEIGHBOR_TOP, point_93_id));
		initial_point_92.addLink(new Link(LINK_NEIGHBOR_LEFT, point_82_id));
		initial_point_92.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_91_id));
		initial_point_92.addLink(new Link(LINK_POSITION, null));

		initial_point_91.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		initial_point_91.addLink(new Link(LINK_NEIGHBOR_TOP, point_92_id));
		initial_point_91.addLink(new Link(LINK_POSITION, null));

		initial_system.addObject(initial_object);
		initial_system.addObject(initial_point_15);
		initial_system.addObject(initial_point_14);
		initial_system.addObject(initial_point_13);
		initial_system.addObject(initial_point_12);
		initial_system.addObject(initial_point_11);
		initial_system.addObject(initial_point_24);
		initial_system.addObject(initial_point_34);
		initial_system.addObject(initial_point_33);
		initial_system.addObject(initial_point_32);
		initial_system.addObject(initial_point_42);
		initial_system.addObject(initial_point_54);
		initial_system.addObject(initial_point_53);
		initial_system.addObject(initial_point_52);
		initial_system.addObject(initial_point_64);
		initial_system.addObject(initial_point_74);
		initial_system.addObject(initial_point_73);
		initial_system.addObject(initial_point_72);
		initial_system.addObject(initial_point_82);
		initial_system.addObject(initial_point_95);
		initial_system.addObject(initial_point_94);
		initial_system.addObject(initial_point_93);
		initial_system.addObject(initial_point_92);
		initial_system.addObject(initial_point_91);

		SystemVariant[] systemVariants;
		systemVariants = initial_system.matchIds(moveRight().getTemplate());
		assertEquals(1, systemVariants.length);

		systemVariants = initial_system.matchIds(moveLeft().getTemplate());
		assertEquals(0, systemVariants.length);

		systemVariants = initial_system.matchIds(moveTop().getTemplate());
		assertEquals(1, systemVariants.length);

		systemVariants = initial_system.matchIds(moveBottom().getTemplate());
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_object = (SystemObject) initial_object.clone();
		final SystemObject final_point_92 = (SystemObject) initial_point_92.clone();
		final_system.addObject(final_object);
		final_system.addObject(final_point_92);

		final_system.getObjectById(object_id).getLink(LINK_POSITION).setObjectId(point_92_id);
		final_system.getObjectById(point_92_id).getAttribute(ATTRIBUTE_OCCUPIED).setValue(true);
		final_system.getObjectById(point_92_id).getLink(LINK_POSITION).setObjectId(object_id);
		final_system.getObjectById(point_92_id).removeLink(LINK_NEIGHBOR_TOP);
		final_system.getObjectById(point_92_id).removeLink(LINK_NEIGHBOR_LEFT);
		final_system.getObjectById(point_92_id).removeLink(LINK_NEIGHBOR_BOTTOM);

		final Element[] elements = new Element[] { moveLeft(), moveRight(), moveTop(), moveBottom() };

		Planner planner = new Planner(initial_system, final_system, elements);
		planner.plan();

		List<String> operations = planner.getShortestPlan();
		assertEquals(14, operations.size());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(0));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(1));
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(2));
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(3));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(4));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(5));
		assertEquals(OPERATION_MOVE_TOP, operations.get(6));
		assertEquals(OPERATION_MOVE_TOP, operations.get(7));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(8));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(9));
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(10));
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(11));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(12));
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(13));
	}
}
