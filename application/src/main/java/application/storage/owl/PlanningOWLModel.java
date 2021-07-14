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

public class PlanningOWLModel implements OWLModel {

	static final String NS = "https://github.com/kinnder/process-engineering/planning";

	static final String URI_Attribute = NS + "#Attribute";

	static final String URI_Edge = NS + "#Edge";

	static final String URI_Link = NS + "#Link";

	static final String URI_NodeNetwork = NS + "#NodeNetwork";

	static final String URI_Node = NS + "#Node";

	static final String URI_Parameter = NS + "#Parameter";

	static final String URI_Process = NS + "#Process";

	static final String URI_System = NS + "#System";

	static final String URI_SystemObject = NS + "#SystemObject";

	static final String URI_SystemOperation = NS + "#SystemOperation";

	static final String URI_hasAttribute = NS + "#hasAttribute";

	static final String URI_hasEdge = NS + "#hasEdge";

	static final String URI_hasLink = NS + "#hasLink";

	static final String URI_hasNode = NS + "#hasNode";

	static final String URI_hasParameter = NS + "#hasParameter";

	static final String URI_hasSystem = NS + "#hasSystem";

	static final String URI_hasSystemObject = NS + "#hasSystemObject";

	static final String URI_hasSystemOperation = NS + "#hasSystemOperation";

	static final String URI_hasBeginNode = NS + "#hasBeginNode";

	static final String URI_isBeginNodeOf = NS + "#isBeginNodeOf";

	static final String URI_isAttributeOf = NS + "#isAttributeOf";

	static final String URI_hasEndNode = NS + "#hasEndNode";

	static final String URI_isEndNodeOf = NS + "#isEndNodeOf";

	static final String URI_isEdgeOf = NS + "#isEdgeOf";

	static final String URI_isLinkOf = NS + "#isLinkOf";

	static final String URI_isNodeOf = NS + "#isNodeOf";

	static final String URI_isParameterOf = NS + "#isParameterOf";

	static final String URI_isSystemObjectOf = NS + "#isSystemObjectOf";

	static final String URI_isSystemOf = NS + "#isSystemOf";

	static final String URI_isSystemOperationOf = NS + "#isSystemOperation";

	static final String URI_beginNodeId = NS + "#beginNodeId";

	static final String URI_checked = NS + "#checked";

	static final String URI_endNodeId = NS + "#endNodeId";

	static final String URI_id = NS + "#id";

	static final String URI_key = NS + "#key";

	static final String URI_name = NS + "#name";

	static final String URI_objectId1 = NS + "#objectId1";

	static final String URI_objectId2 = NS + "#objectId2";

	static final String URI_value = NS + "#value";

	static final String URI_hasSystemObject1 = NS + "#hasSystemObject1";

	static final String URI_isSystemObject1Of = NS + "#isSystemObject1Of";

	static final String URI_hasSystemObject2 = NS + "#hasSystemObject2";

	static final String URI_isSystemObject2Of = NS + "#isSystemObject2Of";

	static final String URI_isLineOf = NS + "#isLineOf";

	static final String URI_hasLine = NS + "#hasLine";

	static final String URI_isParameterUpdaterOf = NS + "#isParameterUpdaterOf";

	static final String URI_hasParameterUpdater = NS + "#hasParameterUpdater";

	static final String URI_isPreConditionCheckerOf = NS + "#isPreConditionCheckerOf";

	static final String URI_hasPreConditionChecker = NS + "#hasPreConditionChecker";

	static final String URI_isLinkTransformationOf = NS + "#isLinkTransformationOf";

	static final String URI_hasLinkTransformation = NS + "#hasLinkTransformation";

	static final String URI_isAttributeTransformationOf = NS + "#isAttributeTransformationOf";

	static final String URI_hasAttributeTransformation = NS + "#hasAttributeTransformation";

	static final String URI_hasTransformation = NS + "#hasTransformation";

	static final String URI_isTransformationOf = NS + "#isTransformationOf";

	static final String URI_isAttributeTemplateOf = NS + "#isAttributeTemplateOf";

	static final String URI_hasAttributeTemplate = NS + "#hasAttributeTemplate";

	static final String URI_isLinkTemplateOf = NS + "#isLinkTemplateOf";

	static final String URI_hasLinkTemplate = NS + "#hasLinkTemplate";

	static final String URI_isObjectTemplateOf = NS + "#isObjectTemplateOf";

	static final String URI_hasObjectTemplate = NS + "#hasObjectTemplate";

	static final String URI_isObjectTemplate1Of = NS + "#isObjectTemplate1Of";

	static final String URI_hasObjectTemplate1 = NS + "#hasObjectTemplate1";

	static final String URI_isObjectTemplate2Of = NS + "#isObjectTemplate2Of";

	static final String URI_hasObjectTemplate2 = NS + "#hasObjectTemplate2";

	static final String URI_isActionOf = NS + "#isActionOf";

	static final String URI_hasAction = NS + "#hasAction";

	static final String URI_areTransformationsOf = NS + "#areTransformationsOf";

	static final String URI_hasTransformations = NS + "#hasTransformations";

	static final String URI_isSystemTemplateOf = NS + "#isSystemTemplateOf";

	static final String URI_hasSystemTemplate = NS + "#hasSystemTemplate";

	static final String URI_isSystemTransformationOf = NS + "#isSystemTransformationOf";

	static final String URI_hasSystemTransformation = NS + "#hasSystemTransformation";

	static final String URI_Line = NS + "#Line";

	static final String URI_ActionFunction = NS + "#ActionFunction";

	static final String URI_LinkTransformation = NS + "#LinkTransformation";

	static final String URI_AttributeTransformation = NS + "#AttributeTransformation";

	static final String URI_Transformation = NS + "#Transformation";

	static final String URI_AttributeTemplate = NS + "#AttributeTemplate";

	static final String URI_LinkTemplate = NS + "#LinkTemplate";

	static final String URI_ObjectTemplate = NS + "#ObjectTemplate";

	static final String URI_SystemTransformations = NS + "#SystemTransformations";

	static final String URI_SystemTransformation = NS + "#SystemTransformation";

	static final String URI_SystemTemplate = NS + "#SystemTemplate";

	static final String URI_Transformations = NS + "#Transformations";

	static final String URI_Action = NS + "#Action";

	static final String URI_objectId = NS + "#objectId";

	static final String URI_oldValue = NS + "#oldValue";

	static final String URI_newValue = NS + "#newValue";

	static final String URI_text = NS + "#text";

	static final String URI_number = NS + "#number";

	static final String URI_TaskDescription = NS + "#TaskDescription";

	static final String URI_InitialSystem = NS + "#InitialSystem";

	static final String URI_FinalSystem = NS + "#FinalSystem";

	static final String URI_hasInitialSystem = NS + "#hasInitialSystem";

	static final String URI_isInitialSystemOf = NS + "#isInitialSystemOf";

	static final String URI_hasFinalSystem = NS + "#hasFinalSystem";

	static final String URI_isFinalSystemOf = NS + "#isFinalSystemOf";

	// TODO (2020-12-17 #31): убрать linkTemplate из схемы objectTemplate

	private void makeHierarchy(OntClass superClass, OntClass subClass) {
		superClass.addSubClass(subClass);
		subClass.addSuperClass(superClass);
	}

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	private void makeDisjoint(OntClass class1, OntClass class2) {
		class1.addDisjointWith(class2);
		class2.addDisjointWith(class1);
	}

	private DataRange createDataRange(RDFNode... members) {
		DataRange d = m.createOntResource(DataRange.class, m.getProfile().DATARANGE(), null);
		d.addProperty(m.getProfile().UNION_OF(), m.createList(members));
		return d;
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
	}

