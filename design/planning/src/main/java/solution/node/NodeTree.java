package solution.node;

import model.SystemState;
import solution.SolutionRoutes;

/** Дерево решений */
public class NodeTree {

	/** просмотрены все узлы */
	public boolean haveNodeMatchingTarget() {
		// TODO Auto-generated method stub
		return false;
	}

	/** найден узел совпадающий с конечным узлом */
	public boolean haveUncheckedNodes() {
		// TODO Auto-generated method stub
		return false;
	}

	/** выбрать перспективный узел для просмотра */
	public Node getEffectiveNode(SystemState targetState) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * добавить пути в перечень решений
	 *
	 * для каждого найденного узла совпадающего с конечным узлом построить путь от
	 * начального узла
	 */
	public SolutionRoutes getSolutionRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	/** интегрировать дерево в поддерево */
	public void integrateWith(NodeTree nodeSubTree) {
		// TODO Auto-generated method stub
	}
}
