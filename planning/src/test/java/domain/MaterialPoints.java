package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;

import planning.method.Planner;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.Element;
import planning.model.Link;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
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

	private static final String OPERATION_MOVE_RIGHT = "Движение вправо";

	private static final String OPERATION_MOVE_LEFT = "Движение влево";

	private static final String OPERATION_MOVE_BOTTOM = "Движение вниз";

	private static final String OPERATION_MOVE_TOP = "Движение вверх";

	public static Element moveRight() {
		SystemTemplate template = new SystemTemplate();
		SystemObjectTemplate object = new SystemObjectTemplate("#OBJECT");
		SystemObjectTemplate point_A = new SystemObjectTemplate("#POINT-A");
		SystemObjectTemplate point_B = new SystemObjectTemplate("#POINT-B");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new LinkTemplate(LINK_POSITION, point_A_id));

		point_A.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new LinkTemplate(LINK_NEIGHBOR_RIGHT, point_B_id));
		point_A.addLink(new LinkTemplate(LINK_POSITION, object_id));

		point_B.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new LinkTemplate(LINK_NEIGHBOR_LEFT, point_A_id));

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
		SystemTemplate template = new SystemTemplate();
		SystemObjectTemplate object = new SystemObjectTemplate("#OBJECT");
		SystemObjectTemplate point_A = new SystemObjectTemplate("#POINT-A");
		SystemObjectTemplate point_B = new SystemObjectTemplate("#POINT-B");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new LinkTemplate(LINK_POSITION, point_B_id));

		point_A.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_A.addLink(new LinkTemplate(LINK_NEIGHBOR_RIGHT, point_B_id));

		point_B.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_B.addLink(new LinkTemplate(LINK_NEIGHBOR_LEFT, point_A_id));
		point_B.addLink(new LinkTemplate(LINK_POSITION, object_id));

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
		SystemTemplate template = new SystemTemplate();
		SystemObjectTemplate object = new SystemObjectTemplate("#OBJECT");
		SystemObjectTemplate point_A = new SystemObjectTemplate("#POINT-A");
		SystemObjectTemplate point_B = new SystemObjectTemplate("#POINT-B");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new LinkTemplate(LINK_POSITION, point_A_id));

		point_A.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new LinkTemplate(LINK_NEIGHBOR_TOP, point_B_id));
		point_A.addLink(new LinkTemplate(LINK_POSITION, object_id));

		point_B.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new LinkTemplate(LINK_NEIGHBOR_BOTTOM, point_A_id));

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
		SystemTemplate template = new SystemTemplate();
		SystemObjectTemplate object = new SystemObjectTemplate("#OBJECT");
		SystemObjectTemplate point_A = new SystemObjectTemplate("#POINT-A");
		SystemObjectTemplate point_B = new SystemObjectTemplate("#POINT-B");

		final String object_id = object.getObjectId();
		final String point_A_id = point_A.getObjectId();
		final String point_B_id = point_B.getObjectId();

		object.addLink(new LinkTemplate(LINK_POSITION, point_A_id));

		point_A.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, true));
		point_A.addLink(new LinkTemplate(LINK_NEIGHBOR_BOTTOM, point_B_id));
		point_A.addLink(new LinkTemplate(LINK_POSITION, object_id));

		point_B.addAttribute(new AttributeTemplate(ATTRIBUTE_OCCUPIED, false));
		point_B.addLink(new LinkTemplate(LINK_NEIGHBOR_TOP, point_A_id));

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
		final SystemObject object = new SystemObject("материальная точка");
		final SystemObject point_1 = new SystemObject("точка-1");
		final SystemObject point_2 = new SystemObject("точка-2");
		final SystemObject point_3 = new SystemObject("точка-3");
		final SystemObject point_4 = new SystemObject("точка-4");

		final String object_id = object.getObjectId();
		final String point_1_id = point_1.getObjectId();
		final String point_2_id = point_2.getObjectId();
		final String point_3_id = point_3.getObjectId();
		final String point_4_id = point_4.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_1_id));

		point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_2_id));
		point_1.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_3_id));
		point_1.addLink(new Link(LINK_POSITION, object_id));

		point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, point_1_id));
		point_2.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_4_id));
		point_2.addLink(new Link(LINK_POSITION, null));

		point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_4_id));
		point_3.addLink(new Link(LINK_NEIGHBOR_TOP, point_1_id));
		point_3.addLink(new Link(LINK_POSITION, null));

		point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, point_3_id));
		point_4.addLink(new Link(LINK_NEIGHBOR_TOP, point_2_id));
		point_4.addLink(new Link(LINK_POSITION, null));

		System system = new System();
		system.addObject(object);
		system.addObject(point_1);
		system.addObject(point_2);
		system.addObject(point_3);
		system.addObject(point_4);

		Element element;
		SystemVariant[] systemVariants;

		element = moveRight();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(point_2_id, system.getObjectById(object_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(false, system.getObjectById(point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(null, system.getObjectById(point_1_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(true, system.getObjectById(point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(object_id, system.getObjectById(point_2_id).getLink(LINK_POSITION).getObjectId());

		element = moveBottom();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(point_4_id, system.getObjectById(object_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(false, system.getObjectById(point_2_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(null, system.getObjectById(point_2_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(true, system.getObjectById(point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(object_id, system.getObjectById(point_4_id).getLink(LINK_POSITION).getObjectId());

		element = moveLeft();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(point_3_id, system.getObjectById(object_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(false, system.getObjectById(point_4_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(null, system.getObjectById(point_4_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(true, system.getObjectById(point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(object_id, system.getObjectById(point_3_id).getLink(LINK_POSITION).getObjectId());

		element = moveTop();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(point_1_id, system.getObjectById(object_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(false, system.getObjectById(point_3_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(null, system.getObjectById(point_3_id).getLink(LINK_POSITION).getObjectId());
		assertEquals(true, system.getObjectById(point_1_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertEquals(object_id, system.getObjectById(point_1_id).getLink(LINK_POSITION).getObjectId());
	}

	@Test
	public void movingOnStraightLine() {
		final System initial_system = new System();
		final SystemObject object = new SystemObject("материальная точка");
		final SystemObject point_1 = new SystemObject("точка-1");
		final SystemObject point_2 = new SystemObject("точка-2");
		final SystemObject point_3 = new SystemObject("точка-3");
		final SystemObject point_4 = new SystemObject("точка-4");

		final String object_id = object.getObjectId();
		final String point_1_id = point_1.getObjectId();
		final String point_2_id = point_2.getObjectId();
		final String point_3_id = point_3.getObjectId();
		final String point_4_id = point_4.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_1_id));

		point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_2_id));
		point_1.addLink(new Link(LINK_NEIGHBOR_LEFT, null));
		point_1.addLink(new Link(LINK_POSITION, object_id));

		point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_2.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_3_id));
		point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, point_1_id));
		point_2.addLink(new Link(LINK_POSITION, null));

		point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_4_id));
		point_3.addLink(new Link(LINK_NEIGHBOR_LEFT, point_2_id));
		point_3.addLink(new Link(LINK_POSITION, null));

		point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_4.addLink(new Link(LINK_NEIGHBOR_RIGHT, null));
		point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, point_3_id));
		point_4.addLink(new Link(LINK_POSITION, null));

		initial_system.addObject(object);
		initial_system.addObject(point_1);
		initial_system.addObject(point_2);
		initial_system.addObject(point_3);
		initial_system.addObject(point_4);

		SystemVariant[] systemVariants;
		systemVariants = moveRight().applyTo(initial_system);
		assertEquals(1, systemVariants.length);

		systemVariants = moveLeft().applyTo(initial_system);
		assertEquals(0, systemVariants.length);

		systemVariants = moveTop().applyTo(initial_system);
		assertEquals(0, systemVariants.length);

		systemVariants = moveBottom().applyTo(initial_system);
		assertEquals(0, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_object = new SystemObject("материальная точка", object_id);
		final SystemObject final_point_4 = new SystemObject("точка-4", point_4_id);
		final_system.addObject(final_object);
		final_system.addObject(final_point_4);

		final_object.addLink(new Link(LINK_POSITION, point_4_id));
		final_point_4.addLink(new Link(LINK_POSITION, object_id));
		final_point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));

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
		final SystemObject object = new SystemObject("материальная точка");
		final SystemObject point_15 = new SystemObject("точка-15");
		final SystemObject point_14 = new SystemObject("точка-14");
		final SystemObject point_13 = new SystemObject("точка-13");
		final SystemObject point_12 = new SystemObject("точка-12");
		final SystemObject point_11 = new SystemObject("точка-11");
		final SystemObject point_24 = new SystemObject("точка-24");
		final SystemObject point_34 = new SystemObject("точка-34");
		final SystemObject point_33 = new SystemObject("точка-33");
		final SystemObject point_32 = new SystemObject("точка-32");
		final SystemObject point_42 = new SystemObject("точка-42");
		final SystemObject point_54 = new SystemObject("точка-54");
		final SystemObject point_53 = new SystemObject("точка-53");
		final SystemObject point_52 = new SystemObject("точка-52");
		final SystemObject point_64 = new SystemObject("точка-64");
		final SystemObject point_74 = new SystemObject("точка-74");
		final SystemObject point_73 = new SystemObject("точка-73");
		final SystemObject point_72 = new SystemObject("точка-72");
		final SystemObject point_82 = new SystemObject("точка-82");
		final SystemObject point_95 = new SystemObject("точка-95");
		final SystemObject point_94 = new SystemObject("точка-94");
		final SystemObject point_93 = new SystemObject("точка-93");
		final SystemObject point_92 = new SystemObject("точка-92");
		final SystemObject point_91 = new SystemObject("точка-91");

		final String object_id = object.getObjectId();
		final String point_15_id = point_15.getObjectId();
		final String point_14_id = point_14.getObjectId();
		final String point_13_id = point_13.getObjectId();
		final String point_12_id = point_12.getObjectId();
		final String point_11_id = point_11.getObjectId();
		final String point_24_id = point_24.getObjectId();
		final String point_34_id = point_34.getObjectId();
		final String point_33_id = point_33.getObjectId();
		final String point_32_id = point_32.getObjectId();
		final String point_42_id = point_42.getObjectId();
		final String point_54_id = point_54.getObjectId();
		final String point_53_id = point_53.getObjectId();
		final String point_52_id = point_52.getObjectId();
		final String point_64_id = point_64.getObjectId();
		final String point_74_id = point_74.getObjectId();
		final String point_73_id = point_73.getObjectId();
		final String point_72_id = point_72.getObjectId();
		final String point_82_id = point_82.getObjectId();
		final String point_95_id = point_95.getObjectId();
		final String point_94_id = point_94.getObjectId();
		final String point_93_id = point_93.getObjectId();
		final String point_92_id = point_92.getObjectId();
		final String point_91_id = point_91.getObjectId();

		object.addLink(new Link(LINK_POSITION, point_14_id));

		point_15.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_15.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_14_id));
		point_15.addLink(new Link(LINK_POSITION, null));

		point_14.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_14.addLink(new Link(LINK_NEIGHBOR_TOP, point_15_id));
		point_14.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_24_id));
		point_14.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_13_id));
		point_14.addLink(new Link(LINK_POSITION, object_id));

		point_13.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_13.addLink(new Link(LINK_NEIGHBOR_TOP, point_14_id));
		point_13.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_12_id));
		point_13.addLink(new Link(LINK_POSITION, null));

		point_12.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_12.addLink(new Link(LINK_NEIGHBOR_TOP, point_13_id));
		point_12.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_11_id));
		point_12.addLink(new Link(LINK_POSITION, null));

		point_11.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_11.addLink(new Link(LINK_NEIGHBOR_TOP, point_12_id));
		point_11.addLink(new Link(LINK_POSITION, null));

		point_24.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_24.addLink(new Link(LINK_NEIGHBOR_LEFT, point_14_id));
		point_24.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_34_id));
		point_24.addLink(new Link(LINK_POSITION, null));

		point_34.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_34.addLink(new Link(LINK_NEIGHBOR_LEFT, point_24_id));
		point_34.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_33_id));
		point_34.addLink(new Link(LINK_POSITION, null));

		point_33.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_33.addLink(new Link(LINK_NEIGHBOR_TOP, point_34_id));
		point_33.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_32_id));
		point_33.addLink(new Link(LINK_POSITION, null));

		point_32.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_32.addLink(new Link(LINK_NEIGHBOR_TOP, point_33_id));
		point_32.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_42_id));
		point_32.addLink(new Link(LINK_POSITION, null));

		point_42.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_42.addLink(new Link(LINK_NEIGHBOR_LEFT, point_32_id));
		point_42.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_52_id));
		point_42.addLink(new Link(LINK_POSITION, null));

		point_52.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_52.addLink(new Link(LINK_NEIGHBOR_TOP, point_53_id));
		point_52.addLink(new Link(LINK_NEIGHBOR_LEFT, point_42_id));
		point_52.addLink(new Link(LINK_POSITION, null));

		point_53.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_53.addLink(new Link(LINK_NEIGHBOR_TOP, point_54_id));
		point_53.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_52_id));
		point_53.addLink(new Link(LINK_POSITION, null));

		point_54.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_54.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_64_id));
		point_54.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_53_id));
		point_54.addLink(new Link(LINK_POSITION, null));

		point_64.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_64.addLink(new Link(LINK_NEIGHBOR_LEFT, point_54_id));
		point_64.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_74_id));
		point_64.addLink(new Link(LINK_POSITION, null));

		point_74.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_74.addLink(new Link(LINK_NEIGHBOR_LEFT, point_64_id));
		point_74.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_73_id));
		point_74.addLink(new Link(LINK_POSITION, null));

		point_73.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_73.addLink(new Link(LINK_NEIGHBOR_TOP, point_74_id));
		point_73.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_72_id));
		point_73.addLink(new Link(LINK_POSITION, null));

		point_72.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_72.addLink(new Link(LINK_NEIGHBOR_TOP, point_73_id));
		point_72.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_82_id));
		point_72.addLink(new Link(LINK_POSITION, null));

		point_82.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_82.addLink(new Link(LINK_NEIGHBOR_LEFT, point_72_id));
		point_82.addLink(new Link(LINK_NEIGHBOR_RIGHT, point_92_id));
		point_82.addLink(new Link(LINK_POSITION, null));

		point_95.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_95.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_94_id));
		point_95.addLink(new Link(LINK_POSITION, null));

		point_94.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_94.addLink(new Link(LINK_NEIGHBOR_TOP, point_95_id));
		point_94.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_93_id));
		point_94.addLink(new Link(LINK_POSITION, null));

		point_93.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_93.addLink(new Link(LINK_NEIGHBOR_TOP, point_94_id));
		point_93.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_92_id));
		point_93.addLink(new Link(LINK_POSITION, null));

		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_92.addLink(new Link(LINK_NEIGHBOR_TOP, point_93_id));
		point_92.addLink(new Link(LINK_NEIGHBOR_LEFT, point_82_id));
		point_92.addLink(new Link(LINK_NEIGHBOR_BOTTOM, point_91_id));
		point_92.addLink(new Link(LINK_POSITION, null));

		point_91.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_91.addLink(new Link(LINK_NEIGHBOR_TOP, point_92_id));
		point_91.addLink(new Link(LINK_POSITION, null));

		initial_system.addObject(object);
		initial_system.addObject(point_15);
		initial_system.addObject(point_14);
		initial_system.addObject(point_13);
		initial_system.addObject(point_12);
		initial_system.addObject(point_11);
		initial_system.addObject(point_24);
		initial_system.addObject(point_34);
		initial_system.addObject(point_33);
		initial_system.addObject(point_32);
		initial_system.addObject(point_42);
		initial_system.addObject(point_54);
		initial_system.addObject(point_53);
		initial_system.addObject(point_52);
		initial_system.addObject(point_64);
		initial_system.addObject(point_74);
		initial_system.addObject(point_73);
		initial_system.addObject(point_72);
		initial_system.addObject(point_82);
		initial_system.addObject(point_95);
		initial_system.addObject(point_94);
		initial_system.addObject(point_93);
		initial_system.addObject(point_92);
		initial_system.addObject(point_91);

		SystemVariant[] systemVariants;
		systemVariants = moveRight().applyTo(initial_system);
		assertEquals(1, systemVariants.length);

		systemVariants = moveLeft().applyTo(initial_system);
		assertEquals(0, systemVariants.length);

		systemVariants = moveTop().applyTo(initial_system);
		assertEquals(1, systemVariants.length);

		systemVariants = moveBottom().applyTo(initial_system);
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_object = new SystemObject("материальная точка", object_id);
		final SystemObject final_point_92 = new SystemObject("точка-92", point_92_id);
		final_system.addObject(final_object);
		final_system.addObject(final_point_92);

		final_object.addLink(new Link(LINK_POSITION, point_92_id));
		final_point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		final_point_92.addLink(new Link(LINK_POSITION, object_id));

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
