package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.UserInterface;
import application.event.CommandStatusEventMatcher;
import application.storage.xml.SystemTransformationsXMLFile;
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

	SystemTransformationsXMLFile transformationsXMLFile_mock;

	@BeforeEach
	public void setup() {
		transformationsXMLFile_mock = context.mock(SystemTransformationsXMLFile.class);

		testable = new NewSystemTransformationsCommand();
		// TODO (2020-07-23 #28): перенести в конструктор
		testable.transformationsXMLFile = transformationsXMLFile_mock;
	}

	@Test
	public void execute() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "unknown";
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(transformationsXMLFile_mock).save(with(any(SystemTransformations.class)), with("systemTransformations.xml"));

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data_mock);
	}

	@Test
	public void execute_assemblyLine() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "assemblyLine";
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(transformationsXMLFile_mock).save(with(any(SystemTransformations.class)), with("systemTransformations.xml"));

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data_mock);
	}

	@Test
	public void execute_materialPoints() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "materialPoints";
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(transformationsXMLFile_mock).save(with(any(SystemTransformations.class)), with("systemTransformations.xml"));

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data_mock);
	}

	@Test
	public void execute_cuttingProcess() throws Exception {
		final NewSystemTransformationsCommandData data_mock = context.mock(NewSystemTransformationsCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.domain = "cuttingProcess";
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new system transformation\"...")));

				// TODO (2020-07-23 #28): добавить Matcher для сравнения SystemTransformations
				oneOf(transformationsXMLFile_mock).save(with(any(SystemTransformations.class)), with("systemTransformations.xml"));

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data_mock);
	}
}
