package application.storage.owl;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import planning.method.SystemTransformations;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	@Override
	public OntModel combine(SystemTransformations object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_process = m.createClass(NS + "System Transformations");
		ontClass_process.addLabel("System Transformations", "en");
		ontClass_process.addLabel("Трансформации системы", "ru");

		return m;
	}

	@Override
	public SystemTransformations parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}

}
