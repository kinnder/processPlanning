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

		OntClass ontClass_taskDescription = m.createClass(NS + "Task Description");
		ontClass_taskDescription.addLabel("Task Description", "en");
		ontClass_taskDescription.addLabel("Описание задания", "ru");

		OntClass ontClass_initialSystem = m.createClass(NS + "Initial System");
		ontClass_initialSystem.addLabel("Initial System", "en");
		ontClass_initialSystem.addLabel("Начальная система", "ru");

		OntClass ontClass_finalSystem = m.createClass(NS + "Final System");
		ontClass_finalSystem.addLabel("Final System", "en");
		ontClass_finalSystem.addLabel("Конечная система", "ru");

		OntClass ontClass_systemObject = m.createClass(NS + "System Object");
		ontClass_systemObject.addLabel("System Object", "en");
		ontClass_systemObject.addLabel("Объект системы", "ru");

		OntClass ontClass_link = m.createClass(NS + "Link");
		ontClass_link.addLabel("Link", "en");
		ontClass_link.addLabel("Связь системы", "ru");

		OntClass ontClass_attribute = m.createClass(NS + "Attribute");
		ontClass_attribute.addLabel("Attribute", "en");
		ontClass_attribute.addLabel("Атрибут", "ru");

		return m;
	}

	@Override
	public TaskDescription parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}
}