	@Override
	public void createOntologyModel() {
		m = createOntologyModelBase();

		m.createOntology(NS);

		createClass_Attribute();
		createClass_Edge();
		createClass_Link();
		createClass_Node();
		createClass_NodeNetwork();
		createClass_Parameter();
		createClass_Process();
		createClass_System();
		createClass_SystemObject();
		createClass_SystemOperation();
		createClass_SystemTransformations();
		createClass_SystemTransformation();
		createClass_SystemTemplate();
		createClass_Transformations();
		createClass_Action();
		createClass_ObjectTemplate();
		createClass_LinkTemplate();
		createClass_AttributeTemplate();
		createClass_AttributeTransformation();
		createClass_LinkTransformation();
		createClass_Transformation();
		createClass_ActionFunction();
		createClass_Line();
		createClass_TaskDescription();
		createClass_InitialSystem();
		createClass_FinalSystem();

		makeHierarchy(class_System, class_InitialSystem);
		makeHierarchy(class_System, class_FinalSystem);

		makeDisjoint(class_SystemOperation, class_Parameter);

		createObjectProperty_hasAttribute();
		createObjectProperty_isAttributeOf();
		makeInverse(objectProperty_hasAttribute, objectProperty_isAttributeOf);

		createObjectProperty_hasEdge();
		createObjectProperty_isEdgeOf();
		makeInverse(objectProperty_hasEdge, objectProperty_isEdgeOf);

		createObjectProperty_hasLink();
		createObjectProperty_isLinkOf();
		makeInverse(objectProperty_hasLink, objectProperty_isLinkOf);

		createObjectProperty_hasNode();
		createObjectProperty_isNodeOf();
		makeInverse(objectProperty_hasNode, objectProperty_isNodeOf);

		createObjectProperty_hasParameter();
		createObjectProperty_isParameterOf();
		makeInverse(objectProperty_hasParameter, objectProperty_isParameterOf);

		createObjectProperty_hasSystem();
		createObjectProperty_isSystemOf();
		makeInverse(objectProperty_hasSystem, objectProperty_isSystemOf);

		createObjectProperty_hasSystemObject();
		createObjectProperty_isSystemObjectOf();
		makeInverse(objectProperty_hasSystemObject, objectProperty_isSystemObjectOf);

		createObjectProperty_hasSystemObject1();
		createObjectProperty_isSystemObject1Of();
		makeInverse(objectProperty_hasSystemObject1, objectProperty_isSystemObject1Of);

		createObjectProperty_hasSystemObject2();
		createObjectProperty_isSystemObject2Of();
		makeInverse(objectProperty_hasSystemObject2, objectProperty_isSystemObject2Of);

		createObjectProperty_hasSystemOperation();
		createObjectProperty_isSystemOperationOf();
		makeInverse(objectProperty_hasSystemOperation, objectProperty_isSystemOperationOf);

		createObjectProperty_hasBeginNode();
		createObjectProperty_isBeginNodeOf();
		makeInverse(objectProperty_hasBeginNode, objectProperty_isBeginNodeOf);

		createObjectProperty_hasEndNode();
		createObjectProperty_isEndNodeOf();
		makeInverse(objectProperty_hasEndNode, objectProperty_isEndNodeOf);

		createObjectProperty_hasSystemTransformation();
		createObjectProperty_isSystemTransformationOf();
		makeInverse(objectProperty_hasSystemTransformation, objectProperty_isSystemTransformationOf);

		createObjectProperty_hasSystemTemplate();
		createObjectProperty_isSystemTemplateOf();
		makeInverse(objectProperty_hasSystemTemplate, objectProperty_isSystemTemplateOf);

		createObjectProperty_hasTransformations();
		createObjectProperty_areTransformationsOf();
		makeInverse(objectProperty_hasTransformations, objectProperty_areTransformationsOf);

		createObjectProperty_hasAction();
		createObjectProperty_isActionOf();
		makeInverse(objectProperty_hasAction, objectProperty_isActionOf);

		createObjectProperty_hasObjectTemplate();
		createObjectProperty_isObjectTemplateOf();
		makeInverse(objectProperty_hasObjectTemplate, objectProperty_isObjectTemplateOf);

		createObjectProperty_hasObjectTemplate1();
		createObjectProperty_isObjectTemplate1Of();
		makeInverse(objectProperty_hasObjectTemplate1, objectProperty_isObjectTemplate1Of);

		createObjectProperty_hasObjectTemplate2();
		createObjectProperty_isObjectTemplate2Of();
		makeInverse(objectProperty_hasObjectTemplate2, objectProperty_isObjectTemplate2Of);

		createObjectProperty_hasLinkTemplate();
		createObjectProperty_isLinkTemplateOf();
		makeInverse(objectProperty_hasLinkTemplate, objectProperty_isLinkTemplateOf);

		createObjectProperty_hasAttributeTemplate();
		createObjectTemplate_isAttributeTemplateOf();
		makeInverse(objectProperty_hasAttributeTemplate, objectProperty_isAttributeTemplateOf);

		createObjectProperty_hasAttributeTransformation();
		createObjectProperty_isAttributeTransformationOf();
		makeInverse(objectProperty_hasAttributeTransformation, objectProperty_isAttributeTransformationOf);

		createObjectProperty_hasLinkTransformation();
		createObjectProperty_isLinkTransformationOf();
		makeInverse(objectProperty_hasLinkTransformation, objectProperty_isLinkTransformationOf);

		createObjectProperty_hasTransformation();
		createObjectProperty_isTransformationOf();
		makeInverse(objectProperty_hasTransformation, objectProperty_isTransformationOf);

		createObjectProperty_hasPreConditionChecker();
		createObjectProperty_isPreConditionCheckerOf();
		makeInverse(objectProperty_hasPreConditionChecker, objectProperty_isPreConditionCheckerOf);

		createObjectProperty_hasParameterUpdater();
		createObjectProperty_isParameterUpdaterOf();
		makeInverse(objectProperty_hasParameterUpdater, objectProperty_isParameterUpdaterOf);

		createObjectProperty_hasLine();
		createObjectProperty_isLineOf();
		makeInverse(objectProperty_hasLine, objectProperty_isLineOf);

		createObjectProperty_hasInitialSystem();
		createObjectProperty_isInitialSystemOf();
		makeInverse(objectProperty_hasInitialSystem, objectProperty_isInitialSystemOf);

		createObjectProperty_hasFinalSystem();
		createObjectProperty_isFinalSystemOf();
		makeInverse(objectProperty_hasFinalSystem, objectProperty_isFinalSystemOf);

		createDataProperty_beginNodeId();
		createDataProperty_checked();
		createDataProperty_endNodeId();
		createDataProperty_id();
		createDataProperty_key();
		createDataProperty_name();
		createDataProperty_objectId1();
		createDataProperty_objectId2();
		createDataProperty_value();
		createDataProperty_objectId();
		createDataProperty_oldValue();
		createDataProperty_newValue();
		createDataProperty_number();
		createDataProperty_text();
	}

	private void createObjectProperty_isFinalSystemOf() {
		objectProperty_isFinalSystemOf = m.createObjectProperty(URI_isFinalSystemOf);
		objectProperty_isFinalSystemOf.addLabel("is final system of", "en");
		objectProperty_isFinalSystemOf.addLabel("является конечной системой для", "ru");
		objectProperty_isFinalSystemOf.addDomain(class_FinalSystem);
		objectProperty_isFinalSystemOf.addRange(class_TaskDescription);
	}

	private void createObjectProperty_hasFinalSystem() {
		objectProperty_hasFinalSystem = m.createObjectProperty(URI_hasFinalSystem);
		objectProperty_hasFinalSystem.addLabel("has final system", "en");
		objectProperty_hasFinalSystem.addLabel("имеет конечную систему", "ru");
		objectProperty_hasFinalSystem.addDomain(class_TaskDescription);
		objectProperty_hasFinalSystem.addRange(class_FinalSystem);
	}

	private void createObjectProperty_isInitialSystemOf() {
		objectProperty_isInitialSystemOf = m.createObjectProperty(URI_isInitialSystemOf);
		objectProperty_isInitialSystemOf.addLabel("is initial system of", "en");
		objectProperty_isInitialSystemOf.addLabel("является начальной системой для", "ru");
		objectProperty_isInitialSystemOf.addDomain(class_InitialSystem);
		objectProperty_isInitialSystemOf.addRange(class_TaskDescription);
	}

	private void createObjectProperty_hasInitialSystem() {
		objectProperty_hasInitialSystem = m.createObjectProperty(URI_hasInitialSystem);
		objectProperty_hasInitialSystem.addLabel("has initial system", "en");
		objectProperty_hasInitialSystem.addLabel("имеет начальную систему", "ru");
		objectProperty_hasInitialSystem.addDomain(class_TaskDescription);
		objectProperty_hasInitialSystem.addRange(class_InitialSystem);
	}

	private void createClass_FinalSystem() {
		class_FinalSystem = m.createClass(URI_FinalSystem);
		class_FinalSystem.addLabel("Final system", "en");
		class_FinalSystem.addLabel("Конечная система", "ru");
	}

	private void createClass_InitialSystem() {
		class_InitialSystem = m.createClass(URI_InitialSystem);
		class_InitialSystem.addLabel("Initial system", "en");
		class_InitialSystem.addLabel("Начальная система", "ru");
	}

	private void createClass_TaskDescription() {
		class_TaskDescription = m.createClass(URI_TaskDescription);
		class_TaskDescription.addLabel("Task Description", "en");
		class_TaskDescription.addLabel("Описание задания", "ru");
	}

	private void createDataProperty_text() {
		dataProperty_text = m.createDatatypeProperty(URI_text);
		dataProperty_text.addLabel("text", "en");
		dataProperty_text.addLabel("текст", "ru");
		dataProperty_text.addDomain(class_Line);
		dataProperty_text.addRange(XSD.xstring);
	}

