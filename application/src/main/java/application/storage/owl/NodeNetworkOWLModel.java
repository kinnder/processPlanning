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

import planning.method.NodeNetwork;

public class NodeNetworkOWLModel implements OWLModel<NodeNetwork>, OWLModelCommonPart, OWLModelCommonPart2 {

	static final String NS = "https://github.com/kinnder/process-engineering/planning/node-network";

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

	// TODO (2021-03-13 #31): включить проверку copy-paste
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

		class_Attribute = m.createClass(URI_Attribute);
		class_Attribute.addLabel("Attribute", "en");
		class_Attribute.addLabel("Атрибут", "ru");

		class_Edge = m.createClass(URI_Edge);
		class_Edge.addLabel("Edge", "en");
		class_Edge.addLabel("Ребро", "ru");

		class_Link = m.createClass(URI_Link);
		class_Link.addLabel("Link", "en");
		class_Link.addLabel("Связь", "ru");

		class_Node = m.createClass(URI_Node);
		class_Node.addLabel("Node", "en");
		class_Node.addLabel("Узел", "ru");

		class_NodeNetwork = m.createClass(URI_NodeNetwork);
		class_NodeNetwork.addLabel("Node Network", "en");
		class_NodeNetwork.addLabel("Сеть узлов", "ru");

		class_Parameter = m.createClass(URI_Parameter);
		class_Parameter.addLabel("Parameter", "en");
		class_Parameter.addLabel("Параметр", "ru");

		class_Process = m.createClass(URI_Process);
		class_Process.addLabel("Process", "en");
		class_Process.addLabel("Процесс", "ru");

		class_System = m.createClass(URI_System);
		class_System.addLabel("System", "en");
		class_System.addLabel("Система", "ru");

		class_SystemObject = m.createClass(URI_SystemObject);
		class_SystemObject.addLabel("System Object", "en");
		class_SystemObject.addLabel("Объект системы", "ru");

		class_SystemOperation = m.createClass(URI_SystemOperation);
		class_SystemOperation.addLabel("System Operation", "en");
		class_SystemOperation.addLabel("Операция системы", "ru");

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

		objectProperty_hasEdge = m.createObjectProperty(URI_hasEdge);
		objectProperty_hasEdge.addLabel("has edge", "en");
		objectProperty_hasEdge.addLabel("имеет ребро", "ru");
		objectProperty_hasEdge.addDomain(class_NodeNetwork);
		objectProperty_hasEdge.addRange(class_Edge);

		objectProperty_isEdgeOf = m.createObjectProperty(URI_isEdgeOf);
		objectProperty_isEdgeOf.addLabel("is edge of", "en");
		objectProperty_isEdgeOf.addLabel("является ребром для", "ru");
		objectProperty_isEdgeOf.addDomain(class_Edge);
		objectProperty_isEdgeOf.addRange(class_NodeNetwork);

		makeInverse(objectProperty_hasEdge, objectProperty_isEdgeOf);

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

		objectProperty_hasNode = m.createObjectProperty(URI_hasNode);
		objectProperty_hasNode.addLabel("has node", "en");
		objectProperty_hasNode.addLabel("имеет узел", "ru");
		objectProperty_hasNode.addDomain(class_NodeNetwork);
		objectProperty_hasNode.addRange(class_Node);

		objectProperty_isNodeOf = m.createObjectProperty(URI_isNodeOf);
		objectProperty_isNodeOf.addLabel("is node of", "en");
		objectProperty_isNodeOf.addLabel("является узлом для", "ru");
		objectProperty_isNodeOf.addDomain(class_Node);
		objectProperty_isNodeOf.addRange(class_NodeNetwork);

		makeInverse(objectProperty_hasNode, objectProperty_isNodeOf);

		objectProperty_hasParameter = m.createObjectProperty(URI_hasParameter);
		objectProperty_hasParameter.addLabel("has parameter", "en");
		objectProperty_hasParameter.addLabel("имеет параметр", "ru");
		objectProperty_hasParameter.addDomain(class_SystemOperation);
		objectProperty_hasParameter.addRange(class_Parameter);

