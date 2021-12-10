package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import planning.method.TaskDescription;
import planning.model.System;

public class TaskDescriptionOWLSchemaTest {

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

	TaskDescriptionOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	SystemOWLSchema systemOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		systemOWLSchema_mock = context.mock(SystemOWLSchema.class);

		testable = new TaskDescriptionOWLSchema(owlModel_mock, systemOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new TaskDescriptionOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class, "taskDescription");
		final Individual i_taskDescription_mock = context.mock(Individual.class, "i-taskDescription");
		final Individual i_initialSystem_mock = context.mock(Individual.class, "i-initialSystem");
		final Individual i_finalSystem_mock = context.mock(Individual.class, "i-finalSystem");
		final System initialSystem_mock = context.mock(System.class, "initialSystem");
		final System finalSystem_mock = context.mock(System.class, "finalSystem");
		final OntClass oc_initialSystem_mock = context.mock(OntClass.class, "oc-initialSystem");
		final OntClass oc_finalSystem_mock = context.mock(OntClass.class, "oc-finalSystem");
		final ObjectProperty op_hasInitialSystem_mock = context.mock(ObjectProperty.class, "op-hasInitialSystem");
		final ObjectProperty op_hasFinalSystem_mock = context.mock(ObjectProperty.class, "op-hasFinalSystem");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_TaskDescription();
				will(returnValue(i_taskDescription_mock));

				oneOf(i_taskDescription_mock).addLabel("Task Description", "en");

				oneOf(i_taskDescription_mock).addLabel("Описание задания", "ru");

				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				oneOf(systemOWLSchema_mock).combine(initialSystem_mock);
				will(returnValue(i_initialSystem_mock));

				oneOf(owlModel_mock).getClass_InitialSystem();
				will(returnValue(oc_initialSystem_mock));

				oneOf(i_initialSystem_mock).setOntClass(oc_initialSystem_mock);

				oneOf(i_initialSystem_mock).addLabel("Initial system", "en");

				oneOf(i_initialSystem_mock).addLabel("Начальная система", "ru");

				oneOf(owlModel_mock).getObjectProperty_hasInitialSystem();
				will(returnValue(op_hasInitialSystem_mock));

				oneOf(i_taskDescription_mock).addProperty(op_hasInitialSystem_mock, i_initialSystem_mock);

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				oneOf(systemOWLSchema_mock).combine(finalSystem_mock);
				will(returnValue(i_finalSystem_mock));

				oneOf(owlModel_mock).getClass_FinalSystem();
				will(returnValue(oc_finalSystem_mock));

				oneOf(i_finalSystem_mock).setOntClass(oc_finalSystem_mock);

				oneOf(i_finalSystem_mock).addLabel("Final system", "en");

				oneOf(i_finalSystem_mock).addLabel("Конечная система", "ru");

				oneOf(owlModel_mock).getObjectProperty_hasFinalSystem();
				will(returnValue(op_hasFinalSystem_mock));

				oneOf(i_taskDescription_mock).addProperty(op_hasFinalSystem_mock, i_finalSystem_mock);
			}
		});

		assertEquals(i_taskDescription_mock, testable.combine(taskDescription_mock));
	}

	@Test
	public void parse() {
		final System initialSystem_mock = context.mock(System.class, "initialSystem");
		final System finalSystem_mock = context.mock(System.class, "finalSystem");
		final OntClass oc_taskDescription_mock = context.mock(OntClass.class, "oc-taskDescription");
		final OntClass oc_initialSystem_mock = context.mock(OntClass.class, "oc-initialSystem");
		final OntClass oc_finalSystem_mock = context.mock(OntClass.class, "oc-finalSystem");
		final ObjectProperty op_hasInitialSystem = context.mock(ObjectProperty.class, "op-hasInitialSystem");
		final ObjectProperty op_hasFinalSystem = context.mock(ObjectProperty.class, "op-hasFinalSystem");

		final Individual i_taskDescription_mock = context.mock(Individual.class, "i-taskDescription");
		final ExtendedIterator<Individual> taskDescriptionIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_taskDescription_mock).iterator());

		final Individual i_initialSystem_mock = context.mock(Individual.class, "i-initialSystem");
		final ExtendedIterator<Individual> initialSystemIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_initialSystem_mock).iterator());

		final Individual i_finalSystem_mock = context.mock(Individual.class, "i_finalSystem");
		final ExtendedIterator<Individual> finalSystemIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_finalSystem_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_TaskDescription();
				will(returnValue(oc_taskDescription_mock));

				oneOf(oc_taskDescription_mock).listInstances();
				will(returnValue(taskDescriptionIterator));

				oneOf(owlModel_mock).getClass_InitialSystem();
				will(returnValue(oc_initialSystem_mock));

				oneOf(oc_initialSystem_mock).listInstances();
				will(returnValue(initialSystemIterator));

				oneOf(owlModel_mock).getObjectProperty_hasInitialSystem();
				will(returnValue(op_hasInitialSystem));

				oneOf(i_taskDescription_mock).hasProperty(op_hasInitialSystem, i_initialSystem_mock);
				will(returnValue(true));

				oneOf(i_initialSystem_mock).asIndividual();
				will(returnValue(i_initialSystem_mock));

				oneOf(systemOWLSchema_mock).parse(i_initialSystem_mock);
				will(returnValue(initialSystem_mock));

				oneOf(owlModel_mock).getClass_FinalSystem();
				will(returnValue(oc_finalSystem_mock));

				oneOf(oc_finalSystem_mock).listInstances();
				will(returnValue(finalSystemIterator));

				oneOf(owlModel_mock).getObjectProperty_hasFinalSystem();
				will(returnValue(op_hasFinalSystem));

				oneOf(i_taskDescription_mock).hasProperty(op_hasFinalSystem, i_finalSystem_mock);
				will(returnValue(true));

				oneOf(i_finalSystem_mock).asIndividual();
				will(returnValue(i_finalSystem_mock));

				oneOf(systemOWLSchema_mock).parse(i_finalSystem_mock);
				will(returnValue(finalSystem_mock));
			}
		});

		TaskDescription result = testable.parse(null);
		assertEquals(initialSystem_mock, result.getInitialSystem());
		assertEquals(finalSystem_mock, result.getFinalSystem());
	}

	@Test
	public void combine_full() {
		owlModel_mock = new PlanningOWLModel();
		testable = new TaskDescriptionOWLSchema(owlModel_mock);

		final TaskDescription taskDescription = AssemblyLine.getTaskDescription();

		owlModel_mock.createOntologyModel();
		testable.combine(taskDescription);

		OntModel model = owlModel_mock.getOntologyModel();
		assertNotNull(model);
		assertEquals(497, model.listObjects().toList().size());
		assertEquals(2532, model.listStatements().toList().size());
	}

	@Test
	public void parse_full() {
		owlModel_mock = new PlanningOWLModel();
		testable = new TaskDescriptionOWLSchema(owlModel_mock);

		final TaskDescription taskDescription = AssemblyLine.getTaskDescription();

		owlModel_mock.createOntologyModel();
		Individual ind_taskDescription = testable.combine(taskDescription);

		testable.parse(ind_taskDescription);
	}
}
