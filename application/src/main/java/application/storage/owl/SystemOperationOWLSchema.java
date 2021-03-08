package application.storage.owl;

import java.util.Map;

import org.apache.jena.ontology.Individual;

import planning.model.Action;
import planning.model.SystemOperation;

public class SystemOperationOWLSchema implements OWLSchema<SystemOperation> {

	private NodeNetworkOWLModel owlModel;

	private ActionParametersOWLSchema actionParametersOWLSchema;

	public SystemOperationOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;

		this.actionParametersOWLSchema = new ActionParametersOWLSchema(owlModel);
	}

	@Override
	public Individual combine(SystemOperation systemOperation) {
		Individual ind_systemOperation = owlModel.getClass_SystemOperation().createIndividual(owlModel.getUniqueURI());
		ind_systemOperation.addLabel("System Operation", "en");
		ind_systemOperation.addLabel("Операция системы", "ru");
		ind_systemOperation.addProperty(owlModel.getDataProperty_name(), systemOperation.getName());

		Individual ind_actionParameters = actionParametersOWLSchema.combine(systemOperation.getActionParameters());
		ind_systemOperation.addProperty(owlModel.getObjectProperty_hasActionParameters(), ind_actionParameters);
		ind_actionParameters.addProperty(owlModel.getObjectProperty_areActionParametersOf(), ind_systemOperation);

		return ind_systemOperation;
	}

	@Override
	public SystemOperation parse(Individual ind_systemOperation) {
		String name = ind_systemOperation.getProperty(owlModel.getDataProperty_name()).getString();

		SystemOperation systemOperation = new SystemOperation(new Action(name), null);

		owlModel.getClass_ActionParameters().listInstances().filterKeep((ind_actionParamteres) -> {
			return ind_systemOperation.hasProperty(owlModel.getObjectProperty_hasActionParameters(), ind_actionParamteres);
		}).forEachRemaining((ind_actionParameters) -> {
			Map<String, String> actionParameters = actionParametersOWLSchema.parse(ind_actionParameters.asIndividual());
			systemOperation.setActionParameters(actionParameters);
		});

		return systemOperation;
	}
}