		objectProperty_isParameterOf = m.createObjectProperty(URI_isParameterOf);
		objectProperty_isParameterOf.addLabel("is parameter of", "en");
		objectProperty_isParameterOf.addLabel("является параметром для", "ru");
		objectProperty_isParameterOf.addDomain(class_Parameter);
		objectProperty_isParameterOf.addRange(class_SystemOperation);

		makeInverse(objectProperty_hasParameter, objectProperty_isParameterOf);

		objectProperty_hasSystem = m.createObjectProperty(URI_hasSystem);
		objectProperty_hasSystem.addLabel("has system", "en");
		objectProperty_hasSystem.addLabel("имеет систему", "ru");
		objectProperty_hasSystem.addDomain(class_Node);
		objectProperty_hasSystem.addRange(class_System);

		objectProperty_isSystemOf = m.createObjectProperty(URI_isSystemOf);
		objectProperty_isSystemOf.addLabel("is system of", "en");
		objectProperty_isSystemOf.addLabel("является системой для", "ru");
		objectProperty_isSystemOf.addDomain(class_System);
		objectProperty_isSystemOf.addRange(class_Node);

		// TODO (2021-07-08 #38): включить проверку copy-paste
		// CPD-OFF

		makeInverse(objectProperty_hasSystem, objectProperty_isSystemOf);

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

		objectProperty_hasSystemObject1 = m.createObjectProperty(URI_hasSystemObject1);
		objectProperty_hasSystemObject1.addLabel("has system object 1", "en");
		objectProperty_hasSystemObject1.addLabel("имеет объет системы 1", "ru");
		objectProperty_hasSystemObject1.addDomain(class_Link);
		objectProperty_hasSystemObject1.addRange(class_SystemObject);

		objectProperty_isSystemObject1Of = m.createObjectProperty(URI_isSystemObject1Of);
		objectProperty_isSystemObject1Of.addLabel("is system object 1 of", "en");
		objectProperty_isSystemObject1Of.addLabel("является объектом системы 1 для", "ru");
		objectProperty_isSystemObject1Of.addDomain(class_SystemObject);
		objectProperty_isSystemObject1Of.addRange(class_Link);

		makeInverse(objectProperty_hasSystemObject1, objectProperty_isSystemObject1Of);

		objectProperty_hasSystemObject2 = m.createObjectProperty(URI_hasSystemObject2);
		objectProperty_hasSystemObject2.addLabel("has system object 2", "en");
		objectProperty_hasSystemObject2.addLabel("имеет объет системы 2", "ru");
		objectProperty_hasSystemObject2.addDomain(class_Link);
		objectProperty_hasSystemObject2.addRange(class_SystemObject);

		objectProperty_isSystemObject2Of = m.createObjectProperty(URI_isSystemObject2Of);
		objectProperty_isSystemObject2Of.addLabel("is system object 2 of", "en");
		objectProperty_isSystemObject2Of.addLabel("является объектом системы 2 для", "ru");
		objectProperty_isSystemObject2Of.addDomain(class_SystemObject);
		objectProperty_isSystemObject2Of.addRange(class_Link);

		makeInverse(objectProperty_hasSystemObject2, objectProperty_isSystemObject2Of);

		// CPD-ON

		objectProperty_hasSystemOperation = m.createObjectProperty(URI_hasSystemOperation);
		objectProperty_hasSystemOperation.addLabel("has system operation", "en");
		objectProperty_hasSystemOperation.addLabel("имеет операцию системы", "ru");
		objectProperty_hasSystemOperation.addDomain(class_Edge);
		objectProperty_hasSystemOperation.addRange(class_SystemOperation);

		objectProperty_isSystemOperationOf = m.createObjectProperty(URI_isSystemOperationOf);
		objectProperty_isSystemOperationOf.addLabel("is system operation of", "en");
		objectProperty_isSystemOperationOf.addLabel("является операцией системы для", "ru");
		objectProperty_isSystemOperationOf.addDomain(class_SystemOperation);
		objectProperty_isSystemOperationOf.addRange(class_Edge);

		makeInverse(objectProperty_hasSystemOperation, objectProperty_isSystemOperationOf);

