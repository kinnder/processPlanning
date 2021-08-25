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

import application.Application;
import application.event.CommandStatusEventMatcher;
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

	SchemaFactory schemaFactory_mock;

	Application application_mock;

	@BeforeEach
	public void setup() {
		schemaFactory_mock = context.mock(SchemaFactory.class);
		application_mock = context.mock(Application.class);

		testable = new VerifyCommand(application_mock);
		// TODO (2021-07-29 #24): перенести в конструктор
		testable.factory = schemaFactory_mock;
	}

	// TODO (2021-08-24): разделить на несколько отдельных тестов
	@Test
	public void execute() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.systemTransformationsFile = "systemTransformations.xml";
		data.taskDescriptionFile = "taskDescription.xml";
		data.processFile = "process.xml";
		data.nodeNetworkFile = "nodeNetwork.xml";

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

		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// >> taskDescription

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.TASK_DESCRIPTION_XSD);
				will(returnValue(taskDescriptionStream_mock));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("verification of taskDescription.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(taskDescriptionSchema_mock));

				oneOf(taskDescriptionSchema_mock).newValidator();
				will(returnValue(taskDescriptionValidator_mock));

				oneOf(taskDescriptionValidator_mock).validate(with(any(Source.class)));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("SUCCESS: taskDescription.xml is correct")));

				// << taskDescription

				// >> nodeNetwork

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.NODE_NETWORK_XSD);
				will(returnValue(nodeNetworkStream_mock));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("verification of nodeNetwork.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(nodeNetworkSchema_mock));

				oneOf(nodeNetworkSchema_mock).newValidator();
				will(returnValue(nodeNetworkValidator_mock));

				oneOf(nodeNetworkValidator_mock).validate(with(any(Source.class)));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("SUCCESS: nodeNetwork.xml is correct")));

				// << nodeNetwork

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("SUCCESS: process.xml is correct")));

				// << process

				// >> systemTransformations

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.SYSTEM_TRANSFORMATIONS_XSD);
				will(returnValue(systemTransformationsStream_mock));

				oneOf(application_mock).notifyCommandStatus(with(
						new CommandStatusEventMatcher().expectMessage("verification of systemTransformations.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(systemTransformationsSchema_mock));

				oneOf(systemTransformationsSchema_mock).newValidator();
				will(returnValue(systemTransformationsValidator_mock));

				oneOf(systemTransformationsValidator_mock).validate(with(any(Source.class)));

				oneOf(application_mock).notifyCommandStatus(with(
						new CommandStatusEventMatcher().expectMessage("SUCCESS: systemTransformations.xml is correct")));

				// << systemTransformations

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data);
	}

	@Test
	public void execute_no_files_specified() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data);
	}

	@Test
	public void execute_throwSAXParseException() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.processFile = "process.xml";
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		final InputStream processStream_mock = context.mock(InputStream.class, "processStream");
		final Schema processSchema_mock = context.mock(Schema.class, "processSchema");
		final Validator processValidator_mock = context.mock(Validator.class, "processValidator");

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));
				will(throwException(new SAXParseException("test-message", "test-publicId", "test-systemId", 10, 20)));

				oneOf(application_mock).notifyCommandStatus(with(
						new CommandStatusEventMatcher().expectMessage("lineNumber: 10; columnNumber: 20; test-message")));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("FAIL: process.xml is not correct")));

				// << process

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data);
	}

	@Test
	public void execute_throwSAXException() throws Exception {
		final VerifyCommandData data = new VerifyCommandData();
		data.processFile = "process.xml";
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		final InputStream processStream_mock = context.mock(InputStream.class, "processStream");
		final Schema processSchema_mock = context.mock(Schema.class, "processSchema");
		final Validator processValidator_mock = context.mock(Validator.class, "processValidator");

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"verify\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// >> process

				oneOf(persistanceStorage_mock).getResourceAsStream(PersistanceStorage.PROCESS_XSD);
				will(returnValue(processStream_mock));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("verification of process.xml ...")));

				oneOf(schemaFactory_mock).newSchema(with(any(Source.class)));
				will(returnValue(processSchema_mock));

				oneOf(processSchema_mock).newValidator();
				will(returnValue(processValidator_mock));

				oneOf(processValidator_mock).validate(with(any(Source.class)));
				will(throwException(new SAXException("test-message")));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("test-message")));

				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("FAIL: process.xml is not correct")));

				// << process

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data);
	}
}