	private void createDataProperty_number() {
		dataProperty_number = m.createDatatypeProperty(URI_number);
		dataProperty_number.addLabel("number", "en");
		dataProperty_number.addLabel("номер", "ru");
		dataProperty_number.addDomain(class_Line);
		dataProperty_number.addRange(XSD.integer);
	}

	private void createDataProperty_newValue() {
		dataProperty_newValue = m.createDatatypeProperty(URI_newValue);
		dataProperty_newValue.addLabel("new value", "en");
		dataProperty_newValue.addLabel("новое значение", "ru");
		dataProperty_newValue.addDomain(class_LinkTransformation);
		dataProperty_newValue.addRange(XSD.xstring);
	}

	private void createDataProperty_oldValue() {
		dataProperty_oldValue = m.createDatatypeProperty(URI_oldValue);
		dataProperty_oldValue.addLabel("old value", "en");
		dataProperty_oldValue.addLabel("старое значение", "ru");
		dataProperty_oldValue.addDomain(class_LinkTransformation);
		dataProperty_oldValue.addRange(XSD.xstring);
	}

	private void createDataProperty_objectId() {
		dataProperty_objectId = m.createDatatypeProperty(URI_objectId);
		dataProperty_objectId.addLabel("objectId", "en");
		dataProperty_objectId.addLabel("идентификатор объекта", "ru");
		dataProperty_objectId.addDomain(class_AttributeTransformation);
		dataProperty_objectId.addDomain(class_LinkTransformation);
		dataProperty_objectId.addDomain(class_Transformation);
		dataProperty_objectId.addDomain(class_ObjectTemplate);
		dataProperty_objectId.addRange(XSD.xstring);
	}

	private void createObjectProperty_isLineOf() {
		objectProperty_isLineOf = m.createObjectProperty(URI_isLineOf);
		objectProperty_isLineOf.addLabel("is line of", "en");
		objectProperty_isLineOf.addLabel("является линией для", "ru");
		objectProperty_isLineOf.addDomain(class_Line);
		objectProperty_isLineOf.addRange(class_ActionFunction);
	}

	private void createObjectProperty_hasLine() {
		objectProperty_hasLine = m.createObjectProperty(URI_hasLine);
		objectProperty_hasLine.addLabel("has line", "en");
		objectProperty_hasLine.addLabel("имеет линию", "ru");
		objectProperty_hasLine.addDomain(class_ActionFunction);
		objectProperty_hasLine.addRange(class_Line);
	}

	private void createObjectProperty_isParameterUpdaterOf() {
		objectProperty_isParameterUpdaterOf = m.createObjectProperty(URI_isParameterUpdaterOf);
		objectProperty_isParameterUpdaterOf.addLabel("is parameter updater of", "en");
		objectProperty_isParameterUpdaterOf.addLabel("является функцией обновления для", "ru");
		objectProperty_isParameterUpdaterOf.addDomain(class_ActionFunction);
		objectProperty_isParameterUpdaterOf.addRange(class_Action);
	}

	private void createObjectProperty_hasParameterUpdater() {
		objectProperty_hasParameterUpdater = m.createObjectProperty(URI_hasParameterUpdater);
		objectProperty_hasParameterUpdater.addLabel("has parameter updater", "en");
		objectProperty_hasParameterUpdater.addLabel("имеет функцию обновления", "ru");
		objectProperty_hasParameterUpdater.addDomain(class_Action);
		objectProperty_hasParameterUpdater.addRange(class_ActionFunction);
	}

	private void createObjectProperty_isPreConditionCheckerOf() {
		objectProperty_isPreConditionCheckerOf = m.createObjectProperty(URI_isPreConditionCheckerOf);
		objectProperty_isPreConditionCheckerOf.addLabel("is precondition checker of", "en");
		objectProperty_isPreConditionCheckerOf.addLabel("является проверкой условий для", "ru");
		objectProperty_isPreConditionCheckerOf.addDomain(class_ActionFunction);
		objectProperty_isPreConditionCheckerOf.addRange(class_Action);
	}

	private void createObjectProperty_hasPreConditionChecker() {
		objectProperty_hasPreConditionChecker = m.createObjectProperty(URI_hasPreConditionChecker);
		objectProperty_hasPreConditionChecker.addLabel("has precondition checker", "en");
		objectProperty_hasPreConditionChecker.addLabel("имеет проверку условий", "ru");
		objectProperty_hasPreConditionChecker.addDomain(class_Action);
		objectProperty_hasPreConditionChecker.addRange(class_ActionFunction);
	}

	private void createObjectProperty_isTransformationOf() {
		objectProperty_isTransformationOf = m.createObjectProperty(URI_isTransformationOf);
		objectProperty_isTransformationOf.addLabel("is transformation of", "en");
		objectProperty_isTransformationOf.addLabel("является трансформацией для", "ru");
		objectProperty_isTransformationOf.addDomain(class_Transformation);
		objectProperty_isTransformationOf.addRange(class_Transformations);
	}

	private void createObjectProperty_hasTransformation() {
		objectProperty_hasTransformation = m.createObjectProperty(URI_hasTransformation);
		objectProperty_hasTransformation.addLabel("has transformation", "en");
		objectProperty_hasTransformation.addLabel("имеет трансформацию", "ru");
		objectProperty_hasTransformation.addDomain(class_Transformations);
		objectProperty_hasTransformation.addRange(class_Transformation);
	}

	private void createObjectProperty_isLinkTransformationOf() {
		objectProperty_isLinkTransformationOf = m.createObjectProperty(URI_isLinkTransformationOf);
		objectProperty_isLinkTransformationOf.addLabel("is link transformation of", "en");
		objectProperty_isLinkTransformationOf.addLabel("является трасформацией связи для", "ru");
		objectProperty_isLinkTransformationOf.addDomain(class_LinkTransformation);
		objectProperty_isLinkTransformationOf.addRange(class_Transformations);
	}

	private void createObjectProperty_hasLinkTransformation() {
		objectProperty_hasLinkTransformation = m.createObjectProperty(URI_hasLinkTransformation);
		objectProperty_hasLinkTransformation.addLabel("has link transformation", "en");
		objectProperty_hasLinkTransformation.addLabel("имеет трансформацию связи", "ru");
		objectProperty_hasLinkTransformation.addDomain(class_Transformations);
		objectProperty_hasLinkTransformation.addRange(class_LinkTransformation);
	}

	private void createObjectProperty_isAttributeTransformationOf() {
		objectProperty_isAttributeTransformationOf = m.createObjectProperty(URI_isAttributeTransformationOf);
		objectProperty_isAttributeTransformationOf.addLabel("is attribute transformation of", "en");
		objectProperty_isAttributeTransformationOf.addLabel("является трансформацией атрибута для", "ru");
		objectProperty_isAttributeTransformationOf.addDomain(class_AttributeTransformation);
		objectProperty_isAttributeTransformationOf.addRange(class_Transformations);
	}

	private void createObjectProperty_hasAttributeTransformation() {
		objectProperty_hasAttributeTransformation = m.createObjectProperty(URI_hasAttributeTransformation);
		objectProperty_hasAttributeTransformation.addLabel("has attribute transformation", "en");
		objectProperty_hasAttributeTransformation.addLabel("имеет трансформацию атрибута", "ru");
		objectProperty_hasAttributeTransformation.addDomain(class_Transformations);
		objectProperty_hasAttributeTransformation.addRange(class_AttributeTransformation);
	}

	private void createObjectTemplate_isAttributeTemplateOf() {
		objectProperty_isAttributeTemplateOf = m.createObjectProperty(URI_isAttributeTemplateOf);
		objectProperty_isAttributeTemplateOf.addLabel("is attribute template of", "en");
		objectProperty_isAttributeTemplateOf.addLabel("является шаблоном атрибута для", "ru");
		objectProperty_isAttributeTemplateOf.addDomain(class_AttributeTemplate);
		objectProperty_isAttributeTemplateOf.addRange(class_ObjectTemplate);
	}

	private void createObjectProperty_hasAttributeTemplate() {
		objectProperty_hasAttributeTemplate = m.createObjectProperty(URI_hasAttributeTemplate);
		objectProperty_hasAttributeTemplate.addLabel("has attribute template", "en");
		objectProperty_hasAttributeTemplate.addLabel("имеет шаблон атрибута", "ru");
		objectProperty_hasAttributeTemplate.addDomain(class_ObjectTemplate);
		objectProperty_hasAttributeTemplate.addRange(class_AttributeTemplate);
	}

	private void createObjectProperty_isLinkTemplateOf() {
		objectProperty_isLinkTemplateOf = m.createObjectProperty(URI_isLinkTemplateOf);
		objectProperty_isLinkTemplateOf.addLabel("is link template of", "en");
		objectProperty_isLinkTemplateOf.addLabel("является шаблоном связи для", "ru");
		objectProperty_isLinkTemplateOf.addDomain(class_LinkTemplate);
		objectProperty_isLinkTemplateOf.addRange(class_SystemTemplate);
	}