		objectProperty_hasBeginNode = m.createObjectProperty(URI_hasBeginNode);
		objectProperty_hasBeginNode.addLabel("has begin node", "en");
		objectProperty_hasBeginNode.addLabel("имеет начальный узел", "ru");
		objectProperty_hasBeginNode.addDomain(class_Edge);
		objectProperty_hasBeginNode.addRange(class_Node);

		objectProperty_isBeginNodeOf = m.createObjectProperty(URI_isBeginNodeOf);
		objectProperty_isBeginNodeOf.addLabel("is begin node of", "en");
		objectProperty_isBeginNodeOf.addLabel("является начальным узлом для", "ru");
		objectProperty_isBeginNodeOf.addDomain(class_Node);
		objectProperty_isBeginNodeOf.addRange(class_Edge);

		makeInverse(objectProperty_hasBeginNode, objectProperty_isBeginNodeOf);

		objectProperty_hasEndNode = m.createObjectProperty(URI_hasEndNode);
		objectProperty_hasEndNode.addLabel("has end node", "en");
		objectProperty_hasEndNode.addLabel("имеет конечный узел", "ru");
		objectProperty_hasEndNode.addDomain(class_Edge);
		objectProperty_hasEndNode.addRange(class_Node);

		objectProperty_isEndNodeOf = m.createObjectProperty(URI_isEndNodeOf);
		objectProperty_isEndNodeOf.addLabel("is end node of", "en");
		objectProperty_isEndNodeOf.addLabel("является конечным узлом для", "ru");
		objectProperty_isEndNodeOf.addDomain(class_Node);
		objectProperty_isEndNodeOf.addRange(class_Edge);

		makeInverse(objectProperty_hasEndNode, objectProperty_isEndNodeOf);

		dataProperty_beginNodeId = m.createDatatypeProperty(URI_beginNodeId);
		dataProperty_beginNodeId.addLabel("begin node id", "en");
		dataProperty_beginNodeId.addLabel("идентификатор начального узла", "ru");
		dataProperty_beginNodeId.addDomain(class_Edge);
		dataProperty_beginNodeId.addRange(XSD.xstring);

		dataProperty_checked = m.createDatatypeProperty(URI_checked);
		dataProperty_checked.addLabel("checked", "en");
		dataProperty_checked.addLabel("просмотрен", "ru");
		dataProperty_checked.addDomain(class_Node);
		dataProperty_checked.addRange(XSD.xboolean);

		dataProperty_endNodeId = m.createDatatypeProperty(URI_endNodeId);
		dataProperty_endNodeId.addLabel("end node id", "en");
		dataProperty_endNodeId.addLabel("индентификатор конечного узла", "ru");
		dataProperty_endNodeId.addDomain(class_Edge);
		dataProperty_endNodeId.addRange(XSD.xstring);

		dataProperty_id = m.createDatatypeProperty(URI_id);
		dataProperty_id.addLabel("id", "en");
		dataProperty_id.addLabel("идентификатор", "ru");
		dataProperty_id.addDomain(class_Edge);
		dataProperty_id.addDomain(class_Node);
		dataProperty_id.addDomain(class_SystemObject);
		dataProperty_id.addRange(XSD.xstring);

		dataProperty_key = m.createDatatypeProperty(URI_key);
		dataProperty_key.addLabel("key", "en");
		dataProperty_key.addLabel("ключ", "ru");
		dataProperty_key.addDomain(class_Parameter);
		dataProperty_key.addRange(XSD.xstring);

		dataProperty_name = m.createDatatypeProperty(URI_name);
		dataProperty_name.addLabel("name", "en");
		dataProperty_name.addLabel("название", "ru");
		dataProperty_name.addDomain(class_SystemObject);
		dataProperty_name.addDomain(class_Attribute);
		dataProperty_name.addDomain(class_Link);
		dataProperty_name.addDomain(class_SystemOperation);
		dataProperty_name.addRange(XSD.xstring);

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

