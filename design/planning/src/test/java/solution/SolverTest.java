package solution;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.DesignTask;
import model.SystemState;
import solution.node.Node;
import solution.node.NodeTree;
import solution.node.NodeTreeBuilder;

public class SolverTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	Solver solver;

	NodeTreeBuilder noteTreeBuilder_mock;

	@Before
	public void setUp() {
		noteTreeBuilder_mock = context.mock(NodeTreeBuilder.class);

		solver = new Solver(noteTreeBuilder_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void solve_targetStateMatchesInitialState() {
		final SolutionRoutes solutionRoutes_mock = context.mock(SolutionRoutes.class);
		final DesignTask task_mock = context.mock(DesignTask.class);
		final NodeTree nodeTree_mock = context.mock(NodeTree.class);

		context.checking(new Expectations() {
			{
				oneOf(noteTreeBuilder_mock).buildNodeTree(null, task_mock);
				will(returnValue(nodeTree_mock));

				oneOf(nodeTree_mock).haveNodeMatchingTarget();
				will(returnValue(true));

				oneOf(nodeTree_mock).getSolutionRoutes();
				will(returnValue(solutionRoutes_mock));
			}
		});

		Assert.assertEquals(solutionRoutes_mock, solver.solve(task_mock));
	}

	@Test
	public void solve_task_is_not_valid() {
		final SolutionRoutes solutionRoutes_mock = context.mock(SolutionRoutes.class);
		final DesignTask task_mock = context.mock(DesignTask.class);
		final NodeTree nodeTree_mock = context.mock(NodeTree.class);

		context.checking(new Expectations() {
			{
				oneOf(noteTreeBuilder_mock).buildNodeTree(null, task_mock);
				will(returnValue(nodeTree_mock));

				oneOf(nodeTree_mock).haveNodeMatchingTarget();
				will(returnValue(false));

				oneOf(nodeTree_mock).haveUncheckedNodes();
				will(returnValue(false));

				oneOf(nodeTree_mock).getSolutionRoutes();
				will(returnValue(solutionRoutes_mock));
			}
		});

		Assert.assertEquals(solutionRoutes_mock, solver.solve(task_mock));
	}

	@Test
	public void solve() {
		final SolutionRoutes solutionRoutes_mock = context.mock(SolutionRoutes.class);
		final DesignTask task_mock = context.mock(DesignTask.class, "task");
		final NodeTree nodeTree_mock = context.mock(NodeTree.class, "nodeTree");
		final SystemState targetState_mock = context.mock(SystemState.class, "targetState");
		final Node node_mock = context.mock(Node.class, "node");
		final SystemState initialState_mock = context.mock(SystemState.class, "initialState");
		final DesignTask subTask_mock = context.mock(DesignTask.class, "subTask");
		final NodeTree subNodeTree_mock = context.mock(NodeTree.class, "subNodeTree");

		context.checking(new Expectations() {
			{
				oneOf(noteTreeBuilder_mock).buildNodeTree(null, task_mock);
				will(returnValue(nodeTree_mock));

				oneOf(nodeTree_mock).haveNodeMatchingTarget();
				will(returnValue(false));

				oneOf(nodeTree_mock).haveUncheckedNodes();
				will(returnValue(true));

				oneOf(task_mock).getTargetState();
				will(returnValue(targetState_mock));

				oneOf(nodeTree_mock).getEffectiveNode(targetState_mock);
				will(returnValue(node_mock));

				oneOf(node_mock).getState();
				will(returnValue(initialState_mock));

				oneOf(task_mock).createSubTask(initialState_mock);
				will(returnValue(subTask_mock));

				oneOf(noteTreeBuilder_mock).buildNodeTree(node_mock, subTask_mock);
				will(returnValue(subNodeTree_mock));

				oneOf(nodeTree_mock).integrateWith(subNodeTree_mock);

				oneOf(nodeTree_mock).haveNodeMatchingTarget();
				will(returnValue(true));

				oneOf(nodeTree_mock).getSolutionRoutes();
				will(returnValue(solutionRoutes_mock));
			}
		});

		Assert.assertEquals(solutionRoutes_mock, solver.solve(task_mock));
	}
}
