package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemProcess;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

public class EditorDataModelTest {

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

	EditorDataModel testable;

	@BeforeEach
	public void setup() {
		testable = new EditorDataModel();
	}

	@Test
	public void newInstance() {
	}

	@Test
	public void loadTaskDescription() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final System initialSystem_mock = context.mock(System.class, "initial-system");
		final System finalSystem_mock = context.mock(System.class, "final-system");
		final Collection<SystemObject> objects = new ArrayList<SystemObject>();

		context.checking(new Expectations() {
			{
				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				// TODO (2022-11-14 #72): перенести в TaskDescription
				oneOf(initialSystem_mock).setName("initialSystem");

				oneOf(initialSystem_mock).getObjects();
				will(returnValue(objects));

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				// TODO (2022-11-14 #72): перенести в TaskDescription
				oneOf(finalSystem_mock).setName("finalSystem");

				oneOf(finalSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});

		testable.loadTaskDescription(taskDescription_mock);
	}

	@Test
	public void saveTaskDescription() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final System initialSystem_mock = context.mock(System.class, "initial-system");
		final System finalSystem_mock = context.mock(System.class, "final-system");
		final Collection<SystemObject> objects = new ArrayList<SystemObject>();

		context.checking(new Expectations() {
			{
				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				oneOf(initialSystem_mock).setName("initialSystem");

				oneOf(initialSystem_mock).getObjects();
				will(returnValue(objects));

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				oneOf(finalSystem_mock).setName("finalSystem");

				oneOf(finalSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadTaskDescription(taskDescription_mock);

		assertEquals(taskDescription_mock, testable.saveTaskDescription());
	}

	@Test
	public void loadSystemTransformations() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		systemTransformations.add(systemTransformation_mock);
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final List<SystemObjectTemplate> systemObjectTemplates = new ArrayList<SystemObjectTemplate>();
		final Transformations transformations = new Transformations();
		final Action action_mock = context.mock(Action.class);
		final List<ActionFunction> parameterUpdaters = new ArrayList<ActionFunction>();
		final List<ActionFunction> preConditionCheckers = new ArrayList<ActionFunction>();

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getSystemTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(systemObjectTemplates));

				oneOf(systemTransformation_mock).getTransformations();
				will(returnValue(transformations));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));
			}
		});

		testable.loadSystemTransformations(systemTransformations);
	}

	@Test
	public void saveSystemTransformations() {
		final SystemTransformations systemTransformations = new SystemTransformations();

		testable.loadSystemTransformations(systemTransformations);

		assertEquals(systemTransformations, testable.saveSystemTransformations());
	}

	@Test
	public void loadNodeNetwork() {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		List<Node> nodes = new ArrayList<Node>();
		final Node node_mock = context.mock(Node.class);
		nodes.add(node_mock);
		List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_mock = context.mock(Edge.class);
		edges.add(edge_mock);
		final System system_mock = context.mock(System.class);
		List<SystemObject> systemObjects = new ArrayList<SystemObject>();
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		systemObjects.add(systemObject_mock);

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).getNodes();
				will(returnValue(nodes));

				oneOf(node_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(system_mock).getObjects();
				will(returnValue(systemObjects));

				oneOf(nodeNetwork_mock).getEdges();
				will(returnValue(edges));
			}
		});

		testable.loadNodeNetwork(nodeNetwork_mock);
	}

	@Test
	public void createEdgeNode() {
		final Edge edge_mock = context.mock(Edge.class);

		DefaultMutableTreeNode result = testable.createEdgeNode(edge_mock);
		assertEquals(edge_mock, result.getUserObject());
	}

	@Test
	public void createNodeNode() {
		final Node node_mock = context.mock(Node.class);
		final System system_mock = context.mock(System.class);
		List<SystemObject> systemObjects = new ArrayList<SystemObject>();
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		systemObjects.add(systemObject_mock);

		context.checking(new Expectations() {
			{
				oneOf(node_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(system_mock).getObjects();
				will(returnValue(systemObjects));
			}
		});

		DefaultMutableTreeNode result = testable.createNodeNode(node_mock);
		assertEquals(node_mock, result.getUserObject());
		assertEquals(1, result.getChildCount());
	}

	@Test
	public void loadSystemProcess() {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);

		testable.loadSystemProcess(systemProcess_mock);
	}

	@Test
	public void createObjectNode() {
		final SystemObject object_mock = context.mock(SystemObject.class);

		DefaultMutableTreeNode result = testable.createObjectNode(object_mock);
		assertEquals(object_mock, result.getUserObject());
	}

	@Test
	public void createObjectTemplateNode() {
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);

		DefaultMutableTreeNode result = testable.createObjectTemplateNode(objectTemplate_mock);
		assertEquals(objectTemplate_mock, result.getUserObject());
	}

	@Test
	public void createSystemNode() {
		final System system_mock = context.mock(System.class);
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		final Collection<SystemObject> objects = new ArrayList<SystemObject>();
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjects();
				will(returnValue(objects));
			}
		});

		DefaultMutableTreeNode result = testable.createSystemNode(system_mock);
		assertEquals(system_mock, result.getUserObject());
		assertEquals(2, result.getChildCount());
	}

	@Test
	public void createSystemTemplateNode() {
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		final Collection<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});

		DefaultMutableTreeNode result = testable.createSystemTemplateNode(systemTemplate_mock);
		assertEquals(systemTemplate_mock, result.getUserObject());
		assertEquals(2, result.getChildCount());
	}

	@Test
	public void createSystemTransformationNode() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final List<SystemObjectTemplate> systemObjectTemplates = new ArrayList<SystemObjectTemplate>();
		final Transformations transformations = new Transformations();
		final Action action_mock = context.mock(Action.class);
		final List<ActionFunction> parameterUpdaters = new ArrayList<ActionFunction>();
		final List<ActionFunction> preConditionCheckers = new ArrayList<ActionFunction>();

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getSystemTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(systemObjectTemplates));

				oneOf(systemTransformation_mock).getTransformations();
				will(returnValue(transformations));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));
			}
		});

		DefaultMutableTreeNode result = testable.createSystemTransformationNode(systemTransformation_mock);
		assertEquals(systemTransformation_mock, result.getUserObject());
	}

	@Test
	public void createActionNode() {
		final Action action_mock = context.mock(Action.class);
		final List<ActionFunction> parameterUpdaters = new ArrayList<ActionFunction>();
		final List<ActionFunction> preConditionCheckers = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		parameterUpdaters.add(actionFunction_1_mock);
		preConditionCheckers.add(actionFunction_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));
			}
		});

		DefaultMutableTreeNode result = testable.createActionNode(action_mock);
		assertEquals(action_mock, result.getUserObject());
		assertEquals(2, result.getChildCount());
	}

	@Test
	public void createActionFunctionNode() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);

		DefaultMutableTreeNode result = testable.createActionFunctionNode(actionFunction_mock);
		assertEquals(actionFunction_mock, result.getUserObject());
	}

	@Test
	public void createTransformationsNode() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		final Transformation transformation_2_mock = context.mock(Transformation.class, "transformation-2");
		transformations.add(transformation_1_mock);
		transformations.add(transformation_2_mock);

		DefaultMutableTreeNode result = testable.createTransformationsNode(transformations);
		assertEquals(transformations, result.getUserObject());
		assertEquals(2, result.getChildCount());
	}

	@Test
	public void createTransformationNode() {
		final Transformation transformation_mock = context.mock(Transformation.class);

		DefaultMutableTreeNode result = testable.createTransformationNode(transformation_mock);
		assertEquals(transformation_mock, result.getUserObject());
	}
}
