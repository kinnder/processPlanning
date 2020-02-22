package domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class GenerateTaskDescription implements CuttingProcess {

	public static System initialSystem() {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE, ID_OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE, ID_OBJECT_CYLINDER_SURFACE);
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A,
				ID_OBJECT_REQUIREMENT_SURFACE_A);
		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B,
				ID_OBJECT_REQUIREMENT_SURFACE_B);
		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C,
				ID_OBJECT_REQUIREMENT_SURFACE_C);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, ID_OBJECT_CYLINDER_SURFACE));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_REQUIREMENT_SURFACE_A));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_REQUIREMENT_SURFACE_B));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_REQUIREMENT_SURFACE_C));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, ID_OBJECT_WORKPIECE));
		cylinderSurface.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		cylinderSurface.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_a.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, ID_OBJECT_REQUIREMENT_SURFACE_B));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_b.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, ID_OBJECT_REQUIREMENT_SURFACE_A));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, ID_OBJECT_REQUIREMENT_SURFACE_C));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_c.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, ID_OBJECT_REQUIREMENT_SURFACE_B));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		return system;
	}

	public static System finalSystem() {
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A,
				ID_OBJECT_REQUIREMENT_SURFACE_A);
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, ID_OBJECT_REQUIREMENT_SURFACE_B));

		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B,
				ID_OBJECT_REQUIREMENT_SURFACE_B);
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, ID_OBJECT_REQUIREMENT_SURFACE_A));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, ID_OBJECT_REQUIREMENT_SURFACE_C));

		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C,
				ID_OBJECT_REQUIREMENT_SURFACE_C);
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, ID_OBJECT_WORKPIECE));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, ID_OBJECT_REQUIREMENT_SURFACE_B));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		final System system = new System();
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

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
