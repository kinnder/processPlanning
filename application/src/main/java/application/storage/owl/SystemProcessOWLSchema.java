package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchema implements OWLSchema<SystemProcess> {

	private OWLModelCommonPart2 owlModel;

	private SystemOperationOWLSchema systemOperationOWLSchema;

	public SystemProcessOWLSchema(OWLModelCommonPart2 owlModel) {
		this(owlModel, new SystemOperationOWLSchema(owlModel));
	}

	SystemProcessOWLSchema(OWLModelCommonPart2 owlModel, SystemOperationOWLSchema systemOperationOWLSchema) {
		this.owlModel = owlModel;
		this.systemOperationOWLSchema = systemOperationOWLSchema;
	}

	@Override
	public Individual combine(SystemProcess systemProcess) {
		Individual ind_process = owlModel.newIndividual_Process();
		ind_process.addLabel("Process", "en");
		ind_process.addLabel("Процесс", "ru");

		for (SystemOperation systemOperation : systemProcess) {
			Individual ind_systemOperation = systemOperationOWLSchema.combine(systemOperation);
			ind_process.addProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);
		}

		return ind_process;
	}

	@Override
	public SystemProcess parse(Individual ind_systemProcess) {
		SystemProcess systemProcess = new SystemProcess();

		owlModel.getClass_Process().listInstances().forEachRemaining((ind_process) -> {
			owlModel.getClass_SystemOperation().listInstances().filterKeep((ind_systemOperation) -> {
				return ind_process.hasProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);
			}).forEachRemaining((ind_systemOperation) -> {
				SystemOperation systemOperation = systemOperationOWLSchema.parse(ind_systemOperation.asIndividual());
				systemProcess.add(systemOperation);
			});
		});

		return systemProcess;
	}
}
