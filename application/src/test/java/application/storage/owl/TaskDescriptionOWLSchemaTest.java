package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import planning.method.TaskDescription;
import planning.model.System;
import planning.model.SystemObject;

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

	TaskDescriptionOWLModel owlModel;

	@BeforeEach
	public void setup() {
		owlModel = new TaskDescriptionOWLModel();

		testable = new TaskDescriptionOWLSchema(owlModel);
	}

	@Test
	public void combine() {
		final SystemObject object1 = new SystemObject("test_object_1");
		object1.addAttribute("test_attribute_1_name", new String("test_attribute_1_value"));

		final SystemObject object2 = new SystemObject("test_object_2");
		object2.addAttribute("test_attribute_2_name", new String("test_attribute_2_value"));

		final System initialSystem = new System();
		initialSystem.addObject(object1);
		initialSystem.addObject(object2);
		initialSystem.addLink("test_link-1-2", object1.getId(), object2.getId());

		final SystemObject object3 = new SystemObject("test_object_3");
		object3.addAttribute("test_attribute_3_name", new String("test_attribute_3_value"));

		final SystemObject object4 = new SystemObject("test_object_4");
		object4.addAttribute("test_attribute_4_name", new String("test_attribute_4_value"));

		final System finalSystem = new System();
		finalSystem.addObject(object3);
		finalSystem.addObject(object4);
		finalSystem.addLink("test_link-3-4", object3.getId(), object4.getId());

		final TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem);
		taskDescription.setFinalSystem(finalSystem);

		owlModel.createOntologyModel();
		testable.combine(taskDescription);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(132, model.listObjects().toList().size());
		assertEquals(426, model.listStatements().toList().size());
	}

	@Test
	public void combine_full() {
		final TaskDescription taskDescription = AssemblyLine.getTaskDescription();

		owlModel.createOntologyModel();
		testable.combine(taskDescription);

		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(226, model.listObjects().toList().size());
		assertEquals(1214, model.listStatements().toList().size());

		// TODO (2020-11-09 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}

	@Test
	public void parse_full() {
		final TaskDescription taskDescription = AssemblyLine.getTaskDescription();

		owlModel.createOntologyModel();
		Individual ind_taskDescription = testable.combine(taskDescription);

		testable.parse(ind_taskDescription);
	}
}
