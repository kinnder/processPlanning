package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.method.TaskDescription;
import planning.model.System;

public class TaskDescriptionOWLSchema implements OWLSchema<TaskDescription> {

	private TaskDescriptionOWLModel owlModel;

	private SystemOWLSchema systemOWLSchema;

	public TaskDescriptionOWLSchema(TaskDescriptionOWLModel owlModel) {
		this.owlModel = owlModel;

		systemOWLSchema = new SystemOWLSchema(owlModel);
	}

	@Override
	public Individual combine(TaskDescription taskDescription) {
		Individual ind_taskDescription = owlModel.getClass_TaskDescription().createIndividual(owlModel.getUniqueURI());
		ind_taskDescription.addLabel("Task Description 1", "en");
		ind_taskDescription.addLabel("Описания задания 1", "ru");

		Individual ind_initialSystem = systemOWLSchema.combine(taskDescription.getInitialSystem());
		ind_initialSystem.setOntClass(owlModel.getClass_InitialSystem());
		ind_initialSystem.addLabel("Initial system 1", "en");
		ind_initialSystem.addLabel("Начальная система 1", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasInitialSystem(), ind_initialSystem);
		ind_initialSystem.addProperty(owlModel.getObjectProperty_isInitialSystemOf(), ind_taskDescription);

		Individual ind_finalSystem = systemOWLSchema.combine(taskDescription.getFinalSystem());
		ind_finalSystem.setOntClass(owlModel.getClass_FinalSystem());
		ind_finalSystem.addLabel("Final system 1", "en");
		ind_finalSystem.addLabel("Конечная система 1", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasFinalSystem(), ind_finalSystem);
		ind_finalSystem.addProperty(owlModel.getObjectProperty_isFinalSystemOf(), ind_taskDescription);

		return ind_taskDescription;
	}

	@Override
	public TaskDescription parse(Individual individual) {
		TaskDescription taskDescription = new TaskDescription();

		owlModel.getClass_TaskDescription().listInstances().forEachRemaining((ind_taskDescription) -> {
			owlModel.getClass_InitialSystem().listInstances().filterKeep((ind_initialSystem) -> {
				return ind_taskDescription.hasProperty(owlModel.getObjectProperty_hasInitialSystem(), ind_initialSystem);
			}).forEachRemaining((ind_initialSystem) -> {
				System initialSystem = systemOWLSchema.parse(ind_initialSystem.asIndividual());
				taskDescription.setInitialSystem(initialSystem);
			});
			owlModel.getClass_FinalSystem().listInstances().filterKeep((ind_finalSystem) -> {
				return ind_taskDescription.hasProperty(owlModel.getObjectProperty_hasFinalSystem(), ind_finalSystem);
			}).forEachRemaining((ind_finalSystem) -> {
				System finalSystem = systemOWLSchema.parse(ind_finalSystem.asIndividual());
				taskDescription.setFinalSystem(finalSystem);
			});
		});

		return taskDescription;
	}
}