	private void createObjectProperty_hasLinkTemplate() {
		objectProperty_hasLinkTemplate = m.createObjectProperty(URI_hasLinkTemplate);
		objectProperty_hasLinkTemplate.addLabel("has link template", "en");
		objectProperty_hasLinkTemplate.addLabel("имеет шаблон связи", "ru");
		objectProperty_hasLinkTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasLinkTemplate.addRange(class_LinkTemplate);
	}

	private void createObjectProperty_isObjectTemplate2Of() {
		objectProperty_isObjectTemplate2Of = m.createObjectProperty(URI_isObjectTemplate2Of);
		objectProperty_isObjectTemplate2Of.addLabel("is object template 2 of", "en");
		objectProperty_isObjectTemplate2Of.addLabel("является шаблоном объекта 2 для", "ru");
		objectProperty_isObjectTemplate2Of.addDomain(class_ObjectTemplate);
		objectProperty_isObjectTemplate2Of.addRange(class_LinkTemplate);
	}

	private void createObjectProperty_hasObjectTemplate2() {
		objectProperty_hasObjectTemplate2 = m.createObjectProperty(URI_hasObjectTemplate2);
		objectProperty_hasObjectTemplate2.addLabel("has object template 2", "en");
		objectProperty_hasObjectTemplate2.addLabel("имеет шаблон объекта 2", "ru");
		objectProperty_hasObjectTemplate2.addDomain(class_LinkTemplate);
		objectProperty_hasObjectTemplate2.addRange(class_ObjectTemplate);
	}

	private void createObjectProperty_isObjectTemplate1Of() {
		objectProperty_isObjectTemplate1Of = m.createObjectProperty(URI_isObjectTemplate1Of);
		objectProperty_isObjectTemplate1Of.addLabel("is object template 1 of", "en");
		objectProperty_isObjectTemplate1Of.addLabel("является шаблоном объекта 1 для", "ru");
		objectProperty_isObjectTemplate1Of.addDomain(class_ObjectTemplate);
		objectProperty_isObjectTemplate1Of.addRange(class_LinkTemplate);
	}

	private void createObjectProperty_hasObjectTemplate1() {
		objectProperty_hasObjectTemplate1 = m.createObjectProperty(URI_hasObjectTemplate1);
		objectProperty_hasObjectTemplate1.addLabel("has object template 1", "en");
		objectProperty_hasObjectTemplate1.addLabel("имеет шаблон объекта 1", "ru");
		objectProperty_hasObjectTemplate1.addDomain(class_LinkTemplate);
		objectProperty_hasObjectTemplate1.addRange(class_ObjectTemplate);
	}

	private void createObjectProperty_isObjectTemplateOf() {
		objectProperty_isObjectTemplateOf = m.createObjectProperty(URI_isObjectTemplateOf);
		objectProperty_isObjectTemplateOf.addLabel("is object template of", "en");
		objectProperty_isObjectTemplateOf.addLabel("является шаблоном объекта для", "ru");
		objectProperty_isObjectTemplateOf.addDomain(class_ObjectTemplate);
		objectProperty_isObjectTemplateOf.addRange(class_SystemTemplate);
	}

	private void createObjectProperty_hasObjectTemplate() {
		objectProperty_hasObjectTemplate = m.createObjectProperty(URI_hasObjectTemplate);
		objectProperty_hasObjectTemplate.addLabel("has object template", "en");
		objectProperty_hasObjectTemplate.addLabel("имеет шаблон объекта", "ru");
		objectProperty_hasObjectTemplate.addDomain(class_SystemTemplate);
		objectProperty_hasObjectTemplate.addRange(class_ObjectTemplate);
	}

	private void createObjectProperty_isActionOf() {
		objectProperty_isActionOf = m.createObjectProperty(URI_isActionOf);
		objectProperty_isActionOf.addLabel("is action of", "en");
		objectProperty_isActionOf.addLabel("является действием для", "ru");
		objectProperty_isActionOf.addDomain(class_Action);
		objectProperty_isActionOf.addRange(class_SystemTransformation);
	}

	private void createObjectProperty_hasAction() {
		objectProperty_hasAction = m.createObjectProperty(URI_hasAction);
		objectProperty_hasAction.addLabel("has action", "en");
		objectProperty_hasAction.addLabel("имеет действие", "ru");
		objectProperty_hasAction.addDomain(class_SystemTransformation);
		objectProperty_hasAction.addRange(class_Action);
	}

	private void createObjectProperty_areTransformationsOf() {
		objectProperty_areTransformationsOf = m.createObjectProperty(URI_areTransformationsOf);
		objectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		objectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		objectProperty_areTransformationsOf.addDomain(class_Transformations);
		objectProperty_areTransformationsOf.addRange(class_SystemTransformation);
	}

	private void createObjectProperty_hasTransformations() {
		objectProperty_hasTransformations = m.createObjectProperty(URI_hasTransformations);
		objectProperty_hasTransformations.addLabel("has transformations", "en");
		objectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		objectProperty_hasTransformations.addDomain(class_SystemTransformation);
		objectProperty_hasTransformations.addRange(class_Transformations);
	}

	private void createObjectProperty_isSystemTemplateOf() {
		objectProperty_isSystemTemplateOf = m.createObjectProperty(URI_isSystemTemplateOf);
		objectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		objectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		objectProperty_isSystemTemplateOf.addDomain(class_SystemTemplate);
		objectProperty_isSystemTemplateOf.addRange(class_SystemTransformation);
	}

	private void createObjectProperty_hasSystemTemplate() {
		objectProperty_hasSystemTemplate = m.createObjectProperty(URI_hasSystemTemplate);
		objectProperty_hasSystemTemplate.addLabel("has system template", "en");
		objectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		objectProperty_hasSystemTemplate.addDomain(class_SystemTransformation);
		objectProperty_hasSystemTemplate.addRange(class_SystemTemplate);
	}

	private void createObjectProperty_isSystemTransformationOf() {
		objectProperty_isSystemTransformationOf = m.createObjectProperty(URI_isSystemTransformationOf);
		objectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		objectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		objectProperty_isSystemTransformationOf.addDomain(class_SystemTransformation);
		objectProperty_isSystemTransformationOf.addRange(class_SystemTransformations);
	}

	private void createObjectProperty_hasSystemTransformation() {
		objectProperty_hasSystemTransformation = m.createObjectProperty(URI_hasSystemTransformation);
		objectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		objectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		objectProperty_hasSystemTransformation.addDomain(class_SystemTransformations);
		objectProperty_hasSystemTransformation.addRange(class_SystemTransformation);
	}

	private void createClass_Line() {
		class_Line = m.createClass(URI_Line);
		class_Line.addLabel("Line", "en");
		class_Line.addLabel("Линия", "ru");
	}

	private void createClass_ActionFunction() {
		class_ActionFunction = m.createClass(URI_ActionFunction);
		class_ActionFunction.addLabel("Action Function", "en");
		class_ActionFunction.addLabel("Функция действия", "ru");
	}

	private void createClass_Transformation() {
		class_Transformation = m.createClass(URI_Transformation);
		class_Transformation.addLabel("Transformation", "en");
		class_Transformation.addLabel("Трансформация", "ru");
	}

	private void createClass_LinkTransformation() {
		class_LinkTransformation = m.createClass(URI_LinkTransformation);
		class_LinkTransformation.addLabel("Link Transformation", "en");
		class_LinkTransformation.addLabel("Трансформация связи", "ru");
	}

	private void createClass_AttributeTransformation() {
		class_AttributeTransformation = m.createClass(URI_AttributeTransformation);
		class_AttributeTransformation.addLabel("Attribute Transformation", "en");
		class_AttributeTransformation.addLabel("Трансформация атрибута", "ru");
	}

	private void createClass_AttributeTemplate() {
		class_AttributeTemplate = m.createClass(URI_AttributeTemplate);
		class_AttributeTemplate.addLabel("Attribute Template", "en");
		class_AttributeTemplate.addLabel("Шаблон атрибута", "ru");
	}

	private void createClass_LinkTemplate() {
		class_LinkTemplate = m.createClass(URI_LinkTemplate);
		class_LinkTemplate.addLabel("Link Template", "en");
		class_LinkTemplate.addLabel("Шаблон связи", "ru");
	}

	private void createClass_ObjectTemplate() {
		class_ObjectTemplate = m.createClass(URI_ObjectTemplate);
		class_ObjectTemplate.addLabel("Object Template", "en");
		class_ObjectTemplate.addLabel("Шаблон объекта", "ru");
	}

	private void createClass_Action() {
		class_Action = m.createClass(URI_Action);
		class_Action.addLabel("Action", "en");
		class_Action.addLabel("Действие", "ru");
	}

