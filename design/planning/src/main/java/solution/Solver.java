package solution;

import model.DesignTask;
import solution.node.Node;
import solution.node.NodeTree;
import solution.node.NodeTreeBuilder;

/** Решатель */
public class Solver {

	/** Построитель дерева переходов */
	NodeTreeBuilder transitionTreeBuilder;

	/**
	 * Конструктор
	 *
	 * @param transitionTreeBuilder - построитель дерева переходов
	 */
	public Solver(NodeTreeBuilder transitionTreeBuilder) {
		this.transitionTreeBuilder = transitionTreeBuilder;
	}

	/**
	 * Решить
	 *
	 * @param task - задание
	 * @return решение
	 */
	public SolutionRoutes solve(DesignTask task) {
		NodeTree nodeTree = transitionTreeBuilder.buildNodeTree(null, task);
		while (!nodeTree.haveNodeMatchingTarget() && nodeTree.haveUncheckedNodes()) {
			Node node = nodeTree.getEffectiveNode(task.getTargetState());
			DesignTask subTask = task.createSubTask(node.getState());
			NodeTree nodeSubTree = transitionTreeBuilder.buildNodeTree(node, subTask);
			nodeTree.integrateWith(nodeSubTree);
		}
		return nodeTree.getSolutionRoutes();
	}
}
