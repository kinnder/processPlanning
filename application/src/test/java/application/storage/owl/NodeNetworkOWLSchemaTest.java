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

import application.domain.AssemblyLine;
import planning.method.Node;
import planning.method.NodeNetwork;
import planning.model.Action;
import planning.model.System;
import planning.model.SystemOperation;

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

	NodeNetworkOWLModel owlModel;

	@BeforeEach
	public void setup() {
		owlModel = new NodeNetworkOWLModel();

		testable = new NodeNetworkOWLSchema(owlModel);
	}

	@Test
	public void combine() {
		final NodeNetwork nodeNetwork = new NodeNetwork();
		final System node1system = AssemblyLine.initialSystem();
		final System node2system = AssemblyLine.finalSystem();
		final Action action = new Action("test-action");
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("test-parameter-key", "test-parameter-value");
		final SystemOperation systemOperation = new SystemOperation(action, actionParameters);
		final Node node1 = nodeNetwork.createNode(node1system);
		final Node node2 = nodeNetwork.createNode(node2system);
		nodeNetwork.createEdge(node1, node2, systemOperation);

		owlModel.createOntologyModel();
		testable.combine(nodeNetwork);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(271, model.listObjects().toList().size());
		assertEquals(1524, model.listStatements().toList().size());

//		 TODO (2021-03-13 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}
}
