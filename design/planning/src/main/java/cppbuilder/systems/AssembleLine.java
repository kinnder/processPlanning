package cppbuilder.systems;

import cppbuilder.Attribute;
import cppbuilder.ElementBase;
import cppbuilder.SystemObject;
import cppbuilder.SystemState;
import cppbuilder.objects.RobotManipulator;
import cppbuilder.objects.RobotPickAndPlace;

/** Предметная область - сборочная линия */
public class AssembleLine {

	public static SystemState create_ConfigurationWorkstation() {
		SystemState state = new SystemState();
		state.systemObjects.add(new RobotPickAndPlace());
		state.systemObjects.add(new RobotManipulator());
		return state;
	}

	public static void add_LensesInFrameTemplates(SystemState l_begin) {
	}

	public static SystemState create_LensesInFrame() {
		// TODO code
		return new SystemState();
	}

	/** Создать базу типовых элементов - операции линии сборки */
	public static ElementBase assembling() {
		// TODO добавить элементы
		return new ElementBase();
	}

	/** Создать состояние системы - робот перекладчик */
	public static SystemState robotPickAndPlace(String startPosition) {
		SystemObject object = new RobotPickAndPlace();
		object.attributes.find(Attribute.ATTRIBUTE_POSITION).value = startPosition;
		SystemState state = new SystemState();
		state.systemObjects.add(object);
		return state;
	}
}
