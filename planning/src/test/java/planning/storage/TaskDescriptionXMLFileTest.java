package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.TaskDescription;

public class TaskDescriptionXMLFileTest {

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

	TaskDescriptionXMLFile testable;

	@BeforeEach
	public void setup() {
		testable = new TaskDescriptionXMLFile();
	}

	@Test
	public void getTaskDescription() {
		assertTrue(testable.getTaskDescription() instanceof TaskDescription);
	}

	@Test
	public void setTaskDescription() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		testable.setTaskDescription(taskDescription_mock);
		assertEquals(taskDescription_mock, testable.getTaskDescription());
	}

	@Test
	public void getXMLModel() {
		assertTrue(testable.getXMLModel() instanceof TaskDescriptionXMLModel);
	}
}
