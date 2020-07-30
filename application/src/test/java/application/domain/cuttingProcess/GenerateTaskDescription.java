package application.domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;
import planning.model.Attribute;
import planning.model.System;
import planning.model.SystemObject;

public class GenerateTaskDescription implements CuttingProcess {

	public static System initialSystem() {
		final System system = new System();

		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE, ID_OBJECT_WORKPIECE);
		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		system.addObject(workpiece);

		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE, ID_OBJECT_CYLINDER_SURFACE);
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		system.addObject(cylinderSurface);

		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A, ID_OBJECT_REQUIREMENT_SURFACE_A);
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_a);

		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B, ID_OBJECT_REQUIREMENT_SURFACE_B);
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_b);

		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C, ID_OBJECT_REQUIREMENT_SURFACE_C);
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_c);

		system.addLink(workpiece, LINK_IS_PART_OF, cylinderSurface);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_a);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_b);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_c);

		system.addLink(cylinderSurface, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(cylinderSurface, LINK_IS_LENGTH_REQUIREMENT, null);

		system.addLink(requirement_a, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_a, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_LEFT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_b);

		system.addLink(requirement_b, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_b, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_b, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_c);

		system.addLink(requirement_c, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_c, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_c, LINK_SURFACE_SIDE_RIGHT, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE, ID_OBJECT_WORKPIECE);
		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		system.addObject(workpiece);

		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A, ID_OBJECT_REQUIREMENT_SURFACE_A);
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_a);

		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B, ID_OBJECT_REQUIREMENT_SURFACE_B);
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_b);

		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C, ID_OBJECT_REQUIREMENT_SURFACE_C);
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_c);

		system.addLink(requirement_a, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_LEFT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_b);

		system.addLink(requirement_b, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_b, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_c);

		system.addLink(requirement_c, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_c, LINK_SURFACE_SIDE_RIGHT, null);

		return system;
	}

	public static void main(String args[]) {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/cuttingProcess/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
