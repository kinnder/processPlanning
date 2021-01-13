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

	final private String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	@Override
	public OntModel combine(SystemTransformations object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_systemTransformations = m.createClass(NS + "System Transformations");
		ontClass_systemTransformations.addLabel("System Transformations", "en");
		ontClass_systemTransformations.addLabel("Трансформации системы", "ru");

		OntClass ontClass_systemTransformation = m.createClass(NS + "System Transformation");
		ontClass_systemTransformation.addLabel("System Transformation", "en");
		ontClass_systemTransformation.addLabel("Трансформация системы", "ru");

		OntClass ontClass_systemTemplate = m.createClass(NS + "System Template");
		ontClass_systemTemplate.addLabel("System Template", "en");
		ontClass_systemTemplate.addLabel("Шаблон системы", "ru");

		OntClass ontClass_transformations = m.createClass(NS + "Transformations");
		ontClass_transformations.addLabel("Transformations", "en");
		ontClass_transformations.addLabel("Трансформации", "ru");

		OntClass ontClass_action = m.createClass(NS + "Action");
		ontClass_action.addLabel("Action", "en");
		ontClass_action.addLabel("Действие", "ru");

		OntClass ontClass_objectTemplate = m.createClass(NS + "Object Template");
		ontClass_objectTemplate.addLabel("Object Template", "en");
		ontClass_objectTemplate.addLabel("Шаблон объекта", "ru");

		OntClass ontClass_linkTemplate = m.createClass(NS + "Link Template");
		ontClass_linkTemplate.addLabel("Link Template", "en");
		ontClass_linkTemplate.addLabel("Шаблон связи", "ru");

		OntClass ontClass_attributeTemplate = m.createClass(NS + "Attribute Template");
		ontClass_attributeTemplate.addLabel("Attribute Template", "en");
		ontClass_attributeTemplate.addLabel("Шаблон атрибута", "ru");

		OntClass ontClass_attributeTransformation = m.createClass(NS + "Attribute Transformations");
		ontClass_attributeTransformation.addLabel("Attribute Transformation", "en");
		ontClass_attributeTransformation.addLabel("Трансформация атрибута", "ru");

		OntClass ontClass_linkTransformation = m.createClass(NS + "Link Transformation");
		ontClass_linkTransformation.addLabel("Link Transformation", "en");
		ontClass_linkTransformation.addLabel("Трансформация связи", "ru");

		OntClass ontClass_preConditionChecker = m.createClass(NS + "preConditionChecker");
		ontClass_preConditionChecker.addLabel("PreConditionChecker", "en");
		ontClass_preConditionChecker.addLabel("Проверка предусловий", "ru");

		OntClass ontClass_parameterUpdater = m.createClass(NS + "parameterUpdater");
		ontClass_parameterUpdater.addLabel("ParameterUpdater", "en");
		ontClass_parameterUpdater.addLabel("Обновление параметров", "ru");

		OntClass ontClass_line = m.createClass(NS + "line");
		ontClass_line.addLabel("Line", "en");
		ontClass_line.addLabel("Линия", "ru");

		ObjectProperty ontObjectProperty_hasSystemTransformation = m
				.createObjectProperty(NS + "hasSystemTransformation");
		ontObjectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		ontObjectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		ontObjectProperty_hasSystemTransformation.addDomain(ontClass_systemTransformations);
		ontObjectProperty_hasSystemTransformation.addRange(ontClass_systemTransformation);

		ObjectProperty ontObjectProperty_isSystemTransformationOf = m
				.createObjectProperty(NS + "isSystemTransformationOf");
		ontObjectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		ontObjectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		ontObjectProperty_isSystemTransformationOf.addDomain(ontClass_systemTransformation);
		ontObjectProperty_isSystemTransformationOf.addRange(ontClass_systemTransformations);

		ontObjectProperty_hasSystemTransformation.addInverseOf(ontObjectProperty_isSystemTransformationOf);
		ontObjectProperty_isSystemTransformationOf.addInverseOf(ontObjectProperty_hasSystemTransformation);

		ObjectProperty ontObjectProperty_hasSystemTemplate = m.createObjectProperty(NS + "hasSystemTemplate");
		ontObjectProperty_hasSystemTemplate.addLabel("has system template", "en");
		ontObjectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		ontObjectProperty_hasSystemTemplate.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasSystemTemplate.addRange(ontClass_systemTemplate);

		ObjectProperty ontObjectProperty_isSystemTemplateOf = m.createObjectProperty(NS + "isSystemTemplateOf");
		ontObjectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		ontObjectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		ontObjectProperty_isSystemTemplateOf.addDomain(ontClass_systemTemplate);
		ontObjectProperty_isSystemTemplateOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasSystemTemplate.addInverseOf(ontObjectProperty_isSystemTemplateOf);
		ontObjectProperty_isSystemTemplateOf.addInverseOf(ontObjectProperty_hasSystemTemplate);

		ObjectProperty ontObjectProperty_hasTransformations = m.createObjectProperty(NS + "hasTransformations");
		ontObjectProperty_hasTransformations.addLabel("has transformations", "en");
		ontObjectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		ontObjectProperty_hasTransformations.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasTransformations.addRange(ontClass_transformations);

		ObjectProperty ontObjectProperty_areTransformationsOf = m.createObjectProperty(NS + "areTransformationsOf");
		ontObjectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		ontObjectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		ontObjectProperty_areTransformationsOf.addDomain(ontClass_transformations);
		ontObjectProperty_areTransformationsOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasTransformations.addInverseOf(ontObjectProperty_areTransformationsOf);
		ontObjectProperty_areTransformationsOf.addInverseOf(ontObjectProperty_hasTransformations);

		ObjectProperty ontObjectProperty_hasAction = m.createObjectProperty(NS + "hasAction");
		ontObjectProperty_hasAction.addLabel("has action", "en");
		ontObjectProperty_hasAction.addLabel("имеет действие", "ru");
		ontObjectProperty_hasAction.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasAction.addRange(ontClass_action);

		ObjectProperty ontObjectProperty_isActionOf = m.createObjectProperty(NS + "isActionOf");
		ontObjectProperty_isActionOf.addLabel("is action of", "en");
		ontObjectProperty_isActionOf.addLabel("является действием для", "ru");
		ontObjectProperty_isActionOf.addDomain(ontClass_action);
		ontObjectProperty_isActionOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasAction.addInverseOf(ontObjectProperty_isActionOf);
		ontObjectProperty_isActionOf.addInverseOf(ontObjectProperty_hasAction);

		ObjectProperty ontObjectProperty_hasObjectTemplate = m.createObjectProperty(NS + "hasObjectTemplate");
		ontObjectProperty_hasObjectTemplate.addLabel("has objectTemplate", "en");
		ontObjectProperty_hasObjectTemplate.addLabel("имеет шаблон объекта", "ru");
		ontObjectProperty_hasObjectTemplate.addDomain(ontClass_systemTemplate);
		ontObjectProperty_hasObjectTemplate.addRange(ontClass_objectTemplate);

		ObjectProperty ontObjectProperty_isObjectTemplateOf = m.createObjectProperty(NS + "isObjectTemplateOf");
		ontObjectProperty_isObjectTemplateOf.addLabel("is object template of", "en");
		ontObjectProperty_isObjectTemplateOf.addLabel("является шаблоном объекта для", "ru");
		ontObjectProperty_isObjectTemplateOf.addDomain(ontClass_objectTemplate);
		ontObjectProperty_isObjectTemplateOf.addRange(ontClass_systemTemplate);

		ontObjectProperty_hasObjectTemplate.addInverseOf(ontObjectProperty_isObjectTemplateOf);
		ontObjectProperty_isObjectTemplateOf.addInverseOf(ontObjectProperty_hasObjectTemplate);

		ObjectProperty ontObjectProperty_hasLinkTemplate = m.createObjectProperty(NS + "hasLinkTemplate");
		ontObjectProperty_hasLinkTemplate.addLabel("has link template", "en");
		ontObjectProperty_hasLinkTemplate.addLabel("имеет шаблон связи", "ru");
		ontObjectProperty_hasLinkTemplate.addDomain(ontClass_systemTemplate);
		ontObjectProperty_hasLinkTemplate.addRange(ontClass_linkTemplate);

		ObjectProperty ontObjectProperty_isLinkTemplateOf = m.createObjectProperty(NS + "isLinkTemplateOf");
		ontObjectProperty_isLinkTemplateOf.addLabel("is link template of", "en");
		ontObjectProperty_isLinkTemplateOf.addLabel("является шаблоном связи для", "ru");
		ontObjectProperty_isLinkTemplateOf.addDomain(ontClass_linkTemplate);
		ontObjectProperty_isLinkTemplateOf.addRange(ontClass_systemTemplate);

		ontObjectProperty_hasLinkTemplate.addInverseOf(ontObjectProperty_isLinkTemplateOf);
		ontObjectProperty_isLinkTemplateOf.addInverseOf(ontObjectProperty_hasLinkTemplate);

		ObjectProperty ontObjectProperty_hasAttributeTemplate = m.createObjectProperty(NS + "hasAttributeTemplate");
		ontObjectProperty_hasAttributeTemplate.addLabel("has attribute template", "en");
		ontObjectProperty_hasAttributeTemplate.addLabel("имеет шаблон атрибута", "ru");
		ontObjectProperty_hasAttributeTemplate.addDomain(ontClass_objectTemplate);
		ontObjectProperty_hasAttributeTemplate.addRange(ontClass_attributeTemplate);

		ObjectProperty ontObjectProperty_isAttributeTemplateOf = m.createObjectProperty(NS + "isAttributeTemplateOf");
		ontObjectProperty_isAttributeTemplateOf.addLabel("is attribute template of", "en");
		ontObjectProperty_isAttributeTemplateOf.addLabel("является шаблоном атрибута для", "ru");
		ontObjectProperty_isAttributeTemplateOf.addDomain(ontClass_attributeTemplate);
		ontObjectProperty_isAttributeTemplateOf.addRange(ontClass_objectTemplate);

		ontObjectProperty_hasAttributeTemplate.addInverseOf(ontObjectProperty_isAttributeTemplateOf);
		ontObjectProperty_isAttributeTemplateOf.addInverseOf(ontObjectProperty_hasAttributeTemplate);

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addDomain(ontClass_systemTransformation);
		ontDatatypeProperty_name.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId = m.createDatatypeProperty(NS + "objectId");
		ontDatatypeProperty_objectId.addLabel("objectId", "en");
		ontDatatypeProperty_objectId.addLabel("идентификатор объекта", "ru");
		ontDatatypeProperty_objectId.addDomain(ontClass_objectTemplate);
		ontDatatypeProperty_objectId.addRange(XSD.xstring);

		ontDatatypeProperty_name.addDomain(ontClass_attributeTemplate);

		DatatypeProperty ontDatatypeProperty_value = m.createDatatypeProperty(NS + "value");
		ontDatatypeProperty_value.addLabel("value", "en");
		ontDatatypeProperty_value.addLabel("значение", "ru");
		ontDatatypeProperty_value.addDomain(ontClass_attributeTemplate);
		ontDatatypeProperty_value.addRange(XSD.xstring);
		ontDatatypeProperty_value.addRange(XSD.xboolean);
		ontDatatypeProperty_value.addRange(XSD.xint);

		ontDatatypeProperty_name.addDomain(ontClass_linkTemplate);

		DatatypeProperty ontDatatypeProperty_objectId1 = m.createDatatypeProperty(NS + "objectId1");
		ontDatatypeProperty_objectId1.addLabel("objectId1", "en");
		ontDatatypeProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		ontDatatypeProperty_objectId1.addDomain(ontClass_linkTemplate);
		ontDatatypeProperty_objectId1.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_objectId2 = m.createDatatypeProperty(NS + "objectId2");
		ontDatatypeProperty_objectId2.addLabel("objectId2", "en");
		ontDatatypeProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		ontDatatypeProperty_objectId2.addDomain(ontClass_linkTemplate);
		ontDatatypeProperty_objectId2.addRange(XSD.xstring);

		ObjectProperty ontObjectProperty_hasAttributeTransformation = m
				.createObjectProperty(NS + "hasAttributeTransformation");
		ontObjectProperty_hasAttributeTransformation.addLabel("has attribute transformation", "en");
		ontObjectProperty_hasAttributeTransformation.addLabel("имеет трансформацию атрибута", "ru");
		ontObjectProperty_hasAttributeTransformation.addDomain(ontClass_transformations);
		ontObjectProperty_hasAttributeTransformation.addRange(ontClass_attributeTransformation);

		ObjectProperty ontObjectProperty_isAttributeTransformationOf = m
				.createObjectProperty(NS + "isAttributeTransformationOf");
		ontObjectProperty_isAttributeTransformationOf.addLabel("is attribute transformation of", "en");
		ontObjectProperty_isAttributeTransformationOf.addLabel("является трансформацией атрибута для", "ru");
		ontObjectProperty_isAttributeTransformationOf.addDomain(ontClass_attributeTransformation);
		ontObjectProperty_isAttributeTransformationOf.addRange(ontClass_transformations);

		ontObjectProperty_hasAttributeTransformation.addInverseOf(ontObjectProperty_isAttributeTransformationOf);
		ontObjectProperty_isAttributeTransformationOf.addInverseOf(ontObjectProperty_hasAttributeTransformation);

		ObjectProperty ontObjectProperty_hasLinkTransformation = m.createObjectProperty(NS + "hasLinkTransformation");
		ontObjectProperty_hasLinkTransformation.addLabel("has link transformation", "en");
		ontObjectProperty_hasLinkTransformation.addLabel("имеет трансформацию связи", "ru");
		ontObjectProperty_hasLinkTransformation.addDomain(ontClass_transformations);
		ontObjectProperty_hasLinkTransformation.addRange(ontClass_linkTransformation);

		ObjectProperty ontObjectProperty_isLinkTransformationOf = m.createObjectProperty(NS + "isLinkTransformationOf");
		ontObjectProperty_isLinkTransformationOf.addLabel("is link transformation of", "en");
		ontObjectProperty_isLinkTransformationOf.addLabel("является трасформацией связи для", "ru");
		ontObjectProperty_isLinkTransformationOf.addDomain(ontClass_linkTransformation);
		ontObjectProperty_isLinkTransformationOf.addRange(ontClass_transformations);

		ontObjectProperty_hasLinkTransformation.addInverseOf(ontObjectProperty_isLinkTransformationOf);
		ontObjectProperty_isLinkTransformationOf.addInverseOf(ontObjectProperty_hasLinkTransformation);

		ontDatatypeProperty_objectId.addDomain(ontClass_linkTransformation);
		ontDatatypeProperty_name.addDomain(ontClass_linkTransformation);

		DatatypeProperty ontDatatypeProperty_oldValue = m.createDatatypeProperty(NS + "oldValue");
		ontDatatypeProperty_oldValue.addLabel("old value", "en");
		ontDatatypeProperty_oldValue.addLabel("старое значение", "ru");
		ontDatatypeProperty_oldValue.addDomain(ontClass_linkTransformation);
		ontDatatypeProperty_oldValue.addRange(XSD.xstring);

		DatatypeProperty ontDatatypeProperty_newValue = m.createDatatypeProperty(NS + "newValue");
		ontDatatypeProperty_newValue.addLabel("new value", "en");
		ontDatatypeProperty_newValue.addLabel("новое значение", "ru");
		ontDatatypeProperty_newValue.addDomain(ontClass_linkTransformation);
		ontDatatypeProperty_newValue.addRange(XSD.xstring);

		ontDatatypeProperty_objectId.addDomain(ontClass_attributeTransformation);
		ontDatatypeProperty_name.addDomain(ontClass_attributeTransformation);
		ontDatatypeProperty_value.addDomain(ontClass_attributeTransformation);

		// action
		ontDatatypeProperty_name.addDomain(ontClass_action);

		ObjectProperty ontObjectProperty_hasPreConditionChecker = m.createObjectProperty(NS + "hasPreConditionChecker");
		ontObjectProperty_hasPreConditionChecker.addLabel("hasPreconditionChecker", "en");
		ontObjectProperty_hasPreConditionChecker.addLabel("имеет проверку условий", "ru");
		ontObjectProperty_hasPreConditionChecker.addDomain(ontClass_action);
		ontObjectProperty_hasPreConditionChecker.addRange(ontClass_preConditionChecker);

		ObjectProperty ontObjectProperty_isPreConditionCheckerOf = m.createObjectProperty(NS + "isPreConditionChecker");
		ontObjectProperty_isPreConditionCheckerOf.addLabel("isPreConditionCheckerOf", "en");
		ontObjectProperty_isPreConditionCheckerOf.addLabel("является проверкой условий для", "ru");
		ontObjectProperty_isPreConditionCheckerOf.addDomain(ontClass_preConditionChecker);
		ontObjectProperty_isPreConditionCheckerOf.addRange(ontClass_action);

		ontObjectProperty_hasPreConditionChecker.addInverseOf(ontObjectProperty_isPreConditionCheckerOf);
		ontObjectProperty_isPreConditionCheckerOf.addInverseOf(ontObjectProperty_hasPreConditionChecker);

		ObjectProperty ontObjectProperty_hasParameterUpdater = m.createObjectProperty(NS + "hasParameterUpdater");
		ontObjectProperty_hasParameterUpdater.addLabel("hasParameterUpdater", "en");
		ontObjectProperty_hasParameterUpdater.addLabel("имеет функцию обновления", "ru");
		ontObjectProperty_hasParameterUpdater.addDomain(ontClass_action);
		ontObjectProperty_hasParameterUpdater.addRange(ontClass_parameterUpdater);

		ObjectProperty ontObjectProperty_isParameterUpdaterOf = m.createObjectProperty(NS + "isParameterUpdater");
		ontObjectProperty_isParameterUpdaterOf.addLabel("isParameterUpdaterOf", "en");
		ontObjectProperty_isParameterUpdaterOf.addLabel("является функцией обновления для", "ru");
		ontObjectProperty_isParameterUpdaterOf.addDomain(ontClass_parameterUpdater);
		ontObjectProperty_isParameterUpdaterOf.addRange(ontClass_action);

		ontObjectProperty_hasParameterUpdater.addInverseOf(ontObjectProperty_isParameterUpdaterOf);
		ontObjectProperty_isParameterUpdaterOf.addInverseOf(ontObjectProperty_hasParameterUpdater);

		// TODO (2021-01-12 #31): добавить общий класс function
		// preConditionChecker
		ObjectProperty ontObjectProperty_hasLine = m.createObjectProperty(NS + "hasLine");
		ontObjectProperty_hasLine.addLabel("hasLine", "en");
		ontObjectProperty_hasLine.addLabel("имеет линию", "ru");
		ontObjectProperty_hasLine.addDomain(ontClass_preConditionChecker);
		ontObjectProperty_hasLine.addRange(ontClass_line);

		ObjectProperty ontObjectProperty_isLineOf = m.createObjectProperty(NS + "isLineOf");
		ontObjectProperty_isLineOf.addLabel("isLineOf", "en");
		ontObjectProperty_isLineOf.addLabel("является линией для", "ru");
		ontObjectProperty_isLineOf.addDomain(ontClass_line);
		ontObjectProperty_isLineOf.addRange(ontClass_preConditionChecker);

		ontObjectProperty_hasLine.addInverseOf(ontObjectProperty_isLineOf);
		ontObjectProperty_isLineOf.addInverseOf(ontObjectProperty_hasLine);

		// parameterUpdater
		ontObjectProperty_hasLine.addDomain(ontClass_parameterUpdater);
		ontObjectProperty_isLineOf.addRange(ontClass_parameterUpdater);

		// line
		DatatypeProperty ontDatatypeProperty_number = m.createDatatypeProperty(NS + "number");
		ontDatatypeProperty_number.addLabel("number", "en");
		ontDatatypeProperty_number.addLabel("номер", "ru");
		ontDatatypeProperty_number.addDomain(ontClass_line);
		ontDatatypeProperty_number.addRange(XSD.integer);

		DatatypeProperty ontDatatypeProperty_text = m.createDatatypeProperty(NS + "text");
		ontDatatypeProperty_text.addLabel("text", "en");
		ontDatatypeProperty_text.addLabel("текст", "ru");
		ontDatatypeProperty_text.addDomain(ontClass_line);
		ontDatatypeProperty_text.addRange(XSD.xstring);

		// Individuals
		// << Individual: SystemTransformations
		Individual ind_systemTransformations = ontClass_systemTransformations
				.createIndividual(NS + UUID.randomUUID().toString());
		ind_systemTransformations.addLabel("System Transformations 1", "en");
		ind_systemTransformations.addLabel("Трансформации системы 1", "ru");

		for (SystemTransformation systemTransformation : object) {
			// << Individual: SystemTransformation
			Individual ind_systemTransformation = ontClass_systemTransformation
					.createIndividual(NS + UUID.randomUUID().toString());
			ind_systemTransformation.addProperty(ontDatatypeProperty_name, systemTransformation.getName());
			// << Individual: Action
			Action action = systemTransformation.getAction();
			Individual ind_action = ontClass_action.createIndividual(NS + UUID.randomUUID().toString());
			ind_action.addProperty(ontDatatypeProperty_name, action.getName());
			for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
				// << Individual: PreConditionChecker
				Individual ind_preConditionChecker = ontClass_preConditionChecker
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
				String lines[] = luaPreConditionChecker.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = ontClass_line.createIndividual(NS + UUID.randomUUID().toString());
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
				Individual ind_parameterUpdater = ontClass_parameterUpdater
						.createIndividual(NS + UUID.randomUUID().toString());
				LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
				String lines[] = luaParameterUpdater.getScript().split("\n");
				for (int i = 0; i < lines.length; i++) {
					// << Individual: Line
					Individual ind_line = ontClass_line.createIndividual(NS + UUID.randomUUID().toString());
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
			ind_action.addProperty(ontObjectProperty_isActionOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(ontObjectProperty_hasAction, ind_action);
			// >> Individual: Action
			// << Individual: SystemTemplate
			SystemTemplate systemTemplate = systemTransformation.getSystemTemplate();
			Individual ind_systemTemplate = ontClass_systemTemplate.createIndividual(NS + UUID.randomUUID().toString());
			ind_systemTemplate.addLabel("System Template 1", "en");
			ind_systemTemplate.addLabel("Шаблон системы 1", "ru");
			int i = 0;
			for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
				// >> Individual: SystemObjectTemplate
				i++;
				Individual ind_objectTemplate = ontClass_objectTemplate
						.createIndividual(NS + UUID.randomUUID().toString());
				ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
				ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
				ind_objectTemplate.addProperty(ontDatatypeProperty_objectId, objectTemplate.getId());
				int j = 0;
				for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
					// >> Individual: AttributeTemplate
					Individual ind_attributeTemplate = ontClass_attributeTemplate
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
					ind_attributeTemplate.addProperty(ontObjectProperty_isAttributeTemplateOf, ind_objectTemplate);
					ind_objectTemplate.addProperty(ontObjectProperty_hasAttributeTemplate, ind_attributeTemplate);
					// << Individual: AttributeTemplate
				}
				ind_objectTemplate.addProperty(ontObjectProperty_isObjectTemplateOf, ind_systemTemplate);
				ind_systemTemplate.addProperty(ontObjectProperty_hasObjectTemplate, ind_objectTemplate);
				// << Individual: SystemObjectTemplate
			}
			i = 0;
			for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
				// >> Individual: LinkTemplate
				Individual ind_linkTemplate = ontClass_linkTemplate.createIndividual(NS + UUID.randomUUID().toString());
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
				ind_linkTemplate.addProperty(ontObjectProperty_isLinkTemplateOf, ind_systemTemplate);
				ind_systemTemplate.addProperty(ontObjectProperty_hasLinkTemplate, ind_linkTemplate);
				// << Individual: LinkTemplate
			}
			ind_systemTemplate.addProperty(ontObjectProperty_isSystemTemplateOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(ontObjectProperty_hasSystemTemplate, ind_systemTemplate);
			// >> Individual: SystemTemplate
			// << Individual: Transformations
			Transformation[] transformations = systemTransformation.getTransformations();
			Individual ind_transformations = ontClass_transformations
					.createIndividual(NS + UUID.randomUUID().toString());
			for (Transformation transformation : transformations) {
				if (transformation instanceof AttributeTransformation) {
					AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
					// >> Individual: AttributeTransformation
					Individual ind_attributeTransformation = ontClass_attributeTransformation
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
					Individual ind_linkTransformation = ontClass_linkTransformation
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
			ind_transformations.addProperty(ontObjectProperty_areTransformationsOf, ind_systemTransformation);
			ind_systemTransformation.addProperty(ontObjectProperty_hasTransformations, ind_transformations);
			// >> Individual: Transformations
			ind_systemTransformations.addProperty(ontObjectProperty_hasSystemTransformation, ind_systemTransformation);
			ind_systemTransformation.addProperty(ontObjectProperty_isSystemTransformationOf, ind_systemTransformations);
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
