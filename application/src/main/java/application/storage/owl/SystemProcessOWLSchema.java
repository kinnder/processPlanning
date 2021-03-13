package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchema implements OWLSchema<SystemProcess> {

	private OWLModelCommonPart2 owlModel;

	private SystemOperationOWLSchema systemOperationOWLSchema;

	public SystemProcessOWLSchema(OWLModelCommonPart2 owlModel) {
		this.owlModel = owlModel;

		this.systemOperationOWLSchema = new SystemOperationOWLSchema(owlModel);
	}

	@Override
	public Individual combine(SystemProcess systemProcess) {
		Individual ind_process = owlModel.getClass_Process().createIndividual(owlModel.getUniqueURI());
		ind_process.addLabel("Process", "en");
		ind_process.addLabel("Процесс", "ru");

		for (SystemOperation systemOperation : systemProcess) {
			Individual ind_systemOperation = systemOperationOWLSchema.combine(systemOperation);
			ind_process.addProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);
			ind_systemOperation.addProperty(owlModel.getObjectProperty_isSystemOperationOf(), ind_process);
		}

		return ind_process;
	}

	@Override
	public SystemProcess parse(Individual ind_systemProcess) {
		// TODO (2021-03-13 #31): добавить реализацию метода parse
		throw new UnsupportedOperationException("Parsing owl-files is not supported");
	}
}
