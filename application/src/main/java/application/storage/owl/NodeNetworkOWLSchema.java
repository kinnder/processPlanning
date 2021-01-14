package application.storage.owl;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import planning.method.NodeNetwork;

public class NodeNetworkOWLSchema implements OWLSchema<NodeNetwork> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/node-network#";

	private OntModel m;

	@Override
	public void combine(NodeNetwork object) {
		// Ontology
		OntClass ontClass_process = m.createClass(NS + "Node Network");
		ontClass_process.addLabel("Node Network", "en");
		ontClass_process.addLabel("Сеть узлов", "ru");
	}

	@Override
	public NodeNetwork parse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		this.m = ontModel;
	}
}
