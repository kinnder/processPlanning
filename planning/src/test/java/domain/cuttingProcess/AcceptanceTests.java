package domain.cuttingProcess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import planning.model.SystemOperation;
import planning.model.SystemProcess;
import planning.model.SystemTransformation;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class AcceptanceTests implements CuttingProcess {

	private static SystemTransformations cuttingProcessTransformations;

	private static TaskDescription taskDescription;

	private static System initialSystem;

	private static System finalSystem;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException, URISyntaxException {
		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.load(AcceptanceTests.class.getResource("/cuttingProcess/systemTransformations.xml"));

		cuttingProcessTransformations = new SystemTransformations();
		cuttingProcessTransformations.addAll(xmlFile.getObject());

		TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
		taskXMLFile.load(AcceptanceTests.class.getResource("/cuttingProcess/taskDescription.xml"));

		taskDescription = taskXMLFile.getObject();
		initialSystem = taskDescription.getInitialSystem();
		finalSystem = taskDescription.getFinalSystem();
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		System system = initialSystem;

		final String cylinderSurface_id = system.getObjectByName(OBJECT_CYLINDER_SURFACE).getId();
		final String requirement_a_id = system.getObjectByName(OBJECT_REQUIREMENT_SURFACE_A).getId();
		final String requirement_b_id = system.getObjectByName(OBJECT_REQUIREMENT_SURFACE_B).getId();
		final String requirement_c_id = system.getObjectByName(OBJECT_REQUIREMENT_SURFACE_C).getId();

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		// 0
		systemTransformation = cuttingProcessTransformations.get(ELEMENT_CUT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(3, systemVariants.length);
		int id;
		for (id = 0; id < 3; id++) {
			if (systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA).equals("2")) {
				break;
			}
		}
		assertNotEquals(3, id);
		assertEquals("2", systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger(),
				Integer.valueOf(20));
		assertNotNull(system.getObjectById(cylinderSurface_id).getLink(LINK_IS_DIAMETER_REQUIREMENT, requirement_a_id));

		// 1
		systemTransformation = cuttingProcessTransformations.get(ELEMENT_SPLIT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(2, systemVariants.length);
		for (id = 0; id < 2; id++) {
			if (systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA).equals("4")) {
				break;
			}
		}
		assertNotEquals(2, id);
		assertEquals("4", systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("45", systemVariants[id].getActionParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_LENGTH).getValueAsInteger(),
				Integer.valueOf(45));
		assertNotNull(system.getObjectById(cylinderSurface_id).getLink(LINK_IS_LENGTH_REQUIREMENT, requirement_a_id));
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);

		assertEquals(6, system.getObjects().size());

		// 2
		systemTransformation = cuttingProcessTransformations.get(ELEMENT_SPLIT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals("4", systemVariants[0].getActionParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("15", systemVariants[0].getActionParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);

		// 3
		systemTransformation = cuttingProcessTransformations.get(ELEMENT_TRIM_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
	}

	@Test
	public void compareInitialAndFinalSystem() {
		assertFalse(initialSystem.equals(finalSystem));
		assertFalse(initialSystem.contains(finalSystem));
	}

	@Test
	public void cuttingProcessForCylindricWorkpiece() throws CloneNotSupportedException {
		Planner planner = new Planner(taskDescription, cuttingProcessTransformations);
		planner.plan();

		SystemProcess operations = planner.getShortestProcess();
		assertEquals(4, operations.size());

		SystemOperation operation;
		operation = operations.get(0);
		assertEquals(OPERATION_CUT_CYLINDER_SURFACE, operation.getName());
		assertEquals("2", operation.getParameter(PARAMETER_DIAMETER_DELTA));

		operation = operations.get(1);
		assertEquals(OPERATION_SPLIT_CYLINDER_SURFACE, operation.getName());
		assertEquals("4", operation.getParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("45", operation.getParameter(PARAMETER_LENGTH_DELTA));

		operation = operations.get(2);
		assertEquals(OPERATION_SPLIT_CYLINDER_SURFACE, operation.getName());
		assertEquals("4", operation.getParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("15", operation.getParameter(PARAMETER_LENGTH_DELTA));

		operation = operations.get(3);
		assertEquals(OPERATION_TRIM_CYLINDER_SURFACE, operation.getName());
	}
}
