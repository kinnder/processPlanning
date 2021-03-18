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

import planning.method.TaskDescription;

public class TaskDescriptionOWLModel implements OWLModel<TaskDescription>, OWLModelCommonPart {

	static final String NS = "https://github.com/kinnder/process-engineering/planning/task-description#";

	static final String URI_TaskDescription = NS + "TaskDescription";

	static final String URI_System = NS + "System";

	static final String URI_InitialSystem = NS + "InitialSystem";

	static final String URI_FinalSystem = NS + "FinalSystem";

	static final String URI_SystemObject = NS + "SystemObject";

	static final String URI_Link = NS + "Link";

	static final String URI_Attribute = NS + "Attribute";

	static final String URI_hasInitialSystem = NS + "hasInitialSystem";

	static final String URI_isInitialSystemOf = NS + "isInitialSystemOf";

	static final String URI_hasFinalSystem = NS + "hasFinalSystem";

	static final String URI_isFinalSystemOf = NS + "isFinalSystemOf";

	static final String URI_hasSystemObject = NS + "hasSystemObject";

	static final String URI_isSystemObjectOf = NS + "isSystemObjectOf";

	static final String URI_hasLink = NS + "hasLink";

	static final String URI_isLinkOf = NS + "isLinkOf";

	static final String URI_hasAttribute = NS + "hasAttribute";

	static final String URI_isAttributeOf = NS + "isAttributeOf";

	static final String URI_name = NS + "name";

	static final String URI_id = NS + "id";

	static final String URI_value = NS + "value";

	static final String URI_objectId1 = NS + "objectId1";

	static final String URI_objectId2 = NS + "objectId2";

	private OntModel m;

	@Override
	public OntModel getOntologyModel() {
		return m;
	}

	private OntClass class_TaskDescription;

	public OntClass getClass_TaskDescription() {
		return class_TaskDescription;
	}

	public Individual newIndividual_TaskDescription() {
		return class_TaskDescription.createIndividual(getUniqueURI());
	}

	private OntClass class_System;

	@Override
	public OntClass getClass_System() {
		return class_System;
	}

	@Override
	public Individual newIndividual_System() {
		return class_System.createIndividual(getUniqueURI());
	}

	private OntClass class_InitialSystem;

	public OntClass getClass_InitialSystem() {
		return class_InitialSystem;
	}

	private OntClass class_FinalSystem;

	public OntClass getClass_FinalSystem() {
		return class_FinalSystem;
	}

	private OntClass class_SystemObject;

	@Override
	public OntClass getClass_SystemObject() {
		return class_SystemObject;
	}

	@Override
	public Individual newIndividual_SystemObject() {
		return class_SystemObject.createIndividual(getUniqueURI());
	}

	private OntClass class_Link;

	@Override
	public OntClass getClass_Link() {
		return class_Link;
	}

	@Override
	public Individual newIndividual_Link() {
		return class_Link.createIndividual(getUniqueURI());
	}

	private OntClass class_Attribute;

	@Override
	public OntClass getClass_Attribute() {
		return class_Attribute;
	}