		dataProperty_value = m.createDatatypeProperty(URI_value);
		dataProperty_value.addLabel("value", "en");
		dataProperty_value.addLabel("значение", "ru");
		dataProperty_value.addDomain(class_Attribute);
		dataProperty_value.addDomain(class_Parameter);
		dataProperty_value.addRange(createDataRange(XSD.xstring, XSD.xboolean, XSD.xint));
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		m = ontModel;

		class_Attribute = m.getOntClass(URI_Attribute);
		class_Edge = m.getOntClass(URI_Edge);
		class_Link = m.getOntClass(URI_Link);
		class_Node = m.getOntClass(URI_Node);
		class_NodeNetwork = m.getOntClass(URI_NodeNetwork);
		class_Parameter = m.getOntClass(URI_Parameter);
		class_Process = m.getOntClass(URI_Process);
		class_System = m.getOntClass(URI_System);
		class_SystemObject = m.getOntClass(URI_SystemObject);
		class_SystemOperation = m.getOntClass(URI_SystemOperation);

		objectProperty_hasAttribute = m.getObjectProperty(URI_hasAttribute);
		objectProperty_hasEdge = m.getObjectProperty(URI_hasEdge);
		objectProperty_hasLink = m.getObjectProperty(URI_hasLink);
		objectProperty_hasNode = m.getObjectProperty(URI_hasNode);
		objectProperty_hasParameter = m.getObjectProperty(URI_hasParameter);
		objectProperty_hasSystem = m.getObjectProperty(URI_hasSystem);
		objectProperty_hasSystemObject = m.getObjectProperty(URI_hasSystemObject);
		objectProperty_hasSystemOperation = m.getObjectProperty(URI_hasSystemOperation);
		objectProperty_isAttributeOf = m.getObjectProperty(URI_isAttributeOf);
		objectProperty_isEdgeOf = m.getObjectProperty(URI_isEdgeOf);
		objectProperty_isLinkOf = m.getObjectProperty(URI_isLinkOf);
		objectProperty_isNodeOf = m.getObjectProperty(URI_isNodeOf);
		objectProperty_isParameterOf = m.getObjectProperty(URI_isParameterOf);
		objectProperty_isSystemObjectOf = m.getObjectProperty(URI_isSystemObjectOf);
		objectProperty_isSystemOf = m.getObjectProperty(URI_isSystemOf);
		objectProperty_isSystemOperationOf = m.getObjectProperty(URI_isSystemOperationOf);
		objectProperty_hasBeginNode = m.getObjectProperty(URI_hasBeginNode);
		objectProperty_hasEndNode = m.getObjectProperty(URI_hasEndNode);
		objectProperty_isBeginNodeOf = m.getObjectProperty(URI_isBeginNodeOf);
		objectProperty_isEndNodeOf = m.getObjectProperty(URI_isEndNodeOf);
		objectProperty_hasSystemObject1 = m.getObjectProperty(URI_hasSystemObject1);
		objectProperty_isSystemObject1Of = m.getObjectProperty(URI_isSystemObject1Of);
		objectProperty_hasSystemObject2 = m.getObjectProperty(URI_hasSystemObject2);
		objectProperty_isSystemObject2Of = m.getObjectProperty(URI_isSystemObject2Of);

