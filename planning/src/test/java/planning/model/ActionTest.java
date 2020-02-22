package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

		testable.registerActionParameterUpdater(new ActionParameterUpdater() {
			@Override
			public void invoke(SystemVariant systemVariant) {
				assertEquals(systemVariant_mock, systemVariant);
			}
		});

		testable.updateActionParameters(systemVariant_mock);
	}

	@Test
	public void allConditionsPasses() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerActionPreConditionChecker(new ActionPreConditionChecker() {
			@Override
			public boolean invoke(SystemVariant systemVariant) {
				assertEquals(systemVariant_mock, systemVariant);
				return true;
			}
		});

		assertTrue(testable.haveAllPreConditionsPassed(systemVariant_mock));
	}

	@Test
	public void allConditionsPasses_false() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable.registerActionPreConditionChecker(new ActionPreConditionChecker() {
			@Override
			public boolean invoke(SystemVariant systemVariant) {
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
