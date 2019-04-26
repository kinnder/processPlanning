package cppbuilder;

import java.util.List;

import cppbuilder.knowledge.CoordinateCalculator;
import cppbuilder.management.Command;
import cppbuilder.objects.Part;

/** Модуль проектирования */
public class Designing {

	/** Дерево решения */
	public SystemStateTree nodeTree = new SystemStateTree();

	/** Преобразование позиции в координату */
	public CoordinateCalculator calculator = new CoordinateCalculator();

	/** Проектирование */
	public void prepareCommands(List<Part> parts, List<Command> commands) {
		addCommonStartCommands(commands);

		for (Part part : parts) {
			addPartSpecificCommand(part, commands);
		}

		addCommonFinishCommands(commands);
	}

	/** Решение задачи */
	public boolean solve(SystemState begin, SystemState end, ElementBase elements) {
		nodeTree.buildTree(begin, end, elements);
		nodeTree.prepareRoutes();
		return (nodeTree.routes.size() > 0);
	}

	/** Получить решение */
	public Route getSolution() {
		return nodeTree.routes.getShortestRoute();
	}

	private Command command;

	/** Добавление общей начальной части */
	protected void addCommonStartCommands(List<Command> commands) {
		// РП - перенос тары с транспортной линии в позицию 2
		command = new Command(1, 2, 0, 0, 0);
		commands.add(command);
	}

	/** Добавление общей конечной части */
	protected void addCommonFinishCommands(List<Command> commands) {
		// РМ - горизонтальное перемещение (0,0)
		command = new Command(2, 1, 0, 0, 0);
		commands.add(command);

		// РП - перенос тары с позиции 2 на транспортную линию
		command = new Command(1, 4, 0, 0, 0);
		commands.add(command);
	}

	/** Добавление сборки узла */
	protected void addPartSpecificCommand(Part part, List<Command> commands) {
		// РМ - горизонтальное перемещение ()
		command = new Command(2, 1, calculator.getXCoordinateForPosition(part.lens.position),
				calculator.getYCoordinateForPosition(part.lens.position), 0);
		commands.add(command);

		// РМ - вертикальное перемещение (1[опустить], 1[сжать])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(1), 1, 0);
		commands.add(command);

		// РМ - вертикальное перемещение (0[поднять], 0[ничего])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(0), 0, 0);
		commands.add(command);

		// РМ - горизонтальное перемещение ()
		command = new Command(2, 1, calculator.getXCoordinateForPosition(part.frame.position),
				calculator.getYCoordinateForPosition(part.frame.position), 0);
		commands.add(command);

		// РМ - вертикальное перемещение (1[опустить], 2[разжать])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(1), 2, 0);
		commands.add(command);

		// РМ - вертикальное перемещение (0[поднять], 0[ничего])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(0), 0, 0);
		commands.add(command);
	}
}
