package asm.elements;

import model.Element;
import model.SystemModificationLinkChange;
import model.SystemModificationLinkChange.LinkChangeType;
import model.SystemObject;
import model.SystemState;
import model.SystemTransition;
import utility.SystemObjectBuilder;
import utility.SystemStateBuilder;

public class RotateToTransportLine {

	private static SystemObject Robot() {
		return new SystemObjectBuilder().addAttribute("class", "Робот - перекладчик").build();
	}

	private static SystemObject Grab() {
		return new SystemObjectBuilder().addAttribute("class", "Захват").addAttribute("положение ЗХВ", "1").build();
	}

	private static SystemObject RotaryDrive() {
		return new SystemObjectBuilder().addAttribute("class", "Поворотный привод").addAttribute("положение ПП", "1")
				.build();
	}

	private static SystemObject Station() {
		return new SystemObjectBuilder().addAttribute("class", "Станция комплектации").addAttribute("положение ПП", "1")
				.build();
	}

	private static SystemObject Line() {
		return new SystemObjectBuilder().addAttribute("class", "Транспортная линия зоны измерений")
				.addAttribute("положение ПП", "1").build();
	}

	public static Element getElement() {
		SystemObject robot, grab, rotaryDrive, station, line;
		SystemState state = new SystemStateBuilder().addObject(robot = Robot()).addObject(grab = Grab())
				.addObject(rotaryDrive = RotaryDrive()).addObject(station = Station()).addObject(line = Line())
				.linkObjects(robot, station).linkObjects(robot, grab).linkObjects(station, robot)
				.linkObjects(station, line).linkObjects(station, rotaryDrive, "положение ПП").build();

		SystemModificationLinkChange modification_1 = new SystemModificationLinkChange();
		modification_1.linkChangeType = LinkChangeType.disconnect;
		modification_1.object_1 = station;
		modification_1.object_2 = rotaryDrive;
		modification_1.linkName = "положение ПП";

		SystemModificationLinkChange modification_2 = new SystemModificationLinkChange();
		modification_2.linkChangeType = LinkChangeType.connect;
		modification_2.object_1 = line;
		modification_2.object_2 = rotaryDrive;
		modification_2.linkName = "положение ПП";

		SystemTransition transition = new SystemTransition();
		transition.name = "Развернуть поворотный привод по направлению к транспортной линии";
		transition.modifications.add(modification_1);
		transition.modifications.add(modification_2);

		return new Element(state, transition);
	}

	public static SystemState getStateBeforeTransition() {
		return getElement().getStatePattern();
	}

	public static SystemState getStateAfterTransition() {
		SystemObject robot, grab, rotaryDrive, station, line;
		SystemState state = new SystemStateBuilder().addObject(robot = Robot()).addObject(grab = Grab())
				.addObject(rotaryDrive = RotaryDrive()).addObject(station = Station()).addObject(line = Line())
				.linkObjects(robot, station).linkObjects(robot, grab).linkObjects(station, robot)
				.linkObjects(station, line).linkObjects(line, rotaryDrive, "положение ПП").build();

		return state;
	}
}
