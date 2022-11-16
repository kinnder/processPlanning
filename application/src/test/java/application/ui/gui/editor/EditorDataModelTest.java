package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemProcess;

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
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		testable.loadSystemTransformations(systemTransformations_mock);
	}

	@Test
	public void saveSystemTransformations() {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		testable.loadSystemTransformations(systemTransformations_mock);

		assertEquals(systemTransformations_mock, testable.saveSystemTransformations());
	}

	@Test
	public void loadNodeNetwork() {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);

		testable.loadNodeNetwork(nodeNetwork_mock);
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
}
