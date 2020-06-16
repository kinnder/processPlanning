package domain.materialPoints;

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
import planning.model.System;
import planning.model.SystemProcess;
import planning.model.SystemTransformation;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class AcceptanceTests implements MaterialPoints {

	private static SystemTransformations materialPointsTransformations;

	private static TaskDescription taskDescription;

	private static System initialSystem;

	private static System finalSystem;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException, URISyntaxException {
		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.load(AcceptanceTests.class.getResource("/materialPoints/systemTransformations.xml"));

		materialPointsTransformations = new SystemTransformations();
		materialPointsTransformations.addAll(xmlFile.getObject());
		TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
		taskXMLFile.load(AcceptanceTests.class.getResource("/materialPoints/taskDescription.xml"));

		taskDescription = taskXMLFile.getObject();
		initialSystem = taskDescription.getInitialSystem();
		finalSystem = taskDescription.getFinalSystem();
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		System system = initialSystem;

		final String object_id = system.getObjectByName(OBJECT_MATERIAL_POINT).getId();
		final String point_14_id = system.getObjectByName(OBJECT_POINT_14).getId();
		final String point_24_id = system.getObjectByName(OBJECT_POINT_24).getId();
		final String point_34_id = system.getObjectByName(OBJECT_POINT_34).getId();

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		// 0
		systemTransformation = materialPointsTransformations.get(ELEMENT_MOVE_RIGHT);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getLink(LINK_POSITION, object_id, point_24_id));
		assertEquals(false, system.getObjectById(point_14_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertNotNull(system.getLink(LINK_POSITION, point_14_id, null));
		assertEquals(true, system.getObjectById(point_24_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertNotNull(system.getLink(LINK_POSITION, point_24_id, object_id));

		// 1
		systemTransformation = materialPointsTransformations.get(ELEMENT_MOVE_RIGHT);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getLink(LINK_POSITION, object_id, point_34_id));
		assertEquals(false, system.getObjectById(point_24_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertNotNull(system.getLink(LINK_POSITION, point_24_id, null));
		assertEquals(true, system.getObjectById(point_34_id).getAttribute(ATTRIBUTE_OCCUPIED).getValueAsBoolean());
		assertNotNull(system.getLink(LINK_POSITION, point_34_id, object_id));
	}

	@Test
	public void compareInitialAndFinalSystem() throws CloneNotSupportedException {
		SystemVariant[] systemVariants;
		systemVariants = materialPointsTransformations.get(ELEMENT_MOVE_RIGHT).applyTo(initialSystem);
		assertEquals(1, systemVariants.length);

		systemVariants = materialPointsTransformations.get(ELEMENT_MOVE_LEFT).applyTo(initialSystem);
		assertEquals(0, systemVariants.length);

		systemVariants = materialPointsTransformations.get(ELEMENT_MOVE_TOP).applyTo(initialSystem);
		assertEquals(1, systemVariants.length);

		systemVariants = materialPointsTransformations.get(ELEMENT_MOVE_BOTTOM).applyTo(initialSystem);
		assertEquals(1, systemVariants.length);

		assertFalse(initialSystem.equals(finalSystem));
		assertFalse(initialSystem.contains(finalSystem));
	}

	@Test
	public void movingInTheField() throws CloneNotSupportedException {
		Planner planner = new Planner(taskDescription, materialPointsTransformations);
		planner.plan();

		SystemProcess operations = planner.getShortestProcess();
		assertEquals(14, operations.size());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(0).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(1).getName());
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(2).getName());
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(3).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(4).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(5).getName());
		assertEquals(OPERATION_MOVE_TOP, operations.get(6).getName());
		assertEquals(OPERATION_MOVE_TOP, operations.get(7).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(8).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(9).getName());
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(10).getName());
		assertEquals(OPERATION_MOVE_BOTTOM, operations.get(11).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(12).getName());
		assertEquals(OPERATION_MOVE_RIGHT, operations.get(13).getName());
	}
}
