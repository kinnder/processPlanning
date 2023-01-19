package application.domain;

import java.util.UUID;

import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformations;

public class MaterialPoints {

	public static final String DOMAIN_NAME = "materialPoints";

	public static final String ID_OBJECT_OBJECT = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_15 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_14 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_13 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_12 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_11 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_24 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_34 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_33 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_32 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_42 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_54 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_53 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_52 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_64 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_72 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_73 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_74 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_82 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_95 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_94 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_93 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_92 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_POINT_91 = UUID.randomUUID().toString();

	/** Материальная точка */
	public static final String OBJECT_MATERIAL_POINT = "OBJECT-MATERIAL-POINT";

	/** точка-82 **/
	public static final String OBJECT_POINT_82 = "OBJECT-POINT-82";

	/** точка-11 **/
	public static final String OBJECT_POINT_11 = "OBJECT-POINT-11";

	/** точка-12 **/
	public static final String OBJECT_POINT_12 = "OBJECT-POINT-12";

	/** точка-13 **/
	public static final String OBJECT_POINT_13 = "OBJECT-POINT-13";

	/** точка-14 **/
	public static final String OBJECT_POINT_14 = "OBJECT-POINT-14";

	/** точка-15 **/
	public static final String OBJECT_POINT_15 = "OBJECT-POINT-15";

	/** точка-24 **/
	public static final String OBJECT_POINT_24 = "OBJECT-POINT-24";

	/** точка-32 **/
	public static final String OBJECT_POINT_32 = "OBJECT-POINT-32";

	/** точка-33 **/
	public static final String OBJECT_POINT_33 = "OBJECT-POINT-33";

	/** точка-34 **/
	public static final String OBJECT_POINT_34 = "OBJECT-POINT-34";

	/** точка-42 **/
	public static final String OBJECT_POINT_42 = "OBJECT-POINT-42";

	/** точка-52 **/
	public static final String OBJECT_POINT_52 = "OBJECT-POINT-52";

	/** точка-53 **/
	public static final String OBJECT_POINT_53 = "OBJECT-POINT-53";

	/** точка-54 **/
	public static final String OBJECT_POINT_54 = "OBJECT-POINT-54";

	/** точка-64 **/
	public static final String OBJECT_POINT_64 = "OBJECT-POINT-64";

	/** точка-72 **/
	public static final String OBJECT_POINT_72 = "OBJECT-POINT-72";

	/** точка-73 **/
	public static final String OBJECT_POINT_73 = "OBJECT-POINT-73";

	/** точка-74 **/
	public static final String OBJECT_POINT_74 = "OBJECT-POINT-74";

	/** точка-95 **/
	public static final String OBJECT_POINT_95 = "OBJECT-POINT-95";

	/** точка-94 **/
	public static final String OBJECT_POINT_94 = "OBJECT-POINT-94";

	/** точка-93 **/
	public static final String OBJECT_POINT_93 = "OBJECT-POINT-93";

	/** точка-92 **/
	public static final String OBJECT_POINT_92 = "OBJECT-POINT-92";

	/** точка-91 **/
	public static final String OBJECT_POINT_91 = "OBJECT-POINT-91";

	/** Местоположение */
	public static final String LINK_POSITION = "LINK-POSITION";

	/** Сосед справа */
	public static final String LINK_NEIGHBOR_RIGHT = "LINK-NEIGHBOR-RIGHT";

	/** Сосед слева */
	public static final String LINK_NEIGHBOR_LEFT = "LINK-NEIGHBOR-LEFT";

	/** Сосед сверху */
	public static final String LINK_NEIGHBOR_TOP = "LINK-NEIGHBOR-TOP";

	/** Сосед снизу */
	public static final String LINK_NEIGHBOR_BOTTOM = "LINK-NEIGHBOR-BOTTOM";

	/** Занята */
	public static final String ATTRIBUTE_OCCUPIED = "ATTRIBUTE-OCCUPIED";

	/** Движение вправо */
	public static final String OPERATION_MOVE_RIGHT = "OPERATION-MOVE-RIGHT";

	/** Движение влево */
	public static final String OPERATION_MOVE_LEFT = "OPERATION-MOVE-LEFT";

