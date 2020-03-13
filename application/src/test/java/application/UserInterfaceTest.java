package application;

import java.io.PrintStream;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class UserInterfaceTest {

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

	UserInterface testable;

	PrintStream printStream_mock;

	@BeforeEach
	public void setup() {
		printStream_mock = context.mock(PrintStream.class);

		testable = new UserInterface(printStream_mock);
	}

	@Test
	public void printHelp() {
		final String msg = "msg";

		context.checking(new Expectations() {
			{
				oneOf(printStream_mock).println(msg);
			}
		});

		testable.printHelp(msg);
	}

	@Test
	public void printCommandStatus() {
		final String msg = "msg";

		context.checking(new Expectations() {
			{
				oneOf(printStream_mock).println(msg);
			}
		});

		testable.printCommandStatus(msg);
	}
}
