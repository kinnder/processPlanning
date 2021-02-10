package application.storage.owl;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import planning.model.SystemProcess;

public class SystemProcessOWLModel implements OWLModel<SystemProcess> {

	private OntModel m = ModelFactory.createOntologyModel();

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		// TODO Auto-generated method stub
	}

	@Override
	public OntModel getOntologyModel() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public void createOntologyModel() {
		// TODO Auto-generated method stub
	}

	@Override
	public OWLSchema<SystemProcess> getOWLSchema() {
		return new SystemProcessOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		// TODO Auto-generated method stub
		return ModelFactory.createOntologyModel();
	}

	@Override
	public String getUniqueURI() {
		// TODO Auto-generated method stub
		return null;
	}
}
