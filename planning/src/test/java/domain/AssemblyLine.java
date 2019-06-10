package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import planning.method.Planner;
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

	// TODO : template сравнение связей на NOT-NULL или ANY-VALUE
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
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ROTARY-DRIVE");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#STATION");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#LINE");

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
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ROTARY-DRIVE");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#STATION");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#LINE");

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
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#GRAB");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");

		final String robot_id = robot.getObjectId();
		final String grab_id = grab.getObjectId();
		final String packageBox_id = packageBox.getObjectId();

		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));
		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, packageBox_id));

		packageBox.addLink(new Link(LINK_GRAB_POSITION, grab_id));

		template.addObject(robot);
		template.addObject(grab);
		template.addObject(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(grab_id, LINK_GRAB_POSITION, null),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, null) };

		return new Element(OPERATION_OPEN_GRAB, template, transformations);
	}

	public static Element closeGrab() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE, "#ROTARY-DRIVE");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#GRAB");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#STATION");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#LINE");
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, "#SHUTTLE");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");

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
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#GRAB");
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, "#SHUTTLE");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");

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
				new AttributeTransformation(robot_id, ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_TOP_PLANE), };

		return new Element(OPERATION_LIFT_UP, template, transformations);
	}

	public static Element lowerDown() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#GRAB");
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, "#TABLE-1");
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, "#PACKAGE-BOX");

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
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");

		final String robot_id = robot.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_TOP_PLANE));
		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2));

		template.addObject(robot);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(robot_id, ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1) };

		return new Element(OPERATION_MOVE_TO_POSITION_1, template, transformations);
	}

	public static Element moveToPosition2() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ROBOT");
		final SystemObject grab = new SystemObject(OBJECT_GRAB, "#GRAB");

		final String robot_id = robot.getObjectId();
		final String grab_id = grab.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION, VALUE_BOTTOM_PLANE));
		robot.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1));
		robot.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, grab_id));

		grab.addLink(new Link(LINK_BETWEEN_ROBOT_AND_GRAB, robot_id));
		grab.addLink(new Link(LINK_GRAB_POSITION, null));

		template.addObject(robot);
		template.addObject(grab);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(robot_id, ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_2) };

		return new Element(OPERATION_MOVE_TO_POSITION_2, template, transformations);
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
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(rotaryDrive_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(line_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(null);
		expected_system.getObjectById(line_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(rotaryDrive_id);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = closeGrab();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(grab_id).getLink(LINK_GRAB_POSITION).setObjectId(packageBox_id);
		expected_system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION).setObjectId(grab_id);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = liftUp();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
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
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(rotaryDrive_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(station_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(rotaryDrive_id);
		expected_system.getObjectById(line_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(null);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = moveToPosition1();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_LINEAR_DRIVE_POSITION).setValue(VALUE_TABLE_1);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = lowerDown();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(table_1_id);
		expected_system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION).setObjectId(packageBox_id);
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_VERTICAL_DRIVE_POSITION)
				.setValue(VALUE_BOTTOM_PLANE);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = openGrab();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);

		expected_system = actual_system.clone();
		expected_system.getObjectById(grab_id).getLink(LINK_GRAB_POSITION).setObjectId(null);
		expected_system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION).setObjectId(null);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		element = moveToPosition2();
		systemVariants = actual_system.prepareSystemVariants(element.getTemplate());
		assertEquals(1, systemVariants.length);
		expected_system = actual_system.clone();
		expected_system.getObjectById(robot_id).getAttribute(ATTRIBUTE_LINEAR_DRIVE_POSITION).setValue(VALUE_TABLE_2);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));
	}

	@Test
	public void movePackageBoxToTable1() {
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

		SystemVariant[] systemVariants;
		systemVariants = initial_system.prepareSystemVariants(rotateToTransportLine().getTemplate());
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_packageBox = new SystemObject(OBJECT_PACKAGE_BOX, packageBox_id);
		final_packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		final_packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, table_1_id));
		final SystemObject final_robot = robot.clone();
		final SystemObject final_table_1 = new SystemObject(OBJECT_TABLE_1, table_1_id);
		final_table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		final_table_1.addAttribute(new Attribute(ATTRIBUTE_LINEAR_DRIVE_POSITION, VALUE_TABLE_1));
		final_system.addObject(final_packageBox);
		final_system.addObject(final_robot);
		final_system.addObject(final_table_1);

		assertFalse(initial_system.equals(final_system));
		assertFalse(initial_system.partially_equals(final_system));

		final Element[] elements = new Element[] { rotateToTransportLine(), rotateToStation(), openGrab(), closeGrab(),
				liftUp(), lowerDown(), moveToPosition1(), moveToPosition2() };

		Planner planner = new Planner(initial_system, final_system, elements);
		planner.plan();

		List<String> operations = planner.getShortestPlan();
		assertEquals(7, operations.size());
		assertEquals(OPERATION_ROTATE_TO_TRANSPORT_LINE, operations.get(0));
		assertEquals(OPERATION_CLOSE_GRAB, operations.get(1));
		assertEquals(OPERATION_LIFT_UP, operations.get(2));
		// OPERATION_ROTATE_TO_STATION
		assertEquals(OPERATION_MOVE_TO_POSITION_1, operations.get(3));
		assertEquals(OPERATION_LOWER_DOWN, operations.get(4));
		assertEquals(OPERATION_OPEN_GRAB, operations.get(5));
		assertEquals(OPERATION_MOVE_TO_POSITION_2, operations.get(6));
	}
}
