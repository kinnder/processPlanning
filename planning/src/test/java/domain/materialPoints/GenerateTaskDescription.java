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
		// y
		// 6 + - - - - - - - - - +
		// 5 | . - - + - - - + . |
		// 4 | A . . | . . . | . |
		// 3 | . | . | . | . | . |
		// 2 | . | . . . | . . B |
		// 1 | . + - - - + - - . |
		// 0 + - - - - - - - - - +
		// 0 0 1 2 3 4 5 6 7 8 9 A x

		final System system = new System();
		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		final SystemObject point_15 = new SystemObject(OBJECT_POINT_15, ID_OBJECT_POINT_15);
		final SystemObject point_14 = new SystemObject(OBJECT_POINT_14, ID_OBJECT_POINT_14);
		final SystemObject point_13 = new SystemObject(OBJECT_POINT_13, ID_OBJECT_POINT_13);
		final SystemObject point_12 = new SystemObject(OBJECT_POINT_12, ID_OBJECT_POINT_12);
		final SystemObject point_11 = new SystemObject(OBJECT_POINT_11, ID_OBJECT_POINT_11);
		final SystemObject point_24 = new SystemObject(OBJECT_POINT_24, ID_OBJECT_POINT_24);
		final SystemObject point_34 = new SystemObject(OBJECT_POINT_34, ID_OBJECT_POINT_34);
		final SystemObject point_33 = new SystemObject(OBJECT_POINT_33, ID_OBJECT_POINT_33);
		final SystemObject point_32 = new SystemObject(OBJECT_POINT_32, ID_OBJECT_POINT_32);
		final SystemObject point_42 = new SystemObject(OBJECT_POINT_42, ID_OBJECT_POINT_42);
		final SystemObject point_54 = new SystemObject(OBJECT_POINT_54, ID_OBJECT_POINT_54);
		final SystemObject point_53 = new SystemObject(OBJECT_POINT_53, ID_OBJECT_POINT_53);
		final SystemObject point_52 = new SystemObject(OBJECT_POINT_52, ID_OBJECT_POINT_52);
		final SystemObject point_64 = new SystemObject(OBJECT_POINT_64, ID_OBJECT_POINT_64);
		final SystemObject point_74 = new SystemObject(OBJECT_POINT_74, ID_OBJECT_POINT_74);
		final SystemObject point_73 = new SystemObject(OBJECT_POINT_73, ID_OBJECT_POINT_73);
		final SystemObject point_72 = new SystemObject(OBJECT_POINT_72, ID_OBJECT_POINT_72);
		final SystemObject point_82 = new SystemObject(OBJECT_POINT_82, ID_OBJECT_POINT_82);
		final SystemObject point_95 = new SystemObject(OBJECT_POINT_95, ID_OBJECT_POINT_95);
		final SystemObject point_94 = new SystemObject(OBJECT_POINT_94, ID_OBJECT_POINT_94);
		final SystemObject point_93 = new SystemObject(OBJECT_POINT_93, ID_OBJECT_POINT_93);
		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		final SystemObject point_91 = new SystemObject(OBJECT_POINT_91, ID_OBJECT_POINT_91);

		object.addLink(new Link(LINK_POSITION, ID_OBJECT_POINT_14));

		point_15.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_15.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_14));
		point_15.addLink(new Link(LINK_POSITION, null));

		point_14.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_14.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_15));
		point_14.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_24));
		point_14.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_13));
		point_14.addLink(new Link(LINK_POSITION, ID_OBJECT_OBJECT));

		point_13.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_13.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_14));
		point_13.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_12));
		point_13.addLink(new Link(LINK_POSITION, null));

		point_12.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_12.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_13));
		point_12.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_11));
		point_12.addLink(new Link(LINK_POSITION, null));

		point_11.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_11.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_12));
		point_11.addLink(new Link(LINK_POSITION, null));

		point_24.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_24.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_14));
		point_24.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_34));
		point_24.addLink(new Link(LINK_POSITION, null));

		point_34.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_34.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_24));
		point_34.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_33));
		point_34.addLink(new Link(LINK_POSITION, null));

		point_33.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_33.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_34));
		point_33.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_32));
		point_33.addLink(new Link(LINK_POSITION, null));

		point_32.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_32.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_33));
		point_32.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_42));
		point_32.addLink(new Link(LINK_POSITION, null));

		point_42.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_42.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_32));
		point_42.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_52));
		point_42.addLink(new Link(LINK_POSITION, null));

		point_52.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_52.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_53));
		point_52.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_42));
		point_52.addLink(new Link(LINK_POSITION, null));

		point_53.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_53.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_54));
		point_53.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_52));
		point_53.addLink(new Link(LINK_POSITION, null));

		point_54.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_54.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_64));
		point_54.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_53));
		point_54.addLink(new Link(LINK_POSITION, null));

		point_64.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_64.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_54));
		point_64.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_74));
		point_64.addLink(new Link(LINK_POSITION, null));

		point_74.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_74.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_64));
		point_74.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_73));
		point_74.addLink(new Link(LINK_POSITION, null));

		point_73.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_73.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_74));
		point_73.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_72));
		point_73.addLink(new Link(LINK_POSITION, null));

		point_72.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_72.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_73));
		point_72.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_82));
		point_72.addLink(new Link(LINK_POSITION, null));

		point_82.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_82.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_72));
		point_82.addLink(new Link(LINK_NEIGHBOR_RIGHT, ID_OBJECT_POINT_92));
		point_82.addLink(new Link(LINK_POSITION, null));

		point_95.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_95.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_94));
		point_95.addLink(new Link(LINK_POSITION, null));

		point_94.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_94.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_95));
		point_94.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_93));
		point_94.addLink(new Link(LINK_POSITION, null));

		point_93.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_93.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_94));
		point_93.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_92));
		point_93.addLink(new Link(LINK_POSITION, null));

		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_92.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_93));
		point_92.addLink(new Link(LINK_NEIGHBOR_LEFT, ID_OBJECT_POINT_82));
		point_92.addLink(new Link(LINK_NEIGHBOR_BOTTOM, ID_OBJECT_POINT_91));
		point_92.addLink(new Link(LINK_POSITION, null));

		point_91.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		point_91.addLink(new Link(LINK_NEIGHBOR_TOP, ID_OBJECT_POINT_92));
		point_91.addLink(new Link(LINK_POSITION, null));

		system.addObject(object);
		system.addObject(point_15);
		system.addObject(point_14);
		system.addObject(point_13);
		system.addObject(point_12);
		system.addObject(point_11);
		system.addObject(point_24);
		system.addObject(point_34);
		system.addObject(point_33);
		system.addObject(point_32);
		system.addObject(point_42);
		system.addObject(point_54);
		system.addObject(point_53);
		system.addObject(point_52);
		system.addObject(point_64);
		system.addObject(point_74);
		system.addObject(point_73);
		system.addObject(point_72);
		system.addObject(point_82);
		system.addObject(point_95);
		system.addObject(point_94);
		system.addObject(point_93);
		system.addObject(point_92);
		system.addObject(point_91);

		return system;
	}

	public static System finalSystem() {
		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		object.addLink(new Link(LINK_POSITION, ID_OBJECT_POINT_92));

		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		point_92.addLink(new Link(LINK_POSITION, ID_OBJECT_OBJECT));

		final System system = new System();
		system.addObject(object);
		system.addObject(point_92);

		return system;
	}

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
