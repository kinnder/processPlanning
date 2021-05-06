package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DataRange;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.XSD;

import planning.method.SystemTransformations;

public class SystemTransformationsOWLModel implements OWLModel<SystemTransformations> {

	static final String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	static final String URI_isLineOf = NS + "isLineOf";

	static final String URI_hasLine = NS + "hasLine";

	static final String URI_isParameterUpdaterOf = NS + "isParameterUpdaterOf";

	static final String URI_hasParameterUpdater = NS + "hasParameterUpdater";

	static final String URI_isPreConditionCheckerOf = NS + "isPreConditionCheckerOf";

	static final String URI_hasPreConditionChecker = NS + "hasPreConditionChecker";

	static final String URI_isLinkTransformationOf = NS + "isLinkTransformationOf";

	static final String URI_hasLinkTransformation = NS + "hasLinkTransformation";

	static final String URI_isAttributeTransformationOf = NS + "isAttributeTransformationOf";

	static final String URI_hasAttributeTransformation = NS + "hasAttributeTransformation";

	static final String URI_hasTransformation = NS + "hasTransformation";

	static final String URI_isTransformationOf = NS + "isTransformationOf";

	static final String URI_isAttributeTemplateOf = NS + "isAttributeTemplateOf";

	static final String URI_hasAttributeTemplate = NS + "hasAttributeTemplate";

	static final String URI_isLinkTemplateOf = NS + "isLinkTemplateOf";

	static final String URI_hasLinkTemplate = NS + "hasLinkTemplate";

	static final String URI_isObjectTemplateOf = NS + "isObjectTemplateOf";

	static final String URI_hasObjectTemplate = NS + "hasObjectTemplate";

	static final String URI_isActionOf = NS + "isActionOf";

	static final String URI_hasAction = NS + "hasAction";

	static final String URI_areTransformationsOf = NS + "areTransformationsOf";

	static final String URI_hasTransformations = NS + "hasTransformations";

	static final String URI_isSystemTemplateOf = NS + "isSystemTemplateOf";

	static final String URI_hasSystemTemplate = NS + "hasSystemTemplate";

	static final String URI_isSystemTransformationOf = NS + "isSystemTransformationOf";

	static final String URI_hasSystemTransformation = NS + "hasSystemTransformation";

	static final String URI_Line = NS + "Line";

	static final String URI_ParameterUpdater = NS + "ParameterUpdater";

	static final String URI_PreConditionChecker = NS + "PreConditionChecker";

	static final String URI_LinkTransformation = NS + "LinkTransformation";

	static final String URI_AttributeTransformation = NS + "AttributeTransformation";

	static final String URI_Transformation = NS + "Transformation";

	static final String URI_AttributeTemplate = NS + "AttributeTemplate";

	static final String URI_LinkTemplate = NS + "LinkTemplate";

	static final String URI_ObjectTemplate = NS + "ObjectTemplate";

	static final String URI_SystemTransformations = NS + "SystemTransformations";

	static final String URI_SystemTransformation = NS + "SystemTransformation";

	static final String URI_SystemTemplate = NS + "SystemTemplate";

	static final String URI_Transformations = NS + "Transformations";

	static final String URI_Action = NS + "Action";

	static final String URI_name = NS + "name";

	static final String URI_objectId = NS + "objectId";

	static final String URI_value = NS + "value";

	static final String URI_objectId1 = NS + "objectId1";

	static final String URI_objectId2 = NS + "objectId2";

	static final String URI_oldValue = NS + "oldValue";

	static final String URI_newValue = NS + "newValue";

	static final String URI_text = NS + "text";

	static final String URI_number = NS + "number";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	private OntModel m;

	@Override
	public OntModel getOntologyModel() {
		return m;
	}

	private OntClass class_SystemTransformations;

	public OntClass getClass_SystemTransformations() {
		return class_SystemTransformations;
	}

	private OntClass class_SystemTransformation;

	public OntClass getClass_SystemTransformation() {
		return class_SystemTransformation;
	}

	private OntClass class_SystemTemplate;

	public OntClass getClass_SystemTemplate() {
		return class_SystemTemplate;
	}

	private OntClass class_Transformations;

	public OntClass getClass_Transformations() {
		return class_Transformations;
	}

