package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;

//TODO : rename schema to match generic class
public class ParameterUpdaterOWLSchema implements OWLSchema<ActionParameterUpdater> {

	private SystemTransformationsOWLModel owlModel;

	public ParameterUpdaterOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(ActionParameterUpdater parameterUpdater) {
		Individual ind_parameterUpdater = owlModel.getClass_ParameterUpdater()
				.createIndividual(owlModel.getUniqueURI());
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		String lines[] = luaParameterUpdater.getScript().split("\n");
		for (int i = 0; i < lines.length; i++) {
			// << Individual: Line
			Individual ind_line = owlModel.getClass_line().createIndividual(owlModel.getUniqueURI());
			ind_line.addProperty(owlModel.getDataProperty_number(), Integer.toString(i + 1));
			ind_line.addProperty(owlModel.getDataProperty_text(), lines[i]);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
			// >> Individual: Line
		}
		return ind_parameterUpdater;
	}

	@Override
	public ActionParameterUpdater parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}
