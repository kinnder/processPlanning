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

import planning.method.SystemTransformations;

public class SystemTransformationsOWLSchemaTest {

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

	SystemTransformationsOWLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationsOWLSchema();
	}

	@Test
	public void combine() {
		final SystemTransformations systemTransformations = new SystemTransformations();

		OntModel model = testable.combine(systemTransformations);
		assertNotNull(model);
		assertEquals(119, model.listObjects().toList().size());
		assertEquals(377, model.listStatements().toList().size());

		// TODO (2020-12-14 #31): удалить
		model.write(java.lang.System.out, "RDF/XML");
	}
}
