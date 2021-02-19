package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import planning.method.SystemTransformations;

public class SystemTransformationOWLSchemaTest {

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

	SystemTransformationsOWLModel owlModel;

	@BeforeEach
	public void setup() {
		owlModel = new SystemTransformationsOWLModel();

		testable = new SystemTransformationsOWLSchema(owlModel);
	}

	@Test
	public void combine_full() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		owlModel.createOntologyModel();
		testable.combine(systemTransformations);

		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(207, model.listObjects().toList().size());
		assertEquals(753, model.listStatements().toList().size());

		// TODO (2020-12-14 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}

	@Test
	@Disabled("FIX")
	public void parse_full() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		owlModel.createOntologyModel();
		Individual ind_systemTransformations = testable.combine(systemTransformations);

		testable.parse(ind_systemTransformations);
	}
}
