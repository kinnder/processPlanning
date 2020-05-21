package domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.model.Attribute;
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
		system.addObject(object);

		final SystemObject point_15 = new SystemObject(OBJECT_POINT_15, ID_OBJECT_POINT_15);
		point_15.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_15);

		final SystemObject point_14 = new SystemObject(OBJECT_POINT_14, ID_OBJECT_POINT_14);
		point_14.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		system.addObject(point_14);

		final SystemObject point_13 = new SystemObject(OBJECT_POINT_13, ID_OBJECT_POINT_13);
		point_13.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_13);

		final SystemObject point_12 = new SystemObject(OBJECT_POINT_12, ID_OBJECT_POINT_12);
		point_12.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_12);

		final SystemObject point_11 = new SystemObject(OBJECT_POINT_11, ID_OBJECT_POINT_11);
		point_11.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_11);

		final SystemObject point_24 = new SystemObject(OBJECT_POINT_24, ID_OBJECT_POINT_24);
		point_24.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_24);

		final SystemObject point_34 = new SystemObject(OBJECT_POINT_34, ID_OBJECT_POINT_34);
		point_34.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_34);

		final SystemObject point_33 = new SystemObject(OBJECT_POINT_33, ID_OBJECT_POINT_33);
		point_33.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_33);

		final SystemObject point_32 = new SystemObject(OBJECT_POINT_32, ID_OBJECT_POINT_32);
		point_32.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_32);

		final SystemObject point_42 = new SystemObject(OBJECT_POINT_42, ID_OBJECT_POINT_42);
		point_42.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_42);

		final SystemObject point_54 = new SystemObject(OBJECT_POINT_54, ID_OBJECT_POINT_54);
		point_54.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_54);

		final SystemObject point_53 = new SystemObject(OBJECT_POINT_53, ID_OBJECT_POINT_53);
		point_53.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_53);

		final SystemObject point_52 = new SystemObject(OBJECT_POINT_52, ID_OBJECT_POINT_52);
		point_52.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_52);

		final SystemObject point_64 = new SystemObject(OBJECT_POINT_64, ID_OBJECT_POINT_64);
		point_64.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_64);

		final SystemObject point_74 = new SystemObject(OBJECT_POINT_74, ID_OBJECT_POINT_74);
		point_74.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_74);

		final SystemObject point_73 = new SystemObject(OBJECT_POINT_73, ID_OBJECT_POINT_73);
		point_73.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_73);

		final SystemObject point_72 = new SystemObject(OBJECT_POINT_72, ID_OBJECT_POINT_72);
		point_72.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_72);

		final SystemObject point_82 = new SystemObject(OBJECT_POINT_82, ID_OBJECT_POINT_82);
		point_82.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_82);

		final SystemObject point_95 = new SystemObject(OBJECT_POINT_95, ID_OBJECT_POINT_95);
		point_95.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_95);

		final SystemObject point_94 = new SystemObject(OBJECT_POINT_94, ID_OBJECT_POINT_94);
		point_94.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_94);

		final SystemObject point_93 = new SystemObject(OBJECT_POINT_93, ID_OBJECT_POINT_93);
		point_93.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_93);

		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_92);

		final SystemObject point_91 = new SystemObject(OBJECT_POINT_91, ID_OBJECT_POINT_91);
		point_91.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, false));
		system.addObject(point_91);

		system.addLink(object, LINK_POSITION, point_14);

		system.addLink(point_15, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_14);
		system.addLink(point_15, LINK_POSITION, null);

		system.addLink(point_14, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_24);
		system.addLink(point_14, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_13);

		system.addLink(point_13, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_12);
		system.addLink(point_13, LINK_POSITION, null);

		system.addLink(point_12, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_11);
		system.addLink(point_12, LINK_POSITION, null);

		system.addLink(point_11, LINK_POSITION, null);

		system.addLink(point_24, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_34);
		system.addLink(point_24, LINK_POSITION, null);

		system.addLink(point_34, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_33);
		system.addLink(point_34, LINK_POSITION, null);

		system.addLink(point_33, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_32);
		system.addLink(point_33, LINK_POSITION, null);

		system.addLink(point_32, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_42);
		system.addLink(point_32, LINK_POSITION, null);

		system.addLink(point_42, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_52);
		system.addLink(point_42, LINK_POSITION, null);

		system.addLink(point_52, LINK_NEIGHBOR_TOP, LINK_NEIGHBOR_BOTTOM, point_53);
		system.addLink(point_52, LINK_POSITION, null);

		system.addLink(point_53, LINK_NEIGHBOR_TOP, LINK_NEIGHBOR_BOTTOM, point_54);
		system.addLink(point_53, LINK_POSITION, null);

		system.addLink(point_54, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_64);
		system.addLink(point_54, LINK_POSITION, null);

		system.addLink(point_64, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_74);
		system.addLink(point_64, LINK_POSITION, null);

		system.addLink(point_74, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_73);
		system.addLink(point_74, LINK_POSITION, null);

		system.addLink(point_73, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_72);
		system.addLink(point_73, LINK_POSITION, null);

		system.addLink(point_72, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_82);
		system.addLink(point_72, LINK_POSITION, null);

		system.addLink(point_82, LINK_NEIGHBOR_RIGHT, LINK_NEIGHBOR_LEFT, point_92);
		system.addLink(point_82, LINK_POSITION, null);

		system.addLink(point_95, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_94);
		system.addLink(point_95, LINK_POSITION, null);

		system.addLink(point_94, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_93);
		system.addLink(point_94, LINK_POSITION, null);

		system.addLink(point_93, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_92);
		system.addLink(point_93, LINK_POSITION, null);

		system.addLink(point_92, LINK_NEIGHBOR_BOTTOM, LINK_NEIGHBOR_TOP, point_91);
		system.addLink(point_92, LINK_POSITION, null);

		system.addLink(point_91, LINK_POSITION, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject object = new SystemObject(OBJECT_MATERIAL_POINT, ID_OBJECT_OBJECT);
		system.addObject(object);

		final SystemObject point_92 = new SystemObject(OBJECT_POINT_92, ID_OBJECT_POINT_92);
		point_92.addAttribute(new Attribute(ATTRIBUTE_OCCUPIED, true));
		system.addObject(point_92);

		system.addLink(object, LINK_POSITION, point_92);

		return system;
	}

	public static void main(String args[]) {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/materialPoints/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
