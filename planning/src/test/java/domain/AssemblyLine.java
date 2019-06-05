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

	private static final String OBJECT_CONFIGURATION_STATION = "Станция комплектации";

	private static final String OBJECT_TRANSPORT_LINE = "Транспортная линия";

	private static final String ATTRIBUTE_GRAB = "захват";

	private static final String LINK_BELONGS = "принадлежит";

	private static final String LINK_ROTATED = "повернут";

	private static final String VALUE_OPEN = "открыт";

	public static Element rotateRobotToTransportLine() {
		final System template = new System();
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, "#ID-1");
		final SystemObject station = new SystemObject(OBJECT_CONFIGURATION_STATION, "#ID-2");
		final SystemObject line = new SystemObject(OBJECT_TRANSPORT_LINE, "#ID-3");

		final String robot_id = robot.getObjectId();
		final String station_id = station.getObjectId();
		final String transportLine_id = line.getObjectId();

		robot.addAttribute(new Attribute(ATTRIBUTE_GRAB, VALUE_OPEN));
		robot.addLink(new Link(LINK_BELONGS, station_id));
		robot.addLink(new Link(LINK_ROTATED, station_id));

		station.addLink(new Link(LINK_BELONGS, transportLine_id));
		station.addLink(new Link(LINK_ROTATED, robot_id));

		line.addLink(new Link(LINK_ROTATED, null));

		template.addObject(robot);
		template.addObject(station);
		template.addObject(line);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_ROTATED, transportLine_id),
				new LinkTransformation(station_id, LINK_ROTATED, null),
				new LinkTransformation(transportLine_id, LINK_ROTATED, robot_id) };

		return new Element("Повернуть робот-перекладчик к транспортной линии", template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final System initial_system = new System();
		final SystemObject initial_robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject initial_station = new SystemObject(OBJECT_CONFIGURATION_STATION);
		final SystemObject initial_line = new SystemObject(OBJECT_TRANSPORT_LINE);

		final String robot_id = initial_robot.getObjectId();
		final String station_id = initial_station.getObjectId();
		final String line_id = initial_line.getObjectId();

		initial_robot.addAttribute(new Attribute(ATTRIBUTE_GRAB, VALUE_OPEN));
		initial_robot.addLink(new Link(LINK_BELONGS, station_id));
		initial_robot.addLink(new Link(LINK_ROTATED, station_id));

		initial_station.addLink(new Link(LINK_BELONGS, line_id));
		initial_station.addLink(new Link(LINK_ROTATED, robot_id));

		initial_line.addLink(new Link(LINK_ROTATED, null));

		initial_system.addObject(initial_robot);
		initial_system.addObject(initial_station);
		initial_system.addObject(initial_line);

		System expected_system;
		System actual_system = initial_system.clone();
		Element element;
		SystemVariant[] systemVariants;

		expected_system = actual_system.clone();
		expected_system.getObjectById(robot_id).getLink(LINK_ROTATED).setObjectId(line_id);
		expected_system.getObjectById(station_id).getLink(LINK_ROTATED).setObjectId(null);
		expected_system.getObjectById(line_id).getLink(LINK_ROTATED).setObjectId(robot_id);

		element = rotateRobotToTransportLine();
		systemVariants = actual_system.matchIds(element.getTemplate());
		assertEquals(1, systemVariants.length);
		element.applyTo(systemVariants[0]);
		actual_system = systemVariants[0].getSystem();
		assertTrue(expected_system.equals(actual_system));
	}
}
