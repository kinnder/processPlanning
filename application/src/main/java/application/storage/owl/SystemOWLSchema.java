package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;

import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class SystemOWLSchema implements OWLSchema<System> {

	private PlanningOWLModel owlModel;

	private SystemObjectOWLSchema systemObjectOWLSchema;

	private LinkOWLSchema linkOWLSchema;

	public SystemOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemObjectOWLSchema(owlModel), new LinkOWLSchema(owlModel));
	}

	SystemOWLSchema(PlanningOWLModel owlModel, SystemObjectOWLSchema systemObjectOWLSchema, LinkOWLSchema linkOWLSchema) {
		this.owlModel = owlModel;
		this.systemObjectOWLSchema = systemObjectOWLSchema;
		this.linkOWLSchema = linkOWLSchema;
	}

	@Override
	public Individual combine(System system) {
		Individual ind_system = owlModel.newIndividual_System();
		ind_system.addLabel("System", "en");
		ind_system.addLabel("Система", "ru");

		Map<String, Individual> ind_systemObjects = new HashMap<String, Individual>();

		for (SystemObject systemObject : system.getObjects()) {
			Individual ind_systemObject = systemObjectOWLSchema.combine(systemObject);
			ind_system.addProperty(owlModel.getObjectProperty_hasSystemObject(), ind_systemObject);

			ind_systemObjects.put(systemObject.getId(), ind_systemObject);
		}

		for (Link link : system.getLinks()) {
			Individual ind_link = linkOWLSchema.combine(link);
			ind_system.addProperty(owlModel.getObjectProperty_hasLink(), ind_link);

			String id1 = link.getId1();
			if (id1 != null) {
				ind_link.addProperty(owlModel.getObjectProperty_hasSystemObject1(), ind_systemObjects.get(id1));
			}
			String id2 = link.getId2();
			if (id2 != null) {
				ind_link.addProperty(owlModel.getObjectProperty_hasSystemObject2(), ind_systemObjects.get(id2));
			}
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
