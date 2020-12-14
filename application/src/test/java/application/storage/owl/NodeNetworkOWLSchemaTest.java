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

import planning.method.NodeNetwork;

public class NodeNetworkOWLSchemaTest {

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

	NodeNetworkOWLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new NodeNetworkOWLSchema();
	}

	@Test
	public void combine() {
		final NodeNetwork nodeNetwork = new NodeNetwork();

		OntModel model = testable.combine(nodeNetwork);
		assertNotNull(model);
		assertEquals(22, model.listObjects().toList().size());
		assertEquals(111, model.listStatements().toList().size());

		// TODO (2020-12-14 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}
}
