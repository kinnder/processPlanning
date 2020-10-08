package application.storage.owl;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import planning.method.NodeNetwork;

public class NodeNetworkOWLSchema implements OWLSchema<NodeNetwork> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/node-network#";

	@Override
	public OntModel combine(NodeNetwork object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_process = m.createClass(NS + "Node Network");
		ontClass_process.addLabel("Node Network", "en");
		ontClass_process.addLabel("Сеть узлов", "ru");

		return m;
	}

	@Override
	public NodeNetwork parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}
}