	@Override
	public Individual newIndividual_Attribute() {
		return class_Attribute.createIndividual(getUniqueURI());
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

	private ObjectProperty objectProperty_hasSystemObject;

	@Override
	public ObjectProperty getObjectProperty_hasSystemObject() {
		return objectProperty_hasSystemObject;
	}

	private ObjectProperty objectProperty_isSystemObjectOf;

	@Override
	public ObjectProperty getObjectProperty_isSystemObjectOf() {
		return objectProperty_isSystemObjectOf;
	}

	private ObjectProperty objectProperty_hasLink;

	@Override
	public ObjectProperty getObjectProperty_hasLink() {
		return objectProperty_hasLink;
	}

	private ObjectProperty objectProperty_isLinkOf;

	@Override
	public ObjectProperty getObjectProperty_isLinkOf() {
		return objectProperty_isLinkOf;
	}

	private ObjectProperty objectProperty_hasAttribute;

	@Override
	public ObjectProperty getObjectProperty_hasAttribute() {
		return objectProperty_hasAttribute;
	}

	private ObjectProperty objectProperty_isAttributeOf;

	@Override
	public ObjectProperty getObjectProperty_isAttributeOf() {
		return objectProperty_isAttributeOf;
	}

	private DatatypeProperty dataProperty_name;

	@Override
	public DatatypeProperty getDataProperty_name() {
		return dataProperty_name;
	}

	private DatatypeProperty dataProperty_id;

	@Override
	public DatatypeProperty getDataProperty_id() {
		return dataProperty_id;
	}

	private DatatypeProperty dataProperty_value;

	@Override
	public DatatypeProperty getDataProperty_value() {
		return dataProperty_value;
	}

	private DatatypeProperty dataProperty_objectId1;

	@Override
	public DatatypeProperty getDataProperty_objectId1() {
		return dataProperty_objectId1;
	}

	private DatatypeProperty dataProperty_objectId2;

	@Override
	public DatatypeProperty getDataProperty_objectId2() {
		return dataProperty_objectId2;
	}

	private void makeHierarchy(OntClass superClass, OntClass subClass) {
		superClass.addSubClass(subClass);
		subClass.addSuperClass(superClass);
	}

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	private DataRange createDataRange(RDFNode... members) {
		DataRange d = m.createOntResource(DataRange.class, m.getProfile().DATARANGE(), null);
		d.addProperty(m.getProfile().UNION_OF(), m.createList(members));
		return d;
	}

	@Override
	public void createOntologyModel() {
		m = createOntologyModelBase();

		class_TaskDescription = m.createClass(URI_TaskDescription);
		class_TaskDescription.addLabel("Task Description", "en");
		class_TaskDescription.addLabel("Описание задания", "ru");

		class_System = m.createClass(URI_System);
		class_System.addLabel("System", "en");
		class_System.addLabel("Система", "ru");

		class_InitialSystem = m.createClass(URI_InitialSystem);
		class_InitialSystem.addLabel("Initial system", "en");
		class_InitialSystem.addLabel("Начальная система", "ru");

		makeHierarchy(class_System, class_InitialSystem);

		class_FinalSystem = m.createClass(URI_FinalSystem);
		class_FinalSystem.addLabel("Final system", "en");
		class_FinalSystem.addLabel("Конечная система", "ru");

		makeHierarchy(class_System, class_FinalSystem);

		class_SystemObject = m.createClass(URI_SystemObject);
		class_SystemObject.addLabel("System object", "en");
		class_SystemObject.addLabel("Объект системы", "ru");

		class_Link = m.createClass(URI_Link);
		class_Link.addLabel("Link", "en");
		class_Link.addLabel("Связь", "ru");

		class_Attribute = m.createClass(URI_Attribute);
		class_Attribute.addLabel("Attribute", "en");
		class_Attribute.addLabel("Аттрибут", "ru");

		objectProperty_hasInitialSystem = m.createObjectProperty(URI_hasInitialSystem);
		objectProperty_hasInitialSystem.addLabel("has initial system", "en");
		objectProperty_hasInitialSystem.addLabel("имеет начальную систему", "ru");
		objectProperty_hasInitialSystem.addDomain(class_TaskDescription);
		objectProperty_hasInitialSystem.addRange(class_InitialSystem);

		objectProperty_isInitialSystemOf = m.createObjectProperty(URI_isInitialSystemOf);
		objectProperty_isInitialSystemOf.addLabel("is initial system of", "en");
		objectProperty_isInitialSystemOf.addLabel("является начальной системой для", "ru");
		objectProperty_isInitialSystemOf.addDomain(class_InitialSystem);
		objectProperty_isInitialSystemOf.addRange(class_TaskDescription);

		makeInverse(objectProperty_hasInitialSystem, objectProperty_isInitialSystemOf);

		objectProperty_hasFinalSystem = m.createObjectProperty(URI_hasFinalSystem);
		objectProperty_hasFinalSystem.addLabel("has final system", "en");
		objectProperty_hasFinalSystem.addLabel("имеет конечную систему", "ru");
		objectProperty_hasFinalSystem.addDomain(class_TaskDescription);
		objectProperty_hasFinalSystem.addRange(class_FinalSystem);

		objectProperty_isFinalSystemOf = m.createObjectProperty(URI_isFinalSystemOf);
		objectProperty_isFinalSystemOf.addLabel("is final system of", "en");
		objectProperty_isFinalSystemOf.addLabel("является конечной системой для", "ru");
		objectProperty_isFinalSystemOf.addDomain(class_FinalSystem);
		objectProperty_isFinalSystemOf.addRange(class_TaskDescription);

		makeInverse(objectProperty_hasFinalSystem, objectProperty_isFinalSystemOf);

		objectProperty_hasSystemObject = m.createObjectProperty(URI_hasSystemObject);
		objectProperty_hasSystemObject.addLabel("has system object", "en");
		objectProperty_hasSystemObject.addLabel("имеет объект системы", "ru");
		objectProperty_hasSystemObject.addDomain(class_System);
		objectProperty_hasSystemObject.addRange(class_SystemObject);

		objectProperty_isSystemObjectOf = m.createObjectProperty(URI_isSystemObjectOf);
		objectProperty_isSystemObjectOf.addLabel("is system object of", "en");
		objectProperty_isSystemObjectOf.addLabel("является объектом системы для", "ru");
		objectProperty_isSystemObjectOf.addDomain(class_SystemObject);
		objectProperty_isSystemObjectOf.addRange(class_System);

		makeInverse(objectProperty_hasSystemObject, objectProperty_isSystemObjectOf);

		objectProperty_hasLink = m.createObjectProperty(URI_hasLink);
		objectProperty_hasLink.addLabel("has link", "en");
		objectProperty_hasLink.addLabel("имеет связь", "ru");
		objectProperty_hasLink.addDomain(class_System);
		objectProperty_hasLink.addRange(class_Link);

		objectProperty_isLinkOf = m.createObjectProperty(URI_isLinkOf);
		objectProperty_isLinkOf.addLabel("is link of", "en");
		objectProperty_isLinkOf.addLabel("является связью для", "ru");
		objectProperty_isLinkOf.addDomain(class_Link);
		objectProperty_isLinkOf.addRange(class_System);

		makeInverse(objectProperty_hasLink, objectProperty_isLinkOf);

		objectProperty_hasAttribute = m.createObjectProperty(URI_hasAttribute);
		objectProperty_hasAttribute.addLabel("has attribute", "en");
		objectProperty_hasAttribute.addLabel("имеет атрибут", "ru");
		objectProperty_hasAttribute.addDomain(class_SystemObject);
		objectProperty_hasAttribute.addRange(class_Attribute);

		objectProperty_isAttributeOf = m.createObjectProperty(URI_isAttributeOf);
		objectProperty_isAttributeOf.addLabel("is attribute of", "en");
		objectProperty_isAttributeOf.addLabel("является атрибутом для", "ru");
		objectProperty_isAttributeOf.addDomain(class_Attribute);
		objectProperty_isAttributeOf.addRange(class_SystemObject);

		makeInverse(objectProperty_hasAttribute, objectProperty_isAttributeOf);

		dataProperty_name = m.createDatatypeProperty(URI_name);
		dataProperty_name.addLabel("name", "en");
		dataProperty_name.addLabel("название", "ru");
		dataProperty_name.addDomain(class_SystemObject);
		dataProperty_name.addDomain(class_Attribute);
		dataProperty_name.addDomain(class_Link);
		dataProperty_name.addRange(XSD.xstring);

		dataProperty_id = m.createDatatypeProperty(URI_id);
		dataProperty_id.addLabel("id", "en");
		dataProperty_id.addLabel("идентификатор", "ru");
		dataProperty_id.addDomain(class_SystemObject);
		dataProperty_id.addRange(XSD.xstring);

		dataProperty_value = m.createDatatypeProperty(URI_value);
		dataProperty_value.addLabel("value", "en");
		dataProperty_value.addLabel("значение", "ru");
		dataProperty_value.addDomain(class_Attribute);
		dataProperty_value.addRange(createDataRange(XSD.xstring, XSD.xboolean, XSD.xint));

		dataProperty_objectId1 = m.createDatatypeProperty(URI_objectId1);
		dataProperty_objectId1.addLabel("objectId1", "en");
		dataProperty_objectId1.addLabel("идентификатор объекта 1", "ru");
		dataProperty_objectId1.addDomain(class_Link);
		dataProperty_objectId1.addRange(XSD.xstring);

		dataProperty_objectId2 = m.createDatatypeProperty(URI_objectId2);
		dataProperty_objectId2.addLabel("objectId2", "en");
		dataProperty_objectId2.addLabel("идентификатор объекта 2", "ru");
		dataProperty_objectId2.addDomain(class_Link);
		dataProperty_objectId2.addRange(XSD.xstring);
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		m = ontModel;

		class_TaskDescription = m.getOntClass(URI_TaskDescription);
		class_System = m.getOntClass(URI_System);
		class_InitialSystem = m.getOntClass(URI_InitialSystem);
		class_FinalSystem = m.getOntClass(URI_FinalSystem);
		class_SystemObject = m.createClass(URI_SystemObject);
		class_Link = m.createClass(URI_Link);
		class_Attribute = m.createClass(URI_Attribute);

		objectProperty_hasInitialSystem = m.getObjectProperty(URI_hasInitialSystem);
		objectProperty_isInitialSystemOf = m.getObjectProperty(URI_isInitialSystemOf);
		objectProperty_hasFinalSystem = m.getObjectProperty(URI_hasFinalSystem);
		objectProperty_isFinalSystemOf = m.getObjectProperty(URI_isFinalSystemOf);
		objectProperty_hasSystemObject = m.getObjectProperty(URI_hasSystemObject);
		objectProperty_isSystemObjectOf = m.getObjectProperty(URI_isSystemObjectOf);
		objectProperty_hasLink = m.getObjectProperty(URI_hasLink);
		objectProperty_isLinkOf = m.getObjectProperty(URI_isLinkOf);
		objectProperty_hasAttribute = m.getObjectProperty(URI_hasAttribute);
		objectProperty_isAttributeOf = m.getObjectProperty(URI_isAttributeOf);

		dataProperty_name = m.getDatatypeProperty(URI_name);
		dataProperty_id = m.getDatatypeProperty(URI_id);
		dataProperty_value = m.getDatatypeProperty(URI_value);
		dataProperty_objectId1 = m.getDatatypeProperty(URI_objectId1);
		dataProperty_objectId2 = m.getDatatypeProperty(URI_objectId2);
	}

	@Override
	public OWLSchema<TaskDescription> getOWLSchema() {
		return new TaskDescriptionOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
	}

	@Override
	public String getUniqueURI() {
		return NS + UUID.randomUUID().toString();
	}
}
