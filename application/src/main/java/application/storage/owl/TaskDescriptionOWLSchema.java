package application.storage.owl;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

import planning.method.TaskDescription;

public class TaskDescriptionOWLSchema implements OWLSchema<TaskDescription> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/task-description#";

	@Override
	public OntModel combine(TaskDescription object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_process = m.createClass(NS + "Task Description");
		ontClass_process.addLabel("Task Description", "en");
		ontClass_process.addLabel("Описание задания", "ru");

		return m;
	}

	@Override
	public TaskDescription parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}
}
