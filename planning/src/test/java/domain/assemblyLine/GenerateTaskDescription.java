package domain.assemblyLine;

import java.io.IOException;
import java.net.URISyntaxException;

import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.System;
import planning.model.SystemObject;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription implements AssemblyLine {

	public static System initialSystem() {
		final System system = new System();

		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		system.addObject(robot);

		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, ID_OBJECT_PLANE_X_TABLE_1);
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_1);

		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, ID_OBJECT_PLANE_X_TABLE_2);
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_2);

		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE, ID_OBJECT_PLANE_Y_OUTSIDE);
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_outside);

		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, ID_OBJECT_PLANE_Y_INSIDE);
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_inside);

		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP, ID_OBJECT_PLANE_Z_TOP);
		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_top);

		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, ID_OBJECT_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_bottom);

		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		system.addObject(packageBox);

		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, ID_OBJECT_SHUTTLE);
		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(shuttle);

		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_1);

		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2, ID_OBJECT_TABLE_2);
		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_2);

		system.addLink(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_table_2);
		system.addLink(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_inside);
		system.addLink(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom);
		system.addLink(robot, LINK_GRAB_POSITION, null);

		system.addLink(plane_x_table_1, LINK_LINEAR_DRIVE_POSITION, null);
		system.addLink(plane_x_table_1, LINK_PLANE_X_POSITION, table_1);

		system.addLink(plane_x_table_2, LINK_PLANE_X_POSITION, table_2);
		system.addLink(plane_x_table_2, LINK_PLANE_X_POSITION, shuttle);

		system.addLink(plane_y_inside, LINK_PLANE_Y_POSITION, table_1);
		system.addLink(plane_y_inside, LINK_PLANE_Y_POSITION, table_2);

		system.addLink(plane_y_outside, LINK_ROTARY_DRIVE_POSITION, null);
		system.addLink(plane_y_outside, LINK_PLANE_Y_POSITION, shuttle);

		system.addLink(plane_z_top, LINK_VERTICAL_DRIVE_POSITION, null);

		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, table_1);
		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, table_2);
		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, shuttle);

		system.addLink(packageBox, LINK_GRAB_POSITION, null);
		system.addLink(packageBox, LINK_PACKAGE_BOX_POSITION, shuttle);

		system.addLink(table_1, LINK_PACKAGE_BOX_POSITION, null);

		system.addLink(table_2, LINK_PACKAGE_BOX_POSITION, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		system.addObject(packageBox);

		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		system.addObject(robot);

		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_1);

		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, ID_OBJECT_PLANE_X_TABLE_1);
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_1);

		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, ID_OBJECT_PLANE_X_TABLE_2);
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_2);

		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, ID_OBJECT_PLANE_Y_INSIDE);
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_inside);

		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, ID_OBJECT_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_bottom);

		system.addLink(packageBox, LINK_GRAB_POSITION, null);
		system.addLink(packageBox, LINK_PACKAGE_BOX_POSITION, table_1);

		system.addLink(robot, LINK_GRAB_POSITION, null);
		system.addLink(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_table_2);
		system.addLink(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_inside);
		system.addLink(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom);

		system.addLink(table_1, LINK_PLANE_X_POSITION, plane_x_table_1);
		system.addLink(table_1, LINK_PLANE_Y_POSITION, plane_y_inside);
		system.addLink(table_1, LINK_PLANE_Z_POSITION, plane_z_bottom);

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
