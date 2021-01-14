package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
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

	private static final String URI_isAttributeTemplateOf = "isAttributeTemplateOf";

	private static final String URI_hasAttributeTemplate = "hasAttributeTemplate";

	private static final String URI_isLinkTemplateOf = "isLinkTemplateOf";

	private static final String URI_hasLinkTemplate = "hasLinkTemplate";

	private static final String URI_isObjectTemplateOf = "isObjectTemplateOf";

	private static final String URI_hasObjectTemplate = "hasObjectTemplate";

	private static final String URI_isActionOf = "isActionOf";

	private static final String URI_hasAction = "hasAction";

	private static final String URI_areTransformationsOf = "areTransformationsOf";

	private static final String URI_hasTransformations = "hasTransformations";

	private static final String URI_isSystemTemplateOf = "isSystemTemplateOf";

	private static final String URI_hasSystemTemplate = "hasSystemTemplate";

	private static final String URI_isSystemTransformationOf = "isSystemTransformationOf";

	private static final String URI_hasSystemTransformation = "hasSystemTransformation";

	private static final String URI_Line = "Line";

	private static final String URI_ParameterUpdater = "ParameterUpdater";

	private static final String URI_PreConditionChecker = "PreConditionChecker";

	private static final String URI_LinkTransformation = "LinkTransformation";

	private static final String URI_AttributeTransformation = "AttributeTransformations";

	private static final String URI_AttributeTemplate = "AttributeTemplate";

	private static final String URI_LinkTemplate = "LinkTemplate";

	private static final String URI_ObjectTemplate = "ObjectTemplate";

	private static final String URI_SystemTransformations = "SystemTransformations";

	private static final String URI_SystemTransformation = "SystemTransformation";

	private static final String URI_SystemTemplate = "SystemTemplate";

	private static final String URI_Transformations = "Transformations";

	private static final String URI_Action = "Action";

	private static final String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	private OntModel m;

	private OntClass class_SystemTransformations;

	private OntClass class_SystemTransformation;

	private OntClass class_SystemTemplate;

	private OntClass class_Transformations;

	private OntClass class_Action;

	private OntClass class_objectTemplate;

	private OntClass class_linkTemplate;

	private OntClass class_attributeTemplate;

	private OntClass class_attributeTransformation;

	private OntClass class_linkTransformation;

	private OntClass class_preConditionChecker;

	private OntClass class_parameterUpdater;

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

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	@Override
	public OntModel combine(SystemTransformations object) {
		// Ontology
		m = ModelFactory.createOntologyModel();

		class_SystemTransformations = m.createClass(NS + URI_SystemTransformations);
		class_SystemTransformations.addLabel("System Transformations", "en");
		class_SystemTransformations.addLabel("Трансформации системы", "ru");

		class_SystemTransformation = m.createClass(NS + URI_SystemTransformation);
		class_SystemTransformation.addLabel("System Transformation", "en");
		class_SystemTransformation.addLabel("Трансформация системы", "ru");

		class_SystemTemplate = m.createClass(NS + URI_SystemTemplate);
		class_SystemTemplate.addLabel("System Template", "en");
		class_SystemTemplate.addLabel("Шаблон системы", "ru");

		class_Transformations = m.createClass(NS + URI_Transformations);
		class_Transformations.addLabel("Transformations", "en");
		class_Transformations.addLabel("Трансформации", "ru");

		class_Action = m.createClass(NS + URI_Action);
		class_Action.addLabel("Action", "en");
		class_Action.addLabel("Действие", "ru");

		class_objectTemplate = m.createClass(NS + URI_ObjectTemplate);
		class_objectTemplate.addLabel("Object Template", "en");
		class_objectTemplate.addLabel("Шаблон объекта", "ru");

		class_linkTemplate = m.createClass(NS + URI_LinkTemplate);
		class_linkTemplate.addLabel("Link Template", "en");
		class_linkTemplate.addLabel("Шаблон связи", "ru");

		class_attributeTemplate = m.createClass(NS + URI_AttributeTemplate);
		class_attributeTemplate.addLabel("Attribute Template", "en");
		class_attributeTemplate.addLabel("Шаблон атрибута", "ru");

		class_attributeTransformation = m.createClass(NS + URI_AttributeTransformation);
		class_attributeTransformation.addLabel("Attribute Transformation", "en");
		class_attributeTransformation.addLabel("Трансформация атрибута", "ru");

		class_linkTransformation = m.createClass(NS + URI_LinkTransformation);
		class_linkTransformation.addLabel("Link Transformation", "en");
		class_linkTransformation.addLabel("Трансформация связи", "ru");

		class_preConditionChecker = m.createClass(NS + URI_PreConditionChecker);
		class_preConditionChecker.addLabel("PreCondition Checker", "en");
		class_preConditionChecker.addLabel("Проверка предусловий", "ru");

		class_parameterUpdater = m.createClass(NS + URI_ParameterUpdater);
		class_parameterUpdater.addLabel("Parameter Updater", "en");
		class_parameterUpdater.addLabel("Обновление параметров", "ru");

		class_line = m.createClass(NS + URI_Line);
		class_line.addLabel("Line", "en");
		class_line.addLabel("Линия", "ru");

		objectProperty_hasSystemTransformation = m.createObjectProperty(NS + URI_hasSystemTransformation);
		objectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		objectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		objectProperty_hasSystemTransformation.addDomain(class_SystemTransformations);
		objectProperty_hasSystemTransformation.addRange(class_SystemTransformation);

		objectProperty_isSystemTransformationOf = m.createObjectProperty(NS + URI_isSystemTransformationOf);
		objectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		objectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		objectProperty_isSystemTransformationOf.addDomain(class_SystemTransformation);
		objectProperty_isSystemTransformationOf.addRange(class_SystemTransformations);

		makeInverse(objectProperty_hasSystemTransformation, objectProperty_isSystemTransformationOf);

		objectProperty_hasSystemTemplate = m.createObjectProperty(NS + URI_hasSystemTemplate);
		objectProperty_hasSystemTemplate.addLabel("has system template", "en");
		objectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		objectProperty_hasSystemTemplate.addDomain(class_SystemTransformation);
		objectProperty_hasSystemTemplate.addRange(class_SystemTemplate);

		objectProperty_isSystemTemplateOf = m.createObjectProperty(NS + URI_isSystemTemplateOf);
		objectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		objectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		objectProperty_isSystemTemplateOf.addDomain(class_SystemTemplate);
		objectProperty_isSystemTemplateOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasSystemTemplate, objectProperty_isSystemTemplateOf);

		objectProperty_hasTransformations = m.createObjectProperty(NS + URI_hasTransformations);
		objectProperty_hasTransformations.addLabel("has transformations", "en");
		objectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		objectProperty_hasTransformations.addDomain(class_SystemTransformation);
		objectProperty_hasTransformations.addRange(class_Transformations);

		objectProperty_areTransformationsOf = m.createObjectProperty(NS + URI_areTransformationsOf);
		objectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		objectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		objectProperty_areTransformationsOf.addDomain(class_Transformations);
		objectProperty_areTransformationsOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasTransformations, objectProperty_areTransformationsOf);

		objectProperty_hasAction = m.createObjectProperty(NS + URI_hasAction);
		objectProperty_hasAction.addLabel("has action", "en");
		objectProperty_hasAction.addLabel("имеет действие", "ru");
		objectProperty_hasAction.addDomain(class_SystemTransformation);
		objectProperty_hasAction.addRange(class_Action);

		objectProperty_isActionOf = m.createObjectProperty(NS + URI_isActionOf);
		objectProperty_isActionOf.addLabel("is action of", "en");
		objectProperty_isActionOf.addLabel("является действием для", "ru");
		objectProperty_isActionOf.addDomain(class_Action);
		objectProperty_isActionOf.addRange(class_SystemTransformation);

		makeInverse(objectProperty_hasAction, objectProperty_isActionOf);

		objectProperty_hasObjectTemplate = m.createObjectProperty(NS + URI_hasObjectTemplate);
		objectProperty_hasObjectTemplate.addLabel("has objectTemplate", "en");
		objectProperty_hasObjectTemplate.addLabel("имеет шаблон объекта", "ru");
		objectProperty_hasObjectTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasObjectTemplate.addRange(class_objectTemplate);

		objectProperty_isObjectTemplateOf = m.createObjectProperty(NS + URI_isObjectTemplateOf);
		objectProperty_isObjectTemplateOf.addLabel("is object template of", "en");
		objectProperty_isObjectTemplateOf.addLabel("является шаблоном объекта для", "ru");
		objectProperty_isObjectTemplateOf.addDomain(class_objectTemplate);
		objectProperty_isObjectTemplateOf.addRange(class_SystemTemplate);

		makeInverse(objectProperty_hasObjectTemplate, objectProperty_isObjectTemplateOf);

		objectProperty_hasLinkTemplate = m.createObjectProperty(NS + URI_hasLinkTemplate);
		objectProperty_hasLinkTemplate.addLabel("has link template", "en");
		objectProperty_hasLinkTemplate.addLabel("имеет шаблон связи", "ru");
		objectProperty_hasLinkTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasLinkTemplate.addRange(class_linkTemplate);

		objectProperty_isLinkTemplateOf = m.createObjectProperty(NS + URI_isLinkTemplateOf);
		objectProperty_isLinkTemplateOf.addLabel("is link template of", "en");
		objectProperty_isLinkTemplateOf.addLabel("является шаблоном связи для", "ru");
		objectProperty_isLinkTemplateOf.addDomain(class_linkTemplate);
		objectProperty_isLinkTemplateOf.addRange(class_SystemTemplate);

		makeInverse(objectProperty_hasLinkTemplate, objectProperty_isLinkTemplateOf);

		objectProperty_hasAttributeTemplate = m.createObjectProperty(NS + URI_hasAttributeTemplate);
		objectProperty_hasAttributeTemplate.addLabel("has attribute template", "en");
		objectProperty_hasAttributeTemplate.addLabel("имеет шаблон атрибута", "ru");
		objectProperty_hasAttributeTemplate.addDomain(class_objectTemplate);
		objectProperty_hasAttributeTemplate.addRange(class_attributeTemplate);

		objectProperty_isAttributeTemplateOf = m.createObjectProperty(NS + URI_isAttributeTemplateOf);
		objectProperty_isAttributeTemplateOf.addLabel("is attribute template of", "en");
		objectProperty_isAttributeTemplateOf.addLabel("является шаблоном атрибута для", "ru");
		objectProperty_isAttributeTemplateOf.addDomain(class_attributeTemplate);
		objectProperty_isAttributeTemplateOf.addRange(class_objectTemplate);

		makeInverse(objectProperty_hasAttributeTemplate, objectProperty_isAttributeTemplateOf);

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addDomain(class_SystemTransformation);
		ontDatatypeProperty_name.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId = m.createDatatypeProperty(NS + "objectId");
		ontDatatypeProperty_objectId.addLabel("objectId", "en");
		ontDatatypeProperty_objectId.addLabel("идентификатор объекта", "ru");
		ontDatatypeProperty_objectId.addDomain(class_objectTemplate);
		ontDatatypeProperty_objectId.addRange(XSD.xstring);

		ontDatatypeProperty_name.addDomain(class_attributeTemplate);

		DatatypeProperty ontDatatypeProperty_value = m.createDatatypeProperty(NS + "value");
		ontDatatypeProperty_value.addLabel("value", "en");
		ontDatatypeProperty_value.addLabel("значение", "ru");
		ontDatatypeProperty_value.addDomain(class_attributeTemplate);
		ontDatatypeProperty_value.addRange(XSD.xstring);
		ontDatatypeProperty_value.addRange(XSD.xboolean);
		ontDatatypeProperty_value.addRange(XSD.xint);

		ontDatatypeProperty_name.addDomain(class_linkTemplate);

		DatatypeProperty ontDatatypeProperty_objectId1 = m.createDatatypeProperty(NS + "objectId1");
		ontDatatypeProperty_objectId1.addLabel("objectId1", "en");
		ontDatatypeProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		ontDatatypeProperty_objectId1.addDomain(class_linkTemplate);
		ontDatatypeProperty_objectId1.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId2 = m.createDatatypeProperty(NS + "objectId2");
		ontDatatypeProperty_objectId2.addLabel("objectId2", "en");
		ontDatatypeProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		ontDatatypeProperty_objectId2.addDomain(class_linkTemplate);
		ontDatatypeProperty_objectId2.addRange(XSD.xstring);

		ObjectProperty ontObjectProperty_hasAttributeTransformation = m
				.createObjectProperty(NS + "hasAttributeTransformation");
		ontObjectProperty_hasAttributeTransformation.addLabel("has attribute transformation", "en");
		ontObjectProperty_hasAttributeTransformation.addLabel("имеет трансформацию атрибута", "ru");
		ontObjectProperty_hasAttributeTransformation.addDomain(class_Transformations);
		ontObjectProperty_hasAttributeTransformation.addRange(class_attributeTransformation);

		ObjectProperty ontObjectProperty_isAttributeTransformationOf = m
				.createObjectProperty(NS + "isAttributeTransformationOf");
		ontObjectProperty_isAttributeTransformationOf.addLabel("is attribute transformation of", "en");
		ontObjectProperty_isAttributeTransformationOf.addLabel("является трансформацией атрибута для", "ru");
		ontObjectProperty_isAttributeTransformationOf.addDomain(class_attributeTransformation);
		ontObjectProperty_isAttributeTransformationOf.addRange(class_Transformations);

		ontObjectProperty_hasAttributeTransformation.addInverseOf(ontObjectProperty_isAttributeTransformationOf);
		ontObjectProperty_isAttributeTransformationOf.addInverseOf(ontObjectProperty_hasAttributeTransformation);

		ObjectProperty ontObjectProperty_hasLinkTransformation = m.createObjectProperty(NS + "hasLinkTransformation");
		ontObjectProperty_hasLinkTransformation.addLabel("has link transformation", "en");
		ontObjectProperty_hasLinkTransformation.addLabel("имеет трансформацию связи", "ru");
		ontObjectProperty_hasLinkTransformation.addDomain(class_Transformations);
		ontObjectProperty_hasLinkTransformation.addRange(class_linkTransformation);

		ObjectProperty ontObjectProperty_isLinkTransformationOf = m.createObjectProperty(NS + "isLinkTransformationOf");
		ontObjectProperty_isLinkTransformationOf.addLabel("is link transformation of", "en");
		ontObjectProperty_isLinkTransformationOf.addLabel("является трасформацией связи для", "ru");
		ontObjectProperty_isLinkTransformationOf.addDomain(class_linkTransformation);
		ontObjectProperty_isLinkTransformationOf.addRange(class_Transformations);

		ontObjectProperty_hasLinkTransformation.addInverseOf(ontObjectProperty_isLinkTransformationOf);
		ontObjectProperty_isLinkTransformationOf.addInverseOf(ontObjectProperty_hasLinkTransformation);

		ontDatatypeProperty_objectId.addDomain(class_linkTransformation);
		ontDatatypeProperty_name.addDomain(class_linkTransformation);

		DatatypeProperty ontDatatypeProperty_oldValue = m.createDatatypeProperty(NS + "oldValue");
		ontDatatypeProperty_oldValue.addLabel("old value", "en");
		ontDatatypeProperty_oldValue.addLabel("старое значение", "ru");
		ontDatatypeProperty_oldValue.addDomain(class_linkTransformation);
		ontDatatypeProperty_oldValue.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_newValue = m.createDatatypeProperty(NS + "newValue");
		ontDatatypeProperty_newValue.addLabel("new value", "en");
		ontDatatypeProperty_newValue.addLabel("новое значение", "ru");
		ontDatatypeProperty_newValue.addDomain(class_linkTransformation);
		ontDatatypeProperty_newValue.addRange(XSD.xstring);

		ontDatatypeProperty_objectId.addDomain(class_attributeTransformation);
		ontDatatypeProperty_name.addDomain(class_attributeTransformation);
		ontDatatypeProperty_value.addDomain(class_attributeTransformation);

		// action
		ontDatatypeProperty_name.addDomain(class_Action);

		ObjectProperty ontObjectProperty_hasPreConditionChecker = m.createObjectProperty(NS + "hasPreConditionChecker");
		ontObjectProperty_hasPreConditionChecker.addLabel("hasPreconditionChecker", "en");
		ontObjectProperty_hasPreConditionChecker.addLabel("имеет проверку условий", "ru");
		ontObjectProperty_hasPreConditionChecker.addDomain(class_Action);
		ontObjectProperty_hasPreConditionChecker.addRange(class_preConditionChecker);

		ObjectProperty ontObjectProperty_isPreConditionCheckerOf = m.createObjectProperty(NS + "isPreConditionChecker");
		ontObjectProperty_isPreConditionCheckerOf.addLabel("isPreConditionCheckerOf", "en");
		ontObjectProperty_isPreConditionCheckerOf.addLabel("является проверкой условий для", "ru");
		ontObjectProperty_isPreConditionCheckerOf.addDomain(class_preConditionChecker);
		ontObjectProperty_isPreConditionCheckerOf.addRange(class_Action);

		ontObjectProperty_hasPreConditionChecker.addInverseOf(ontObjectProperty_isPreConditionCheckerOf);
		ontObjectProperty_isPreConditionCheckerOf.addInverseOf(ontObjectProperty_hasPreConditionChecker);

		ObjectProperty ontObjectProperty_hasParameterUpdater = m.createObjectProperty(NS + "hasParameterUpdater");
		ontObjectProperty_hasParameterUpdater.addLabel("hasParameterUpdater", "en");
		ontObjectProperty_hasParameterUpdater.addLabel("имеет функцию обновления", "ru");
		ontObjectProperty_hasParameterUpdater.addDomain(class_Action);
		ontObjectProperty_hasParameterUpdater.addRange(class_parameterUpdater);

		ObjectProperty ontObjectProperty_isParameterUpdaterOf = m.createObjectProperty(NS + "isParameterUpdater");
		ontObjectProperty_isParameterUpdaterOf.addLabel("isParameterUpdaterOf", "en");
		ontObjectProperty_isParameterUpdaterOf.addLabel("является функцией обновления для", "ru");
		ontObjectProperty_isParameterUpdaterOf.addDomain(class_parameterUpdater);
		ontObjectProperty_isParameterUpdaterOf.addRange(class_Action);

		ontObjectProperty_hasParameterUpdater.addInverseOf(ontObjectProperty_isParameterUpdaterOf);
		ontObjectProperty_isParameterUpdaterOf.addInverseOf(ontObjectProperty_hasParameterUpdater);

		// TODO (2021-01-12 #31): добавить общий класс function
		// preConditionChecker
		ObjectProperty ontObjectProperty_hasLine = m.createObjectProperty(NS + "hasLine");
		ontObjectProperty_hasLine.addLabel("hasLine", "en");
		ontObjectProperty_hasLine.addLabel("имеет линию", "ru");
		ontObjectProperty_hasLine.addDomain(class_preConditionChecker);
		ontObjectProperty_hasLine.addRange(class_line);

		ObjectProperty ontObjectProperty_isLineOf = m.createObjectProperty(NS + "isLineOf");
		ontObjectProperty_isLineOf.addLabel("isLineOf", "en");
		ontObjectProperty_isLineOf.addLabel("является линией для", "ru");
		ontObjectProperty_isLineOf.addDomain(class_line);
		ontObjectProperty_isLineOf.addRange(class_preConditionChecker);

		ontObjectProperty_hasLine.addInverseOf(ontObjectProperty_isLineOf);
		ontObjectProperty_isLineOf.addInverseOf(ontObjectProperty_hasLine);

		// parameterUpdater
		ontObjectProperty_hasLine.addDomain(class_parameterUpdater);
		ontObjectProperty_isLineOf.addRange(class_parameterUpdater);

		// line
		DatatypeProperty ontDatatypeProperty_number = m.createDatatypeProperty(NS + "number");
		ontDatatypeProperty_number.addLabel("number", "en");
		ontDatatypeProperty_number.addLabel("номер", "ru");
		ontDatatypeProperty_number.addDomain(class_line);
		ontDatatypeProperty_number.addRange(XSD.integer);

		DatatypeProperty ontDatatypeProperty_text = m.createDatatypeProperty(NS + "text");
		ontDatatypeProperty_text.addLabel("text", "en");
		ontDatatypeProperty_text.addLabel("текст", "ru");
		ontDatatypeProperty_text.addDomain(class_line);
		ontDatatypeProperty_text.addRange(XSD.xstring);

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
			ind_systemTransformation.addProperty(ontDatatypeProperty_name, systemTransformation.getName());
			// << Individual: Action
			Action action = systemTransformation.getAction();
			Individual ind_action = class_Action.createIndividual(NS + UUID.randomUUID().toString());
			ind_action.addProperty(ontDatatypeProperty_name, action.getName());
			for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
				// << Individual: PreConditionChecker
				Individual ind_preConditionChecker = class_preConditionChecker
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
				String lines[] = luaPreConditionChecker.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = class_line.createIndividual(NS + UUID.randomUUID().toString());
					ind_line.addProperty(ontDatatypeProperty_number, Integer.toString(i + 1));
					ind_line.addProperty(ontDatatypeProperty_text, lines[i]);
					ind_line.addProperty(ontObjectProperty_isLineOf, ind_preConditionChecker);
					ind_preConditionChecker.addProperty(ontObjectProperty_hasLine, ind_line);
					// >> Individual: Line
				}
				ind_action.addProperty(ontObjectProperty_hasPreConditionChecker, ind_preConditionChecker);
				ind_preConditionChecker.addProperty(ontObjectProperty_isPreConditionCheckerOf, ind_action);
				// >> Individual: PreConditionChecker
			}
			for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
				// << Individual: ParameterUpdater
				Individual ind_parameterUpdater = class_parameterUpdater
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
				String lines[] = luaParameterUpdater.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = class_line.createIndividual(NS + UUID.randomUUID().toString());
					ind_line.addProperty(ontDatatypeProperty_number, Integer.toString(i + 1));
					ind_line.addProperty(ontDatatypeProperty_text, lines[i]);
					ind_line.addProperty(ontObjectProperty_isLineOf, ind_parameterUpdater);
					ind_parameterUpdater.addProperty(ontObjectProperty_hasLine, ind_line);
					// >> Individual: Line
				}
				ind_action.addProperty(ontObjectProperty_hasPreConditionChecker, ind_parameterUpdater);
				ind_parameterUpdater.addProperty(ontObjectProperty_isPreConditionCheckerOf, ind_action);
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
				Individual ind_objectTemplate = class_objectTemplate
						.createIndividual(NS + UUID.randomUUID().toString());
				ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
				ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
				ind_objectTemplate.addProperty(ontDatatypeProperty_objectId, objectTemplate.getId());
				int j = 0;
				for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
					// >> Individual: AttributeTemplate
					Individual ind_attributeTemplate = class_attributeTemplate
							.createIndividual(NS + UUID.randomUUID().toString());
					ind_attributeTemplate.addLabel(
							"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
							"en");
					ind_attributeTemplate.addLabel(
							"Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
							"ru");
					ind_attributeTemplate.addProperty(ontDatatypeProperty_name, attributeTemplate.getName());
					Object value = attributeTemplate.getValue();
					if (value != null) {
						// TODO (2021-01-13 #31): поддержка других DataType
						ind_attributeTemplate.addProperty(ontDatatypeProperty_value, value.toString());
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
				Individual ind_linkTemplate = class_linkTemplate.createIndividual(NS + UUID.randomUUID().toString());
				ind_linkTemplate.addLabel("Link Template ".concat(Integer.toString(i)), "en");
				ind_linkTemplate.addLabel("Шаблон связи ".concat(Integer.toString(i)), "ru");
				ind_linkTemplate.addProperty(ontDatatypeProperty_name, linkTemplate.getName());
				String objectId1 = linkTemplate.getObjectId1();
				if (objectId1 != null) {
					ind_linkTemplate.addProperty(ontDatatypeProperty_objectId1, objectId1);
				}
				String objectId2 = linkTemplate.getObjectId2();
				if (objectId2 != null) {
					ind_linkTemplate.addProperty(ontDatatypeProperty_objectId2, objectId2);
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
					Individual ind_attributeTransformation = class_attributeTransformation
							.createIndividual(NS + UUID.randomUUID().toString());
					ind_attributeTransformation.addProperty(ontDatatypeProperty_objectId,
							attributeTransformation.getObjectId());
					ind_attributeTransformation.addProperty(ontDatatypeProperty_name,
							attributeTransformation.getAttributeName());
					// TODO (2021-01-13 #31): поддержка других типов данных
					ind_attributeTransformation.addProperty(ontDatatypeProperty_value,
							attributeTransformation.getAttributeValue().toString());
					ind_attributeTransformation.addProperty(ontObjectProperty_isAttributeTransformationOf,
							ind_transformations);
					ind_transformations.addProperty(ontObjectProperty_hasAttributeTransformation,
							ind_attributeTransformation);
					// << Individual: AttributeTransformation
				} else if (transformation instanceof LinkTransformation) {
					LinkTransformation linkTransformation = (LinkTransformation) transformation;
					// >> Individual: LinkTransformation
					Individual ind_linkTransformation = class_linkTransformation
							.createIndividual(NS + UUID.randomUUID().toString());
					ind_linkTransformation.addProperty(ontDatatypeProperty_objectId, linkTransformation.getObjectId());
					ind_linkTransformation.addProperty(ontDatatypeProperty_name, linkTransformation.getLinkName());
					String objectIdOld = linkTransformation.getLinkObjectId2Old();
					if (objectIdOld != null) {
						ind_linkTransformation.addProperty(ontDatatypeProperty_oldValue, objectIdOld);
					}
					String objectIdNew = linkTransformation.getLinkObjectId2New();
					if (objectIdNew != null) {
						ind_linkTransformation.addProperty(ontDatatypeProperty_newValue, objectIdNew);
					}
					ind_linkTransformation.addProperty(ontObjectProperty_isLinkTransformationOf, ind_transformations);
					ind_transformations.addProperty(ontObjectProperty_hasLinkTransformation, ind_linkTransformation);
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

		return m;
	}

	@Override
	public SystemTransformations parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO (2021-01-13 #31): включить проверку copy-paste
	// CPD-ON
}
