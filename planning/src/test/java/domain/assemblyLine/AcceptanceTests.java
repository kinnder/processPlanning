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
import planning.model.SystemTransformation;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class AcceptanceTests implements AssemblyLine {

	private static SystemTransformations assemblyLineTransformations;

	private static TaskDescription taskDesription;

	private static System initialSystem;

	private static System finalSystem;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException, URISyntaxException {
		SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();
		transformationsXMLFile.load(AcceptanceTests.class.getResource("/assemblyLine/systemTransformations.xml"));

		assemblyLineTransformations = new SystemTransformations();
		assemblyLineTransformations.addElements(transformationsXMLFile.getSystemTransformations());

		TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
		transformationsXMLFile.load(AcceptanceTests.class.getResource("/assemblyLine/taskDescription.xml"));

		taskDesription = taskXMLFile.getTaskDescription();
		// TODO : remove
		initialSystem = GenerateTaskDescription.initialSystem();
		finalSystem = GenerateTaskDescription.finalSystem();
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		System system = initialSystem;

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_ROTARY_DRIVE_POSITION,
				ID_OBJECT_PLANE_Y_OUTSIDE));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Y_OUTSIDE).getLink(LINK_ROTARY_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Y_INSIDE).getLink(LINK_ROTARY_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_CLOSE_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_GRAB_POSITION,
				ID_OBJECT_PACKAGE_BOX));
		assertNotNull(system.getObjectById(ID_OBJECT_PACKAGE_BOX).getLink(LINK_GRAB_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LIFT_UP);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PACKAGE_BOX).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_SHUTTLE).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_VERTICAL_DRIVE_POSITION,
				ID_OBJECT_PLANE_Z_TOP));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Z_TOP).getLink(LINK_VERTICAL_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Z_BOTTOM).getLink(LINK_VERTICAL_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_INSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_ROTARY_DRIVE_POSITION,
				ID_OBJECT_PLANE_Y_INSIDE));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Y_OUTSIDE).getLink(LINK_ROTARY_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Y_INSIDE).getLink(LINK_ROTARY_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_1, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_LINEAR_DRIVE_POSITION,
				ID_OBJECT_PLANE_X_TABLE_1));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_X_TABLE_1).getLink(LINK_LINEAR_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_X_TABLE_2).getLink(LINK_LINEAR_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LOWER_DOWN);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(
				system.getObjectById(ID_OBJECT_PACKAGE_BOX).getLink(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_TABLE_1));
		assertNotNull(
				system.getObjectById(ID_OBJECT_TABLE_1).getLink(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_PACKAGE_BOX));
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_VERTICAL_DRIVE_POSITION,
				ID_OBJECT_PLANE_Z_BOTTOM));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Z_TOP).getLink(LINK_VERTICAL_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_Z_BOTTOM).getLink(LINK_VERTICAL_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_OPEN_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_GRAB_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_PACKAGE_BOX).getLink(LINK_GRAB_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_2, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(ID_OBJECT_PICK_AND_PLACE_ROBOT).getLink(LINK_LINEAR_DRIVE_POSITION,
				ID_OBJECT_PLANE_X_TABLE_2));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_X_TABLE_1).getLink(LINK_LINEAR_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(ID_OBJECT_PLANE_X_TABLE_2).getLink(LINK_LINEAR_DRIVE_POSITION,
				ID_OBJECT_PICK_AND_PLACE_ROBOT));
	}

	@Test
	public void compareInitialAndFinalSystem() throws CloneNotSupportedException {
		SystemVariant[] systemVariants;
		systemVariants = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD).applyTo(initialSystem);
		assertEquals(1, systemVariants.length);

		assertFalse(initialSystem.equals(finalSystem));
		assertFalse(initialSystem.contains(finalSystem));
	}

	@Test
	public void movePackageBoxToTable1() throws CloneNotSupportedException {
		Planner planner = new Planner(initialSystem, finalSystem, assemblyLineTransformations.getElements());
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