	private void createClass_Transformations() {
		class_Transformations = m.createClass(URI_Transformations);
		class_Transformations.addLabel("Transformations", "en");
		class_Transformations.addLabel("Трансформации", "ru");
	}

	private void createClass_SystemTemplate() {
		class_SystemTemplate = m.createClass(URI_SystemTemplate);
		class_SystemTemplate.addLabel("System Template", "en");
		class_SystemTemplate.addLabel("Шаблон системы", "ru");
	}

	private void createClass_SystemTransformation() {
		class_SystemTransformation = m.createClass(URI_SystemTransformation);
		class_SystemTransformation.addLabel("System Transformation", "en");
		class_SystemTransformation.addLabel("Трансформация системы", "ru");
	}

	private void createClass_SystemTransformations() {
		class_SystemTransformations = m.createClass(URI_SystemTransformations);
		class_SystemTransformations.addLabel("System Transformations", "en");
		class_SystemTransformations.addLabel("Трансформации системы", "ru");
	}

	private void createDataProperty_value() {
		dataProperty_value = m.createDatatypeProperty(URI_value);
		dataProperty_value.addLabel("value", "en");
		dataProperty_value.addLabel("значение", "ru");
		dataProperty_value.addDomain(class_Attribute);
		dataProperty_value.addDomain(class_AttributeTemplate);
		dataProperty_value.addDomain(class_AttributeTransformation);
		dataProperty_value.addDomain(class_Parameter);
		dataProperty_value.addRange(createDataRange(XSD.xstring, XSD.xboolean, XSD.xint));
	}

	private void createDataProperty_objectId2() {
		dataProperty_objectId2 = m.createDatatypeProperty(URI_objectId2);
		dataProperty_objectId2.addLabel("objectId2", "en");
		dataProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		dataProperty_objectId2.addDomain(class_Link);
		dataProperty_objectId2.addDomain(class_LinkTemplate);
		dataProperty_objectId2.addRange(XSD.xstring);
	}

	private void createDataProperty_objectId1() {
		dataProperty_objectId1 = m.createDatatypeProperty(URI_objectId1);
		dataProperty_objectId1.addLabel("objectId1", "en");
		dataProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		dataProperty_objectId1.addDomain(class_Link);
		dataProperty_objectId1.addDomain(class_LinkTemplate);
		dataProperty_objectId1.addRange(XSD.xstring);
	}

	private void createDataProperty_name() {
		dataProperty_name = m.createDatatypeProperty(URI_name);
		dataProperty_name.addLabel("name", "en");
		dataProperty_name.addLabel("название", "ru");
		dataProperty_name.addDomain(class_Action);
		dataProperty_name.addDomain(class_Attribute);
		dataProperty_name.addDomain(class_AttributeTemplate);
		dataProperty_name.addDomain(class_AttributeTransformation);
		dataProperty_name.addDomain(class_Link);
		dataProperty_name.addDomain(class_LinkTemplate);
		dataProperty_name.addDomain(class_LinkTransformation);
		dataProperty_name.addDomain(class_SystemObject);
		dataProperty_name.addDomain(class_SystemOperation);
		dataProperty_name.addDomain(class_SystemTransformation);
		dataProperty_name.addRange(XSD.xstring);
	}

	private void createDataProperty_key() {
		dataProperty_key = m.createDatatypeProperty(URI_key);
		dataProperty_key.addLabel("key", "en");
		dataProperty_key.addLabel("ключ", "ru");
		dataProperty_key.addDomain(class_Parameter);
		dataProperty_key.addRange(XSD.xstring);
	}

	private void createDataProperty_id() {
		dataProperty_id = m.createDatatypeProperty(URI_id);
		dataProperty_id.addLabel("id", "en");
		dataProperty_id.addLabel("идентификатор", "ru");
		dataProperty_id.addDomain(class_Edge);
		dataProperty_id.addDomain(class_Node);
		dataProperty_id.addDomain(class_SystemObject);
		dataProperty_id.addRange(XSD.xstring);
	}

	private void createDataProperty_endNodeId() {
		dataProperty_endNodeId = m.createDatatypeProperty(URI_endNodeId);
		dataProperty_endNodeId.addLabel("end node id", "en");
		dataProperty_endNodeId.addLabel("индентификатор конечного узла", "ru");
		dataProperty_endNodeId.addDomain(class_Edge);
		dataProperty_endNodeId.addRange(XSD.xstring);
	}

	private void createDataProperty_checked() {
		dataProperty_checked = m.createDatatypeProperty(URI_checked);
		dataProperty_checked.addLabel("checked", "en");
		dataProperty_checked.addLabel("просмотрен", "ru");
		dataProperty_checked.addDomain(class_Node);
		dataProperty_checked.addRange(XSD.xboolean);
	}

	private void createDataProperty_beginNodeId() {
		dataProperty_beginNodeId = m.createDatatypeProperty(URI_beginNodeId);
		dataProperty_beginNodeId.addLabel("begin node id", "en");
		dataProperty_beginNodeId.addLabel("идентификатор начального узла", "ru");
		dataProperty_beginNodeId.addDomain(class_Edge);
		dataProperty_beginNodeId.addRange(XSD.xstring);
	}

	private void createObjectProperty_isEndNodeOf() {
		objectProperty_isEndNodeOf = m.createObjectProperty(URI_isEndNodeOf);
		objectProperty_isEndNodeOf.addLabel("is end node of", "en");
		objectProperty_isEndNodeOf.addLabel("является конечным узлом для", "ru");
		objectProperty_isEndNodeOf.addDomain(class_Node);
		objectProperty_isEndNodeOf.addRange(class_Edge);
	}

	private void createObjectProperty_hasEndNode() {
		objectProperty_hasEndNode = m.createObjectProperty(URI_hasEndNode);
		objectProperty_hasEndNode.addLabel("has end node", "en");
		objectProperty_hasEndNode.addLabel("имеет конечный узел", "ru");
		objectProperty_hasEndNode.addDomain(class_Edge);
		objectProperty_hasEndNode.addRange(class_Node);
	}

	private void createObjectProperty_isBeginNodeOf() {
		objectProperty_isBeginNodeOf = m.createObjectProperty(URI_isBeginNodeOf);
		objectProperty_isBeginNodeOf.addLabel("is begin node of", "en");
		objectProperty_isBeginNodeOf.addLabel("является начальным узлом для", "ru");
		objectProperty_isBeginNodeOf.addDomain(class_Node);
		objectProperty_isBeginNodeOf.addRange(class_Edge);
	}

	private void createObjectProperty_hasBeginNode() {
		objectProperty_hasBeginNode = m.createObjectProperty(URI_hasBeginNode);
		objectProperty_hasBeginNode.addLabel("has begin node", "en");
		objectProperty_hasBeginNode.addLabel("имеет начальный узел", "ru");
		objectProperty_hasBeginNode.addDomain(class_Edge);
		objectProperty_hasBeginNode.addRange(class_Node);
	}

	private void createObjectProperty_isSystemOperationOf() {
		objectProperty_isSystemOperationOf = m.createObjectProperty(URI_isSystemOperationOf);
		objectProperty_isSystemOperationOf.addLabel("is system operation of", "en");
		objectProperty_isSystemOperationOf.addLabel("является операцией системы для", "ru");
		objectProperty_isSystemOperationOf.addDomain(class_SystemOperation);
		objectProperty_isSystemOperationOf.addRange(class_Edge);
		objectProperty_isSystemOperationOf.addRange(class_Process);
	}

	private void createObjectProperty_hasSystemOperation() {
		objectProperty_hasSystemOperation = m.createObjectProperty(URI_hasSystemOperation);
		objectProperty_hasSystemOperation.addLabel("has system operation", "en");
		objectProperty_hasSystemOperation.addLabel("имеет операцию системы", "ru");
		objectProperty_hasSystemOperation.addDomain(class_Edge);
		objectProperty_hasSystemOperation.addDomain(class_Process);
		objectProperty_hasSystemOperation.addRange(class_SystemOperation);
	}

	private void createObjectProperty_isSystemObject2Of() {
		objectProperty_isSystemObject2Of = m.createObjectProperty(URI_isSystemObject2Of);
		objectProperty_isSystemObject2Of.addLabel("is system object 2 of", "en");
		objectProperty_isSystemObject2Of.addLabel("является объектом системы 2 для", "ru");
		objectProperty_isSystemObject2Of.addDomain(class_SystemObject);
		objectProperty_isSystemObject2Of.addRange(class_Link);
	}

	private void createObjectProperty_hasSystemObject2() {
		objectProperty_hasSystemObject2 = m.createObjectProperty(URI_hasSystemObject2);
		objectProperty_hasSystemObject2.addLabel("has system object 2", "en");
		objectProperty_hasSystemObject2.addLabel("имеет объет системы 2", "ru");
		objectProperty_hasSystemObject2.addDomain(class_Link);
		objectProperty_hasSystemObject2.addRange(class_SystemObject);
	}

