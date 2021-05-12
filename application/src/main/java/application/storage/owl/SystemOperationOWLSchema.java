package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;

import planning.model.Action;
import planning.model.SystemOperation;

public class SystemOperationOWLSchema implements OWLSchema<SystemOperation> {

	private OWLModelCommonPart2 owlModel;

	public SystemOperationOWLSchema(OWLModelCommonPart2 owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(SystemOperation systemOperation) {
		Individual ind_systemOperation = owlModel.newIndividual_SystemOperation();
		String name = systemOperation.getName();
		ind_systemOperation.addLabel(String.format("System operation \"%s\"", name), "en");
		ind_systemOperation.addLabel(String.format("Операция системы \"%s\"", name), "ru");
		ind_systemOperation.addProperty(owlModel.getDataProperty_name(), name);

		Map<String, String> actionParameters = systemOperation.getActionParameters();
		for (String key : actionParameters.keySet()) {
			Individual ind_parameter = owlModel.newIndividual_Parameter();
			ind_parameter.addLabel(String.format("Parameter \"%s\"", key), "en");
			ind_parameter.addLabel(String.format("Параметр \"%s\"", key), "ru");
			ind_parameter.addProperty(owlModel.getDataProperty_key(), key);
			ind_parameter.addProperty(owlModel.getDataProperty_value(), actionParameters.get(key));
			ind_systemOperation.addProperty(owlModel.getObjectProperty_hasParameter(), ind_parameter);
			ind_parameter.addProperty(owlModel.getObjectProperty_isParameterOf(), ind_systemOperation);
		}

		return ind_systemOperation;
	}

	@Override
	public SystemOperation parse(Individual ind_systemOperation) {
		String name = ind_systemOperation.getProperty(owlModel.getDataProperty_name()).getString();

		Map<String, String> actionParameters = new HashMap<>();
		owlModel.getClass_Parameter().listInstances().filterKeep((ind_parameter) -> {
			return ind_systemOperation.hasProperty(owlModel.getObjectProperty_hasParameter(), ind_parameter);
		}).forEachRemaining((ind_parameter) -> {
			String parameterName = ind_parameter.getProperty(owlModel.getDataProperty_key()).getString();
			String parameterValue = ind_parameter.getProperty(owlModel.getDataProperty_value()).getString();
			actionParameters.put(parameterName, parameterValue);
		});

		return new SystemOperation(new Action(name), actionParameters);
	}
}