	private OntClass class_Action;

	public OntClass getClass_Action() {
		return class_Action;
	}

	private OntClass class_ObjectTemplate;

	public OntClass getClass_ObjectTemplate() {
		return class_ObjectTemplate;
	}

	private OntClass class_LinkTemplate;

	public OntClass getClass_LinkTemplate() {
		return class_LinkTemplate;
	}

	private OntClass class_AttributeTemplate;

	public OntClass getClass_AttributeTemplate() {
		return class_AttributeTemplate;
	}

	private OntClass class_AttributeTransformation;

	public OntClass getClass_AttributeTransformation() {
		return class_AttributeTransformation;
	}

	private OntClass class_LinkTransformation;

	public OntClass getClass_LinkTransformation() {
		return class_LinkTransformation;
	}

	private OntClass class_Transformation;

	public OntClass getClass_Transformation() {
		return class_Transformation;
	}

	private OntClass class_PreConditionChecker;

	public OntClass getClass_PreConditionChecker() {
		return class_PreConditionChecker;
	}

	private OntClass class_ParameterUpdater;

	public OntClass getClass_ParameterUpdater() {
		return class_ParameterUpdater;
	}

	private OntClass class_Line;

	public OntClass getClass_Line() {
		return class_Line;
	}

	private ObjectProperty objectProperty_hasSystemTransformation;

	public ObjectProperty getObjectProperty_hasSystemTransformation() {
		return objectProperty_hasSystemTransformation;
	}

	private ObjectProperty objectProperty_isSystemTransformationOf;

	public ObjectProperty getObjectProperty_isSystemTransformationOf() {
		return objectProperty_isSystemTransformationOf;
	}

	private ObjectProperty objectProperty_hasSystemTemplate;

	public ObjectProperty getObjectProperty_hasSystemTemplate() {
		return objectProperty_hasSystemTemplate;
	}

	private ObjectProperty objectProperty_isSystemTemplateOf;

	public ObjectProperty getObjectProperty_isSystemTemplateOf() {
		return objectProperty_isSystemTemplateOf;
	}

	private ObjectProperty objectProperty_hasTransformations;

	public ObjectProperty getObjectProperty_hasTransformations() {
		return objectProperty_hasTransformations;
	}

	private ObjectProperty objectProperty_areTransformationsOf;

	public ObjectProperty getObjectProperty_areTransformationsOf() {
		return objectProperty_areTransformationsOf;
	}

	private ObjectProperty objectProperty_hasAction;

	public ObjectProperty getObjectProperty_hasAction() {
		return objectProperty_hasAction;
	}

	private ObjectProperty objectProperty_isActionOf;

	public ObjectProperty getObjectProperty_isActionOf() {
		return objectProperty_isActionOf;
	}

	private ObjectProperty objectProperty_hasObjectTemplate;

	public ObjectProperty getObjectProperty_hasObjectTemplate() {
		return objectProperty_hasObjectTemplate;
	}

	private ObjectProperty objectProperty_isObjectTemplateOf;

	public ObjectProperty getObjectProperty_isObjectTemplateOf() {
		return objectProperty_isObjectTemplateOf;
	}

	private ObjectProperty objectProperty_hasLinkTemplate;

	public ObjectProperty getObjectProperty_hasLinkTemplate() {
		return objectProperty_hasLinkTemplate;
	}

	private ObjectProperty objectProperty_isLinkTemplateOf;

	public ObjectProperty getObjectProperty_isLinkTemplateOf() {
		return objectProperty_isLinkTemplateOf;
	}

	private ObjectProperty objectProperty_hasAttributeTemplate;

	public ObjectProperty getObjectProperty_hasAttributeTemplate() {
		return objectProperty_hasAttributeTemplate;
	}

	private ObjectProperty objectProperty_isAttributeTemplateOf;

	public ObjectProperty getObjectProperty_isAttributeTemplateOf() {
		return objectProperty_isAttributeTemplateOf;
	}

	private ObjectProperty objectProperty_hasAttributeTransformation;

	public ObjectProperty getObjectProperty_hasAttributeTransformation() {
		return objectProperty_hasAttributeTransformation;
	}

	private ObjectProperty objectProperty_isAttributeTransformationOf;

