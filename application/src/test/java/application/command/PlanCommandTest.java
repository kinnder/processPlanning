package application.command;

import java.util.Map;

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
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.System;
import planning.model.SystemProcess;
import planning.model.SystemTransformation;
import planning.model.SystemVariant;

public class PlanCommandTest {

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

	PlanCommand testable;

	PersistanceStorage persistanceStorage_mock;

	Application application_mock;

	@BeforeEach
	public void setup() {
		persistanceStorage_mock = context.mock(PersistanceStorage.class);
		application_mock = context.mock(Application.class);

		testable = new PlanCommand(application_mock);
		// TODO (2020-07-10 #22): перенести в конструктор
		testable.persistanceStorage = persistanceStorage_mock;
	}

	@Test
	public void execute() throws Exception {
		final PlanCommandData data_mock = context.mock(PlanCommandData.class);
		data_mock.systemTransformationsFile = "systemTransformations.xml";
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.processFile = "process.xml";
		data_mock.nodeNetworkFile = "nodeNetwork.xml";

		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class,
				"systemTransformation");
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(systemTransformation_mock);
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final System initialSystem_mock = context.mock(System.class, "initial-system");
		final System finalSystem_mock = context.mock(System.class, "final-system");
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);
		final Map<?, ?> actionParameters_mock = context.mock(Map.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("executing command: \"plan\"...")));

				oneOf(persistanceStorage_mock).loadSystemTransformations("systemTransformations.xml");
				will(returnValue(systemTransformations));

				oneOf(persistanceStorage_mock).loadTaskDescription("taskDescription.xml");
				will(returnValue(taskDescription_mock));

				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				oneOf(initialSystem_mock).contains(finalSystem_mock);
				will(returnValue(false));

				oneOf(systemTransformation_mock).applyTo(initialSystem_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(systemVariant_mock).getActionParameters();
				will(returnValue(actionParameters_mock));

				oneOf(system_mock).contains(finalSystem_mock);
				will(returnValue(true));

				oneOf(persistanceStorage_mock).saveSystemProcess(with(any(SystemProcess.class)), with("process.xml"));

				oneOf(persistanceStorage_mock).saveNodeNetwork(with(any(NodeNetwork.class)), with("nodeNetwork.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}
}
