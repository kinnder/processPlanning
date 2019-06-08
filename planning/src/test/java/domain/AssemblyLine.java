package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import planning.model.Attribute;
import planning.model.AttributeTransformation;
import planning.model.Element;
import planning.model.Link;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemVariant;
import planning.model.Transformation;

public class AssemblyLine {

	private static final String OBJECT_PICK_AND_PLACE_ROBOT = "Робот-перекладчик";

	private static final String OBJECT_ROTARY_DRIVE = "поворотный привод";

	private static final String OBJECT_GRAB = "захват";

	private static final String OBJECT_CONFIGURATION_STATION = "Станция комплектации";

	private static final String OBJECT_TRANSPORT_LINE = "Транспортная линия";

	private static final String OBJECT_PACKAGE_BOX = "Тара";

	private static final String OBJECT_SHUTTLE = "Шаттл";

	private static final String OBJECT_TABLE_1 = "Стол 1";

	private static final String OBJECT_TABLE_2 = "Стол 2";

	private static final String ATTRIBUTE_VERTICAL_DRIVE_POSITION = "положение вертикального привода";

	private static final String ATTRIBUTE_LINEAR_DRIVE_POSITION = "положение линейного привода";

	private static final String VALUE_BOTTOM_PLANE = "нижняя плоскость";

	private static final String VALUE_TOP_PLANE = "верхняя плоскость";

	private static final String VALUE_TABLE_1 = "стол 1";

	private static final String VALUE_TABLE_2 = "стол 2";

	// TODO : поддержка массивов связей с одинаковым именем
	private static final String LINK_BETWEEN_STATION_AND_LINE = "связь между (станция-линия)";

	private static final String LINK_BETWEEN_ROBOT_AND_STATION = "связь между (робот-станция)";

	private static final String LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE = "связь между (робот-поворотный привод)";

	private static final String LINK_BETWEEN_ROBOT_AND_GRAB = "связь между (робот-захват)";

	private static final String LINK_GRAB_POSITION = "положение захвата";

	private static final String LINK_ROTARY_DRIVE_POSITION = "положение поворотного привода";

	private static final String LINK_SHUTTLE_POSITION = "место вокзала";

	private static final String LINK_PACKAGE_BOX_POSITION = "место тары";

	private static final String OPERATION_ROTATE_TO_TRANSPORT_LINE = "Повернуть к транспортной линии";

	private static final String OPERATION_ROTATE_TO_STATION = "Повернуть к станции";

	private static final String OPERATION_OPEN_GRAB = "Открыть захват";

	private static final String OPERATION_CLOSE_GRAB = "Закрыть захват";

	private static final String OPERATION_LIFT_UP = "Поднять вверх";

	private static final String OPERATION_LOWER_DOWN = "Опустить вниз";

	private static final String OPERATION_MOVE_TO_POSITION_1 = "Переместить к позиции 1";

	private static final String OPERATION_MOVE_TO_POSITION_2 = "Переместить к позиции 2";