	public ObjectProperty getObjectProperty_isAttributeTransformationOf() {
		return objectProperty_isAttributeTransformationOf;
	}

	private ObjectProperty objectProperty_hasLinkTransformation;

	public ObjectProperty getObjectProperty_hasLinkTransformation() {
		return objectProperty_hasLinkTransformation;
	}

	private ObjectProperty objectProperty_isLinkTransformationOf;

	public ObjectProperty getObjectProperty_isLinkTransformationOf() {
		return objectProperty_isLinkTransformationOf;
	}

	private ObjectProperty objectProperty_hasTransformation;

	public ObjectProperty getObjectProperty_hasTransformation() {
		return objectProperty_hasTransformation;
	}

	private ObjectProperty objectProperty_isTransformationOf;

	public ObjectProperty getObjectProperty_isTransformationOf() {
		return objectProperty_isTransformationOf;
	}

	private ObjectProperty objectProperty_hasPreConditionChecker;

	public ObjectProperty getObjectProperty_hasPreConditionChecker() {
		return objectProperty_hasPreConditionChecker;
	}

	private ObjectProperty objectProperty_isPreConditionCheckerOf;

	public ObjectProperty getObjectProperty_isPreConditionCheckerOf() {
		return objectProperty_isPreConditionCheckerOf;
	}

	private ObjectProperty objectProperty_hasParameterUpdater;

	public ObjectProperty getObjectProperty_hasParameterUpdater() {
		return objectProperty_hasParameterUpdater;
	}

	private ObjectProperty objectProperty_isParameterUpdaterOf;

	public ObjectProperty getObjectProperty_isParameterUpdaterOf() {
		return objectProperty_isParameterUpdaterOf;
	}

	private ObjectProperty objectProperty_hasLine;

	public ObjectProperty getObjectProperty_hasLine() {
		return objectProperty_hasLine;
	}

	private ObjectProperty objectProperty_isLineOf;

	public ObjectProperty getObjectProperty_isLineOf() {
		return objectProperty_isLineOf;
	}

	private DatatypeProperty dataProperty_name;

	public DatatypeProperty getDataProperty_name() {
		return dataProperty_name;
	}

	private DatatypeProperty dataProperty_objectId;

	public DatatypeProperty getDataProperty_objectId() {
		return dataProperty_objectId;
	}

	private DatatypeProperty dataProperty_value;

	public DatatypeProperty getDataProperty_value() {
		return dataProperty_value;
	}

	private DatatypeProperty dataProperty_objectId1;

	public DatatypeProperty getDataProperty_objectId1() {
		return dataProperty_objectId1;
	}

	private DatatypeProperty dataProperty_objectId2;

	public DatatypeProperty getDataProperty_objectId2() {
		return dataProperty_objectId2;
	}

	private DatatypeProperty dataProperty_oldValue;

	public DatatypeProperty getDataProperty_oldValue() {
		return dataProperty_oldValue;
	}

	private DatatypeProperty dataProperty_newValue;

	public DatatypeProperty getDataProperty_newValue() {
		return dataProperty_newValue;
	}

	private DatatypeProperty dataProperty_number;

	public DatatypeProperty getDataProperty_number() {
		return dataProperty_number;
	}

	private DatatypeProperty dataProperty_text;

	public DatatypeProperty getDataProperty_text() {
		return dataProperty_text;
	}

	// TODO (2021-05-06 #36): включить проверку copy-paste
	// CPD-OFF

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	private DataRange createDataRange(RDFNode... members) {
		DataRange d = m.createOntResource(DataRange.class, m.getProfile().DATARANGE(), null);
		d.addProperty(m.getProfile().UNION_OF(), m.createList(members));
		return d;
	}

	// CPD-ON

