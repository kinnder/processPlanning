package application.storage.owl;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import planning.method.NodeNetwork;

public class NodeNetworkOWLModel implements OWLModel<NodeNetwork> {

	@Override
	public void createOntologyModel() {
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		// TODO Auto-generated method stub
	}

	@Override
	public OntModel getOntologyModel() {
		// TODO Auto-generated method stub
		return ModelFactory.createOntologyModel();
	}

	@Override
	public OWLSchema<NodeNetwork> getOWLSchema() {
		return new NodeNetworkOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
	}

	@Override
	public String getUniqueURI() {
		// TODO Auto-generated method stub
		return null;
	}
}
