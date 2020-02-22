package domain.assemblyLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemTransformation;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemProcess;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class AcceptanceTests implements AssemblyLine {

	private static SystemTransformations assemblyLineTransformations;

	private static TaskDescription taskDescription;

	private static System initialSystem;

	private static System finalSystem;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException, URISyntaxException {
		SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();
		transformationsXMLFile.load(AcceptanceTests.class.getResource("/assemblyLine/systemTransformations.xml"));

		assemblyLineTransformations = new SystemTransformations();
		assemblyLineTransformations.addAll(transformationsXMLFile.getSystemTransformations());

		TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
		taskXMLFile.load(AcceptanceTests.class.getResource("/assemblyLine/taskDescription.xml"));

		taskDescription = taskXMLFile.getTaskDescription();
		initialSystem = taskDescription.getInitialSystem();
		finalSystem = taskDescription.getFinalSystem();
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		System system = initialSystem;

		final String robot_id = system.getObjectByName(OBJECT_PICK_AND_PLACE_ROBOT).getId();
		final String package_box_id = system.getObjectByName(OBJECT_PACKAGE_BOX).getId();
		final String shuttle_id = system.getObjectByName(OBJECT_SHUTTLE).getId();
		final String table_1_id = system.getObjectByName(OBJECT_TABLE_1).getId();
		final String plane_y_outside_id = system.getObjectByName(OBJECT_PLANE_Y_OUTSIDE).getId();
		final String plane_y_inside_id = system.getObjectByName(OBJECT_PLANE_Y_INSIDE).getId();
		final String plane_z_top_id = system.getObjectByName(OBJECT_PLANE_Z_TOP).getId();
		final String plane_z_bottom_id = system.getObjectByName(OBJECT_PLANE_Z_BOTTOM).getId();
		final String plane_x_table_1 = system.getObjectByName(OBJECT_PLANE_X_TABLE_1).getId();
		final String plane_x_table_2 = system.getObjectByName(OBJECT_PLANE_X_TABLE_2).getId();

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		// 0
		systemTransformation = assemblyLineTransformations.get(ELEMENT_TURN_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_outside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));

		// 1
		systemTransformation = assemblyLineTransformations.get(ELEMENT_CLOSE_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, package_box_id));
		assertNotNull(system.getObjectById(package_box_id).getLink(LINK_GRAB_POSITION, robot_id));

		// 2
		systemTransformation = assemblyLineTransformations.get(ELEMENT_LIFT_UP);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(package_box_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));

		// 3
		systemTransformation = assemblyLineTransformations.get(ELEMENT_TURN_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_INSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));

		// 4
		systemTransformation = assemblyLineTransformations.get(ELEMENT_MOVE_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_1, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_1));
		assertNotNull(system.getObjectById(plane_x_table_1).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_x_table_2).getLink(LINK_LINEAR_DRIVE_POSITION, null));

		// 5
		systemTransformation = assemblyLineTransformations.get(ELEMENT_LOWER_DOWN);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(package_box_id).getLink(LINK_PACKAGE_BOX_POSITION, table_1_id));
		assertNotNull(system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION, package_box_id));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		// 6
		systemTransformation = assemblyLineTransformations.get(ELEMENT_OPEN_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, null));
		assertNotNull(system.getObjectById(package_box_id).getLink(LINK_GRAB_POSITION, null));

		// 7
		systemTransformation = assemblyLineTransformations.get(ELEMENT_MOVE_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_2, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2));
		assertNotNull(system.getObjectById(plane_x_table_1).getLink(LINK_LINEAR_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_x_table_2).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
	}

	@Test
	public void compareInitialAndFinalSystem() throws CloneNotSupportedException {
		SystemVariant[] systemVariants;
		systemVariants = assemblyLineTransformations.get(ELEMENT_TURN_WITHOUT_LOAD).applyTo(initialSystem);
		assertEquals(1, systemVariants.length);

		assertFalse(initialSystem.equals(finalSystem));
		assertFalse(initialSystem.contains(finalSystem));
	}

	@Test
	public void movePackageBoxToTable1() throws CloneNotSupportedException {
		Planner planner = new Planner(taskDescription, assemblyLineTransformations);
		planner.plan();

		SystemProcess operations = planner.getShortestProcess();
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