	private void createObjectProperty_isSystemObject1Of() {
		objectProperty_isSystemObject1Of = m.createObjectProperty(URI_isSystemObject1Of);
		objectProperty_isSystemObject1Of.addLabel("is system object 1 of", "en");
		objectProperty_isSystemObject1Of.addLabel("является объектом системы 1 для", "ru");
		objectProperty_isSystemObject1Of.addDomain(class_SystemObject);
		objectProperty_isSystemObject1Of.addRange(class_Link);
	}

	private void createObjectProperty_hasSystemObject1() {
		objectProperty_hasSystemObject1 = m.createObjectProperty(URI_hasSystemObject1);
		objectProperty_hasSystemObject1.addLabel("has system object 1", "en");
		objectProperty_hasSystemObject1.addLabel("имеет объет системы 1", "ru");
		objectProperty_hasSystemObject1.addDomain(class_Link);
		objectProperty_hasSystemObject1.addRange(class_SystemObject);
	}

	private void createObjectProperty_isSystemObjectOf() {
		objectProperty_isSystemObjectOf = m.createObjectProperty(URI_isSystemObjectOf);
		objectProperty_isSystemObjectOf.addLabel("is system object of", "en");
		objectProperty_isSystemObjectOf.addLabel("является объектом системы для", "ru");
		objectProperty_isSystemObjectOf.addDomain(class_SystemObject);
		objectProperty_isSystemObjectOf.addRange(class_System);
	}

	private void createObjectProperty_hasSystemObject() {
		objectProperty_hasSystemObject = m.createObjectProperty(URI_hasSystemObject);
		objectProperty_hasSystemObject.addLabel("has system object", "en");
		objectProperty_hasSystemObject.addLabel("имеет объект системы", "ru");
		objectProperty_hasSystemObject.addDomain(class_System);
		objectProperty_hasSystemObject.addRange(class_SystemObject);
	}

	private void createObjectProperty_isSystemOf() {
		objectProperty_isSystemOf = m.createObjectProperty(URI_isSystemOf);
		objectProperty_isSystemOf.addLabel("is system of", "en");
		objectProperty_isSystemOf.addLabel("является системой для", "ru");
		objectProperty_isSystemOf.addDomain(class_System);
		objectProperty_isSystemOf.addRange(class_Node);
	}

	private void createObjectProperty_hasSystem() {
		objectProperty_hasSystem = m.createObjectProperty(URI_hasSystem);
		objectProperty_hasSystem.addLabel("has system", "en");
		objectProperty_hasSystem.addLabel("имеет систему", "ru");
		objectProperty_hasSystem.addDomain(class_Node);
		objectProperty_hasSystem.addRange(class_System);
	}

	private void createObjectProperty_isParameterOf() {
		objectProperty_isParameterOf = m.createObjectProperty(URI_isParameterOf);
		objectProperty_isParameterOf.addLabel("is parameter of", "en");
		objectProperty_isParameterOf.addLabel("является параметром для", "ru");
		objectProperty_isParameterOf.addDomain(class_Parameter);
		objectProperty_isParameterOf.addRange(class_SystemOperation);
	}

	private void createObjectProperty_hasParameter() {
		objectProperty_hasParameter = m.createObjectProperty(URI_hasParameter);
		objectProperty_hasParameter.addLabel("has parameter", "en");
		objectProperty_hasParameter.addLabel("имеет параметр", "ru");
		objectProperty_hasParameter.addDomain(class_SystemOperation);
		objectProperty_hasParameter.addRange(class_Parameter);
	}

	private void createObjectProperty_isNodeOf() {
		objectProperty_isNodeOf = m.createObjectProperty(URI_isNodeOf);
		objectProperty_isNodeOf.addLabel("is node of", "en");
		objectProperty_isNodeOf.addLabel("является узлом для", "ru");
		objectProperty_isNodeOf.addDomain(class_Node);
		objectProperty_isNodeOf.addRange(class_NodeNetwork);
	}

	private void createObjectProperty_hasNode() {
		objectProperty_hasNode = m.createObjectProperty(URI_hasNode);
		objectProperty_hasNode.addLabel("has node", "en");
		objectProperty_hasNode.addLabel("имеет узел", "ru");
		objectProperty_hasNode.addDomain(class_NodeNetwork);
		objectProperty_hasNode.addRange(class_Node);
	}

	private void createObjectProperty_isLinkOf() {
		objectProperty_isLinkOf = m.createObjectProperty(URI_isLinkOf);
		objectProperty_isLinkOf.addLabel("is link of", "en");
		objectProperty_isLinkOf.addLabel("является связью для", "ru");
		objectProperty_isLinkOf.addDomain(class_Link);
		objectProperty_isLinkOf.addRange(class_System);
	}

	private void createObjectProperty_hasLink() {
		objectProperty_hasLink = m.createObjectProperty(URI_hasLink);
		objectProperty_hasLink.addLabel("has link", "en");
		objectProperty_hasLink.addLabel("имеет связь", "ru");
		objectProperty_hasLink.addDomain(class_System);
		objectProperty_hasLink.addRange(class_Link);
	}

	private void createObjectProperty_isEdgeOf() {
		objectProperty_isEdgeOf = m.createObjectProperty(URI_isEdgeOf);
		objectProperty_isEdgeOf.addLabel("is edge of", "en");
		objectProperty_isEdgeOf.addLabel("является ребром для", "ru");
		objectProperty_isEdgeOf.addDomain(class_Edge);
		objectProperty_isEdgeOf.addRange(class_NodeNetwork);
	}

	private void createObjectProperty_hasEdge() {
		objectProperty_hasEdge = m.createObjectProperty(URI_hasEdge);
		objectProperty_hasEdge.addLabel("has edge", "en");
		objectProperty_hasEdge.addLabel("имеет ребро", "ru");
		objectProperty_hasEdge.addDomain(class_NodeNetwork);
		objectProperty_hasEdge.addRange(class_Edge);
	}

	private void createObjectProperty_isAttributeOf() {
		objectProperty_isAttributeOf = m.createObjectProperty(URI_isAttributeOf);
		objectProperty_isAttributeOf.addLabel("is attribute of", "en");
		objectProperty_isAttributeOf.addLabel("является атрибутом для", "ru");
		objectProperty_isAttributeOf.addDomain(class_Attribute);
		objectProperty_isAttributeOf.addRange(class_SystemObject);
	}

	private void createObjectProperty_hasAttribute() {
		objectProperty_hasAttribute = m.createObjectProperty(URI_hasAttribute);
		objectProperty_hasAttribute.addLabel("has attribute", "en");
		objectProperty_hasAttribute.addLabel("имеет атрибут", "ru");
		objectProperty_hasAttribute.addDomain(class_SystemObject);
		objectProperty_hasAttribute.addRange(class_Attribute);
	}

	private void createClass_SystemOperation() {
		class_SystemOperation = m.createClass(URI_SystemOperation);
		class_SystemOperation.addLabel("System Operation", "en");
		class_SystemOperation.addLabel("Операция системы", "ru");
	}

	private void createClass_SystemObject() {
		class_SystemObject = m.createClass(URI_SystemObject);
		class_SystemObject.addLabel("System object", "en");
		class_SystemObject.addLabel("Объект системы", "ru");
	}

	private void createClass_System() {
		class_System = m.createClass(URI_System);
		class_System.addLabel("System", "en");
		class_System.addLabel("Система", "ru");
	}

	private void createClass_Process() {
		class_Process = m.createClass(URI_Process);
		class_Process.addLabel("Process", "en");
		class_Process.addLabel("Процесс", "ru");
	}

	private void createClass_Parameter() {
		class_Parameter = m.createClass(URI_Parameter);
		class_Parameter.addLabel("Parameter", "en");
		class_Parameter.addLabel("Параметр", "ru");
	}

	private void createClass_NodeNetwork() {
		class_NodeNetwork = m.createClass(URI_NodeNetwork);
		class_NodeNetwork.addLabel("Node Network", "en");
		class_NodeNetwork.addLabel("Сеть узлов", "ru");
	}

	private void createClass_Node() {
		class_Node = m.createClass(URI_Node);
		class_Node.addLabel("Node", "en");
		class_Node.addLabel("Узел", "ru");
	}

	private void createClass_Link() {
		class_Link = m.createClass(URI_Link);
		class_Link.addLabel("Link", "en");
		class_Link.addLabel("Связь", "ru");
	}

	private void createClass_Edge() {
		class_Edge = m.createClass(URI_Edge);
		class_Edge.addLabel("Edge", "en");
		class_Edge.addLabel("Ребро", "ru");
	}

