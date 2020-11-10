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

		OntModel model = testable.combine(taskDescription);
		assertNotNull(model);
		assertEquals(86, model.listObjects().toList().size());
		assertEquals(289, model.listStatements().toList().size());

		// TODO (2020-11-09 #31): удалить
//		model.write(System.out, "RDF/XML");
	}
}
