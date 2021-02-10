package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import planning.method.NodeNetwork;

public class NodeNetworkOWLSchema implements OWLSchema<NodeNetwork> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/node-network#";

	private NodeNetworkOWLModel owlModel;

	public NodeNetworkOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(NodeNetwork object) {
		OntModel m = owlModel.getOntologyModel();
		// Ontology
		OntClass ontClass_process = m.createClass(NS + "Node Network");
		ontClass_process.addLabel("Node Network", "en");
		ontClass_process.addLabel("Сеть узлов", "ru");
		return null;
	}

	@Override
	public NodeNetwork parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}