	private void createClass_Attribute() {
		class_Attribute = m.createClass(URI_Attribute);
		class_Attribute.addLabel("Attribute", "en");
		class_Attribute.addLabel("Атрибут", "ru");
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		m = ontModel;

		class_Action = m.getOntClass(URI_Action);
		class_ActionFunction = m.getOntClass(URI_ActionFunction);
		class_Attribute = m.getOntClass(URI_Attribute);
		class_AttributeTemplate = m.getOntClass(URI_AttributeTemplate);
		class_AttributeTransformation = m.getOntClass(URI_AttributeTransformation);
		class_Edge = m.getOntClass(URI_Edge);
		class_FinalSystem = m.getOntClass(URI_FinalSystem);
		class_InitialSystem = m.getOntClass(URI_InitialSystem);
		class_Line = m.getOntClass(URI_Line);
		class_Link = m.getOntClass(URI_Link);
		class_LinkTemplate = m.getOntClass(URI_LinkTemplate);
		class_LinkTransformation = m.getOntClass(URI_LinkTransformation);
		class_Node = m.getOntClass(URI_Node);
		class_NodeNetwork = m.getOntClass(URI_NodeNetwork);
		class_ObjectTemplate = m.getOntClass(URI_ObjectTemplate);
		class_Parameter = m.getOntClass(URI_Parameter);
		class_Process = m.getOntClass(URI_Process);
		class_System = m.getOntClass(URI_System);
		class_SystemObject = m.getOntClass(URI_SystemObject);
		class_SystemOperation = m.getOntClass(URI_SystemOperation);
		class_SystemTemplate = m.getOntClass(URI_SystemTemplate);
		class_SystemTransformation = m.getOntClass(URI_SystemTransformation);
		class_SystemTransformations = m.getOntClass(URI_SystemTransformations);
		class_TaskDescription = m.getOntClass(URI_TaskDescription);
		class_Transformation = m.getOntClass(URI_Transformation);
		class_Transformations = m.getOntClass(URI_Transformations);

		objectProperty_areTransformationsOf = m.getObjectProperty(URI_areTransformationsOf);
		objectProperty_hasAction = m.getObjectProperty(URI_hasAction);
		objectProperty_hasAttribute = m.getObjectProperty(URI_hasAttribute);
		objectProperty_hasAttributeTemplate = m.getObjectProperty(URI_hasAttributeTemplate);
		objectProperty_hasAttributeTransformation = m.getObjectProperty(URI_hasAttributeTransformation);
		objectProperty_hasBeginNode = m.getObjectProperty(URI_hasBeginNode);
		objectProperty_hasEdge = m.getObjectProperty(URI_hasEdge);
		objectProperty_hasEndNode = m.getObjectProperty(URI_hasEndNode);
		objectProperty_hasFinalSystem = m.getObjectProperty(URI_hasFinalSystem);
		objectProperty_hasInitialSystem = m.getObjectProperty(URI_hasInitialSystem);
		objectProperty_hasLine = m.getObjectProperty(URI_hasLine);
		objectProperty_hasLink = m.getObjectProperty(URI_hasLink);
		objectProperty_hasLinkTemplate = m.getObjectProperty(URI_hasLinkTemplate);
		objectProperty_hasLinkTransformation = m.getObjectProperty(URI_hasLinkTransformation);
		objectProperty_hasNode = m.getObjectProperty(URI_hasNode);
		objectProperty_hasObjectTemplate = m.getObjectProperty(URI_hasObjectTemplate);
		objectProperty_hasObjectTemplate1 = m.getObjectProperty(URI_hasObjectTemplate1);
		objectProperty_hasObjectTemplate2 = m.getObjectProperty(URI_hasObjectTemplate2);
		objectProperty_hasParameter = m.getObjectProperty(URI_hasParameter);
		objectProperty_hasParameterUpdater = m.getObjectProperty(URI_hasParameterUpdater);
		objectProperty_hasPreConditionChecker = m.getObjectProperty(URI_hasPreConditionChecker);
		objectProperty_hasSystem = m.getObjectProperty(URI_hasSystem);
		objectProperty_hasSystemObject = m.getObjectProperty(URI_hasSystemObject);
		objectProperty_hasSystemObject1 = m.getObjectProperty(URI_hasSystemObject1);
		objectProperty_hasSystemObject2 = m.getObjectProperty(URI_hasSystemObject2);
		objectProperty_hasSystemOperation = m.getObjectProperty(URI_hasSystemOperation);
		objectProperty_hasSystemTemplate = m.getObjectProperty(URI_hasSystemTemplate);
		objectProperty_hasSystemTransformation = m.getObjectProperty(URI_hasSystemTransformation);
		objectProperty_hasTransformation = m.getObjectProperty(URI_hasTransformation);
		objectProperty_hasTransformations = m.getObjectProperty(URI_hasTransformations);
		objectProperty_isActionOf = m.getObjectProperty(URI_isActionOf);
		objectProperty_isAttributeOf = m.getObjectProperty(URI_isAttributeOf);
		objectProperty_isAttributeTemplateOf = m.getObjectProperty(URI_isAttributeTemplateOf);
		objectProperty_isAttributeTransformationOf = m.getObjectProperty(URI_isAttributeTransformationOf);
		objectProperty_isBeginNodeOf = m.getObjectProperty(URI_isBeginNodeOf);
		objectProperty_isEdgeOf = m.getObjectProperty(URI_isEdgeOf);
		objectProperty_isEndNodeOf = m.getObjectProperty(URI_isEndNodeOf);
		objectProperty_isFinalSystemOf = m.getObjectProperty(URI_isFinalSystemOf);
		objectProperty_isInitialSystemOf = m.getObjectProperty(URI_isInitialSystemOf);
		objectProperty_isLineOf = m.getObjectProperty(URI_isLineOf);
		objectProperty_isLinkOf = m.getObjectProperty(URI_isLinkOf);
		objectProperty_isLinkTemplateOf = m.getObjectProperty(URI_isLinkTemplateOf);
		objectProperty_isLinkTransformationOf = m.getObjectProperty(URI_isLinkTransformationOf);
		objectProperty_isNodeOf = m.getObjectProperty(URI_isNodeOf);
		objectProperty_isObjectTemplateOf = m.getObjectProperty(URI_isObjectTemplateOf);
		objectProperty_isObjectTemplate1Of = m.getObjectProperty(URI_isObjectTemplate1Of);
		objectProperty_isObjectTemplate2Of = m.getObjectProperty(URI_isObjectTemplate2Of);
		objectProperty_isParameterOf = m.getObjectProperty(URI_isParameterOf);
		objectProperty_isParameterUpdaterOf = m.getObjectProperty(URI_isParameterUpdaterOf);
		objectProperty_isPreConditionCheckerOf = m.getObjectProperty(URI_isPreConditionCheckerOf);
		objectProperty_isSystemObject1Of = m.getObjectProperty(URI_isSystemObject1Of);
		objectProperty_isSystemObject2Of = m.getObjectProperty(URI_isSystemObject2Of);
		objectProperty_isSystemObjectOf = m.getObjectProperty(URI_isSystemObjectOf);
		objectProperty_isSystemOf = m.getObjectProperty(URI_isSystemOf);
		objectProperty_isSystemOperationOf = m.getObjectProperty(URI_isSystemOperationOf);
		objectProperty_isSystemTemplateOf = m.getObjectProperty(URI_isSystemTemplateOf);
		objectProperty_isSystemTransformationOf = m.getObjectProperty(URI_isSystemTransformationOf);
		objectProperty_isTransformationOf = m.getObjectProperty(URI_isTransformationOf);

		dataProperty_beginNodeId = m.getDatatypeProperty(URI_beginNodeId);
		dataProperty_checked = m.getDatatypeProperty(URI_checked);
		dataProperty_endNodeId = m.getDatatypeProperty(URI_endNodeId);
		dataProperty_id = m.getDatatypeProperty(URI_id);
		dataProperty_key = m.getDatatypeProperty(URI_key);
		dataProperty_name = m.getDatatypeProperty(URI_name);
		dataProperty_newValue = m.getDatatypeProperty(URI_newValue);
		dataProperty_number = m.getDatatypeProperty(URI_number);
		dataProperty_objectId = m.getDatatypeProperty(URI_objectId);
		dataProperty_objectId1 = m.getDatatypeProperty(URI_objectId1);
		dataProperty_objectId2 = m.getDatatypeProperty(URI_objectId2);
		dataProperty_oldValue = m.getDatatypeProperty(URI_oldValue);
		dataProperty_text = m.getDatatypeProperty(URI_text);
		dataProperty_value = m.getDatatypeProperty(URI_value);
	}

	private OntModel m;

	@Override
	public OntModel getOntologyModel() {
		return m;
	}

	@Override
	public String getUniqueURI() {
		return NS + "#" + UUID.randomUUID().toString();
	}

