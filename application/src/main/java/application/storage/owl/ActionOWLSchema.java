package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;

public class ActionOWLSchema implements OWLSchema<Action> {

	@Override
	public Individual combine(Action action) {
		Individual ind_action = class_Action
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_action.addProperty(dataProperty_name, action.getName());
		for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
			// << Individual: PreConditionChecker
			Individual ind_preConditionChecker = class_PreConditionChecker
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
			String lines[] = luaPreConditionChecker.getScript().split("\n");
			for (int i = 0; i < lines.length; i++) {
				// << Individual: Line
				Individual ind_line = class_line
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_line.addProperty(dataProperty_number, Integer.toString(i + 1));
				ind_line.addProperty(dataProperty_text, lines[i]);
				ind_line.addProperty(objectProperty_isLineOf, ind_preConditionChecker);
				ind_preConditionChecker.addProperty(objectProperty_hasLine, ind_line);
				// >> Individual: Line
			}
			ind_action.addProperty(objectProperty_hasPreConditionChecker, ind_preConditionChecker);
			ind_preConditionChecker.addProperty(objectProperty_isPreConditionCheckerOf, ind_action);
			// >> Individual: PreConditionChecker
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			// << Individual: ParameterUpdater
			Individual ind_parameterUpdater = class_ParameterUpdater
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
			String lines[] = luaParameterUpdater.getScript().split("\n");
			for (int i = 0; i < lines.length; i++) {
				// << Individual: Line
				Individual ind_line = class_line
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_line.addProperty(dataProperty_number, Integer.toString(i + 1));
				ind_line.addProperty(dataProperty_text, lines[i]);
				ind_line.addProperty(objectProperty_isLineOf, ind_parameterUpdater);
				ind_parameterUpdater.addProperty(objectProperty_hasLine, ind_line);
				// >> Individual: Line
			}
			ind_action.addProperty(objectProperty_hasParameterUpdater, ind_parameterUpdater);
			ind_parameterUpdater.addProperty(objectProperty_isParameterUpdaterOf, ind_action);
			// >> Individual: ParameterUpdater
		}
		return ind_action;
	}

	@Override
	public Action parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	private OntClass class_Action;

	private OntClass class_PreConditionChecker;

	private OntClass class_ParameterUpdater;

	private OntClass class_line;

	private ObjectProperty objectProperty_hasPreConditionChecker;

	private ObjectProperty objectProperty_isPreConditionCheckerOf;

	private ObjectProperty objectProperty_hasParameterUpdater;

	private ObjectProperty objectProperty_isParameterUpdaterOf;

	private ObjectProperty objectProperty_hasLine;

	private ObjectProperty objectProperty_isLineOf;

	private DatatypeProperty dataProperty_name;

	private DatatypeProperty dataProperty_number;

	private DatatypeProperty dataProperty_text;

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_Action = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Action);
		class_PreConditionChecker = ontModel.getOntClass(SystemTransformationsOWLModel.URI_PreConditionChecker);
		class_ParameterUpdater = ontModel.getOntClass(SystemTransformationsOWLModel.URI_ParameterUpdater);
		class_line = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Line);

		objectProperty_hasPreConditionChecker = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasPreConditionChecker);
		objectProperty_isPreConditionCheckerOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isPreConditionCheckerOf);
		objectProperty_hasParameterUpdater = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasParameterUpdater);
		objectProperty_isParameterUpdaterOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isParameterUpdaterOf);
		objectProperty_hasLine = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasLine);
		objectProperty_isLineOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isLineOf);

		dataProperty_name = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_name);
		dataProperty_number = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_number);
		dataProperty_text = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_text);
	}
}
