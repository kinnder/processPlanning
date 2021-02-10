package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.jmock.Expectations;
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

	NodeNetworkOWLModel nodeNetworkOWLModel_mock;

	@BeforeEach
	public void setup() {
		nodeNetworkOWLModel_mock = context.mock(NodeNetworkOWLModel.class);

		testable = new NodeNetworkOWLSchema(nodeNetworkOWLModel_mock);
	}

	@Test
	public void combine() {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OntClass ontClass_mock = context.mock(OntClass.class);

		context.checking(new Expectations() {
			{
				oneOf(nodeNetworkOWLModel_mock).getOntologyModel();
				will(returnValue(ontModel_mock));

				oneOf(ontModel_mock).createClass(with(any(String.class)));
				will(returnValue(ontClass_mock));

				oneOf(ontClass_mock).addLabel("Node Network", "en");

				oneOf(ontClass_mock).addLabel("Сеть узлов", "ru");
			}
		});

		Individual result = testable.combine(nodeNetwork_mock);
		assertNull(result);
	}
}
