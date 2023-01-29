package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ActionTest {

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

	Action testable;

	@BeforeEach
	public void setup() {
		testable = new Action("action");
	}

	@Test
	public void newInstance() {
		testable = new Action();
		assertTrue(testable.getName().startsWith("action-"));
		assertNotNull(testable.getActionFunctions());
		assertEquals(0, testable.getActionFunctions().size());
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new Action();
		Action testable2 = new Action();
		assertNotEquals(testable.getName(), testable2.getName());
	}

	@Test
	public void getName() {
		assertEquals("action", testable.getName());
	}

	@Test
	public void setName() {
		testable.setName("new-action");
		assertEquals("new-action", testable.getName());
	}

	@Test
	public void updateParameters() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		context.checking(new Expectations() {
			{
				allowing(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				allowing(actionFunction_2_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).setType(ActionFunction.TYPE_PARAMETER_UPDATER);

				oneOf(actionFunction_2_mock).setType(ActionFunction.TYPE_PRECONDITION_CHECKER);
			}
		});
		testable.registerParameterUpdater(actionFunction_1_mock);
		testable.registerPreConditionChecker(actionFunction_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).accept(systemVariant_mock);
			}
		});
		testable.updateParameters(systemVariant_mock);
	}

	@Test
	public void haveAllPreConditionsPassed_true() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		context.checking(new Expectations() {
			{
				allowing(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				allowing(actionFunction_2_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).setType(ActionFunction.TYPE_PARAMETER_UPDATER);

				oneOf(actionFunction_2_mock).setType(ActionFunction.TYPE_PRECONDITION_CHECKER);
			}
		});
		testable.registerParameterUpdater(actionFunction_1_mock);
		testable.registerPreConditionChecker(actionFunction_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_2_mock).test(systemVariant_mock);
				will(returnValue(true));
			}
		});
		assertTrue(testable.haveAllPreConditionsPassed(systemVariant_mock));
	}

	@Test
	public void haveAllPreConditionsPassed_false() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		context.checking(new Expectations() {
			{
				allowing(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				allowing(actionFunction_2_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).setType(ActionFunction.TYPE_PARAMETER_UPDATER);

				oneOf(actionFunction_2_mock).setType(ActionFunction.TYPE_PRECONDITION_CHECKER);
			}
		});
		testable.registerParameterUpdater(actionFunction_1_mock);
		testable.registerPreConditionChecker(actionFunction_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_2_mock).test(systemVariant_mock);
				will(returnValue(false));
			}
		});
		assertFalse(testable.haveAllPreConditionsPassed(systemVariant_mock));
	}

	@Test
	public void getParameterUpdaters() {
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		context.checking(new Expectations() {
			{
				allowing(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				allowing(actionFunction_2_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});
		testable.addActionFunction(actionFunction_1_mock);
		testable.addActionFunction(actionFunction_2_mock);

		List<ActionFunction> result = testable.getParameterUpdaters();
		assertEquals(1, result.size());
		assertTrue(result.contains(actionFunction_1_mock));
	}

	@Test
	public void getPreConditionCheckers() {
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		context.checking(new Expectations() {
			{
				allowing(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				allowing(actionFunction_2_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});
		testable.addActionFunction(actionFunction_1_mock);
		testable.addActionFunction(actionFunction_2_mock);

		List<ActionFunction> result = testable.getPreConditionCheckers();
		assertEquals(1, result.size());
		assertTrue(result.contains(actionFunction_2_mock));
	}

	@Test
	public void toString_test() {
		assertEquals("Action action", testable.toString());
	}

	@Test
	public void addActionFunction() {
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		testable.addActionFunction(actionFunction_1_mock);
		testable.addActionFunction(actionFunction_2_mock);
		assertEquals(2, testable.getActionFunctions().size());
	}

	@Test
	public void getActionFunctions() {
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		testable.addActionFunction(actionFunction_1_mock);
		testable.addActionFunction(actionFunction_2_mock);
		List<ActionFunction> result = testable.getActionFunctions();
		assertEquals(2, result.size());
		assertTrue(result.contains(actionFunction_1_mock));
		assertTrue(result.contains(actionFunction_2_mock));
	}

	@Test
	public void removeActionFunction() {
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		testable.addActionFunction(actionFunction_1_mock);
		testable.addActionFunction(actionFunction_2_mock);

		testable.removeActionFunction(actionFunction_2_mock);
		assertEquals(1, testable.getActionFunctions().size());
	}
}