	public static Element rotateToTransportLine() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ID-2");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#ID-3");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#ID-4");

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();

		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, station_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, rotaryDrive_id));

		rotaryDrive.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, station_id));

		station.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, robot_id));
		station.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));

		line.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, station_id));
		line.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));

		template.addObject(robot);
		template.addObject(rotaryDrive);
		template.addObject(station);
		template.addObject(line);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(rotaryDrive_id, LINK_ROTARY_DRIVE_POSITION, line_id),
				new LinkTransformation(station_id, LINK_ROTARY_DRIVE_POSITION, null),
				new LinkTransformation(line_id, LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id) };

		return new Element(OPERATION_ROTATE_TO_TRANSPORT_LINE, template, transformations);
	}

	public static Element rotateToStation() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ID-2");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#ID-3");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#ID-4");

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();

		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, station_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, rotaryDrive_id));

		rotaryDrive.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, line_id));

		station.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, robot_id));
		station.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));

		line.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, station_id));
		line.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));

		template.addObject(robot);
		template.addObject(rotaryDrive);
		template.addObject(station);
		template.addObject(line);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(rotaryDrive_id, LINK_ROTARY_DRIVE_POSITION, station_id),
				new LinkTransformation(station_id, LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id),
				new LinkTransformation(line_id, LINK_ROTARY_DRIVE_POSITION, null) };

		return new Element(OPERATION_ROTATE_TO_STATION, template, transformations);
	}

	public static Element openGrab() {
		return new Element(OPERATION_OPEN_GRAB, null, null);
	}

	public static Element closeGrab() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ID-2");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#ID-3");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#ID-4");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#ID-5");
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, "#ID-6");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#ID-7");

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String grab_id = grab.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();
		final String shuttle_id = shuttle.getObjectId();
		final String packageBox_id = packageBox.getObjectId();

		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, station_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, rotaryDrive_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));
		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE));
		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2));

		rotaryDrive.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, line_id));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, null));

		station.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, robot_id));
		station.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, line_id));
		station.addLink(new Link(LINK_SHUTTLE_POSITION, shuttle_id));

		line.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, station_id));
		line.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));

		shuttle.addLink(new Link(LINK_SHUTTLE_POSITION, station_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		template.addObject(robot);
		template.addObject(rotaryDrive);
		template.addObject(grab);
		template.addObject(station);
		template.addObject(line);
		template.addObject(shuttle);
		template.addObject(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(grab_id, LINK_GRAB_POSITION, packageBox_id),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, grab_id) };

		return new Element(OPERATION_CLOSE_GRAB, template, transformations);
	}

	public static Element liftUp() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#ID-2");
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, "#ID-3");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#ID-4");

		final String robot_id = robot.getObjectId();
		final String grab_id = grab.getObjectId();
		final String shuttle_id = shuttle.getObjectId();
		final String packageBox_id = packageBox.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		packageBox.addLink(new Link(LINK_GRAB_POSITION, grab_id));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		template.addObject(robot);
		template.addObject(grab);
		template.addObject(shuttle);
		template.addObject(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, null),
				new LinkTransformation(shuttle_id, LINK_PACKAGE_BOX_POSITION, null),
				new AttributeTransformation(robot_id, ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_TOP_PLANE) };

		return new Element(OPERATION_LIFT_UP, template, transformations);
	}

	public static Element lowerDown() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#ID-2");
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, "#ID-3");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#ID-4");

		final String robot_id = robot.getObjectId();
		final String grab_id = grab.getObjectId();
		final String table_1_id = table_1.getObjectId();
		final String packageBox_id = packageBox.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1));
		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_TOP_PLANE));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));
		table_1.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1));

		packageBox.addLink(new Link(LINK_GRAB_POSITION, grab_id));

		template.addObject(robot);
		template.addObject(grab);
		template.addObject(table_1);
		template.addObject(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, table_1_id),
				new LinkTransformation(table_1_id, LINK_PACKAGE_BOX_POSITION, packageBox_id),
				new AttributeTransformation(robot_id, ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE) };

		return new Element(OPERATION_LOWER_DOWN, template, transformations);
	}

	public static Element moveToPosition1() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");

		final String robot_id = robot.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_TOP_PLANE));
		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2));

		template.addObject(robot);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(robot_id, ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1) };

		return new Element(OPERATION_MOVE_TO_POSITION_1, template, transformations);
	}

	public static Element moveToPosition2() {
		return new Element(OPERATION_MOVE_TO_POSITION_2, null, null);
	}

	@Test
	public void applicationOfElements() {
		final System initial_system = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE);
		final SystemObject grab = new SystemObject(OBJECT_GRAB);
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION);
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2);

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String grab_id = grab.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();
		final String shuttle_id = shuttle.getObjectId();
		final String packageBox_id = packageBox.getObjectId();
		final String table_1_id = table_1.getObjectId();
		final String table_2_id = table_2.getObjectId();

		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, station_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, rotaryDrive_id));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));
		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE));
		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2));

		rotaryDrive.addLink(new Link(LINK_BETWEEN_ROBOT_AND_ROTARY_DRIVE, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, station_id));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, null));

		station.addLink(new Link(LINK_BETWEEN_ROBOT_AND_STATION, robot_id));
		station.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));
		station.addLink(new Link(LINK_SHUTTLE_POSITION, shuttle_id));

		line.addLink(new Link(LINK_BETWEEN_STATION_AND_LINE, station_id));
		line.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));

		shuttle.addLink(new Link(LINK_SHUTTLE_POSITION, station_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));
		table_1.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1));

		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));
		table_2.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2));

		initial_system.addObject(robot);
		initial_system.addObject(rotaryDrive);
		initial_system.addObject(grab);
		initial_system.addObject(station);
		initial_system.addObject(line);
		initial_system.addObject(shuttle);
		initial_system.addObject(packageBox);
		initial_system.addObject(table_1);
		initial_system.addObject(table_2);

		System expected_system;
		System actual_system = initial_system.clone();
		Element element;
		SystemVariant[] systemVariants;

		element = rotateToTransportLine();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(rotaryDrive_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(line_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(null);
		expected_system.getObjectById(line_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(rotaryDrive_id);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = closeGrab();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(grab_id).getLink(LINK_GRAB_POSITION).setObjectId(packageBox_id);
		expected_system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION).setObjectId(grab_id);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = liftUp();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(null);
		expected_system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(null);
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION)
				.setValue(VALUE_TOP_PLANE);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = rotateToStation();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(rotaryDrive_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(station_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(rotaryDrive_id);
		expected_system.getObjectById(line_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(null);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = moveToPosition1();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_LINEAR_DRIVE_POSITION).setValue(VALUE_TABLE_1);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = lowerDown();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(table_1_id);
		expected_system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(packageBox_id);
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION)
				.setValue(VALUE_BOTTOM_PLANE);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));
	}
}
