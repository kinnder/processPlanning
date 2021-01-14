package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	// TODO (2021-01-13 #31): включить проверку copy-paste
	// CPD-OFF

	private OntClass class_SystemTransformations;

	private OntClass class_SystemTransformation;

	private OntClass class_SystemTemplate;

	private OntClass class_Transformations;

	private OntClass class_Action;

	private OntClass class_ObjectTemplate;

	private OntClass class_LinkTemplate;

	private OntClass class_AttributeTemplate;

	private OntClass class_AttributeTransformation;

	private OntClass class_LinkTransformation;

	private OntClass class_PreConditionChecker;

	private OntClass class_ParameterUpdater;

	private OntClass class_line;

	private ObjectProperty objectProperty_hasSystemTransformation;

	private ObjectProperty objectProperty_isSystemTransformationOf;

	private ObjectProperty objectProperty_hasSystemTemplate;

	private ObjectProperty objectProperty_isSystemTemplateOf;

	private ObjectProperty objectProperty_hasTransformations;

	private ObjectProperty objectProperty_areTransformationsOf;

	private ObjectProperty objectProperty_hasAction;

	private ObjectProperty objectProperty_isActionOf;

	private ObjectProperty objectProperty_hasObjectTemplate;

	private ObjectProperty objectProperty_isObjectTemplateOf;

	private ObjectProperty objectProperty_hasLinkTemplate;

	private ObjectProperty objectProperty_isLinkTemplateOf;

	private ObjectProperty objectProperty_hasAttributeTemplate;

	private ObjectProperty objectProperty_isAttributeTemplateOf;

	private ObjectProperty objectProperty_hasAttributeTransformation;

	private ObjectProperty objectProperty_isAttributeTransformationOf;

	private ObjectProperty objectProperty_hasLinkTransformation;

	private ObjectProperty objectProperty_isLinkTransformationOf;

	private ObjectProperty objectProperty_hasPreConditionChecker;

	private ObjectProperty objectProperty_isPreConditionCheckerOf;

	private ObjectProperty objectProperty_hasParameterUpdater;

	private ObjectProperty objectProperty_isParameterUpdaterOf;

	private ObjectProperty objectProperty_hasLine;

	private ObjectProperty objectProperty_isLineOf;

	private DatatypeProperty dataProperty_name;

	private DatatypeProperty dataProperty_objectId;

	private DatatypeProperty dataProperty_value;

	private DatatypeProperty dataProperty_objectId1;

	private DatatypeProperty dataProperty_objectId2;

	private DatatypeProperty dataProperty_oldValue;

	private DatatypeProperty dataProperty_newValue;

	private DatatypeProperty dataProperty_number;

	private DatatypeProperty dataProperty_text;

	@Override
	public void combine(SystemTransformations object) {
		// Individuals
		// << Individual: SystemTransformations
		Individual ind_systemTransformations = class_SystemTransformations
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_systemTransformations.addLabel("System Transformations 1", "en");
		ind_systemTransformations.addLabel("Трансформации системы 1", "ru");

		for (SystemTransformation systemTransformation : object) {
			// << Individual: SystemTransformation
			Individual ind_systemTransformation = class_SystemTransformation
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_systemTransformation.addProperty(dataProperty_name, systemTransformation.getName());
			// << Individual: Action
			Action action = systemTransformation.getAction();
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
			ind_action.addProperty(objectProperty_isActionOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_hasAction, ind_action);
			// >> Individual: Action
			// << Individual: SystemTemplate
			SystemTemplate systemTemplate = systemTransformation.getSystemTemplate();
			Individual ind_systemTemplate = class_SystemTemplate
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_systemTemplate.addLabel("System Template 1", "en");
			ind_systemTemplate.addLabel("Шаблон системы 1", "ru");
			int i = 0;
			for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
				// >> Individual: SystemObjectTemplate
				i++;
				Individual ind_objectTemplate = class_ObjectTemplate
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
				ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
				ind_objectTemplate.addProperty(dataProperty_objectId, objectTemplate.getId());
				int j = 0;
				for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
					// >> Individual: AttributeTemplate
					Individual ind_attributeTemplate = class_AttributeTemplate
							.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
					ind_attributeTemplate.addLabel(
							"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
							"en");
					ind_attributeTemplate.addLabel(
							"Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
							"ru");
					ind_attributeTemplate.addProperty(dataProperty_name, attributeTemplate.getName());
					Object value = attributeTemplate.getValue();
					if (value != null) {
						// TODO (2021-01-13 #31): поддержка других DataType
						ind_attributeTemplate.addProperty(dataProperty_value, value.toString());
					}
					ind_attributeTemplate.addProperty(objectProperty_isAttributeTemplateOf, ind_objectTemplate);
					ind_objectTemplate.addProperty(objectProperty_hasAttributeTemplate, ind_attributeTemplate);
					// << Individual: AttributeTemplate
				}
				ind_objectTemplate.addProperty(objectProperty_isObjectTemplateOf, ind_systemTemplate);
				ind_systemTemplate.addProperty(objectProperty_hasObjectTemplate, ind_objectTemplate);
				// << Individual: SystemObjectTemplate
			}
			i = 0;
			for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
				// >> Individual: LinkTemplate
				Individual ind_linkTemplate = class_LinkTemplate
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_linkTemplate.addLabel("Link Template ".concat(Integer.toString(i)), "en");
				ind_linkTemplate.addLabel("Шаблон связи ".concat(Integer.toString(i)), "ru");
				ind_linkTemplate.addProperty(dataProperty_name, linkTemplate.getName());
				String objectId1 = linkTemplate.getObjectId1();
				if (objectId1 != null) {
					ind_linkTemplate.addProperty(dataProperty_objectId1, objectId1);
				}
				String objectId2 = linkTemplate.getObjectId2();
				if (objectId2 != null) {
					ind_linkTemplate.addProperty(dataProperty_objectId2, objectId2);
				}
				ind_linkTemplate.addProperty(objectProperty_isLinkTemplateOf, ind_systemTemplate);
				ind_systemTemplate.addProperty(objectProperty_hasLinkTemplate, ind_linkTemplate);
				// << Individual: LinkTemplate
			}
			ind_systemTemplate.addProperty(objectProperty_isSystemTemplateOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_hasSystemTemplate, ind_systemTemplate);
			// >> Individual: SystemTemplate
			// << Individual: Transformations
			Transformation[] transformations = systemTransformation.getTransformations();
			Individual ind_transformations = class_Transformations
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			for (Transformation transformation : transformations) {
				if (transformation instanceof AttributeTransformation) {
					AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
					// >> Individual: AttributeTransformation
					Individual ind_attributeTransformation = class_AttributeTransformation
							.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
					ind_attributeTransformation.addProperty(dataProperty_objectId,
							attributeTransformation.getObjectId());
					ind_attributeTransformation.addProperty(dataProperty_name,
							attributeTransformation.getAttributeName());
					// TODO (2021-01-13 #31): поддержка других типов данных
					ind_attributeTransformation.addProperty(dataProperty_value,
							attributeTransformation.getAttributeValue().toString());
					ind_attributeTransformation.addProperty(objectProperty_isAttributeTransformationOf,
							ind_transformations);
					ind_transformations.addProperty(objectProperty_hasAttributeTransformation,
							ind_attributeTransformation);
					// << Individual: AttributeTransformation
				} else if (transformation instanceof LinkTransformation) {
					LinkTransformation linkTransformation = (LinkTransformation) transformation;
					// >> Individual: LinkTransformation
					Individual ind_linkTransformation = class_LinkTransformation
							.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
					ind_linkTransformation.addProperty(dataProperty_objectId, linkTransformation.getObjectId());
					ind_linkTransformation.addProperty(dataProperty_name, linkTransformation.getLinkName());
					String objectIdOld = linkTransformation.getLinkObjectId2Old();
					if (objectIdOld != null) {
						ind_linkTransformation.addProperty(dataProperty_oldValue, objectIdOld);
					}
					String objectIdNew = linkTransformation.getLinkObjectId2New();
					if (objectIdNew != null) {
						ind_linkTransformation.addProperty(dataProperty_newValue, objectIdNew);
					}
					ind_linkTransformation.addProperty(objectProperty_isLinkTransformationOf, ind_transformations);
					ind_transformations.addProperty(objectProperty_hasLinkTransformation, ind_linkTransformation);
					// << Individual: LinkTransformation
				} else {
					// TODO (2021-01-13 #31): remove this or update systemTransformations.xsd
				}
			}
			ind_transformations.addProperty(objectProperty_areTransformationsOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_hasTransformations, ind_transformations);
			// >> Individual: Transformations
			ind_systemTransformations.addProperty(objectProperty_hasSystemTransformation, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_isSystemTransformationOf, ind_systemTransformations);
			// >> Individual: SystemTransformation
		}
	}

	@Override
	public SystemTransformations parse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_SystemTransformations = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTransformations);
		class_SystemTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);
		class_SystemTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTemplate);
		class_Transformations = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Transformations);
		class_Action = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Action);
		class_ObjectTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_ObjectTemplate);
		class_LinkTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_LinkTemplate);
		class_AttributeTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_AttributeTemplate);
		class_AttributeTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_AttributeTransformation);
		class_LinkTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_LinkTransformation);
		class_PreConditionChecker = ontModel.getOntClass(SystemTransformationsOWLModel.URI_PreConditionChecker);
		class_ParameterUpdater = ontModel.getOntClass(SystemTransformationsOWLModel.URI_ParameterUpdater);
		class_line = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Line);

		objectProperty_hasSystemTransformation = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTransformation);
		objectProperty_isSystemTransformationOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTransformationOf);
		objectProperty_hasSystemTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTemplate);
		objectProperty_isSystemTemplateOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTemplateOf);
		objectProperty_hasTransformations = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasTransformations);
		objectProperty_areTransformationsOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_areTransformationsOf);
		objectProperty_hasAction = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasAction);
		objectProperty_isActionOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isActionOf);
		objectProperty_hasObjectTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasObjectTemplate);
		objectProperty_isObjectTemplateOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isObjectTemplateOf);
		objectProperty_hasLinkTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasLinkTemplate);
		objectProperty_isLinkTemplateOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isLinkTemplateOf);
		objectProperty_hasAttributeTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasAttributeTemplate);
		objectProperty_isAttributeTemplateOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isAttributeTemplateOf);
		objectProperty_hasAttributeTransformation = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasAttributeTransformation);
		objectProperty_isAttributeTransformationOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isAttributeTransformationOf);
		objectProperty_hasLinkTransformation = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasLinkTransformation);
		objectProperty_isLinkTransformationOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isLinkTransformationOf);
		objectProperty_hasPreConditionChecker = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasPreConditionChecker);
		objectProperty_isPreConditionCheckerOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isPreConditionCheckerOf);
		objectProperty_hasParameterUpdater = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasParameterUpdater);
		objectProperty_isParameterUpdaterOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isParameterUpdaterOf);
		objectProperty_hasLine = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasLine);
		objectProperty_isLineOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isLineOf);

		dataProperty_name = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_name);
		dataProperty_objectId = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId);
		dataProperty_value = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_value);
		dataProperty_objectId1 = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId1);
		dataProperty_objectId2 = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId2);
		dataProperty_oldValue = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_oldValue);
		dataProperty_newValue = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_newValue);
		dataProperty_number = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_number);
		dataProperty_text = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_text);
	}

	// TODO (2021-01-13 #31): включить проверку copy-paste
	// CPD-ON
}
