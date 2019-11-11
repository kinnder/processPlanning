package domain.assemblyLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.SystemTransformation;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemOperation;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;

public class AcceptanceTests implements AssemblyLine {

	private static SystemTransformations assemblyLineTransformations;

	private static TaskDescription taskDesription;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException, URISyntaxException {
		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.load(AcceptanceTests.class.getResource("/assemblyLine/systemTransformations.xml"));

		assemblyLineTransformations = new SystemTransformations();
		assemblyLineTransformations.addElements(xmlFile.getSystemTransformations());

		taskDesription = new TaskDescription();
		// TODO : добавить считывание из файла
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
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

		final String robot_id = robot.getId();
		final String plane_x_table_1_id = plane_x_table_1.getId();
		final String plane_x_table_2_id = plane_x_table_2.getId();
		final String plane_y_outside_id = plane_y_outside.getId();
		final String plane_y_inside_id = plane_y_inside.getId();
		final String plane_z_top_id = plane_z_top.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String shuttle_id = shuttle.getId();
		final String table_1_id = table_1.getId();
		final String table_2_id = table_2.getId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, null));
		plane_x_table_1.addLink(new Link(LINK_PLANE_X_POSITION, table_1_id));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, robot_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, table_2_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, shuttle_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, robot_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_1_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_2_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));
		plane_y_outside.addLink(new Link(LINK_PLANE_Y_POSITION, shuttle_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_1_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_2_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, shuttle_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		// TODO : заменить на taskDescription

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_outside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_CLOSE_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LIFT_UP);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_INSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_1, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_1_id));
		assertNotNull(system.getObjectById(plane_x_table_1_id).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_x_table_2_id).getLink(LINK_LINEAR_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LOWER_DOWN);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, table_1_id));
		assertNotNull(system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_OPEN_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, null));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_2, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		assertNotNull(system.getObjectById(plane_x_table_1_id).getLink(LINK_LINEAR_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_x_table_2_id).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
	}

	@Test
	public void movePackageBoxToTable1() throws CloneNotSupportedException {
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

		final String robot_id = robot.getId();
		final String plane_x_table_1_id = plane_x_table_1.getId();
		final String plane_x_table_2_id = plane_x_table_2.getId();
		final String plane_y_outside_id = plane_y_outside.getId();
		final String plane_y_inside_id = plane_y_inside.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String shuttle_id = shuttle.getId();
		final String table_1_id = table_1.getId();
		final String table_2_id = table_2.getId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, null));
		plane_x_table_1.addLink(new Link(LINK_PLANE_X_POSITION, table_1_id));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, robot_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, table_2_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, shuttle_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, robot_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_1_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_2_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));
		plane_y_outside.addLink(new Link(LINK_PLANE_Y_POSITION, shuttle_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_1_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_2_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, shuttle_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		SystemVariant[] systemVariants;
		systemVariants = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD).applyTo(system);
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_packageBox = new SystemObject(OBJECT_PACKAGE_BOX, packageBox_id);
		final_packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		final_packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		final_packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, table_1_id));
		final SystemObject final_robot = robot.clone();
		final SystemObject final_table_1 = new SystemObject(OBJECT_TABLE_1, table_1_id);
		final_table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		final_table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		final_table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		final_table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		final_table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		final_system.addObject(final_packageBox);
		final_system.addObject(final_robot);
		final_system.addObject(final_table_1);

		assertFalse(system.equals(final_system));
		assertFalse(system.contains(final_system));

		Planner planner = new Planner(system, final_system, assemblyLineTransformations.getElements());
		planner.plan();

		List<SystemOperation> operations = planner.getShortestPlan();
		assertEquals(8, operations.size());

		SystemOperation operation;
		operation = operations.get(0);
		assertEquals(OPERATION_TURN_WITHOUT_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(1);
		assertEquals(OPERATION_CLOSE_GRAB, operation.getName());

		operation = operations.get(2);
		assertEquals(OPERATION_LIFT_UP, operation.getName());

		operation = operations.get(3);
		assertEquals(OPERATION_TURN_WITH_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_Y_INSIDE, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(4);
		assertEquals(OPERATION_MOVE_WITH_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_X_TABLE_1, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(5);
		assertEquals(OPERATION_LOWER_DOWN, operation.getName());

		operation = operations.get(6);
		assertEquals(OPERATION_OPEN_GRAB, operation.getName());

		operation = operations.get(7);
		assertEquals(OPERATION_MOVE_WITHOUT_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_X_TABLE_2, operation.getParameter(PARAMETER_TARGET));
	}
}
