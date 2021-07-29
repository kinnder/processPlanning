package application.command;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import application.UserInterface;
import application.event.CommandStatusEventMatcher;
import application.event.HelpMessageEventMatcher;
import application.storage.PersistanceStorage;

public class VerifyCommandTest {

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

	VerifyCommand testable;

	PersistanceStorage persistanceStorage_mock;

	SchemaFactory schemaFactory_mock;

	@BeforeEach
	public void setup() {
		persistanceStorage_mock = context.mock(PersistanceStorage.class);
		schemaFactory_mock = context.mock(SchemaFactory.class);

		testable = new VerifyCommand();
		// TODO (2021-07-29 #24): перенести в конструктор
		testable.persistanceStorage = persistanceStorage_mock;
		testable.factory = schemaFactory_mock;
	}

	@Test
	public void execute() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.systemTransformationsFile = "systemTransformations.xml";
		data.taskDescriptionFile = "taskDescription.xml";
		data.processFile = "process.xml";
		data.nodeNetworkFile = "nodeNetwork.xml";

		final UserInterface ui_mock = context.mock(UserInterface.class);

		final InputStream taskDescriptionStream_mock = context.mock(InputStream.class, "taskDescriptionStream");
		final Schema taskDescriptionSchema_mock = context.mock(Schema.class, "taskDescriptionSchema");
		final Validator taskDescriptionValidator_mock = context.mock(Validator.class, "taskDescriptionValidator");

		final InputStream nodeNetworkStream_mock = context.mock(InputStream.class, "nodeNetworkStream");
		final Schema nodeNetworkSchema_mock = context.mock(Schema.class, "nodeNetworkSchema");
		final Validator nodeNetworkValidator_mock = context.mock(Validator.class, "nodeNetworkValidator");

		final InputStream processStream_mock = context.mock(InputStream.class, "processStream");
		final Schema processSchema_mock = context.mock(Schema.class, "processSchema");
		final Validator processValidator_mock = context.mock(Validator.class, "processValidator");

		final InputStream systemTransformationsStream_mock = context.mock(InputStream.class,
				"systemTransformationsStream");
		final Schema systemTransformationsSchema_mock = context.mock(Schema.class, "systemTransformationsSchema");
		final Validator systemTransformationsValidator_mock = context.mock(Validator.class,
				"systemTransformationsValidator");

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				// >> taskDescription

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.TASK_DESCRIPTION_XSD);
				will(returnValue(taskDescriptionStream_mock));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("verification of taskDescription.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(taskDescriptionSchema_mock));

				oneOf(taskDescriptionSchema_mock).newValidator();
				will(returnValue(taskDescriptionValidator_mock));

				oneOf(taskDescriptionValidator_mock).validate(with(any(Source.class)));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("SUCCESS: taskDescription.xml is correct")));

				// << taskDescription

				// >> nodeNetwork

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.NODE_NETWORK_XSD);
				will(returnValue(nodeNetworkStream_mock));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("verification of nodeNetwork.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(nodeNetworkSchema_mock));

				oneOf(nodeNetworkSchema_mock).newValidator();
				will(returnValue(nodeNetworkValidator_mock));

				oneOf(nodeNetworkValidator_mock).validate(with(any(Source.class)));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("SUCCESS: nodeNetwork.xml is correct")));

				// << nodeNetwork

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("SUCCESS: process.xml is correct")));

				// << process

				// >> systemTransformations

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.SYSTEM_TRANSFORMATIONS_XSD);
				will(returnValue(systemTransformationsStream_mock));

				oneOf(ui_mock).notifyHelpMessage(with(
						new HelpMessageEventMatcher().expectMessage("verification of systemTransformations.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(systemTransformationsSchema_mock));

				oneOf(systemTransformationsSchema_mock).newValidator();
				will(returnValue(systemTransformationsValidator_mock));

				oneOf(systemTransformationsValidator_mock).validate(with(any(Source.class)));

				oneOf(ui_mock).notifyHelpMessage(with(
						new HelpMessageEventMatcher().expectMessage("SUCCESS: systemTransformations.xml is correct")));

				// << systemTransformations

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data);
	}

	@Test
	public void execute_no_files_specified() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();

		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data);
	}

	@Test
	public void execute_throwSAXParseException() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.processFile = "process.xml";

		final UserInterface ui_mock = context.mock(UserInterface.class);

		final InputStream processStream_mock = context.mock(InputStream.class, "processStream");
		final Schema processSchema_mock = context.mock(Schema.class, "processSchema");
		final Validator processValidator_mock = context.mock(Validator.class, "processValidator");

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));
				will(throwException(new SAXParseException("test-message", "test-publicId", "test-systemId", 10, 20)));

				oneOf(ui_mock).notifyHelpMessage(with(
						new HelpMessageEventMatcher().expectMessage("lineNumber: 10; columnNumber: 20; test-message")));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("FAIL: process.xml is not correct")));

				// << process

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data);
	}

	@Test
	public void execute_throwSAXException() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.processFile = "process.xml";

		final UserInterface ui_mock = context.mock(UserInterface.class);

		final InputStream processStream_mock = context.mock(InputStream.class, "processStream");
		final Schema processSchema_mock = context.mock(Schema.class, "processSchema");
		final Validator processValidator_mock = context.mock(Validator.class, "processValidator");

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));
				will(throwException(new SAXException("test-message")));

				oneOf(ui_mock).notifyHelpMessage(with(new HelpMessageEventMatcher().expectMessage("test-message")));

				oneOf(ui_mock).notifyHelpMessage(
						with(new HelpMessageEventMatcher().expectMessage("FAIL: process.xml is not correct")));

				// << process

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data);
	}
}
