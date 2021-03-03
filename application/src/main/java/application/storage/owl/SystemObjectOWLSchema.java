package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;

import planning.model.Attribute;
import planning.model.SystemObject;

public class SystemObjectOWLSchema implements OWLSchema<SystemObject> {

	private TaskDescriptionOWLModel owlModel;

	public SystemObjectOWLSchema(TaskDescriptionOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(SystemObject systemObject) {
		int i = 0;
		Individual ind_systemObject = owlModel.getClass_SystemObject().createIndividual(owlModel.getUniqueURI());
		ind_systemObject.addLabel("System Object ".concat(Integer.toString(i)), "en");
		ind_systemObject.addLabel("Объект системы ".concat(Integer.toString(i)), "ru");
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
		return ind_systemObject;
	}

	@Override
	public SystemObject parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}
