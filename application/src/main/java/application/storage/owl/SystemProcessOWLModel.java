package application.storage.owl;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

public class SystemProcessOWLModel implements OWLModel {

	@Override
	public OntModel createOntologyModel() {
		// TODO Auto-generated method stub
		return ModelFactory.createOntologyModel();
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		// TODO Auto-generated method stub
	}
}
