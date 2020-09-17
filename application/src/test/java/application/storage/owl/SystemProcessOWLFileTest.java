package application.storage.owl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import planning.model.Action;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLFileTest {

	@Test
	@Disabled
	public void example() {
		// Ontology

		String NS = "https://github.com/kinnder/process-engineering/planning#";

		OntModel m = ModelFactory.createOntologyModel();

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
		ontDatatypeProperty_value.addRange(XSD.integer);

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

		SystemProcess prrocess = new SystemProcess();
		Map<String, String> parameters1 = new HashMap<>();
		parameters1.put("параметр-1-1", "10");
		SystemOperation operation1 = new SystemOperation(new Action("операция-1"), parameters1);
		prrocess.add(operation1);

		Individual ind_process1 = ontClass_process.createIndividual(NS + UUID.randomUUID().toString());
		ind_process1.addLabel("Process 1", "en");
		ind_process1.addLabel("Процесс 1", "ru");

		Individual ind_operation1 = ontClass_operation.createIndividual(NS + UUID.randomUUID().toString());
		ind_operation1.addLabel("Operation 1", "en");
		ind_operation1.addLabel("Операция 1", "ru");
		ind_operation1.addProperty(ontDatatypeProperty_name, operation1.getName());

		ind_process1.addProperty(ontObjectProperty_hasOperation, ind_operation1);

		Individual ind_parameter1 = ontClass_parameter.createIndividual(NS + UUID.randomUUID().toString());
		ind_parameter1.addLabel("Parameter 1", "en");
		ind_parameter1.addLabel("Параметр 1", "ru");
		ind_parameter1.addProperty(ontDatatypeProperty_name, "параметр-1-1");
		ind_parameter1.addProperty(ontDatatypeProperty_value, parameters1.get("параметр-1-1"), XSDDatatype.XSDinteger);

		ind_operation1.addProperty(ontObjectProperty_hasParameter, ind_parameter1);

		m.write(System.out, "RDF/XML");
	}

	public void test() {
		String NS = "http://localhost/process-engineering#";

		OntModel m = ModelFactory.createOntologyModel();

		// Classes

		OntClass node = m.createClass(NS + "Node");
		node.addLabel("Node", "en");
		node.addLabel("Вершина", "ru");

		OntClass action = m.createClass(NS + "Action");
		action.addLabel("Action", "en");
		action.addLabel("Дейсвие", "ru");

		OntClass nodeTree = m.createClass(NS + "NodeTree");
		nodeTree.addLabel("Node Tree", "en");
		nodeTree.addLabel("Дерево Вершин", "ru");

		OntClass task = m.createClass(NS + "Task");
		task.addLabel("Task", "en");
		task.addLabel("Задача", "ru");

		OntClass object = m.createClass(NS + "Object");
		object.addLabel("Object", "en");
		object.addLabel("Объект", "ru");

		OntClass operation = m.createClass(NS + "Operation");
		operation.addLabel("Operation", "en");
		operation.addLabel("Операция", "ru");

		OntClass domain = m.createClass(NS + "Domain");
		domain.addLabel("Domain", "en");
		domain.addLabel("Предметная Область", "ru");

		OntClass edge = m.createClass(NS + "Edge");
		edge.addLabel("Edge", "en");
		edge.addLabel("Ребро", "ru");

		OntClass solution = m.createClass(NS + "Solution");
		solution.addLabel("Solution", "en");
		solution.addLabel("Решение", "ru");

		OntClass link = m.createClass(NS + "Link");
		link.addLabel("Link", "en");
		link.addLabel("Связь", "ru");

		OntClass system = m.createClass(NS + "System");
		system.addLabel("System", "en");
		system.addLabel("Система", "ru");

		OntClass objectTransformation = m.createClass(NS + "ObjectTransformation");
		objectTransformation.addLabel("Object Transformation", "en");
		objectTransformation.addLabel("Трансформация Объекта", "ru");

		OntClass linkTransformation = m.createClass(NS + "LinkTransformation");
		linkTransformation.addLabel("Link Transformation", "en");
		linkTransformation.addLabel("Трансформация Связи", "ru");

		OntClass systemTransformation = m.createClass(NS + "SystemTransformation");
		systemTransformation.addLabel("System Transformation", "en");
		systemTransformation.addLabel("Трансформация Системы", "ru");

		OntClass objectTemplate = m.createClass(NS + "ObjectTemplate");
		objectTemplate.addLabel("Object Template", "en");
		objectTemplate.addLabel("Шаблон объекта", "ru");

		OntClass linkTemplate = m.createClass(NS + "LinkTemplate");
		linkTemplate.addLabel("Link Template", "en");
		linkTemplate.addLabel("Шаблон Связи", "ru");

		OntClass systemTemplate = m.createClass(NS + "SystemTemplate");
		systemTemplate.addLabel("System Template", "en");
		systemTemplate.addLabel("Шаблон Системы", "ru");

		OntClass element = m.createClass(NS + "Element");
		element.addLabel("Element", "en");
		element.addLabel("Элемент", "ru");

		// Data properties

		DatatypeProperty objectAttribute = m.createDatatypeProperty(NS + "objectAttribute");
		objectAttribute.addLabel("object attribute", "en");
		objectAttribute.addLabel("атрибут объекта", "ru");
		objectAttribute.addDomain(object);
		objectAttribute.addRange(XSD.xstring);

		DatatypeProperty objectTemplateAttribute = m.createDatatypeProperty(NS + "objectTemplateAttribute");
		objectTemplateAttribute.addLabel("object template attribute", "en");
		objectTemplateAttribute.addLabel("атрибут шаблона объекта", "ru");
		objectTemplateAttribute.addDomain(objectTemplate);
		objectTemplateAttribute.addRange(XSD.xstring);

		DatatypeProperty valueOfAttributeTransformation = m
				.createDatatypeProperty(NS + "valueOfAttributeTransformation");
		valueOfAttributeTransformation.addLabel("value of attribute transformation", "en");
		valueOfAttributeTransformation.addLabel("значение трансформируемого атрибута", "ru");
		valueOfAttributeTransformation.addDomain(objectTransformation);
		valueOfAttributeTransformation.addRange(XSD.xboolean);
		valueOfAttributeTransformation.addRange(XSD.xint);
		valueOfAttributeTransformation.addRange(XSD.xstring);

		DatatypeProperty nodeId = m.createDatatypeProperty(NS + "nodeId");
		nodeId.addLabel("node id", "en");
		nodeId.addLabel("идентификатор вершины", "ru");
		nodeId.addDomain(node);
		nodeId.addRange(XSD.xstring);

		DatatypeProperty objectId = m.createDatatypeProperty(NS + "objectId");
		objectId.addLabel("object id", "en");
		objectId.addLabel("идентификатор объекта", "ru");
		objectId.addDomain(object);
		objectId.addRange(XSD.xstring);

		DatatypeProperty edgeId = m.createDatatypeProperty(NS + "edgeId");
		edgeId.addLabel("edge id", "en");
		edgeId.addLabel("идентификатор ребра", "ru");
		edgeId.addDomain(edge);
		edgeId.addRange(XSD.xstring);

		DatatypeProperty objectTemplateId = m.createDatatypeProperty(NS + "objectTemplateId");
		objectTemplateId.addLabel("object template id", "en");
		objectTemplateId.addLabel("идентификатор шаблона объекта", "ru");
		objectTemplateId.addDomain(objectTemplate);
		objectTemplateId.addRange(XSD.xstring);

		DatatypeProperty actionName = m.createDatatypeProperty(NS + "actionName");
		actionName.addLabel("action name", "en");
		actionName.addLabel("имя действия", "ru");
		actionName.addDomain(action);
		actionName.addRange(XSD.xstring);

		DatatypeProperty objectName = m.createDatatypeProperty(NS + "objectName");
		objectName.addLabel("object name", "en");
		objectName.addLabel("имя объекта", "ru");
		objectName.addDomain(object);
		objectName.addRange(XSD.xstring);

		DatatypeProperty linkName = m.createDatatypeProperty(NS + "linkName");
		linkName.addLabel("link name", "en");
		linkName.addLabel("имя связи", "ru");
		linkName.addDomain(link);
		linkName.addRange(XSD.xstring);

		DatatypeProperty elementName = m.createDatatypeProperty(NS + "elementName");
		elementName.addLabel("element name", "en");
		elementName.addLabel("имя элемента", "ru");
		elementName.addDomain(element);
		elementName.addRange(XSD.xstring);

		DatatypeProperty nameOfAttributeTransformation = m.createDatatypeProperty(NS + "nameOfAttributeTransformation");
		nameOfAttributeTransformation.addLabel("name of attribute transformation", "en");
		nameOfAttributeTransformation.addLabel("имя трансформируемого атрибута", "ru");
		nameOfAttributeTransformation.addDomain(objectTransformation);
		nameOfAttributeTransformation.addRange(XSD.xstring);

		DatatypeProperty linkTemplateName = m.createDatatypeProperty(NS + "linkTemplateName");
		linkTemplateName.addLabel("object template name", "en");
		linkTemplateName.addLabel("имя шаблона связи", "ru");
		linkTemplateName.addDomain(linkTemplate);
		linkTemplateName.addRange(XSD.xstring);

		DatatypeProperty actionCommands = m.createDatatypeProperty(NS + "actionCommands");
		actionCommands.addLabel("action commands", "en");
		actionCommands.addLabel("команды действия", "ru");
		actionCommands.addDomain(action);
		actionCommands.addRange(XSD.xstring);

		DatatypeProperty operationParameter = m.createDatatypeProperty(NS + "operationParameter");
		operationParameter.addLabel("operation parameter", "en");
		operationParameter.addLabel("параметр операции", "ru");
		operationParameter.addDomain(operation);

		DatatypeProperty actionCondition = m.createDatatypeProperty(NS + "actionCondition");
		actionCondition.addLabel("action condition", "en");
		actionCondition.addLabel("условие действия", "ru");
		actionCondition.addDomain(action);
		actionCondition.addRange(XSD.xstring);

		m.write(System.out, "RDF/XML");
	}
}
