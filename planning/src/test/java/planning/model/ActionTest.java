package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
		final System system_mock = context.mock(System.class);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		testable.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				assertEquals(system_mock, system);
				assertEquals(idsMatching_mock, idsMatching);

				parameters.put("parameter-name", "parameter-value");
			}
		});

		testable.updateParameters(system_mock, idsMatching_mock);

		assertEquals("parameter-value", testable.getParameter("parameter-name"));
	}

	@Test
	public void clone_test() {
		final System system_mock = context.mock(System.class);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		testable.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				String value = parameters.get("parameter");
				if (value == null) {
					value = "one";
				} else {
					value = "two";
				}
				parameters.put("parameter", value);
			}
		});
		testable.updateParameters(system_mock, idsMatching_mock);

		Action cloned = testable.clone();
		assertNotEquals(cloned, testable);

		cloned.updateParameters(system_mock, idsMatching_mock);
		assertEquals("one", testable.getParameter("parameter"));
		assertEquals("two", cloned.getParameter("parameter"));
	}
}
