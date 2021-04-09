package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.System;

public class TaskDescriptionTest {

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

	TaskDescription testable;

	@BeforeEach
	public void setup() {
		testable = new TaskDescription();
	}

	@Test
	public void getInitialSystem() {
		assertNull(testable.getInitialSystem());
	}

	@Test
	public void setInitialSystem() {
		final System system_mock = context.mock(System.class);
		testable.setInitialSystem(system_mock);

		assertEquals(system_mock, testable.getInitialSystem());
	}

	@Test
	public void getFinalSystem() {
		assertNull(testable.getFinalSystem());
	}

	@Test
	public void setFinalSystem() {
		final System system_mock = context.mock(System.class);
		testable.setFinalSystem(system_mock);

		assertEquals(system_mock, testable.getFinalSystem());
	}
}
