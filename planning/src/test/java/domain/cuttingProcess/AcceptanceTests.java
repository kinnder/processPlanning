package domain.cuttingProcess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemOperation;
import planning.model.SystemTransformation;
import planning.model.SystemVariant;
import planning.storage.SystemTransformationsXMLFile;

public class AcceptanceTests implements CuttingProcess {

	private static SystemTransformations cuttingProcessTransformations;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException {
		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.load(AcceptanceTests.class.getResource("/cuttingProcess/systemTransformations.xml"));

		cuttingProcessTransformations = new SystemTransformations();
		cuttingProcessTransformations.addElements(xmlFile.getSystemTransformations());
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE);
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A);
		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B);
		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_a_id = requirement_a.getId();
		final String requirement_b_id = requirement_b.getId();
		final String requirement_c_id = requirement_c.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_a_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_b_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_c_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));
		cylinderSurface.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		cylinderSurface.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_CUT_CYLINDER_SURFACE);
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

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_SPLIT_CYLINDER_SURFACE);
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

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_SPLIT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals("4", systemVariants[0].getActionParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("15", systemVariants[0].getActionParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_TRIM_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
	}

	@Test
	public void cuttingProcessForCylindricWorkpiece() throws CloneNotSupportedException {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE);
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A);
		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B);
		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_a_id = requirement_a.getId();
		final String requirement_b_id = requirement_b.getId();
		final String requirement_c_id = requirement_c.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_a_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_b_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_c_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));
		cylinderSurface.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		cylinderSurface.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		// TODO : финальный вариант системы необходимо задавать комбинацией Template
		// классов
		final System final_system = new System();
		final SystemObject final_requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A, requirement_a_id);
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		final_requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));
		final SystemObject final_requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B, requirement_b_id);
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		final_requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));
		final SystemObject final_requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C, requirement_c_id);
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		final_requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));
		final_system.addObject(final_requirement_a);
		final_system.addObject(final_requirement_b);
		final_system.addObject(final_requirement_c);

		assertFalse(system.equals(final_system));
		assertFalse(system.contains(final_system));

		Planner planner = new Planner(system, final_system, cuttingProcessTransformations.getElements());
		planner.plan();

		List<SystemOperation> operations = planner.getShortestPlan();
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
