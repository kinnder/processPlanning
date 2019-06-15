package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class AssemblyLine {

	private static final String OBJECT_PICK_AND_PLACE_ROBOT = "Робот-перекладчик";

	private static final String OBJECT_PACKAGE_BOX = "Тара";

	private static final String OBJECT_SHUTTLE = "Шаттл";

	private static final String OBJECT_TABLE_1 = "Стол 1";

	private static final String OBJECT_TABLE_2 = "Стол 2";

	private static final String OBJECT_PACKAGE_PLACE = "Место для тары";

	private static final String OBJECT_PLANE_Y = "плоскость Y";

	private static final String OBJECT_PLANE_X = "плоскость X";

	private static final String OBJECT_PLANE_Z = "плоскость Z";

	private static final String OBJECT_PLANE_X_TABLE_1 = "плоскость X стол 1";

	private static final String OBJECT_PLANE_X_TABLE_2 = "плоскость X стол 2";

	private static final String OBJECT_PLANE_Y_OUTSIDE = "плоскость Y снаружи станции";

	private static final String OBJECT_PLANE_Y_INSIDE = "плоскоть Y внутри станции";

	private static final String OBJECT_PLANE_Z_TOP = "плоскость Z вверх";

	private static final String OBJECT_PLANE_Z_BOTTOM = "плоскость Z низ";

	private static final String ATTRIBUTE_PICK_AND_PLACE_ROBOT = "Робот-перекладчик";

	private static final String ATTRIBUTE_PACKAGE = "Тара";

	private static final String ATTRIBUTE_PACKAGE_BOX_POSITION = "Место тары";

	private static final String ATTRIBUTE_PLANE_X = "Плоскость X";

	private static final String ATTRIBUTE_PLANE_Y = "Плоскость Y";

	private static final String ATTRIBUTE_PLANE_Z = "Плоскость Z";

	// TODO : заменить на связь между столом и плоскостью
	private static final String ATTRIBUTE_HACK_TABLE_1 = "стол 1";

	private static final String ATTRIBUTE_HACK_TABLE_2 = "стол 2";

	private static final String ATTRIBUTE_HACK_SHUTTLE = "шаттл";

	// TODO : заменить на вычисляемый параметр операции

	private static final String ATTRIBUTE_HACK_PLANE_Y_OUTSIDE = "плоскоть Y снаружи станции";

	private static final String ATTRIBUTE_HACK_PLANE_Y_INSIDE = "плоскость Y внутри станции";

	private static final String ATTRIBUTE_HACK_PLANE_X_TABLE_1 = "плоскость X стол 1";

	private static final String ATTRIBUTE_HACK_PLANE_X_TABLE_2 = "плоскость X стол 2";

	// TODO : template сравнение связей на NOT-NULL или ANY-VALUE
	// TODO : поддержка массивов связей с одинаковым именем
	private static final String LINK_GRAB_POSITION = "положение захвата";

	private static final String LINK_RD_POSITION = "положение поворотного привода";

	private static final String LINK_LD_POSITION = "положение линейного привода";

	private static final String LINK_VD_POSITION = "положение вертикального привода";

	private static final String LINK_PACKAGE_BOX_POSITION = "место тары";

	private static final String LINK_PLANE_X_POSITION = "положение в плоскости X";

	private static final String LINK_PLANE_Y_POSITION = "положение в плоскости Y";

	private static final String LINK_PLANE_Z_POSITION = "положение в плоскости Z";

	private static final String OPERATION_ROTATE_TO_TRANSPORT_LINE = "Повернуть к транспортной линии";

	private static final String OPERATION_ROTATE_TO_STATION = "Повернуть к станции";

	private static final String OPERATION_OPEN_GRAB = "Открыть захват";

	private static final String OPERATION_CLOSE_GRAB = "Закрыть захват";

	private static final String OPERATION_LIFT_UP = "Поднять вверх";

	private static final String OPERATION_LOWER_DOWN = "Опустить вниз";

	private static final String OPERATION_MOVE_TO_POSITION_1 = "Переместить к позиции 1";

	private static final String OPERATION_MOVE_TO_POSITION_2 = "Переместить к позиции 2";

	public static Element rotateToTransportLine() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE, "#PLANE-Y-OUTSIDE");
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, "#PLANE-Y-INSIDE");

		final String robot_id = robot.getObjectId();
		final String plane_y_outside_id = plane_y_outside.getObjectId();
		final String plane_y_inside_id = plane_y_inside.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_INSIDE, true));
		plane_y_inside.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_OUTSIDE, true));
		plane_y_outside.addLink(new Link(LINK_RD_POSITION, null));

		final System template = new System();
		template.addObject(robot);
		template.addObject(plane_y_outside);
		template.addObject(plane_y_inside);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_RD_POSITION, plane_y_outside_id),
				new LinkTransformation(plane_y_outside_id, LINK_RD_POSITION, robot_id),
				new LinkTransformation(plane_y_inside_id, LINK_RD_POSITION, null) };

		return new Element(OPERATION_ROTATE_TO_TRANSPORT_LINE, template, transformations);
	}

	public static Element rotateToStation() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE, "#PLANE-Y-OUTSIDE");
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, "#PLANE-Y-INSIDE");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");

		final String robot_id = robot.getObjectId();
		final String plane_y_outside_id = plane_y_outside.getObjectId();
		final String plane_y_inside_id = plane_y_inside.getObjectId();
		final String packageBox_id = packageBox.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_outside_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_INSIDE, true));
		plane_y_inside.addLink(new Link(LINK_RD_POSITION, null));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_OUTSIDE, true));
		plane_y_outside.addLink(new Link(LINK_RD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, robot_id));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		final System template = new System();
		template.addObject(robot);
		template.addObject(plane_y_outside);
		template.addObject(plane_y_inside);
		template.addObject(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_RD_POSITION, plane_y_inside_id),
				new LinkTransformation(plane_y_outside_id, LINK_RD_POSITION, null),
				new LinkTransformation(plane_y_inside_id, LINK_RD_POSITION, robot_id) };

		return new Element(OPERATION_ROTATE_TO_STATION, template, transformations);
	}

	public static Element openGrab() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_y = new SystemObject(OBJECT_PLANE_Y, "#PLANE-Y");
		final SystemObject plane_x = new SystemObject(OBJECT_PLANE_X, "#PLANE-X");
		final SystemObject plane_z = new SystemObject(OBJECT_PLANE_Z, "#PLANE-Z");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");
		final SystemObject packageBoxPosition = new SystemObject(OBJECT_PACKAGE_PLACE, "#PACKAGE-PLACE");

		template.addObject(robot);
		template.addObject(plane_y);
		template.addObject(plane_x);
		template.addObject(plane_z);
		template.addObject(packageBox);
		template.addObject(packageBoxPosition);

		final String robot_id = robot.getObjectId();
		final String plane_y_id = plane_y.getObjectId();
		final String plane_x_id = plane_x.getObjectId();
		final String plane_z_id = plane_z.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String packageBoxPosition_id = packageBoxPosition.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_id));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_id));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_id));

		plane_x.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_y.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_z.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLink(new Link(LINK_VD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, robot_id));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_id));
		packageBoxPosition.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_GRAB_POSITION, null),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, null) };

		return new Element(OPERATION_OPEN_GRAB, template, transformations);
	}

	public static Element closeGrab() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_y = new SystemObject(OBJECT_PLANE_Y, "#PLANE-Y");
		final SystemObject plane_x = new SystemObject(OBJECT_PLANE_X, "#PLANE-X");
		final SystemObject plane_z = new SystemObject(OBJECT_PLANE_Z, "#PLANE-Z");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");
		final SystemObject packageBoxPosition = new SystemObject(OBJECT_PACKAGE_PLACE, "#PACKAGE-PLACE");

		template.addObject(robot);
		template.addObject(plane_y);
		template.addObject(plane_x);
		template.addObject(plane_z);
		template.addObject(packageBox);
		template.addObject(packageBoxPosition);

		final String robot_id = robot.getObjectId();
		final String plane_y_id = plane_y.getObjectId();
		final String plane_x_id = plane_x.getObjectId();
		final String plane_z_id = plane_z.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String packageBoxPosition_id = packageBoxPosition.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_id));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_id));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_id));

		plane_x.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_y.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_z.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLink(new Link(LINK_VD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_id));
		packageBoxPosition.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_GRAB_POSITION, packageBox_id),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, robot_id) };

		return new Element(OPERATION_CLOSE_GRAB, template, transformations);
	}

	public static Element liftUp() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP, "#PLANE-Z-TOP");
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, "#PLANE-Z-BOTTOM");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");
		final SystemObject packageBoxPosition = new SystemObject(OBJECT_PACKAGE_PLACE, "#PACKAGE-PLACE");

		template.addObject(robot);
		template.addObject(plane_z_top);
		template.addObject(plane_z_bottom);
		template.addObject(packageBox);
		template.addObject(packageBoxPosition);

		final String robot_id = robot.getObjectId();
		final String plane_z_top_id = plane_z_top.getObjectId();
		final String plane_z_bottom_id = plane_z_bottom.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String packageBoxPosition_id = packageBoxPosition.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VD_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, robot_id));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, null),
				new LinkTransformation(packageBoxPosition_id, LINK_PACKAGE_BOX_POSITION, null),
				new LinkTransformation(robot_id, LINK_VD_POSITION, plane_z_top_id),
				new LinkTransformation(plane_z_top_id, LINK_VD_POSITION, robot_id),
				new LinkTransformation(plane_z_bottom_id, LINK_VD_POSITION, null) };

		return new Element(OPERATION_LIFT_UP, template, transformations);
	}

	public static Element lowerDown() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_y = new SystemObject(OBJECT_PLANE_Y, "#PLANE-Y");
		final SystemObject plane_x = new SystemObject(OBJECT_PLANE_X, "#PLANE-X");
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP, "#PLANE-Z-TOP");
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, "#PLANE-Z-BOTTOM");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");
		final SystemObject packageBoxPosition = new SystemObject(OBJECT_PACKAGE_PLACE, "#PACKAGE-PLACE");

		template.addObject(robot);
		template.addObject(plane_x);
		template.addObject(plane_y);
		template.addObject(plane_z_top);
		template.addObject(plane_z_bottom);
		template.addObject(packageBox);
		template.addObject(packageBoxPosition);

		final String robot_id = robot.getObjectId();
		final String plane_y_id = plane_y.getObjectId();
		final String plane_x_id = plane_x.getObjectId();
		final String plane_z_top_id = plane_z_top.getObjectId();
		final String plane_z_bottom_id = plane_z_bottom.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String packageBoxPosition_id = packageBoxPosition.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_id));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_top_id));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		plane_x.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_y.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VD_POSITION, robot_id));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VD_POSITION, null));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, robot_id));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		packageBoxPosition.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_1, true));
		packageBoxPosition.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id),
				new LinkTransformation(packageBoxPosition_id, LINK_PACKAGE_BOX_POSITION, packageBox_id),
				new LinkTransformation(robot_id, LINK_VD_POSITION, plane_z_bottom_id),
				new LinkTransformation(plane_z_top_id, LINK_VD_POSITION, null),
				new LinkTransformation(plane_z_bottom_id, LINK_VD_POSITION, robot_id) };

		return new Element(OPERATION_LOWER_DOWN, template, transformations);
	}

	public static Element moveToPosition1() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, "#PLANE-X-TABLE-1");
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, "#PLANE-X-TABLE-2");

		final String robot_id = robot.getObjectId();
		final String plane_x_table_1_id = plane_x_table_1.getObjectId();
		final String plane_x_table_2_id = plane_x_table_2.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_table_2_id));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_1, true));
		plane_x_table_1.addLink(new Link(LINK_LD_POSITION, null));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_2, true));
		plane_x_table_2.addLink(new Link(LINK_LD_POSITION, robot_id));

		final System template = new System();
		template.addObject(robot);
		template.addObject(plane_x_table_1);
		template.addObject(plane_x_table_2);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_LD_POSITION, plane_x_table_1_id),
				new LinkTransformation(plane_x_table_1_id, LINK_LD_POSITION, robot_id),
				new LinkTransformation(plane_x_table_2_id, LINK_LD_POSITION, null) };

		return new Element(OPERATION_MOVE_TO_POSITION_1, template, transformations);
	}

	public static Element moveToPosition2() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, "#PLANE-X-TABLE-1");
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, "#PLANE-X-TABLE-2");

		final String robot_id = robot.getObjectId();
		final String plane_x_table_1_id = plane_x_table_1.getObjectId();
		final String plane_x_table_2_id = plane_x_table_2.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_table_1_id));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_1, true));
		plane_x_table_1.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_2, true));
		plane_x_table_2.addLink(new Link(LINK_LD_POSITION, null));

		final System template = new System();
		template.addObject(robot);
		template.addObject(plane_x_table_1);
		template.addObject(plane_x_table_2);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_LD_POSITION, plane_x_table_2_id),
				new LinkTransformation(plane_x_table_1_id, LINK_LD_POSITION, null),
				new LinkTransformation(plane_x_table_2_id, LINK_LD_POSITION, robot_id) };

		return new Element(OPERATION_MOVE_TO_POSITION_2, template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1);
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2);
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE);
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE);
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP);
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2);

		System system = new System();
		system.addObject(robot);
		system.addObject(plane_x_table_1);
		system.addObject(plane_x_table_2);
		system.addObject(plane_y_outside);
		system.addObject(plane_y_inside);
		system.addObject(plane_z_top);
		system.addObject(plane_z_bottom);
		system.addObject(packageBox);
		system.addObject(shuttle);
		system.addObject(table_1);
		system.addObject(table_2);

		final String robot_id = robot.getObjectId();
		final String plane_x_table_1_id = plane_x_table_1.getObjectId();
		final String plane_x_table_2_id = plane_x_table_2.getObjectId();
		final String plane_y_outside_id = plane_y_outside.getObjectId();
		final String plane_y_inside_id = plane_y_inside.getObjectId();
		final String plane_z_top_id = plane_z_top.getObjectId();
		final String plane_z_bottom_id = plane_z_bottom.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String shuttle_id = shuttle.getObjectId();
		final String table_1_id = table_1.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_1, true));
		plane_x_table_1.addLink(new Link(LINK_LD_POSITION, null));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_2, true));
		plane_x_table_2.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_INSIDE, true));
		plane_y_inside.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_OUTSIDE, true));
		plane_y_outside.addLink(new Link(LINK_RD_POSITION, null));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VD_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addAttribute(new Attribute(ATTRIBUTE_HACK_SHUTTLE, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_1, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_2, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		Element element;
		SystemVariant[] systemVariants;

		element = rotateToTransportLine();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(plane_y_outside_id, system.getObjectById(robot_id).getLink(LINK_RD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_y_outside_id).getLink(LINK_RD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_y_inside_id).getLink(LINK_RD_POSITION).getObjectId());

		element = closeGrab();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(packageBox_id, system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION).getObjectId());

		element = liftUp();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(null, system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION).getObjectId());
		assertEquals(plane_z_top_id, system.getObjectById(robot_id).getLink(LINK_VD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_z_top_id).getLink(LINK_VD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_z_bottom_id).getLink(LINK_VD_POSITION).getObjectId());

		element = rotateToStation();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(plane_y_inside_id, system.getObjectById(robot_id).getLink(LINK_RD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_y_outside_id).getLink(LINK_RD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_y_inside_id).getLink(LINK_RD_POSITION).getObjectId());

		element = moveToPosition1();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(plane_x_table_1_id, system.getObjectById(robot_id).getLink(LINK_LD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_x_table_1_id).getLink(LINK_LD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_x_table_2_id).getLink(LINK_LD_POSITION).getObjectId());

		element = lowerDown();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(table_1_id, system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION).getObjectId());
		assertEquals(packageBox_id, system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION).getObjectId());
		assertEquals(plane_z_bottom_id, system.getObjectById(robot_id).getLink(LINK_VD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_z_top_id).getLink(LINK_VD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_z_bottom_id).getLink(LINK_VD_POSITION).getObjectId());

		element = openGrab();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(null, system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION).getObjectId());

		element = moveToPosition2();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(plane_x_table_2_id, system.getObjectById(robot_id).getLink(LINK_LD_POSITION).getObjectId());
		assertEquals(null, system.getObjectById(plane_x_table_1_id).getLink(LINK_LD_POSITION).getObjectId());
		assertEquals(robot_id, system.getObjectById(plane_x_table_2_id).getLink(LINK_LD_POSITION).getObjectId());
	}

	@Test
	public void movePackageBoxToTable1() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1);
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2);
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE);
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE);
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP);
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2);

		System system = new System();
		system.addObject(robot);
		system.addObject(plane_x_table_1);
		system.addObject(plane_x_table_2);
		system.addObject(plane_y_outside);
		system.addObject(plane_y_inside);
		system.addObject(plane_z_top);
		system.addObject(plane_z_bottom);
		system.addObject(packageBox);
		system.addObject(shuttle);
		system.addObject(table_1);
		system.addObject(table_2);

		final String robot_id = robot.getObjectId();
		final String plane_x_table_1_id = plane_x_table_1.getObjectId();
		final String plane_x_table_2_id = plane_x_table_2.getObjectId();
		final String plane_y_outside_id = plane_y_outside.getObjectId();
		final String plane_y_inside_id = plane_y_inside.getObjectId();
		final String plane_z_bottom_id = plane_z_bottom.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String shuttle_id = shuttle.getObjectId();
		final String table_1_id = table_1.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LD_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_RD_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VD_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_1, true));
		plane_x_table_1.addLink(new Link(LINK_LD_POSITION, null));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_X_TABLE_2, true));
		plane_x_table_2.addLink(new Link(LINK_LD_POSITION, robot_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_INSIDE, true));
		plane_y_inside.addLink(new Link(LINK_RD_POSITION, robot_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_HACK_PLANE_Y_OUTSIDE, true));
		plane_y_outside.addLink(new Link(LINK_RD_POSITION, null));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VD_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VD_POSITION, robot_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addAttribute(new Attribute(ATTRIBUTE_HACK_SHUTTLE, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_1, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_2, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		SystemVariant[] systemVariants;
		systemVariants = rotateToTransportLine().applyTo(system);
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_packageBox = new SystemObject(OBJECT_PACKAGE_BOX, packageBox_id);
		final_packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		final_packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		final_packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, table_1_id));
		final SystemObject final_robot = robot.clone();
		final SystemObject final_table_1 = new SystemObject(OBJECT_TABLE_1, table_1_id);
		final_table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		final_table_1.addAttribute(new Attribute(ATTRIBUTE_HACK_TABLE_1, true));
		final_table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		final_table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		final_table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		final_table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		final_system.addObject(final_packageBox);
		final_system.addObject(final_robot);
		final_system.addObject(final_table_1);

		assertFalse(system.equals(final_system));
		assertFalse(system.partially_equals(final_system));

		final Element[] elements = new Element[] { rotateToTransportLine(), rotateToStation(), openGrab(), closeGrab(),
				liftUp(), lowerDown(), moveToPosition1(), moveToPosition2() };

		Planner planner = new Planner(system, final_system, elements);
		planner.plan();

		List<String> operations = planner.getShortestPlan();
		assertEquals(8, operations.size());
		assertEquals(OPERATION_ROTATE_TO_TRANSPORT_LINE, operations.get(0));
		assertEquals(OPERATION_CLOSE_GRAB, operations.get(1));
		assertEquals(OPERATION_LIFT_UP, operations.get(2));
		assertEquals(OPERATION_ROTATE_TO_STATION, operations.get(3));
		assertEquals(OPERATION_MOVE_TO_POSITION_1, operations.get(4));
		assertEquals(OPERATION_LOWER_DOWN, operations.get(5));
		assertEquals(OPERATION_OPEN_GRAB, operations.get(6));
		assertEquals(OPERATION_MOVE_TO_POSITION_2, operations.get(7));
	}
}
