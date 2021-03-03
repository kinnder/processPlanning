package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;

import planning.model.Attribute;

public class AttributeOWLSchema implements OWLSchema<Attribute> {

	private TaskDescriptionOWLModel owlModel;

	public AttributeOWLSchema(TaskDescriptionOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Attribute attribute) {
		int i = 0;
		int j = 0;
		Individual ind_attribute = owlModel.getClass_Attribute().createIndividual(owlModel.getUniqueURI());
		ind_attribute.addLabel("Атрибут ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "ru");
		ind_attribute.addLabel("Attribute ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "en");
		ind_attribute.addProperty(owlModel.getDataProperty_name(), attribute.getName());
		// TODO (2020-12-13 #31): поддержка других DataType
		ind_attribute.addProperty(owlModel.getDataProperty_value(), attribute.getValue().toString(),
				XSDDatatype.XSDstring);
		return ind_attribute;
	}

	@Override
	public Attribute parse(Individual individual) {
		// TODO Auto-generated method stub
		return new Attribute("test-name", "test-value");
	}
}
