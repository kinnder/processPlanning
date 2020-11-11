package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;

import planning.model.System;
import planning.model.SystemObject;
import planning.method.TaskDescription;

public class TaskDescriptionOWLSchema implements OWLSchema<TaskDescription> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/task-description#";

	@Override
	public OntModel combine(TaskDescription taskDescription) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_taskDescription = m.createClass(NS + "TaskDescription");
		ontClass_taskDescription.addLabel("Task Description", "en");
		ontClass_taskDescription.addLabel("Описание задания", "ru");

		OntClass ontClass_initialSystem = m.createClass(NS + "InitialSystem");
		ontClass_initialSystem.addLabel("Initial System", "en");
		ontClass_initialSystem.addLabel("Начальная система", "ru");

		OntClass ontClass_finalSystem = m.createClass(NS + "FinalSystem");
		ontClass_finalSystem.addLabel("Final System", "en");
		ontClass_finalSystem.addLabel("Конечная система", "ru");

		OntClass ontClass_system = m.createClass(NS + "System");
		ontClass_system.addLabel("System", "en");
		ontClass_system.addLabel("Система", "ru");

		ontClass_system.addSubClass(ontClass_initialSystem);
		ontClass_system.addSubClass(ontClass_finalSystem);
		ontClass_initialSystem.addSuperClass(ontClass_system);
		ontClass_finalSystem.addSuperClass(ontClass_system);

		OntClass ontClass_systemObject = m.createClass(NS + "SystemObject");
		ontClass_systemObject.addLabel("System Object", "en");
		ontClass_systemObject.addLabel("Объект системы", "ru");

		OntClass ontClass_link = m.createClass(NS + "Link");
		ontClass_link.addLabel("Link", "en");
		ontClass_link.addLabel("Связь", "ru");

		OntClass ontClass_attribute = m.createClass(NS + "Attribute");
		ontClass_attribute.addLabel("Attribute", "en");
		ontClass_attribute.addLabel("Атрибут", "ru");

		ObjectProperty ontObjectProperty_hasInitialSystem = m.createObjectProperty(NS + "hasInitialSystem");
		ontObjectProperty_hasInitialSystem.addLabel("has initial system", "en");
		ontObjectProperty_hasInitialSystem.addLabel("имеет начальную систему", "ru");
		ontObjectProperty_hasInitialSystem.addDomain(ontClass_taskDescription);
		ontObjectProperty_hasInitialSystem.addRange(ontClass_initialSystem);

		ObjectProperty ontObjectProperty_isInitialSystemOf = m.createObjectProperty(NS + "isInitialSystemOf");
		ontObjectProperty_isInitialSystemOf.addLabel("is initial system of", "en");
		ontObjectProperty_isInitialSystemOf.addLabel("является начальной системой для", "ru");
		ontObjectProperty_isInitialSystemOf.addDomain(ontClass_initialSystem);
		ontObjectProperty_isInitialSystemOf.addRange(ontClass_taskDescription);

		ontObjectProperty_hasInitialSystem.addInverseOf(ontObjectProperty_isInitialSystemOf);
		ontObjectProperty_isInitialSystemOf.addInverseOf(ontObjectProperty_hasInitialSystem);

		ObjectProperty ontObjectProperty_hasFinalSystem = m.createObjectProperty(NS + "hasFinalSystem");
		ontObjectProperty_hasFinalSystem.addLabel("has final system", "en");
		ontObjectProperty_hasFinalSystem.addLabel("имеет конечную систему", "ru");
		ontObjectProperty_hasFinalSystem.addDomain(ontClass_taskDescription);
		ontObjectProperty_hasFinalSystem.addRange(ontClass_finalSystem);

		ObjectProperty ontObjectProperty_isFinalSystemOf = m.createObjectProperty(NS + "isFinalSystemOf");
		ontObjectProperty_isFinalSystemOf.addLabel("is final system of", "en");
		ontObjectProperty_isFinalSystemOf.addLabel("является конечной системой для", "ru");
		ontObjectProperty_isFinalSystemOf.addDomain(ontClass_finalSystem);
		ontObjectProperty_isFinalSystemOf.addRange(ontClass_taskDescription);

		ontObjectProperty_hasFinalSystem.addInverseOf(ontObjectProperty_isFinalSystemOf);
		ontObjectProperty_isFinalSystemOf.addInverseOf(ontObjectProperty_hasFinalSystem);

		ObjectProperty ontObjectProperty_hasSystemObject = m.createObjectProperty(NS + "hasSystemObject");
		ontObjectProperty_hasSystemObject.addLabel("has system object", "en");
		ontObjectProperty_hasSystemObject.addLabel("имеет объект системы", "ru");
		ontObjectProperty_hasSystemObject.addDomain(ontClass_system);
		ontObjectProperty_hasSystemObject.addRange(ontClass_systemObject);

		ObjectProperty ontObjectProperty_isSystemObjectOf = m.createObjectProperty(NS + "isSystemObjectOf");
		ontObjectProperty_isSystemObjectOf.addLabel("is system object of", "en");
		ontObjectProperty_isSystemObjectOf.addLabel("является объектом системы для", "ru");
		ontObjectProperty_isSystemObjectOf.addDomain(ontClass_systemObject);
		ontObjectProperty_isSystemObjectOf.addRange(ontClass_system);

		ontObjectProperty_hasSystemObject.addInverseOf(ontObjectProperty_isSystemObjectOf);
		ontObjectProperty_isSystemObjectOf.addInverseOf(ontObjectProperty_hasSystemObject);

		ObjectProperty ontObjectProperty_hasLink = m.createObjectProperty(NS + "hasLink");
		ontObjectProperty_hasLink.addLabel("has link", "en");
		ontObjectProperty_hasLink.addLabel("имеет связь", "run");
		ontObjectProperty_hasLink.addDomain(ontClass_system);
		ontObjectProperty_hasLink.addRange(ontClass_link);

		ObjectProperty ontObjectProperty_isLinkOf = m.createObjectProperty(NS + "isLinkOf");
		ontObjectProperty_isLinkOf.addLabel("is link of", "en");
		ontObjectProperty_isLinkOf.addLabel("является связью для", "ru");
		ontObjectProperty_isLinkOf.addDomain(ontClass_link);
		ontObjectProperty_isLinkOf.addRange(ontClass_system);

		ontObjectProperty_hasLink.addInverseOf(ontObjectProperty_isLinkOf);
		ontObjectProperty_isLinkOf.addInverseOf(ontObjectProperty_hasLink);

		ObjectProperty ontObjectProperty_hasAttribute = m.createObjectProperty(NS + "hasAttribute");
		ontObjectProperty_hasAttribute.addLabel("has attribute", "en");
		ontObjectProperty_hasAttribute.addLabel("имеет атрибут", "ru");
		ontObjectProperty_hasAttribute.addDomain(ontClass_systemObject);
		ontObjectProperty_hasAttribute.addRange(ontClass_attribute);

		ObjectProperty ontObjectProperty_isAttributeOf = m.createObjectProperty(NS + "isAttributeOf");
		ontObjectProperty_isAttributeOf.addLabel("is attribute of", "en");
		ontObjectProperty_isAttributeOf.addLabel("является атрибутом для", "ru");
		ontObjectProperty_isAttributeOf.addDomain(ontClass_attribute);
		ontObjectProperty_isAttributeOf.addRange(ontClass_systemObject);

		ontObjectProperty_hasAttribute.addInverseOf(ontObjectProperty_isAttributeOf);
		ontObjectProperty_isAttributeOf.addInverseOf(ontObjectProperty_hasAttribute);

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addDomain(ontClass_systemObject);
		ontDatatypeProperty_name.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_id = m.createDatatypeProperty(NS + "id");
		ontDatatypeProperty_id.addLabel("id", "en");
		ontDatatypeProperty_id.addLabel("идентификатор", "ru");
		ontDatatypeProperty_id.addDomain(ontClass_systemObject);
		ontDatatypeProperty_id.addRange(XSD.xstring);

		ontDatatypeProperty_name.addDomain(ontClass_attribute);

		DatatypeProperty ontDatatypeProperty_value = m.createDatatypeProperty(NS + "value");
		ontDatatypeProperty_value.addLabel("value", "en");
		ontDatatypeProperty_value.addLabel("значение", "ru");
		ontDatatypeProperty_value.addDomain(ontClass_attribute);
		ontDatatypeProperty_value.addRange(XSD.xstring);
		ontDatatypeProperty_value.addRange(XSD.xboolean);
		ontDatatypeProperty_value.addRange(XSD.xint);

		ontDatatypeProperty_name.addDomain(ontClass_link);

		DatatypeProperty ontDatatypeProperty_objectId1 = m.createDatatypeProperty(NS + "objectId1");
		ontDatatypeProperty_objectId1.addLabel("objectId1", "en");
		ontDatatypeProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		ontDatatypeProperty_objectId1.addDomain(ontClass_link);
		ontDatatypeProperty_objectId1.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId2 = m.createDatatypeProperty(NS + "objectId2");
		ontDatatypeProperty_objectId2.addLabel("objectId2", "en");
		ontDatatypeProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		ontDatatypeProperty_objectId2.addDomain(ontClass_link);
		ontDatatypeProperty_objectId2.addRange(XSD.xstring);

		// Individuals
		Individual ind_taskDescription = ontClass_taskDescription.createIndividual(NS + UUID.randomUUID().toString());
		ind_taskDescription.addLabel("Task Description 1", "en");
		ind_taskDescription.addLabel("Описания задания 1", "ru");

		Individual ind_initialSystem = ontClass_initialSystem.createIndividual(NS + UUID.randomUUID().toString());
		ind_initialSystem.addLabel("Initial system 1", "en");
		ind_initialSystem.addLabel("Начальная система 1", "ru");
		ind_taskDescription.addProperty(ontObjectProperty_hasInitialSystem, ind_initialSystem);
		ind_initialSystem.addProperty(ontObjectProperty_isInitialSystemOf, ind_taskDescription);

		System initialSystem = taskDescription.getInitialSystem();
		int i = 0;
		for (SystemObject systemObject : initialSystem.getObjects()) {
			i++;
			Individual ind_systemObject = ontClass_systemObject.createIndividual(NS + UUID.randomUUID().toString());
			ind_systemObject.addLabel("System Object ".concat(Integer.toString(i)), "en");
			ind_systemObject.addLabel("Объект системы ".concat(Integer.toString(i)), "ru");
			ind_initialSystem.addProperty(ontObjectProperty_hasSystemObject, ind_systemObject);
			ind_systemObject.addProperty(ontObjectProperty_isSystemObjectOf, ind_initialSystem);
			ind_systemObject.addProperty(ontDatatypeProperty_name, systemObject.getName());
			ind_systemObject.addProperty(ontDatatypeProperty_id, systemObject.getId());
		}
//		for (Link link : initialSystem.getLinks()) {
//		}

		Individual ind_finalSystem = ontClass_initialSystem.createIndividual(NS + UUID.randomUUID().toString());
		ind_finalSystem.addLabel("Final system 1", "en");
		ind_finalSystem.addLabel("Конечная система 1", "ru");
		ind_taskDescription.addProperty(ontObjectProperty_hasFinalSystem, ind_finalSystem);
		ind_finalSystem.addProperty(ontObjectProperty_isFinalSystemOf, ind_taskDescription);

//		System finalSystem = taskDescription.getFinalSystem();
//		for (SystemObject systemObject : finalSystem.getObjects()) {
//		}
//		for (Link link : finalSystem.getLinks()) {
//		}

		return m;
	}

	@Override
	public TaskDescription parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}
}
