package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.OntModel;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchemaTest {

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

	SystemProcessOWLSchema testable;

	SystemProcessOWLModel owlModel;

	@BeforeEach
	public void setup() {
		owlModel = new SystemProcessOWLModel();

		testable = new SystemProcessOWLSchema(owlModel);
	}

	@Test
	public void combine() {
		final SystemProcess systemProcess = new SystemProcess();
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("test-parameter-name", "test-parameter-value");
		final Action action = new Action("test-action");
		systemProcess.add(new SystemOperation(action, parameters));

		owlModel.createOntologyModel();
		testable.combine(systemProcess);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(76, model.listObjects().toList().size());
		assertEquals(261, model.listStatements().toList().size());

		// TODO (2020-11-09 #31): удалить
//		model.write(System.out, "RDF/XML");
	}
}