	/** Движение вниз */
	public static final String OPERATION_MOVE_BOTTOM = "OPERATION-MOVE-BOTTOM";

	/** Движение вверх */
	public static final String OPERATION_MOVE_TOP = "OPERATION-MOVE-TOP";

	public static final String ELEMENT_MOVE_RIGHT = "ELEMENT-MOVE-RIGHT";

	public static final String ELEMENT_MOVE_LEFT = "ELEMENT-MOVE-LEFT";

	public static final String ELEMENT_MOVE_TOP = "ELEMENT-MOVE-TOP";

	public static final String ELEMENT_MOVE_BOTTOM = "ELEMENT-MOVE-BOTTOM";

	public static final String ID_TEMPLATE_OBJECT = "ID-OBJECT";

	public static final String ID_TEMPLATE_POINT_A = "ID-POINT-A";

	public static final String ID_TEMPLATE_POINT_B = "ID-POINT-B";

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

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true));

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

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_B, ID_TEMPLATE_POINT_A));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, null, ID_TEMPLATE_OBJECT));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, ID_TEMPLATE_OBJECT, null));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, true));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, false));

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

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true));

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

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_OBJECT, LINK_POSITION, ID_TEMPLATE_POINT_A, ID_TEMPLATE_POINT_B));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_A, LINK_POSITION, ID_TEMPLATE_OBJECT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_POINT_B, LINK_POSITION, null, ID_TEMPLATE_OBJECT));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_A, ATTRIBUTE_OCCUPIED, false));
		transformations.add(new AttributeTransformation(ID_TEMPLATE_POINT_B, ATTRIBUTE_OCCUPIED, true));

		final Action action = new Action(OPERATION_MOVE_BOTTOM);

		return new SystemTransformation(ELEMENT_MOVE_BOTTOM, action, systemTemplate, transformations);
	}

	public static SystemTransformations getSystemTransformations() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(moveRight());
		systemTransformations.add(moveLeft());
		systemTransformations.add(moveTop());
		systemTransformations.add(moveBottom());
		return systemTransformations;
	}

	public static System initialSystem() {
		// y
		// 6 + - - - - - - - - - +
		// 5 | . - - + - - - + . |
		// 4 | A . . | . . . | . |
		// 3 | . | . | . | . | . |
		// 2 | . | . . . | . . B |
		// 1 | . + - - - + - - . |
		// 0 + - - - - - - - - - +
		// 0 0 1 2 3 4 5 6 7 8 9 A x

		final System system = new System();

		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		system.addObject(object);

		final SystemObject point_15 = new SystemObject(OBJECT_POINT_15, ID_OBJECT_POINT_15);
		point_15.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_15);

		final SystemObject point_14 = new SystemObject(OBJECT_POINT_14, ID_OBJECT_POINT_14);
		point_14.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		system.addObject(point_14);

		final SystemObject point_13 = new SystemObject(OBJECT_POINT_13, ID_OBJECT_POINT_13);
		point_13.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_13);

		final SystemObject point_12 = new SystemObject(OBJECT_POINT_12, ID_OBJECT_POINT_12);
		point_12.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_12);

		final SystemObject point_11 = new SystemObject(OBJECT_POINT_11, ID_OBJECT_POINT_11);
		point_11.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_11);

		final SystemObject point_24 = new SystemObject(OBJECT_POINT_24, ID_OBJECT_POINT_24);
		point_24.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_24);

		final SystemObject point_34 = new SystemObject(OBJECT_POINT_34, ID_OBJECT_POINT_34);
		point_34.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_34);

		final SystemObject point_33 = new SystemObject(OBJECT_POINT_33, ID_OBJECT_POINT_33);
		point_33.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_33);

		final SystemObject point_32 = new SystemObject(OBJECT_POINT_32, ID_OBJECT_POINT_32);
		point_32.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_32);

		final SystemObject point_42 = new SystemObject(OBJECT_POINT_42, ID_OBJECT_POINT_42);
		point_42.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_42);

		final SystemObject point_54 = new SystemObject(OBJECT_POINT_54, ID_OBJECT_POINT_54);
		point_54.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_54);

		final SystemObject point_53 = new SystemObject(OBJECT_POINT_53, ID_OBJECT_POINT_53);
		point_53.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_53);

		final SystemObject point_52 = new SystemObject(OBJECT_POINT_52, ID_OBJECT_POINT_52);
		point_52.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_52);

		final SystemObject point_64 = new SystemObject(OBJECT_POINT_64, ID_OBJECT_POINT_64);
		point_64.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_64);

		final SystemObject point_74 = new SystemObject(OBJECT_POINT_74, ID_OBJECT_POINT_74);
		point_74.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_74);

		final SystemObject point_73 = new SystemObject(OBJECT_POINT_73, ID_OBJECT_POINT_73);
		point_73.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_73);

		final SystemObject point_72 = new SystemObject(OBJECT_POINT_72, ID_OBJECT_POINT_72);
		point_72.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_72);

		final SystemObject point_82 = new SystemObject(OBJECT_POINT_82, ID_OBJECT_POINT_82);
		point_82.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_82);

		final SystemObject point_95 = new SystemObject(OBJECT_POINT_95, ID_OBJECT_POINT_95);
		point_95.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_95);

		final SystemObject point_94 = new SystemObject(OBJECT_POINT_94, ID_OBJECT_POINT_94);
		point_94.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_94);

		final SystemObject point_93 = new SystemObject(OBJECT_POINT_93, ID_OBJECT_POINT_93);
		point_93.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_93);

		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_92);

		final SystemObject point_91 = new SystemObject(OBJECT_POINT_91, ID_OBJECT_POINT_91);
		point_91.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_91);

		system.addLink(object, LINK_POSITION, point_14);

		system.addLink(point_15, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_14);
		system.addLink(point_15, LINK_POSITION, null);

		system.addLink(point_14, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_24);
		system.addLink(point_14, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_13);

		system.addLink(point_13, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_12);
		system.addLink(point_13, LINK_POSITION, null);

		system.addLink(point_12, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_11);
		system.addLink(point_12, LINK_POSITION, null);

		system.addLink(point_11, LINK_POSITION, null);

		system.addLink(point_24, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_34);
		system.addLink(point_24, LINK_POSITION, null);

		system.addLink(point_34, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_33);
		system.addLink(point_34, LINK_POSITION, null);

		system.addLink(point_33, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_32);
		system.addLink(point_33, LINK_POSITION, null);

		system.addLink(point_32, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_42);
		system.addLink(point_32, LINK_POSITION, null);

		system.addLink(point_42, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_52);
		system.addLink(point_42, LINK_POSITION, null);

		system.addLink(point_52, LINK_NEIGHBOR_TOP, LINK_NEIGHBOR_BOTTOM, point_53);
		system.addLink(point_52, LINK_POSITION, null);

		system.addLink(point_53, LINK_NEIGHBOR_TOP, LINK_NEIGHBOR_BOTTOM, point_54);
		system.addLink(point_53, LINK_POSITION, null);

		system.addLink(point_54, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_64);
		system.addLink(point_54, LINK_POSITION, null);

		system.addLink(point_64, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_74);
		system.addLink(point_64, LINK_POSITION, null);

		system.addLink(point_74, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_73);
		system.addLink(point_74, LINK_POSITION, null);

		system.addLink(point_73, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_72);
		system.addLink(point_73, LINK_POSITION, null);

		system.addLink(point_72, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_82);
		system.addLink(point_72, LINK_POSITION, null);

		system.addLink(point_82, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_92);
		system.addLink(point_82, LINK_POSITION, null);

		system.addLink(point_95, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_94);
		system.addLink(point_95, LINK_POSITION, null);

		system.addLink(point_94, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_93);
		system.addLink(point_94, LINK_POSITION, null);

		system.addLink(point_93, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_92);
		system.addLink(point_93, LINK_POSITION, null);

		system.addLink(point_92, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_91);
		system.addLink(point_92, LINK_POSITION, null);

		system.addLink(point_91, LINK_POSITION, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		system.addObject(object);

		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		system.addObject(point_92);

		system.addLink(object, LINK_POSITION, point_92);

		return system;
	}

	public static TaskDescription getTaskDescription() {
		final TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());
		return taskDescription;
	}
}
