package application.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.event.Event.Type;

public class EventTest {

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

	Event testable;

	@BeforeEach
	public void setup() {
	}

	@Test
	public void newInstance() {
		testable = new Event("message");

		assertEquals("message", testable.message);
		assertEquals(Type.Unknown, testable.type);
	}

	@Test
	public void newInstance_2() {
		testable = new Event(Type.StopApplication);

		assertEquals(Type.StopApplication, testable.type);
	}

	@Test
	public void stopApplication() {
		testable = Event.stopApplication();

		assertEquals(null, testable.message);
		assertEquals(Type.StopApplication, testable.type);
	}
}
