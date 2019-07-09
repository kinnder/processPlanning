package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

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
	public void getName() {
		assertEquals("action", testable.getName());
	}

	@Test
	public void getParameter() {
		assertEquals(null, testable.getParameter("parameter-name"));
	}

	@Test
	public void updateParameters() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(SystemVariant systemVariant, Map<String, String> parameters) {
				assertEquals(systemVariant_mock, systemVariant);

				parameters.put("parameter-name", "parameter-value");
			}
		});

		testable.updateParameters(systemVariant_mock);

		assertEquals("parameter-value", testable.getParameter("parameter-name"));
	}

	@Test
	public void allConditionsPasses() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerConditionChecker(new ConditionChecker() {
			@Override
			public boolean invoke(SystemVariant systemVariant, Map<String, String> parameters) {
				assertEquals(systemVariant_mock, systemVariant);

				return true;
			}
		});

		assertTrue(testable.allConditionsPasses(systemVariant_mock));
	}

	@Test
	public void clone_test() throws CloneNotSupportedException {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(SystemVariant systemVariant, Map<String, String> parameters) {
				String value = parameters.get("parameter");
				if (value == null) {
					value = "one";
				} else {
					value = "two";
				}
				parameters.put("parameter", value);
			}
		});
		testable.updateParameters(systemVariant_mock);

		testable.registerConditionChecker(new ConditionChecker() {
			@Override
			public boolean invoke(SystemVariant systemVariant, Map<String, String> parameters) {
				return false;
			}
		});

		Action cloned = testable.clone();
		assertNotEquals(cloned, testable);

		cloned.updateParameters(systemVariant_mock);
		assertEquals("one", testable.getParameter("parameter"));
		assertEquals("two", cloned.getParameter("parameter"));

		assertFalse(cloned.allConditionsPasses(systemVariant_mock));
	}
}
