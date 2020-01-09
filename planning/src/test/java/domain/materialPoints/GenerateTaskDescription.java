package domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription implements MaterialPoints {

	public static System initialSystem() {
		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		final SystemObject point_1 = new SystemObject(OBJECT_POINT_1, ID_OBJECT_POINT_1);
		final SystemObject point_2 = new SystemObject(OBJECT_POINT_2, ID_OBJECT_POINT_2);
		final SystemObject point_3 = new SystemObject(OBJECT_POINT_3, ID_OBJECT_POINT_3);
		final SystemObject point_4 = new SystemObject(OBJECT_POINT_4, ID_OBJECT_POINT_4);

		System system = new System();
		system.addObject(object);
		system.addObject(point_1);
		system.addObject(point_2);
		system.addObject(point_3);
		system.addObject(point_4);

		object.addLink(new Link(LINK_POSITION, ID_OBJECT_POINT_1));

		point_1.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_1.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_2));
		point_1.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_3));
		point_1.addLink(new Link(LINK_POSITION, ID_OBJECT_OBJECT));

		point_2.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_2.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_1));
		point_2.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_4));
		point_2.addLink(new Link(LINK_POSITION, null));

		point_3.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_3.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_4));
		point_3.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_1));
		point_3.addLink(new Link(LINK_POSITION, null));

		point_4.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_4.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_3));
		point_4.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_2));
		point_4.addLink(new Link(LINK_POSITION, null));

		return system;
	}

	public static System finalSystem() {
		final System system = new System();
		
		return system;
	}

	// TODO : implement
	public static void main(String args[]) {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setTaskDescription(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/materialPoints/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
