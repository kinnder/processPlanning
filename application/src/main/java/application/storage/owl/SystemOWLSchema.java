package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class SystemOWLSchema implements OWLSchema<System> {

	private OWLModelCommonPart owlModel;

	private SystemObjectOWLSchema systemObjectOWLSchema;

	private LinkOWLSchema linkOWLSchema;

	public SystemOWLSchema(OWLModelCommonPart owlModel) {
		this.owlModel = owlModel;

		this.systemObjectOWLSchema = new SystemObjectOWLSchema(owlModel);
		this.linkOWLSchema = new LinkOWLSchema(owlModel);
	}

	@Override
	public Individual combine(System system) {
		Individual ind_system = owlModel.newIndividual_System();
		ind_system.addLabel("System", "en");
		ind_system.addLabel("Система", "ru");

		for (SystemObject systemObject : system.getObjects()) {
			Individual ind_systemObject = systemObjectOWLSchema.combine(systemObject);
			ind_systemObject.addProperty(owlModel.getObjectProperty_isSystemObjectOf(), ind_system);
			ind_system.addProperty(owlModel.getObjectProperty_hasSystemObject(), ind_systemObject);
		}

		for (Link link : system.getLinks()) {
			Individual ind_link = linkOWLSchema.combine(link);
			ind_link.addProperty(owlModel.getObjectProperty_isLinkOf(), ind_system);
			ind_system.addProperty(owlModel.getObjectProperty_hasLink(), ind_link);
		}

		return ind_system;
	}

	@Override
	public System parse(Individual ind_system) {
		System system = new System();

		owlModel.getClass_SystemObject().listInstances().filterKeep((ind_object) -> {
			return ind_system.hasProperty(owlModel.getObjectProperty_hasSystemObject(), ind_object);
		}).forEachRemaining((ind_object) -> {
			SystemObject systemObject = systemObjectOWLSchema.parse(ind_object.asIndividual());
			system.addObject(systemObject);
		});

		owlModel.getClass_Link().listInstances().filterKeep((ind_link) -> {
			return ind_system.hasProperty(owlModel.getObjectProperty_hasLink(), ind_link);
		}).forEachRemaining((ind_link) -> {
			Link link = linkOWLSchema.parse(ind_link.asIndividual());
			system.addLink(link);
		});

		return system;
	}
}
