package application.command;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;

public class CommandTest {

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

	Command testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new Command(application_mock) {
			@Override
			public void execute(CommandData data) throws Exception {
			}
		};
	}

	@Test
	public void execute() throws Exception {
		final CommandData data_mock = context.mock(CommandData.class);

		testable.execute(data_mock);
	}
}
