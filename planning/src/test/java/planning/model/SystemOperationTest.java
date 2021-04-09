package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemOperationTest {

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

	SystemOperation testable;

	Action action_mock;

	Map<String, String> actionParameters_mock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		action_mock = context.mock(Action.class);
		actionParameters_mock = context.mock(Map.class);

		testable = new SystemOperation(action_mock, actionParameters_mock);
	}

	@Test
	public void getAction() {
		assertEquals(action_mock, testable.getAction());
	}

	@Test
	public void getActionParameters() {
		assertEquals(actionParameters_mock, testable.getActionParameters());
	}

	@Test
	public void setActionParameters() {
		testable.setActionParameters(null);

		assertNull(testable.getActionParameters());
	}

	@Test
	public void getName() {
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("action-name"));
			}
		});

		assertEquals("action-name", testable.getName());
	}

	@Test
	public void getParameter() {
		context.checking(new Expectations() {
			{
				oneOf(actionParameters_mock).get("parameter-name");
				will(returnValue("parameter-value"));
			}
		});

		assertEquals("parameter-value", testable.getParameter("parameter-name"));
	}
}
