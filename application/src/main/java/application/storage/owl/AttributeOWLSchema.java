package application.storage.owl;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;

import planning.model.Attribute;

public class AttributeOWLSchema implements OWLSchema<Attribute> {

	private PlanningOWLModel owlModel;

	public AttributeOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Attribute attribute) {
		final Individual ind_attribute = owlModel.newIndividual_Attribute();
		final String name = attribute.getName();
		ind_attribute.addLabel(String.format("Атрибут \"%s\"", name), "ru");
		ind_attribute.addLabel(String.format("Attribute \"%s\"", name), "en");
		ind_attribute.addProperty(owlModel.getDataProperty_name(), name);
		final Object value = attribute.getValue();
		if (value instanceof Boolean) {
			ind_attribute.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDboolean);
		} else if (value instanceof Integer) {
			ind_attribute.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDinteger);
		} else if (value instanceof String) {
			ind_attribute.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDstring);
		}
		return ind_attribute;
	}

	@Override
	public Attribute parse(Individual ind_attribute) {
		final String name = ind_attribute.getProperty(owlModel.getDataProperty_name()).getString();

		final Statement valueStatement = ind_attribute.getProperty(owlModel.getDataProperty_value());
		if (valueStatement != null) {
			final Literal valueLiteral = valueStatement.getLiteral();
			final RDFDatatype valueType = valueLiteral.getDatatype();
			if (valueType == XSDDatatype.XSDboolean) {
				return new Attribute(name, valueLiteral.getBoolean());
			}
			if (valueType == XSDDatatype.XSDstring) {
				return new Attribute(name, valueLiteral.getString());
			}
			if (valueType == XSDDatatype.XSDinteger) {
				return new Attribute(name, valueLiteral.getInt());
			}
		}
		return new Attribute(name);
	}
}
