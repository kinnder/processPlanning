package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.jena.ontology.OntModel;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.TaskDescription;
import planning.model.Link;
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

	@BeforeEach
	public void setup() {
		testable = new TaskDescriptionOWLSchema();
	}

	@Test
	public void combine() {
		final TaskDescription taskDescription = new TaskDescription();
		final System initialSystem = new System();
		final SystemObject object1 = new SystemObject("test-object-1");
		initialSystem.addObject(object1);
		final SystemObject object2 = new SystemObject("test-object-2");
		initialSystem.addObject(object2);
		final Link link_1_2 = new Link("test-link-1-2", object1.getId(), object2.getId());
		initialSystem.addLink(link_1_2);

		final System finalSystem = new System();
		taskDescription.setInitialSystem(initialSystem);
		taskDescription.setFinalSystem(finalSystem);

		OntModel model = testable.combine(taskDescription);
		assertNotNull(model);
		assertEquals(109, model.listObjects().toList().size());
		assertEquals(339, model.listStatements().toList().size());

		// TODO (2020-11-09 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}
}
