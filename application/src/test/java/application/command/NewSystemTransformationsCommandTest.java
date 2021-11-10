package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandStatusEventMatcher;
import planning.method.SystemTransformations;

public class NewSystemTransformationsCommandTest {

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

	NewSystemTransformationsCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new NewSystemTransformationsCommand(application_mock);
	}

	@Test
	public void execute() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "unknown";

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(application_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
						with("systemTransformations.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_assemblyLine() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "assemblyLine";

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(application_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
						with("systemTransformations.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_materialPoints() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "materialPoints";

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(application_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
						with("systemTransformations.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_cuttingProcess() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "cuttingProcess";

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(application_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
						with("systemTransformations.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}
}
