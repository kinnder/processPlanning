package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;
import planning.method.TaskDescription;

public class TaskDescriptionOWLSchema implements OWLSchema<TaskDescription> {

	// TODO (2020-12-13 #31): включить проверку copy-paste
	// CPD-OFF

	private TaskDescriptionOWLModel owlModel;

	public TaskDescriptionOWLSchema(TaskDescriptionOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(TaskDescription taskDescription) {
		// Individuals
		Individual ind_taskDescription = owlModel.getClass_TaskDescription().createIndividual(owlModel.getUniqueURI());
		ind_taskDescription.addLabel("Task Description 1", "en");
		ind_taskDescription.addLabel("Описания задания 1", "ru");

		Individual ind_initialSystem = owlModel.getClass_InitialSystem().createIndividual(owlModel.getUniqueURI());
		ind_initialSystem.addLabel("Initial system 1", "en");
		ind_initialSystem.addLabel("Начальная система 1", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasInitialSystem(), ind_initialSystem);
		ind_initialSystem.addProperty(owlModel.getObjectProperty_isInitialSystemOf(), ind_taskDescription);

		System initialSystem = taskDescription.getInitialSystem();
		int i = 0;
		for (SystemObject systemObject : initialSystem.getObjects()) {
			i++;
			Individual ind_systemObject = owlModel.getClass_SystemObject().createIndividual(owlModel.getUniqueURI());
			ind_systemObject.addLabel("System Object ".concat(Integer.toString(i)), "en");
			ind_systemObject.addLabel("Объект системы ".concat(Integer.toString(i)), "ru");
			ind_initialSystem.addProperty(owlModel.getObjectProperty_hasSystemObject(), ind_systemObject);
			ind_systemObject.addProperty(owlModel.getObjectProperty_isSystemObjectOf(), ind_initialSystem);
			ind_systemObject.addProperty(owlModel.getDataProperty_name(), systemObject.getName());
			ind_systemObject.addProperty(owlModel.getDataProperty_id(), systemObject.getId());
			int j = 0;
			for (Attribute attribute : systemObject.getAttributes()) {
				Individual ind_attribute = owlModel.getClass_Attribute().createIndividual(owlModel.getUniqueURI());
				ind_attribute.addLabel("Атрибут ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"ru");
				ind_attribute.addLabel("Attribute ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"en");
				ind_attribute.addProperty(owlModel.getDataProperty_name(), attribute.getName());
				// TODO (2020-12-13 #31): поддержка других DataType
				ind_attribute.addProperty(owlModel.getDataProperty_value(), attribute.getValue().toString(),
						XSDDatatype.XSDstring);
				ind_systemObject.addProperty(owlModel.getObjectProperty_hasAttribute(), ind_attribute);
				ind_attribute.addProperty(owlModel.getObjectProperty_isAttributeOf(), ind_systemObject);
			}
		}
		i = 0;
		for (Link link : initialSystem.getLinks()) {
			Individual ind_link = owlModel.getClass_Link().createIndividual(owlModel.getUniqueURI());
			ind_link.addLabel("Link ".concat(Integer.toString(i)), "en");
			ind_link.addLabel("Связь ".concat(Integer.toString(i)), "ru");
			ind_initialSystem.addProperty(owlModel.getObjectProperty_hasLink(), ind_link);
			ind_link.addProperty(owlModel.getObjectProperty_isLinkOf(), ind_initialSystem);
			ind_link.addProperty(owlModel.getDataProperty_name(), link.getName());
			String objectId1 = link.getObjectId1();
			if (objectId1 != null) {
				ind_link.addProperty(owlModel.getDataProperty_objectId1(), objectId1);
			}
			String objectId2 = link.getObjectId2();
			if (objectId2 != null) {
				ind_link.addProperty(owlModel.getDataProperty_objectId2(), objectId2);
			}
		}

		Individual ind_finalSystem = owlModel.getClass_InitialSystem().createIndividual(owlModel.getUniqueURI());
		ind_finalSystem.addLabel("Final system 1", "en");
		ind_finalSystem.addLabel("Конечная система 1", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasFinalSystem(), ind_finalSystem);
		ind_finalSystem.addProperty(owlModel.getObjectProperty_isFinalSystemOf(), ind_taskDescription);

		//
		System finalSystem = taskDescription.getFinalSystem();
		i = 0;
		for (SystemObject systemObject : finalSystem.getObjects()) {
			i++;
			Individual ind_systemObject = owlModel.getClass_SystemObject().createIndividual(owlModel.getUniqueURI());
			ind_systemObject.addLabel("System Object ".concat(Integer.toString(i)), "en");
			ind_systemObject.addLabel("Объект системы ".concat(Integer.toString(i)), "ru");
			ind_initialSystem.addProperty(owlModel.getObjectProperty_hasSystemObject(), ind_systemObject);
			ind_systemObject.addProperty(owlModel.getObjectProperty_isSystemObjectOf(), ind_initialSystem);
			ind_systemObject.addProperty(owlModel.getDataProperty_name(), systemObject.getName());
			ind_systemObject.addProperty(owlModel.getDataProperty_id(), systemObject.getId());
			int j = 0;
			for (Attribute attribute : systemObject.getAttributes()) {
				Individual ind_attribute = owlModel.getClass_Attribute().createIndividual(owlModel.getUniqueURI());
				ind_attribute.addLabel("Атрибут ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"ru");
				ind_attribute.addLabel("Attribute ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"en");
				ind_attribute.addProperty(owlModel.getDataProperty_name(), attribute.getName());
				// TODO (2020-12-13 #31): поддержка других DataType
				ind_attribute.addProperty(owlModel.getDataProperty_value(), attribute.getValue().toString(),
						XSDDatatype.XSDstring);
				ind_systemObject.addProperty(owlModel.getObjectProperty_hasAttribute(), ind_attribute);
				ind_attribute.addProperty(owlModel.getObjectProperty_isAttributeOf(), ind_systemObject);
			}
		}
		i = 0;
		for (Link link : finalSystem.getLinks()) {
			Individual ind_link = owlModel.getClass_Link().createIndividual(owlModel.getUniqueURI());
			ind_link.addLabel("Link ".concat(Integer.toString(i)), "en");
			ind_link.addLabel("Связь ".concat(Integer.toString(i)), "ru");
			ind_initialSystem.addProperty(owlModel.getObjectProperty_hasLink(), ind_link);
			ind_link.addProperty(owlModel.getObjectProperty_isLinkOf(), ind_initialSystem);
			ind_link.addProperty(owlModel.getDataProperty_name(), link.getName());
			String objectId1 = link.getObjectId1();
			if (objectId1 != null) {
				ind_link.addProperty(owlModel.getDataProperty_objectId1(), objectId1);
			}
			String objectId2 = link.getObjectId2();
			if (objectId2 != null) {
				ind_link.addProperty(owlModel.getDataProperty_objectId2(), objectId2);
			}
		}
		return ind_taskDescription;
	}

	@Override
	public TaskDescription parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO (2020-12-13 #31): включить проверку copy-paste
	// CPD-ON
}
