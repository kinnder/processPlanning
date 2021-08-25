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
import application.storage.PersistanceStorage;
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
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(persistanceStorage_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
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
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(persistanceStorage_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
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
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(persistanceStorage_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
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
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(persistanceStorage_mock).saveSystemTransformations(with(any(SystemTransformations.class)),
						with("systemTransformations.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}
}
