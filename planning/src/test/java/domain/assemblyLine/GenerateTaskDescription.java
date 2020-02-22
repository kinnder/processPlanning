package domain.assemblyLine;

import java.io.IOException;
import java.net.URISyntaxException;

import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription implements AssemblyLine {

	public static System initialSystem() {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, ID_OBJECT_PLANE_X_TABLE_1);
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, ID_OBJECT_PLANE_X_TABLE_2);
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE, ID_OBJECT_PLANE_Y_OUTSIDE);
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, ID_OBJECT_PLANE_Y_INSIDE);
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP, ID_OBJECT_PLANE_Z_TOP);
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, ID_OBJECT_PLANE_Z_BOTTOM);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, ID_OBJECT_SHUTTLE);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2, ID_OBJECT_TABLE_2);

		System system = new System();
		system.addObject(robot);
		system.addObject(plane_x_table_1);
		system.addObject(plane_x_table_2);
		system.addObject(plane_y_outside);
		system.addObject(plane_y_inside);
		system.addObject(plane_z_top);
		system.addObject(plane_z_bottom);
		system.addObject(packageBox);
		system.addObject(shuttle);
		system.addObject(table_1);
		system.addObject(table_2);

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, ID_OBJECT_PLANE_X_TABLE_2));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, ID_OBJECT_PLANE_Y_INSIDE));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, null));
		plane_x_table_1.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_TABLE_1));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, ID_OBJECT_PICK_AND_PLACE_ROBOT));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_TABLE_2));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_SHUTTLE));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, ID_OBJECT_PICK_AND_PLACE_ROBOT));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_TABLE_1));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_TABLE_2));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));
		plane_y_outside.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_SHUTTLE));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, ID_OBJECT_PICK_AND_PLACE_ROBOT));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_TABLE_1));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_TABLE_2));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_SHUTTLE));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_SHUTTLE));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_PLANE_X_TABLE_2));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_PLANE_Y_OUTSIDE));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_PACKAGE_BOX));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_PLANE_X_TABLE_1));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_PLANE_Y_INSIDE));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_PLANE_X_TABLE_2));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_PLANE_Y_INSIDE));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		return system;
	}

	public static System finalSystem() {
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_TABLE_1));

		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, ID_OBJECT_PLANE_X_TABLE_2));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, ID_OBJECT_PLANE_Y_INSIDE));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, ID_OBJECT_PLANE_X_TABLE_1));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, ID_OBJECT_PLANE_Y_INSIDE));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, ID_OBJECT_PLANE_Z_BOTTOM));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, ID_OBJECT_PACKAGE_BOX));

		final System system = new System();
		system.addObject(packageBox);
		system.addObject(robot);
		system.addObject(table_1);

		return system;
	}

	public static void main(String args[]) {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/assemblyLine/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
