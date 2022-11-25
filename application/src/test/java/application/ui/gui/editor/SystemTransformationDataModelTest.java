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

import planning.model.SystemTransformation;

public class SystemTransformationDataModelTest {

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

	EditorDataModel editorDataModel_mock;

	JTextField jtfSystemTransformationName_mock;

	SystemTransformationDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jtfSystemTransformationName_mock = context.mock(JTextField.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfSystemTransformationName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new SystemTransformationDataModel(jtfSystemTransformationName_mock, editorDataModel_mock);
	}

	@Test
	public void loadSystemTransformation() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getName();
				will(returnValue("element-name"));

				oneOf(jtfSystemTransformationName_mock).setText("element-name");
			}
		});

		testable.loadSystemTransformation(systemTransformation_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void systemTransformationName_keyReleased() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getName();
				will(returnValue("element-name"));

				oneOf(jtfSystemTransformationName_mock).setText("element-name");
			}
		});
		testable.loadSystemTransformation(systemTransformation_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfSystemTransformationName_mock).getText();
				will(returnValue("new-element-name"));

				oneOf(systemTransformation_mock).setName("new-element-name");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfSystemTransformationNameKeyAdapter.keyReleased(keyEvent_mock);
	}
}
