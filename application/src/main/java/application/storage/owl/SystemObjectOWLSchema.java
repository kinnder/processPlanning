package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.Attribute;
import planning.model.SystemObject;

public class SystemObjectOWLSchema implements OWLSchema<SystemObject> {

	private OWLModelCommonPart owlModel;

	private AttributeOWLSchema attributeOWLSchema;

	public SystemObjectOWLSchema(OWLModelCommonPart owlModel) {
		this.owlModel = owlModel;

		this.attributeOWLSchema = new AttributeOWLSchema(owlModel);
	}

	@Override
	public Individual combine(SystemObject systemObject) {
		Individual ind_systemObject = owlModel.getClass_SystemObject().createIndividual(owlModel.getUniqueURI());
		ind_systemObject.addLabel("System Object", "en");
		ind_systemObject.addLabel("Объект системы", "ru");
		ind_systemObject.addProperty(owlModel.getDataProperty_name(), systemObject.getName());
		ind_systemObject.addProperty(owlModel.getDataProperty_id(), systemObject.getId());

		for (Attribute attribute : systemObject.getAttributes()) {
			Individual ind_attribute = attributeOWLSchema.combine(attribute);
			ind_attribute.addProperty(owlModel.getObjectProperty_isAttributeOf(), ind_systemObject);
			ind_systemObject.addProperty(owlModel.getObjectProperty_hasAttribute(), ind_attribute);
		}

		return ind_systemObject;
	}

	@Override
	public SystemObject parse(Individual ind_systemObject) {
		String name = ind_systemObject.getProperty(owlModel.getDataProperty_name()).getString();
		String id = ind_systemObject.getProperty(owlModel.getDataProperty_id()).getString();

		SystemObject systemObject = new SystemObject(name, id);

		owlModel.getClass_Attribute().listInstances().filterKeep((ind_attribute) -> {
			return ind_systemObject.hasProperty(owlModel.getObjectProperty_hasAttribute(), ind_attribute);
		}).forEachRemaining((ind_attribute) -> {
			Attribute attribute = attributeOWLSchema.parse(ind_attribute.asIndividual());
			systemObject.addAttribute(attribute);
		});

		return systemObject;
	}
}
