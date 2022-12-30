package application.ui.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Edge;
import planning.model.SystemOperation;

public class OperationDataModelTest {

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

	JTextField jtfOperationName_mock;

	EditorDataModel editorDataModel_mock;

	OperationDataModel testable;

	@BeforeEach
	public void setup() {
		jtfOperationName_mock = context.mock(JTextField.class);
		editorDataModel_mock = context.mock(EditorDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfOperationName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new OperationDataModel(jtfOperationName_mock, editorDataModel_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadOperation() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final SystemOperation operation_mock = context.mock(SystemOperation.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getName();
				will(returnValue("operation-name"));

				oneOf(jtfOperationName_mock).setText("operation-name");
			}
		});

		testable.loadOperation(selectedObject_mock, selectedNode_mock);
	}

	@Test
	public void operationName_keyReleased() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final SystemOperation operation_mock = context.mock(SystemOperation.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getName();
				will(returnValue("operation-name"));

				oneOf(jtfOperationName_mock).setText("operation-name");
			}
		});
		testable.loadOperation(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfOperationName_mock).getText();
				will(returnValue("new-operation-name"));

				oneOf(operation_mock).setName("new-operation-name");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfOperationNameKeyAdapter.keyReleased(keyEvent_mock);
	}
}
