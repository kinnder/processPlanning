package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
	public void updateParameters() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerParameterUpdater(new ActionFunction() {
			@Override
			public void accept(SystemVariant systemVariant) {
				assertEquals(systemVariant_mock, systemVariant);
			}

			@Override
			public boolean test(SystemVariant t) {
				fail();
				return false;
			}
		});

		testable.updateParameters(systemVariant_mock);
	}

	@Test
	public void haveAllPreConditionsPassed_true() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerPreConditionChecker(new ActionFunction() {
			@Override
			public void accept(SystemVariant t) {
				fail();
			}

			@Override
			public boolean test(SystemVariant systemVariant) {
				assertEquals(systemVariant_mock, systemVariant);
				return true;
			}
		});

		assertTrue(testable.haveAllPreConditionsPassed(systemVariant_mock));
	}

	@Test
	public void haveAllPreConditionsPassed_false() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerPreConditionChecker(new ActionFunction() {
			@Override
			public void accept(SystemVariant t) {
				fail();
			}

			@Override
			public boolean test(SystemVariant systemVariant) {
				assertEquals(systemVariant_mock, systemVariant);
				return false;
			}
		});

		assertFalse(testable.haveAllPreConditionsPassed(systemVariant_mock));
	}

	@Test
	public void getParameterUpdaters() {
		assertEquals(0, testable.getParameterUpdaters().size());
	}

	@Test
	public void getPreConditionCheckers() {
		assertEquals(0, testable.getPreConditionCheckers().size());
	}
}
