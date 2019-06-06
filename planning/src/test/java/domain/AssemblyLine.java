package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import planning.model.Attribute;
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

	private static final String LINK_BELONGS = "принадлежит";

	private static final String LINK_GRAB_POSITION = "положение захвата";

	private static final String LINK_ROTARY_DRIVE_POSITION = "положение поворотного привода";

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
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#ID-4");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#ID-3");

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();

		robot.addLink(new Link(LINK_BELONGS, station_id));

		rotaryDrive.addLink(new Link(LINK_BELONGS, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, station_id));

		station.addLink(new Link(LINK_BELONGS, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));

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

		robot.addLink(new Link(LINK_BELONGS, station_id));

		rotaryDrive.addLink(new Link(LINK_BELONGS, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, line_id));

		station.addLink(new Link(LINK_BELONGS, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));

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
		return new Element(OPERATION_CLOSE_GRAB, null, null);
	}

	public static Element liftUp() {
		return new Element(OPERATION_LIFT_UP, null, null);
	}

	public static Element lowerDown() {
		return new Element(OPERATION_LOWER_DOWN, null, null);
	}

	public static Element moveToPosition1() {
		return new Element(OPERATION_MOVE_TO_POSITION_1, null, null);
	}

	public static Element moveToPosition2() {
		return new Element(OPERATION_MOVE_TO_POSITION_2, null, null);
	}

	@Test
	public void applicationOfElements() {
		final System initial_system = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject rotaryDrive = new SystemObject(OBJECT_ROTARY_DRIVE);
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION);
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE);

		final String robot_id = robot.getObjectId();
		final String rotaryDrive_id = rotaryDrive.getObjectId();
		final String station_id = station.getObjectId();
		final String line_id = line.getObjectId();

		robot.addLink(new Link(LINK_BELONGS, station_id));

		rotaryDrive.addLink(new Link(LINK_BELONGS, robot_id));
		rotaryDrive.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, station_id));

		station.addLink(new Link(LINK_BELONGS, line_id));
		station.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, rotaryDrive_id));

		line.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));

		initial_system.addObject(robot);
		initial_system.addObject(rotaryDrive);
		initial_system.addObject(station);
		initial_system.addObject(line);

		System expected_system;
		System actual_system = initial_system.clone();
		Element element;
		SystemVariant[] systemVariants;

		expected_system = actual_system.clone();
		expected_system.getObjectById(rotaryDrive_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(line_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(null);
		expected_system.getObjectById(line_id).getLink(LINK_ROTARY_DRIVE_POSITION).setObjectId(rotaryDrive_id);

		element = rotateToTransportLine();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));

		expected_system = actual_system.clone();
		// TODO : configure expected system

		element = closeGrab();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));
	}
}
