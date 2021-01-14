package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.vocabulary.XSD;

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

	private static final String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	private static final String URI_isLineOf = NS + "isLineOf";

	private static final String URI_hasLine = NS + "hasLine";

	private static final String URI_isParameterUpdaterOf = NS + "isParameterUpdaterOf";

	private static final String URI_hasParameterUpdater = NS + "hasParameterUpdater";

	private static final String URI_isPreConditionCheckerOf = NS + "isPreConditionCheckerOf";

	private static final String URI_hasPreConditionChecker = NS + "hasPreConditionChecker";

	private static final String URI_isLinkTransformationOf = NS + "isLinkTransformationOf";

	private static final String URI_hasLinkTransformation = NS + "hasLinkTransformation";

	private static final String URI_isAttributeTransformationOf = NS + "isAttributeTransformationOf";

	private static final String URI_hasAttributeTransformation = NS + "hasAttributeTransformation";

	private static final String URI_isAttributeTemplateOf = NS + "isAttributeTemplateOf";

	private static final String URI_hasAttributeTemplate = NS + "hasAttributeTemplate";

	private static final String URI_isLinkTemplateOf = NS + "isLinkTemplateOf";

	private static final String URI_hasLinkTemplate = NS + "hasLinkTemplate";

	private static final String URI_isObjectTemplateOf = NS + "isObjectTemplateOf";

	private static final String URI_hasObjectTemplate = NS + "hasObjectTemplate";

	private static final String URI_isActionOf = NS + "isActionOf";

	private static final String URI_hasAction = NS + "hasAction";

	private static final String URI_areTransformationsOf = NS + "areTransformationsOf";

	private static final String URI_hasTransformations = NS + "hasTransformations";

	private static final String URI_isSystemTemplateOf = NS + "isSystemTemplateOf";

	private static final String URI_hasSystemTemplate = NS + "hasSystemTemplate";

	private static final String URI_isSystemTransformationOf = NS + "isSystemTransformationOf";

	private static final String URI_hasSystemTransformation = NS + "hasSystemTransformation";

	private static final String URI_Line = NS + "Line";

	private static final String URI_ParameterUpdater = NS + "ParameterUpdater";

	private static final String URI_PreConditionChecker = NS + "PreConditionChecker";

	private static final String URI_LinkTransformation = NS + "LinkTransformation";

	private static final String URI_AttributeTransformation = NS + "AttributeTransformations";

	private static final String URI_AttributeTemplate = NS + "AttributeTemplate";

	private static final String URI_LinkTemplate = NS + "LinkTemplate";

	private static final String URI_ObjectTemplate = NS + "ObjectTemplate";

	private static final String URI_SystemTransformations = NS + "SystemTransformations";

	private static final String URI_SystemTransformation = NS + "SystemTransformation";

	private static final String URI_SystemTemplate = NS + "SystemTemplate";

	private static final String URI_Transformations = NS + "Transformations";

	private static final String URI_Action = NS + "Action";

	private static final String URI_name = NS + "name";

	private static final String URI_objectId = NS + "objectId";

	private static final String URI_value = NS + "value";

	private static final String URI_objectId1 = NS + "objectId1";

	private static final String URI_objectId2 = NS + "objectId2";

	private static final String URI_oldValue = NS + "oldValue";

	private static final String URI_newValue = NS + "newValue";

	private static final String URI_text = NS + "text";

	private static final String URI_number = NS + "number";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	private OntModel m;

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

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	private void createOntologyModel() {
		class_SystemTransformations = m.createClass(URI_SystemTransformations);
		class_SystemTransformations.addLabel("System Transformations", "en");
		class_SystemTransformations.addLabel("Трансформации системы", "ru");

		class_SystemTransformation = m.createClass(URI_SystemTransformation);
		class_SystemTransformation.addLabel("System Transformation", "en");
		class_SystemTransformation.addLabel("Трансформация системы", "ru");

		class_SystemTemplate = m.createClass(URI_SystemTemplate);
		class_SystemTemplate.addLabel("System Template", "en");
		class_SystemTemplate.addLabel("Шаблон системы", "ru");

		class_Transformations = m.createClass(URI_Transformations);
		class_Transformations.addLabel("Transformations", "en");
		class_Transformations.addLabel("Трансформации", "ru");

		class_Action = m.createClass(URI_Action);
		class_Action.addLabel("Action", "en");
		class_Action.addLabel("Действие", "ru");

		class_ObjectTemplate = m.createClass(URI_ObjectTemplate);
		class_ObjectTemplate.addLabel("Object Template", "en");
		class_ObjectTemplate.addLabel("Шаблон объекта", "ru");

		class_LinkTemplate = m.createClass(URI_LinkTemplate);
		class_LinkTemplate.addLabel("Link Template", "en");
		class_LinkTemplate.addLabel("Шаблон связи", "ru");

		class_AttributeTemplate = m.createClass(URI_AttributeTemplate);
		class_AttributeTemplate.addLabel("Attribute Template", "en");
		class_AttributeTemplate.addLabel("Шаблон атрибута", "ru");

		class_AttributeTransformation = m.createClass(URI_AttributeTransformation);
		class_AttributeTransformation.addLabel("Attribute Transformation", "en");
		class_AttributeTransformation.addLabel("Трансформация атрибута", "ru");

		class_LinkTransformation = m.createClass(URI_LinkTransformation);
		class_LinkTransformation.addLabel("Link Transformation", "en");
		class_LinkTransformation.addLabel("Трансформация связи", "ru");

		class_PreConditionChecker = m.createClass(URI_PreConditionChecker);
		class_PreConditionChecker.addLabel("PreCondition Checker", "en");
		class_PreConditionChecker.addLabel("Проверка предусловий", "ru");

		class_ParameterUpdater = m.createClass(URI_ParameterUpdater);
		class_ParameterUpdater.addLabel("Parameter Updater", "en");
		class_ParameterUpdater.addLabel("Обновление параметров", "ru");

		class_line = m.createClass(URI_Line);
		class_line.addLabel("Line", "en");
		class_line.addLabel("Линия", "ru");

		objectProperty_hasSystemTransformation = m.createObjectProperty(URI_hasSystemTransformation);
		objectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		objectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		objectProperty_hasSystemTransformation.addDomain(class_SystemTransformations);
		objectProperty_hasSystemTransformation.addRange(class_SystemTransformation);

		objectProperty_isSystemTransformationOf = m.createObjectProperty(URI_isSystemTransformationOf);
		objectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		objectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		objectProperty_isSystemTransformationOf.addDomain(class_SystemTransformation);
		objectProperty_isSystemTransformationOf.addRange(class_SystemTransformations);

		makeInverse(objectProperty_hasSystemTransformation, objectProperty_isSystemTransformationOf);

		objectProperty_hasSystemTemplate = m.createObjectProperty(URI_hasSystemTemplate);
		objectProperty_hasSystemTemplate.addLabel("has system template", "en");
		objectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		objectProperty_hasSystemTemplate.addDomain(class_SystemTransformation);
		objectProperty_hasSystemTemplate.addRange(class_SystemTemplate);

		objectProperty_isSystemTemplateOf = m.createObjectProperty(URI_isSystemTemplateOf);
		objectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		objectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		objectProperty_isSystemTemplateOf.addDomain(class_SystemTemplate);
		objectProperty_isSystemTemplateOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasSystemTemplate, objectProperty_isSystemTemplateOf);

		objectProperty_hasTransformations = m.createObjectProperty(URI_hasTransformations);
		objectProperty_hasTransformations.addLabel("has transformations", "en");
		objectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		objectProperty_hasTransformations.addDomain(class_SystemTransformation);
		objectProperty_hasTransformations.addRange(class_Transformations);

		objectProperty_areTransformationsOf = m.createObjectProperty(URI_areTransformationsOf);
		objectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		objectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		objectProperty_areTransformationsOf.addDomain(class_Transformations);
		objectProperty_areTransformationsOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasTransformations, objectProperty_areTransformationsOf);

		objectProperty_hasAction = m.createObjectProperty(URI_hasAction);
		objectProperty_hasAction.addLabel("has action", "en");
		objectProperty_hasAction.addLabel("имеет действие", "ru");
		objectProperty_hasAction.addDomain(class_SystemTransformation);
		objectProperty_hasAction.addRange(class_Action);

		objectProperty_isActionOf = m.createObjectProperty(URI_isActionOf);
		objectProperty_isActionOf.addLabel("is action of", "en");
		objectProperty_isActionOf.addLabel("является действием для", "ru");
		objectProperty_isActionOf.addDomain(class_Action);
		objectProperty_isActionOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasAction, objectProperty_isActionOf);

		objectProperty_hasObjectTemplate = m.createObjectProperty(URI_hasObjectTemplate);
		objectProperty_hasObjectTemplate.addLabel("has objectTemplate", "en");
		objectProperty_hasObjectTemplate.addLabel("имеет шаблон объекта", "ru");
		objectProperty_hasObjectTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasObjectTemplate.addRange(class_ObjectTemplate);

		objectProperty_isObjectTemplateOf = m.createObjectProperty(URI_isObjectTemplateOf);
		objectProperty_isObjectTemplateOf.addLabel("is object template of", "en");
		objectProperty_isObjectTemplateOf.addLabel("является шаблоном объекта для", "ru");
		objectProperty_isObjectTemplateOf.addDomain(class_ObjectTemplate);
		objectProperty_isObjectTemplateOf.addRange(class_SystemTemplate);

		makeInverse(objectProperty_hasObjectTemplate, objectProperty_isObjectTemplateOf);

		objectProperty_hasLinkTemplate = m.createObjectProperty(URI_hasLinkTemplate);
		objectProperty_hasLinkTemplate.addLabel("has link template", "en");
		objectProperty_hasLinkTemplate.addLabel("имеет шаблон связи", "ru");
		objectProperty_hasLinkTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasLinkTemplate.addRange(class_LinkTemplate);

		objectProperty_isLinkTemplateOf = m.createObjectProperty(URI_isLinkTemplateOf);
		objectProperty_isLinkTemplateOf.addLabel("is link template of", "en");
		objectProperty_isLinkTemplateOf.addLabel("является шаблоном связи для", "ru");
		objectProperty_isLinkTemplateOf.addDomain(class_LinkTemplate);
		objectProperty_isLinkTemplateOf.addRange(class_SystemTemplate);

		makeInverse(objectProperty_hasLinkTemplate, objectProperty_isLinkTemplateOf);

		objectProperty_hasAttributeTemplate = m.createObjectProperty(URI_hasAttributeTemplate);
		objectProperty_hasAttributeTemplate.addLabel("has attribute template", "en");
		objectProperty_hasAttributeTemplate.addLabel("имеет шаблон атрибута", "ru");
		objectProperty_hasAttributeTemplate.addDomain(class_ObjectTemplate);
		objectProperty_hasAttributeTemplate.addRange(class_AttributeTemplate);

		objectProperty_isAttributeTemplateOf = m.createObjectProperty(URI_isAttributeTemplateOf);
		objectProperty_isAttributeTemplateOf.addLabel("is attribute template of", "en");
		objectProperty_isAttributeTemplateOf.addLabel("является шаблоном атрибута для", "ru");
		objectProperty_isAttributeTemplateOf.addDomain(class_AttributeTemplate);
		objectProperty_isAttributeTemplateOf.addRange(class_ObjectTemplate);

		makeInverse(objectProperty_hasAttributeTemplate, objectProperty_isAttributeTemplateOf);

		objectProperty_hasAttributeTransformation = m.createObjectProperty(URI_hasAttributeTransformation);
		objectProperty_hasAttributeTransformation.addLabel("has attribute transformation", "en");
		objectProperty_hasAttributeTransformation.addLabel("имеет трансформацию атрибута", "ru");
		objectProperty_hasAttributeTransformation.addDomain(class_Transformations);
		objectProperty_hasAttributeTransformation.addRange(class_AttributeTransformation);

		objectProperty_isAttributeTransformationOf = m.createObjectProperty(URI_isAttributeTransformationOf);
		objectProperty_isAttributeTransformationOf.addLabel("is attribute transformation of", "en");
		objectProperty_isAttributeTransformationOf.addLabel("является трансформацией атрибута для", "ru");
		objectProperty_isAttributeTransformationOf.addDomain(class_AttributeTransformation);
		objectProperty_isAttributeTransformationOf.addRange(class_Transformations);

		makeInverse(objectProperty_hasAttributeTransformation, objectProperty_isAttributeTransformationOf);

		objectProperty_hasLinkTransformation = m.createObjectProperty(URI_hasLinkTransformation);
		objectProperty_hasLinkTransformation.addLabel("has link transformation", "en");
		objectProperty_hasLinkTransformation.addLabel("имеет трансформацию связи", "ru");
		objectProperty_hasLinkTransformation.addDomain(class_Transformations);
		objectProperty_hasLinkTransformation.addRange(class_LinkTransformation);

		objectProperty_isLinkTransformationOf = m.createObjectProperty(URI_isLinkTransformationOf);
		objectProperty_isLinkTransformationOf.addLabel("is link transformation of", "en");
		objectProperty_isLinkTransformationOf.addLabel("является трасформацией связи для", "ru");
		objectProperty_isLinkTransformationOf.addDomain(class_LinkTransformation);
		objectProperty_isLinkTransformationOf.addRange(class_Transformations);

		makeInverse(objectProperty_hasLinkTransformation, objectProperty_isLinkTransformationOf);

		objectProperty_hasPreConditionChecker = m.createObjectProperty(URI_hasPreConditionChecker);
		objectProperty_hasPreConditionChecker.addLabel("has precondition checker", "en");
		objectProperty_hasPreConditionChecker.addLabel("имеет проверку условий", "ru");
		objectProperty_hasPreConditionChecker.addDomain(class_Action);
		objectProperty_hasPreConditionChecker.addRange(class_PreConditionChecker);

		objectProperty_isPreConditionCheckerOf = m.createObjectProperty(URI_isPreConditionCheckerOf);
		objectProperty_isPreConditionCheckerOf.addLabel("is precondition checker of", "en");
		objectProperty_isPreConditionCheckerOf.addLabel("является проверкой условий для", "ru");
		objectProperty_isPreConditionCheckerOf.addDomain(class_PreConditionChecker);
		objectProperty_isPreConditionCheckerOf.addRange(class_Action);

		makeInverse(objectProperty_hasPreConditionChecker, objectProperty_isPreConditionCheckerOf);

		objectProperty_hasParameterUpdater = m.createObjectProperty(URI_hasParameterUpdater);
		objectProperty_hasParameterUpdater.addLabel("has parameter updater", "en");
		objectProperty_hasParameterUpdater.addLabel("имеет функцию обновления", "ru");
		objectProperty_hasParameterUpdater.addDomain(class_Action);
		objectProperty_hasParameterUpdater.addRange(class_ParameterUpdater);

		objectProperty_isParameterUpdaterOf = m.createObjectProperty(URI_isParameterUpdaterOf);
		objectProperty_isParameterUpdaterOf.addLabel("is parameter updater of", "en");
		objectProperty_isParameterUpdaterOf.addLabel("является функцией обновления для", "ru");
		objectProperty_isParameterUpdaterOf.addDomain(class_ParameterUpdater);
		objectProperty_isParameterUpdaterOf.addRange(class_Action);

		makeInverse(objectProperty_hasParameterUpdater, objectProperty_isParameterUpdaterOf);

		objectProperty_hasLine = m.createObjectProperty(URI_hasLine);
		objectProperty_hasLine.addLabel("has line", "en");
		objectProperty_hasLine.addLabel("имеет линию", "ru");
		objectProperty_hasLine.addDomain(class_PreConditionChecker);
		objectProperty_hasLine.addDomain(class_ParameterUpdater);
		objectProperty_hasLine.addRange(class_line);

		objectProperty_isLineOf = m.createObjectProperty(URI_isLineOf);
		objectProperty_isLineOf.addLabel("is line of", "en");
		objectProperty_isLineOf.addLabel("является линией для", "ru");
		objectProperty_isLineOf.addDomain(class_line);
		objectProperty_isLineOf.addRange(class_PreConditionChecker);
		objectProperty_isLineOf.addRange(class_ParameterUpdater);

		makeInverse(objectProperty_hasLine, objectProperty_isLineOf);

		dataProperty_name = m.createDatatypeProperty(URI_name);
		dataProperty_name.addLabel("name", "en");
		dataProperty_name.addLabel("название", "ru");
		dataProperty_name.addDomain(class_Action);
		dataProperty_name.addDomain(class_AttributeTemplate);
		dataProperty_name.addDomain(class_AttributeTransformation);
		dataProperty_name.addDomain(class_LinkTemplate);
		dataProperty_name.addDomain(class_LinkTransformation);
		dataProperty_name.addDomain(class_SystemTransformation);
		dataProperty_name.addRange(XSD.xstring);

		dataProperty_objectId = m.createDatatypeProperty(URI_objectId);
		dataProperty_objectId.addLabel("objectId", "en");
		dataProperty_objectId.addLabel("идентификатор объекта", "ru");
		dataProperty_objectId.addDomain(class_AttributeTransformation);
		dataProperty_objectId.addDomain(class_LinkTransformation);
		dataProperty_objectId.addDomain(class_ObjectTemplate);
		dataProperty_objectId.addRange(XSD.xstring);

		dataProperty_value = m.createDatatypeProperty(URI_value);
		dataProperty_value.addLabel("value", "en");
		dataProperty_value.addLabel("значение", "ru");
		dataProperty_value.addDomain(class_AttributeTemplate);
		dataProperty_value.addDomain(class_AttributeTransformation);
		dataProperty_value.addRange(XSD.xstring);
		dataProperty_value.addRange(XSD.xboolean);
		dataProperty_value.addRange(XSD.xint);

		dataProperty_objectId1 = m.createDatatypeProperty(URI_objectId1);
		dataProperty_objectId1.addLabel("objectId1", "en");
		dataProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		dataProperty_objectId1.addDomain(class_LinkTemplate);
		dataProperty_objectId1.addRange(XSD.xstring);

		dataProperty_objectId2 = m.createDatatypeProperty(URI_objectId2);
		dataProperty_objectId2.addLabel("objectId2", "en");
		dataProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		dataProperty_objectId2.addDomain(class_LinkTemplate);
		dataProperty_objectId2.addRange(XSD.xstring);

		dataProperty_oldValue = m.createDatatypeProperty(URI_oldValue);
		dataProperty_oldValue.addLabel("old value", "en");
		dataProperty_oldValue.addLabel("старое значение", "ru");
		dataProperty_oldValue.addDomain(class_LinkTransformation);
		dataProperty_oldValue.addRange(XSD.xstring);

		dataProperty_newValue = m.createDatatypeProperty(URI_newValue);
		dataProperty_newValue.addLabel("new value", "en");
		dataProperty_newValue.addLabel("новое значение", "ru");
		dataProperty_newValue.addDomain(class_LinkTransformation);
		dataProperty_newValue.addRange(XSD.xstring);

		dataProperty_number = m.createDatatypeProperty(URI_number);
		dataProperty_number.addLabel("number", "en");
		dataProperty_number.addLabel("номер", "ru");
		dataProperty_number.addDomain(class_line);
		dataProperty_number.addRange(XSD.integer);

		dataProperty_text = m.createDatatypeProperty(URI_text);
		dataProperty_text.addLabel("text", "en");
		dataProperty_text.addLabel("текст", "ru");
		dataProperty_text.addDomain(class_line);
		dataProperty_text.addRange(XSD.xstring);
	}

	@Override
	public void combine(SystemTransformations object) {
		// Ontology
		createOntologyModel();

		// Individuals
		// << Individual: SystemTransformations
		Individual ind_systemTransformations = class_SystemTransformations
				.createIndividual(NS + UUID.randomUUID().toString());
		ind_systemTransformations.addLabel("System Transformations 1", "en");
		ind_systemTransformations.addLabel("Трансформации системы 1", "ru");

		for (SystemTransformation systemTransformation : object) {
			// << Individual: SystemTransformation
			Individual ind_systemTransformation = class_SystemTransformation
					.createIndividual(NS + UUID.randomUUID().toString());
			ind_systemTransformation.addProperty(dataProperty_name, systemTransformation.getName());
			// << Individual: Action
			Action action = systemTransformation.getAction();
			Individual ind_action = class_Action.createIndividual(NS + UUID.randomUUID().toString());
			ind_action.addProperty(dataProperty_name, action.getName());
			for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
				// << Individual: PreConditionChecker
				Individual ind_preConditionChecker = class_PreConditionChecker
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
				String lines[] = luaPreConditionChecker.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = class_line.createIndividual(NS + UUID.randomUUID().toString());
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
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
				String lines[] = luaParameterUpdater.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = class_line.createIndividual(NS + UUID.randomUUID().toString());
					ind_line.addProperty(dataProperty_number, Integer.toString(i + 1));
					ind_line.addProperty(dataProperty_text, lines[i]);
					ind_line.addProperty(objectProperty_isLineOf, ind_parameterUpdater);
					ind_parameterUpdater.addProperty(objectProperty_hasLine, ind_line);
					// >> Individual: Line
				}
				ind_action.addProperty(objectProperty_hasPreConditionChecker, ind_parameterUpdater);
				ind_parameterUpdater.addProperty(objectProperty_isPreConditionCheckerOf, ind_action);
				// >> Individual: ParameterUpdater
			}
			ind_action.addProperty(objectProperty_isActionOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_hasAction, ind_action);
			// >> Individual: Action
			// << Individual: SystemTemplate
			SystemTemplate systemTemplate = systemTransformation.getSystemTemplate();
			Individual ind_systemTemplate = class_SystemTemplate.createIndividual(NS + UUID.randomUUID().toString());
			ind_systemTemplate.addLabel("System Template 1", "en");
			ind_systemTemplate.addLabel("Шаблон системы 1", "ru");
			int i = 0;
			for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
				// >> Individual: SystemObjectTemplate
				i++;
				Individual ind_objectTemplate = class_ObjectTemplate
						.createIndividual(NS + UUID.randomUUID().toString());
				ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
				ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
				ind_objectTemplate.addProperty(dataProperty_objectId, objectTemplate.getId());
				int j = 0;
				for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
					// >> Individual: AttributeTemplate
					Individual ind_attributeTemplate = class_AttributeTemplate
							.createIndividual(NS + UUID.randomUUID().toString());
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
				Individual ind_linkTemplate = class_LinkTemplate.createIndividual(NS + UUID.randomUUID().toString());
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
			Individual ind_transformations = class_Transformations.createIndividual(NS + UUID.randomUUID().toString());
			for (Transformation transformation : transformations) {
				if (transformation instanceof AttributeTransformation) {
					AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
					// >> Individual: AttributeTransformation
					Individual ind_attributeTransformation = class_AttributeTransformation
							.createIndividual(NS + UUID.randomUUID().toString());
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
							.createIndividual(NS + UUID.randomUUID().toString());
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
		// TODO Auto-generated method stub
		this.m = ontModel;
	}

	// TODO (2021-01-13 #31): включить проверку copy-paste
	// CPD-ON
}