		dataProperty_beginNodeId = m.getDatatypeProperty(URI_beginNodeId);
		dataProperty_checked = m.getDatatypeProperty(URI_checked);
		dataProperty_endNodeId = m.getDatatypeProperty(URI_endNodeId);
		dataProperty_id = m.getDatatypeProperty(URI_id);
		dataProperty_key = m.getDatatypeProperty(URI_key);
		dataProperty_name = m.getDatatypeProperty(URI_name);
		dataProperty_objectId1 = m.getDatatypeProperty(URI_objectId1);
		dataProperty_objectId2 = m.getDatatypeProperty(URI_objectId2);
		dataProperty_value = m.getDatatypeProperty(URI_value);
	}

	private OntModel m;

	@Override
	public OntModel getOntologyModel() {
		return m;
	}

	@Override
	public OWLSchema<NodeNetwork> getOWLSchema() {
		return new NodeNetworkOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
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

	@Override
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

	@Override
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

	@Override
	public ObjectProperty getObjectProperty_hasSystemOperation() {
		return objectProperty_hasSystemOperation;
	}

	private ObjectProperty objectProperty_isSystemOperationOf;

	@Override
	public ObjectProperty getObjectProperty_isSystemOperationOf() {
		return objectProperty_isSystemOperationOf;
	}

	private OntClass class_SystemOperation;

	@Override
	public OntClass getClass_SystemOperation() {
		return class_SystemOperation;
	}

	private DatatypeProperty dataProperty_name;

	@Override
	public DatatypeProperty getDataProperty_name() {
		return dataProperty_name;
	}

	private OntClass class_Parameter;

	@Override
	public OntClass getClass_Parameter() {
		return class_Parameter;
	}

	private OntClass class_Process;

	private DatatypeProperty dataProperty_key;

	@Override
	public DatatypeProperty getDataProperty_key() {
		return dataProperty_key;
	}

	private DatatypeProperty dataProperty_value;

	@Override
	public DatatypeProperty getDataProperty_value() {
		return dataProperty_value;
	}

	private ObjectProperty objectProperty_hasParameter;

	@Override
	public ObjectProperty getObjectProperty_hasParameter() {
		return objectProperty_hasParameter;
	}

	private ObjectProperty objectProperty_isParameterOf;

	@Override
	public ObjectProperty getObjectProperty_isParameterOf() {
		return objectProperty_isParameterOf;
	}

	private ObjectProperty objectProperty_isSystemObjectOf;

	@Override
	public ObjectProperty getObjectProperty_isSystemObjectOf() {
		return objectProperty_isSystemObjectOf;
	}

	private ObjectProperty objectProperty_hasSystemObject;

	@Override
	public ObjectProperty getObjectProperty_hasSystemObject() {
		return objectProperty_hasSystemObject;
	}

	private ObjectProperty objectProperty_isLinkOf;

	@Override
	public ObjectProperty getObjectProperty_isLinkOf() {
		return objectProperty_isLinkOf;
	}

	private ObjectProperty objectProperty_hasLink;

	@Override
	public ObjectProperty getObjectProperty_hasLink() {
		return objectProperty_hasLink;
	}

	private OntClass class_SystemObject;

	@Override
	public OntClass getClass_SystemObject() {
		return class_SystemObject;
	}

	private OntClass class_Link;

	@Override
	public OntClass getClass_Link() {
		return class_Link;
	}

	private ObjectProperty objectProperty_isAttributeOf;

	@Override
	public ObjectProperty getObjectProperty_isAttributeOf() {
		return objectProperty_isAttributeOf;
	}

	private ObjectProperty objectProperty_hasAttribute;

	@Override
	public ObjectProperty getObjectProperty_hasAttribute() {
		return objectProperty_hasAttribute;
	}

	private OntClass class_Attribute;

	@Override
	public OntClass getClass_Attribute() {
		return class_Attribute;
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

	@Override
	public OntClass getClass_Process() {
		return class_Process;
	}

	@Override
	public Individual newIndividual_System() {
		return class_System.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_SystemObject() {
		return class_SystemObject.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_Link() {
		return class_Link.createIndividual(getUniqueURI());
	}

	@Override
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

	@Override
	public Individual newIndividual_Parameter() {
		return class_Parameter.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_Process() {
		return class_Process.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_SystemOperation() {
		return class_SystemOperation.createIndividual(getUniqueURI());
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

	@Override
	public ObjectProperty getObjectProperty_hasSystemObject1() {
		return objectProperty_hasSystemObject1;
	}

	private ObjectProperty objectProperty_isSystemObject1Of;

	@Override
	public ObjectProperty getObjectProperty_isSystemObject1Of() {
		return objectProperty_isSystemObject1Of;
	}

	private ObjectProperty objectProperty_hasSystemObject2;

	@Override
	public ObjectProperty getObjectProperty_hasSystemObject2() {
		return objectProperty_hasSystemObject2;
	}

	private ObjectProperty objectProperty_isSystemObject2Of;

	@Override
	public ObjectProperty getObjectProperty_isSystemObject2Of() {
		return objectProperty_isSystemObject2Of;
	}
}
