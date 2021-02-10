package application.storage.owl;

import java.util.UUID;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.vocabulary.XSD;

import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchema implements OWLSchema<SystemProcess> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/system-process#";

	private OntModel m;

	public SystemProcessOWLSchema(SystemProcessOWLModel owlModel) {
		m = owlModel.getOntologyModel();
	}

	@Override
	public Individual combine(SystemProcess systemProcess) {
		// Ontology
		OntClass ontClass_process = m.createClass(NS + "Process");
		ontClass_process.addLabel("Process", "en");
		ontClass_process.addLabel("Процесс", "ru");

		OntClass ontClass_operation = m.createClass(NS + "Operation");
		ontClass_operation.addLabel("Operation", "en");
		ontClass_operation.addLabel("Операция", "ru");

		OntClass ontClass_parameter = m.createClass(NS + "Parameter");
		ontClass_parameter.addLabel("Parameter", "en");
		ontClass_parameter.addLabel("Параметр", "ru");

		ontClass_operation.addDisjointWith(ontClass_parameter);
		ontClass_parameter.addDisjointWith(ontClass_operation);

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_value = m.createDatatypeProperty(NS + "value");
		ontDatatypeProperty_value.addLabel("value", "en");
		ontDatatypeProperty_value.addLabel("значение", "ru");
		ontDatatypeProperty_value.addDomain(ontClass_parameter);
		ontDatatypeProperty_value.addRange(XSD.xstring);

		ObjectProperty ontObjectProperty_hasOperation = m.createObjectProperty(NS + "hasOperation");
		ontObjectProperty_hasOperation.addLabel("has operation", "en");
		ontObjectProperty_hasOperation.addLabel("имеет операцию", "ru");
		ontObjectProperty_hasOperation.addDomain(ontClass_process);
		ontObjectProperty_hasOperation.addRange(ontClass_operation);

		ObjectProperty ontObjectProperty_isOperationOf = m.createObjectProperty(NS + "isOperationOf");
		ontObjectProperty_isOperationOf.addLabel("is operation of", "en");
		ontObjectProperty_isOperationOf.addLabel("является операция для", "ru");
		ontObjectProperty_isOperationOf.addDomain(ontClass_operation);
		ontObjectProperty_isOperationOf.addRange(ontClass_process);

		ontObjectProperty_hasOperation.addInverseOf(ontObjectProperty_isOperationOf);
		ontObjectProperty_isOperationOf.addInverseOf(ontObjectProperty_hasOperation);

		ObjectProperty ontObjectProperty_hasParameter = m.createObjectProperty(NS + "hasParameter");
		ontObjectProperty_hasParameter.addLabel("has parameter", "en");
		ontObjectProperty_hasParameter.addLabel("имеет параметр", "ru");
		ontObjectProperty_hasParameter.addDomain(ontClass_operation);
		ontObjectProperty_hasParameter.addRange(ontClass_parameter);

		ObjectProperty ontObjectProperty_isParameterOf = m.createObjectProperty(NS + "isParameterOf");
		ontObjectProperty_isParameterOf.addLabel("is parameter of", "en");
		ontObjectProperty_isParameterOf.addLabel("является параметром для", "ru");
		ontObjectProperty_isParameterOf.addDomain(ontClass_parameter);
		ontObjectProperty_isParameterOf.addRange(ontClass_operation);

		ontObjectProperty_hasParameter.addInverseOf(ontObjectProperty_isParameterOf);
		ontObjectProperty_isParameterOf.addInverseOf(ontObjectProperty_hasParameter);

		// Individuals
		Individual ind_process = ontClass_process.createIndividual(NS + UUID.randomUUID().toString());
		ind_process.addLabel("Process 1", "en");
		ind_process.addLabel("Процесс 1", "ru");

		for (int operationIdx = 0; operationIdx < systemProcess.size(); operationIdx++) {
			String operationNum = Integer.toString(operationIdx);
			SystemOperation operation = systemProcess.get(operationIdx);

			Individual ind_operation = ontClass_operation.createIndividual(NS + UUID.randomUUID().toString());
			ind_operation.addLabel("Operation ".concat(operationNum), "en");
			ind_operation.addLabel("Операция ".concat(operationNum), "ru");
			ind_operation.addProperty(ontDatatypeProperty_name, operation.getName());

			ind_process.addProperty(ontObjectProperty_hasOperation, ind_operation);

			int parameterIdx = 0;
			for (String parameterName : operation.getActionParameters().keySet()) {
				String parameterNum = Integer.toString(parameterIdx);
				String parameterValue = operation.getParameter(parameterName);

				Individual ind_parameter = ontClass_parameter.createIndividual(NS + UUID.randomUUID().toString());
				ind_parameter.addLabel("Parameter ".concat(operationNum).concat(" ").concat(parameterNum), "en");
				ind_parameter.addLabel("Параметр ".concat(operationNum).concat(" ").concat(parameterNum), "ru");
				ind_parameter.addProperty(ontDatatypeProperty_name, parameterName);
				ind_parameter.addProperty(ontDatatypeProperty_value, parameterValue, XSDDatatype.XSDstring);

				ind_operation.addProperty(ontObjectProperty_hasParameter, ind_parameter);
			}
		}
		return ind_process;
	}

	@Override
	public SystemProcess parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}