	private OntClass class_NodeNetwork;

	public OntClass getClass_NodeNetwork() {
		return class_NodeNetwork;
	}

	private ObjectProperty objectProperty_hasNode;

	public ObjectProperty getObjectProperty_hasNode() {
		return objectProperty_hasNode;
	}

	private ObjectProperty objectProperty_isNodeOf;

	public ObjectProperty getObjectProperty_isNodeOf() {
		return objectProperty_isNodeOf;
	}

	private ObjectProperty objectProperty_hasEdge;

	public ObjectProperty getObjectProperty_hasEdge() {
		return objectProperty_hasEdge;
	}

	private ObjectProperty objectProperty_isEdgeOf;

	public ObjectProperty getObjectProperty_isEdgeOf() {
		return objectProperty_isEdgeOf;
	}

	private OntClass class_Node;

	public OntClass getClass_Node() {
		return class_Node;
	}

	private OntClass class_Edge;

	public OntClass getClass_Edge() {
		return class_Edge;
	}

	private DatatypeProperty dataProperty_id;

	public DatatypeProperty getDataProperty_id() {
		return dataProperty_id;
	}

	private DatatypeProperty dataProperty_checked;

	public DatatypeProperty getDataProperty_checked() {
		return dataProperty_checked;
	}

	private ObjectProperty objectProperty_hasSystem;

	public ObjectProperty getObjectProperty_hasSystem() {
		return objectProperty_hasSystem;
	}

	private ObjectProperty objectProperty_isSystemOf;

	public ObjectProperty getObjectProperty_isSystemOf() {
		return objectProperty_isSystemOf;
	}

	private OntClass class_System;

	public OntClass getClass_System() {
		return class_System;
	}

	private DatatypeProperty dataProperty_beginNodeId;

	public DatatypeProperty getDataProperty_beginNodeId() {
		return dataProperty_beginNodeId;
	}

	private DatatypeProperty dataProperty_endNodeId;

	public DatatypeProperty getDataProperty_endNodeId() {
		return dataProperty_endNodeId;
	}

	private ObjectProperty objectProperty_hasSystemOperation;

	public ObjectProperty getObjectProperty_hasSystemOperation() {
		return objectProperty_hasSystemOperation;
	}

	private ObjectProperty objectProperty_isSystemOperationOf;

	public ObjectProperty getObjectProperty_isSystemOperationOf() {
		return objectProperty_isSystemOperationOf;
	}

	private OntClass class_SystemOperation;

	public OntClass getClass_SystemOperation() {
		return class_SystemOperation;
	}

	private OntClass class_Parameter;

	public OntClass getClass_Parameter() {
		return class_Parameter;
	}

	private OntClass class_Process;

	private DatatypeProperty dataProperty_key;

	public DatatypeProperty getDataProperty_key() {
		return dataProperty_key;
	}

	private ObjectProperty objectProperty_hasParameter;

	public ObjectProperty getObjectProperty_hasParameter() {
		return objectProperty_hasParameter;
	}

	private ObjectProperty objectProperty_isParameterOf;

	public ObjectProperty getObjectProperty_isParameterOf() {
		return objectProperty_isParameterOf;
	}

	private ObjectProperty objectProperty_isSystemObjectOf;

	public ObjectProperty getObjectProperty_isSystemObjectOf() {
		return objectProperty_isSystemObjectOf;
	}

	private ObjectProperty objectProperty_hasSystemObject;

	public ObjectProperty getObjectProperty_hasSystemObject() {
		return objectProperty_hasSystemObject;
	}

	private ObjectProperty objectProperty_isLinkOf;

	public ObjectProperty getObjectProperty_isLinkOf() {
		return objectProperty_isLinkOf;
	}

	private ObjectProperty objectProperty_hasLink;

	public ObjectProperty getObjectProperty_hasLink() {
		return objectProperty_hasLink;
	}

	private OntClass class_SystemObject;

	public OntClass getClass_SystemObject() {
		return class_SystemObject;
	}

	private OntClass class_Link;

	public OntClass getClass_Link() {
		return class_Link;
	}

	private ObjectProperty objectProperty_isAttributeOf;

	public ObjectProperty getObjectProperty_isAttributeOf() {
		return objectProperty_isAttributeOf;
	}

	private ObjectProperty objectProperty_hasAttribute;

	public ObjectProperty getObjectProperty_hasAttribute() {
		return objectProperty_hasAttribute;
	}

	private OntClass class_Attribute;

	public OntClass getClass_Attribute() {
		return class_Attribute;
	}

	public OntClass getClass_Process() {
		return class_Process;
	}

	public Individual newIndividual_System() {
		return class_System.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_SystemObject() {
		return class_SystemObject.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Link() {
		return class_Link.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Attribute() {
		return class_Attribute.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Edge() {
		return class_Edge.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Node() {
		return class_Node.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_NodeNetwork() {
		return class_NodeNetwork.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Parameter() {
		return class_Parameter.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_Process() {
		return class_Process.createIndividual(getUniqueURI());
	}

	public Individual newIndividual_SystemOperation() {
		return class_SystemOperation.createIndividual(getUniqueURI());
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

	public Individual newIndividual_ActionFunction() {
		return class_ActionFunction.createIndividual(getUniqueURI());
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

	private ObjectProperty objectProperty_hasBeginNode;

	public ObjectProperty getObjectProperty_hasBeginNode() {
		return objectProperty_hasBeginNode;
	}

	private ObjectProperty objectProperty_isBeginNodeOf;

	public ObjectProperty getObjectProperty_isBeginNodeOf() {
		return objectProperty_isBeginNodeOf;
	}

	private ObjectProperty objectProperty_hasEndNode;

	public ObjectProperty getObjectProperty_hasEndNode() {
		return objectProperty_hasEndNode;
	}

	private ObjectProperty objectProperty_isEndNodeOf;

	public ObjectProperty getObjectProperty_isEndNodeOf() {
		return objectProperty_isEndNodeOf;
	}

	private ObjectProperty objectProperty_hasSystemObject1;

	public ObjectProperty getObjectProperty_hasSystemObject1() {
		return objectProperty_hasSystemObject1;
	}

	private ObjectProperty objectProperty_isSystemObject1Of;

	public ObjectProperty getObjectProperty_isSystemObject1Of() {
		return objectProperty_isSystemObject1Of;
	}

	private ObjectProperty objectProperty_hasSystemObject2;

	public ObjectProperty getObjectProperty_hasSystemObject2() {
		return objectProperty_hasSystemObject2;
	}

	private ObjectProperty objectProperty_isSystemObject2Of;

	public ObjectProperty getObjectProperty_isSystemObject2Of() {
		return objectProperty_isSystemObject2Of;
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

	private OntClass class_ActionFunction;

	public OntClass getClass_ActionFunction() {
		return class_ActionFunction;
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

	private ObjectProperty objectProperty_hasObjectTemplate1;

	public ObjectProperty getObjectProperty_hasObjectTemplate1() {
		return objectProperty_hasObjectTemplate1;
	}

	private ObjectProperty objectProperty_isObjectTemplate1Of;

	public ObjectProperty getObjectProperty_isObjectTemplate1Of() {
		return objectProperty_isObjectTemplate1Of;
	}

	private ObjectProperty objectProperty_hasObjectTemplate2;

	public ObjectProperty getObjectProperty_hasObjectTemplate2() {
		return objectProperty_hasObjectTemplate2;
	}

	private ObjectProperty objectProperty_isObjectTemplate2Of;

	public ObjectProperty getObjectProperty_isObjectTemplate2Of() {
		return objectProperty_isObjectTemplate2Of;
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

	private OntClass class_TaskDescription;

	public OntClass getClass_TaskDescription() {
		return class_TaskDescription;
	}

	public Individual newIndividual_TaskDescription() {
		return class_TaskDescription.createIndividual(getUniqueURI());
	}

	private OntClass class_InitialSystem;

	public OntClass getClass_InitialSystem() {
		return class_InitialSystem;
	}

	private OntClass class_FinalSystem;

	public OntClass getClass_FinalSystem() {
		return class_FinalSystem;
	}

	private ObjectProperty objectProperty_hasInitialSystem;

	public ObjectProperty getObjectProperty_hasInitialSystem() {
		return objectProperty_hasInitialSystem;
	}

	private ObjectProperty objectProperty_isInitialSystemOf;

	public ObjectProperty getObjectProperty_isInitialSystemOf() {
		return objectProperty_isInitialSystemOf;
	}

	private ObjectProperty objectProperty_hasFinalSystem;

	public ObjectProperty getObjectProperty_hasFinalSystem() {
		return objectProperty_hasFinalSystem;
	}

	private ObjectProperty objectProperty_isFinalSystemOf;

	public ObjectProperty getObjectProperty_isFinalSystemOf() {
		return objectProperty_isFinalSystemOf;
	}
}