	@Override
	public void createOntologyModel() {
		m = createOntologyModelBase();

		m.createOntology(NS);

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

		class_Transformation = m.createClass(URI_Transformation);
		class_Transformation.addLabel("Transformation", "en");
		class_Transformation.addLabel("Трансформация", "ru");

		class_PreConditionChecker = m.createClass(URI_PreConditionChecker);
		class_PreConditionChecker.addLabel("PreCondition Checker", "en");
		class_PreConditionChecker.addLabel("Проверка предусловий", "ru");

		class_ParameterUpdater = m.createClass(URI_ParameterUpdater);
		class_ParameterUpdater.addLabel("Parameter Updater", "en");
		class_ParameterUpdater.addLabel("Обновление параметров", "ru");

		class_Line = m.createClass(URI_Line);
		class_Line.addLabel("Line", "en");
		class_Line.addLabel("Линия", "ru");

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

		objectProperty_hasTransformation = m.createObjectProperty(URI_hasTransformation);
		objectProperty_hasTransformation.addLabel("has transformation", "en");
		objectProperty_hasTransformation.addLabel("имеет трансформацию", "ru");
		objectProperty_hasTransformation.addDomain(class_Transformations);
		objectProperty_hasTransformation.addRange(class_Transformation);

		objectProperty_isTransformationOf = m.createObjectProperty(URI_isTransformationOf);
		objectProperty_isTransformationOf.addLabel("is transformation of", "en");
		objectProperty_isTransformationOf.addLabel("является трансформацией для", "ru");
		objectProperty_isTransformationOf.addDomain(class_Transformation);
		objectProperty_isTransformationOf.addRange(class_Transformations);

		makeInverse(objectProperty_hasTransformation, objectProperty_isTransformationOf);

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
		objectProperty_hasLine.addRange(class_Line);

		objectProperty_isLineOf = m.createObjectProperty(URI_isLineOf);
		objectProperty_isLineOf.addLabel("is line of", "en");
		objectProperty_isLineOf.addLabel("является линией для", "ru");
		objectProperty_isLineOf.addDomain(class_Line);
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
		dataProperty_objectId.addDomain(class_Transformation);
		dataProperty_objectId.addDomain(class_ObjectTemplate);
		dataProperty_objectId.addRange(XSD.xstring);

		dataProperty_value = m.createDatatypeProperty(URI_value);
		dataProperty_value.addLabel("value", "en");
		dataProperty_value.addLabel("значение", "ru");
		dataProperty_value.addDomain(class_AttributeTemplate);
		dataProperty_value.addDomain(class_AttributeTransformation);
		dataProperty_value.addRange(createDataRange(XSD.xstring, XSD.xboolean, XSD.xint));

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
		dataProperty_number.addDomain(class_Line);
		dataProperty_number.addRange(XSD.integer);

		dataProperty_text = m.createDatatypeProperty(URI_text);
		dataProperty_text.addLabel("text", "en");
		dataProperty_text.addLabel("текст", "ru");
		dataProperty_text.addDomain(class_Line);
		dataProperty_text.addRange(XSD.xstring);
	}

