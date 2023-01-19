package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import planning.model.LuaScriptLine;

public class LuaScriptLineOWLSchema implements OWLSchema<LuaScriptLine> {

	private PlanningOWLModel owlModel;

	public LuaScriptLineOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LuaScriptLine scriptLine) {
		final Individual ind_line = owlModel.newIndividual_Line();
		final String number = scriptLine.getNumber().toString();
		ind_line.addLabel(String.format("Линия \"%s\"", number), "ru");
		ind_line.addLabel(String.format("Line \"%s\"", number), "en");
		ind_line.addProperty(owlModel.getDataProperty_text(), scriptLine.getText());
		ind_line.addProperty(owlModel.getDataProperty_number(), number, XSDDatatype.XSDinteger);
		return ind_line;
	}

	@Override
	public LuaScriptLine parse(Individual ind_line) {
		final String text = ind_line.getProperty(owlModel.getDataProperty_text()).getString();
		final int number = ind_line.getProperty(owlModel.getDataProperty_number()).getInt();
		return new LuaScriptLine(number, text);
	}
}
