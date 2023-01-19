package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchema implements OWLSchema<SystemProcess> {

	private PlanningOWLModel owlModel;

	private SystemOperationOWLSchema systemOperationOWLSchema;

	public SystemProcessOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemOperationOWLSchema(owlModel));
	}

	SystemProcessOWLSchema(PlanningOWLModel owlModel, SystemOperationOWLSchema systemOperationOWLSchema) {
		this.owlModel = owlModel;
		this.systemOperationOWLSchema = systemOperationOWLSchema;
	}

	@Override
	public Individual combine(SystemProcess systemProcess) {
		final Individual ind_process = owlModel.newIndividual_Process();
		ind_process.addLabel("Process", "en");
		ind_process.addLabel("Процесс", "ru");

		for (SystemOperation systemOperation : systemProcess) {
			final Individual ind_systemOperation = systemOperationOWLSchema.combine(systemOperation);
			ind_process.addProperty(owlModel.getObjectProperty_performsSystemOperation(), ind_systemOperation);
		}

		return ind_process;
	}

	@Override
	public SystemProcess parse(Individual ind_systemProcess) {
		final SystemProcess systemProcess = new SystemProcess();

		owlModel.getClass_Process().listInstances().forEachRemaining((ind_process) -> {
			owlModel.getClass_SystemOperation().listInstances().filterKeep((ind_systemOperation) -> {
				return ind_process.hasProperty(owlModel.getObjectProperty_performsSystemOperation(), ind_systemOperation);
			}).forEachRemaining((ind_systemOperation) -> {
				final SystemOperation systemOperation = systemOperationOWLSchema.parse(ind_systemOperation.asIndividual());
				systemProcess.add(systemOperation);
			});
		});

		return systemProcess;
	}
}