	@Override
	public String getUniqueURI() {
		return NS + UUID.randomUUID().toString();
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		m = ontModel;

		class_SystemTransformations = m.getOntClass(URI_SystemTransformations);
		class_SystemTransformation = m.getOntClass(URI_SystemTransformation);
		class_SystemTemplate = ontModel.getOntClass(URI_SystemTemplate);
		class_Transformations = ontModel.getOntClass(URI_Transformations);
		class_Action = ontModel.getOntClass(URI_Action);
		class_PreConditionChecker = ontModel.getOntClass(URI_PreConditionChecker);
		class_ParameterUpdater = ontModel.getOntClass(URI_ParameterUpdater);
		class_Line = ontModel.getOntClass(URI_Line);
		class_ObjectTemplate = ontModel.getOntClass(URI_ObjectTemplate);
		class_LinkTemplate = ontModel.getOntClass(URI_LinkTemplate);
		class_AttributeTemplate = ontModel.getOntClass(URI_AttributeTemplate);
		class_AttributeTransformation = ontModel.getOntClass(URI_AttributeTransformation);
		class_LinkTransformation = ontModel.getOntClass(URI_LinkTransformation);
		class_Transformation = ontModel.getOntClass(URI_Transformation);

		objectProperty_hasSystemTransformation = m.getObjectProperty(URI_hasSystemTransformation);
		objectProperty_isSystemTransformationOf = m.getObjectProperty(URI_isSystemTransformationOf);
		objectProperty_hasSystemTemplate = ontModel.getObjectProperty(URI_hasSystemTemplate);
		objectProperty_isSystemTemplateOf = ontModel.getObjectProperty(URI_isSystemTemplateOf);
		objectProperty_hasTransformations = ontModel.getObjectProperty(URI_hasTransformations);
		objectProperty_areTransformationsOf = ontModel.getObjectProperty(URI_areTransformationsOf);
		objectProperty_hasAction = ontModel.getObjectProperty(URI_hasAction);
		objectProperty_isActionOf = ontModel.getObjectProperty(URI_isActionOf);
		objectProperty_hasPreConditionChecker = ontModel.getObjectProperty(URI_hasPreConditionChecker);
		objectProperty_isPreConditionCheckerOf = ontModel.getObjectProperty(URI_isPreConditionCheckerOf);
		objectProperty_hasParameterUpdater = ontModel.getObjectProperty(URI_hasParameterUpdater);
		objectProperty_isParameterUpdaterOf = ontModel.getObjectProperty(URI_isParameterUpdaterOf);
		objectProperty_hasLine = ontModel.getObjectProperty(URI_hasLine);
		objectProperty_isLineOf = ontModel.getObjectProperty(URI_isLineOf);
		objectProperty_hasObjectTemplate = ontModel.getObjectProperty(URI_hasObjectTemplate);
		objectProperty_isObjectTemplateOf = ontModel.getObjectProperty(URI_isObjectTemplateOf);
		objectProperty_hasLinkTemplate = ontModel.getObjectProperty(URI_hasLinkTemplate);
		objectProperty_isLinkTemplateOf = ontModel.getObjectProperty(URI_isLinkTemplateOf);
		objectProperty_hasAttributeTemplate = ontModel.getObjectProperty(URI_hasAttributeTemplate);
		objectProperty_isAttributeTemplateOf = ontModel.getObjectProperty(URI_isAttributeTemplateOf);
		objectProperty_hasAttributeTransformation = ontModel.getObjectProperty(URI_hasAttributeTransformation);
		objectProperty_isAttributeTransformationOf = ontModel.getObjectProperty(URI_isAttributeTransformationOf);
		objectProperty_hasLinkTransformation = ontModel.getObjectProperty(URI_hasLinkTransformation);
		objectProperty_isLinkTransformationOf = ontModel.getObjectProperty(URI_isLinkTransformationOf);
		objectProperty_hasTransformation = ontModel.getObjectProperty(URI_hasTransformation);
		objectProperty_isTransformationOf = ontModel.getObjectProperty(URI_isTransformationOf);

		dataProperty_name = ontModel.getDatatypeProperty(URI_name);
		dataProperty_number = ontModel.getDatatypeProperty(URI_number);
		dataProperty_text = ontModel.getDatatypeProperty(URI_text);
		dataProperty_objectId = ontModel.getDatatypeProperty(URI_objectId);
		dataProperty_objectId1 = ontModel.getDatatypeProperty(URI_objectId1);
		dataProperty_objectId2 = ontModel.getDatatypeProperty(URI_objectId2);
		dataProperty_value = ontModel.getDatatypeProperty(URI_value);
		dataProperty_oldValue = ontModel.getDatatypeProperty(URI_oldValue);
		dataProperty_newValue = ontModel.getDatatypeProperty(URI_newValue);
	}

	@Override
	public OWLSchema<SystemTransformations> getOWLSchema() {
		return new SystemTransformationsOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
	}

	public Individual newIndividual_Action() {
		return class_Action.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_AttributeTemplate() {
		return class_AttributeTemplate.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_AttributeTransformation() {
		return class_AttributeTransformation.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Line() {
		return class_Line.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_LinkTemplate() {
		return class_LinkTemplate.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_LinkTransformation() {
		return class_LinkTransformation.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_ObjectTemplate() {
		return class_ObjectTemplate.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_ParameterUpdater() {
		return class_ParameterUpdater.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_PreConditionChecker() {
		return class_PreConditionChecker.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_SystemTemplate() {
		return class_SystemTemplate.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_SystemTransformation() {
		return class_SystemTransformation.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_SystemTransformations() {
		return class_SystemTransformations.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Transformation() {
		return class_Transformation.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Transformations() {
		return class_Transformations.createIndividual(getUniqueURI());
	}
}
