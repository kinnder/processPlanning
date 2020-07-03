package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.SystemTransformation;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemProcess;
import planning.model.SystemVariant;

public class PlannerTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	Planner testable;

	TaskDescription taskDescription_mock;

	System initial_system_mock;

	System final_system_mock;

	SystemTransformation systemTransformation_mock;

	SystemTransformations systemTransformations;

	NodeNetwork nodeNetwork_mock;

	@BeforeEach
	public void setup() {
		taskDescription_mock = context.mock(TaskDescription.class);
		initial_system_mock = context.mock(System.class, "initial-system");
		final_system_mock = context.mock(System.class, "final-system");
		systemTransformation_mock = context.mock(SystemTransformation.class, "systemTransformation");
		systemTransformations = new SystemTransformations();
		systemTransformations.add(systemTransformation_mock);
		nodeNetwork_mock = context.mock(NodeNetwork.class);

		context.checking(new Expectations() {
			{
				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initial_system_mock));

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(final_system_mock));
			}
		});

		testable = new Planner(taskDescription_mock, systemTransformations, nodeNetwork_mock);
	}

	@Test
	public void plan() throws CloneNotSupportedException {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);
		final Map<?, ?> actionParameters_mock = context.mock(Map.class);
		final Node initialNode_mock = context.mock(Node.class, "initial-node");
		final Node finalNode_mock = context.mock(Node.class, "final-node");

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).createNode(initial_system_mock);
				will(returnValue(initialNode_mock));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(true));

				oneOf(nodeNetwork_mock).nextUncheckedNode();
				will(returnValue(initialNode_mock));

				oneOf(initialNode_mock).getSystem();
				will(returnValue(initial_system_mock));

				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(systemTransformation_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(nodeNetwork_mock).findNode(system_mock);
				will(returnValue(null));

				oneOf(nodeNetwork_mock).createNode(system_mock);
				will(returnValue(finalNode_mock));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(systemVariant_mock).getActionParameters();
				will(returnValue(actionParameters_mock));

				oneOf(nodeNetwork_mock).createEdge(with(equal(initialNode_mock)), with(equal(finalNode_mock)),
						with(any(SystemOperation.class)));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(true));

				oneOf(nodeNetwork_mock).nextUncheckedNode();
				will(returnValue(finalNode_mock));

				oneOf(finalNode_mock).getSystem();
				will(returnValue(final_system_mock));

				oneOf(final_system_mock).contains(final_system_mock);
				will(returnValue(true));
			}
		});

		testable.plan();
	}

	@Test
	public void plan_no_variants() throws CloneNotSupportedException {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);
		final Map<?, ?> actionParameters_mock = context.mock(Map.class);
		final Node initialNode_mock = context.mock(Node.class, "initial-node");

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).createNode(initial_system_mock);
				will(returnValue(initialNode_mock));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(true));

				oneOf(nodeNetwork_mock).nextUncheckedNode();
				will(returnValue(initialNode_mock));

				oneOf(initialNode_mock).getSystem();
				will(returnValue(initial_system_mock));

				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(systemTransformation_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(nodeNetwork_mock).findNode(system_mock);
				will(returnValue(initialNode_mock));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(systemVariant_mock).getActionParameters();
				will(returnValue(actionParameters_mock));

				oneOf(nodeNetwork_mock).createEdge(with(equal(initialNode_mock)), with(equal(initialNode_mock)),
						with(any(SystemOperation.class)));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(false));
			}
		});

		testable.plan();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getShortestProcess() throws CloneNotSupportedException {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);
		final Map<?, ?> actionParameters_mock = context.mock(Map.class);
		final Node initialNode_mock = context.mock(Node.class, "initial-node");
		final Node finalNode_mock = context.mock(Node.class, "final-node");

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).createNode(initial_system_mock);
				will(returnValue(initialNode_mock));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(true));

				oneOf(nodeNetwork_mock).nextUncheckedNode();
				will(returnValue(initialNode_mock));

				oneOf(initialNode_mock).getSystem();
				will(returnValue(initial_system_mock));

				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(systemTransformation_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(nodeNetwork_mock).findNode(system_mock);
				will(returnValue(null));

				oneOf(nodeNetwork_mock).createNode(system_mock);
				will(returnValue(finalNode_mock));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(systemVariant_mock).getActionParameters();
				will(returnValue(actionParameters_mock));

				oneOf(nodeNetwork_mock).createEdge(with(equal(initialNode_mock)), with(equal(finalNode_mock)),
						with(any(SystemOperation.class)));

				oneOf(nodeNetwork_mock).hasNextUncheckedNode();
				will(returnValue(true));

				oneOf(nodeNetwork_mock).nextUncheckedNode();
				will(returnValue(finalNode_mock));

				oneOf(finalNode_mock).getSystem();
				will(returnValue(final_system_mock));

				oneOf(final_system_mock).contains(final_system_mock);
				will(returnValue(true));
			}
		});

		testable.plan();

		final GraphPath<Node, Edge> graphPath_mock = context.mock(GraphPath.class);
		final Edge edge_mock = context.mock(Edge.class);
		final List<Edge> edges = new ArrayList<>();
		edges.add(edge_mock);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).getShortestPath(initialNode_mock, finalNode_mock);
				will(returnValue(graphPath_mock));

				oneOf(graphPath_mock).getEdgeList();
				will(returnValue(edges));

				oneOf(edge_mock).getSystemOperation();
				will(returnValue(systemOperation_mock));
			}
		});

		SystemProcess process = testable.getShortestProcess();
		assertEquals(1, process.size());
		assertEquals(systemOperation_mock, process.get(0));
	}
}
