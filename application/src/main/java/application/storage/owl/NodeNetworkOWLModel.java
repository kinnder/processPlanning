package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import planning.method.NodeNetwork;

public class NodeNetworkOWLModel implements OWLModel<NodeNetwork>, OWLModelCommonPart {

	static final String NS = "https://github.com/kinnder/process-engineering/planning/node-network#";

	static final String URI_ActionParameters = NS + "ActionParameters";

	static final String URI_Attribute = NS + "Attribute";

	static final String URI_Edge = NS + "Edge";

	static final String URI_Link = NS + "Link";

	static final String URI_NodeNetwork = NS + "NodeNetwork";

	static final String URI_Node = NS + "Node";

	static final String URI_Parameter = NS + "Parameter";

	static final String URI_System = NS + "System";

	static final String URI_SystemObject = NS + "SystemObject";

	static final String URI_SystemOperation = NS + "SystemOperation";

	static final String URI_areActionParametersOf = NS + "areActionParametersOf";

	static final String URI_hasActionParameters = NS + "hasActionParameters";

	static final String URI_hasAttribute = NS + "hasAttribute";

	static final String URI_hasEdge = NS + "hasEdge";

	static final String URI_hasLink = NS + "hasLink";

	static final String URI_hasNode = NS + "hasNode";

	static final String URI_hasParameter = NS + "hasParameter";

	static final String URI_hasSystem = NS + "hasSystem";

	static final String URI_hasSystemObject = NS + "hasSystemObject";

	static final String URI_hasSystemOperation = NS + "hasSystemOperation";

	static final String URI_isAttributeOf = NS + "isAttributeOf";

	static final String URI_isEdgeOf = NS + "isEdgeOf";

	static final String URI_isLinkOf = NS + "isLinkOf";

	static final String URI_isNodeOf = NS + "isNodeOf";

	static final String URI_isParameterOf = NS + "isParameterOf";

	static final String URI_isSystemObjectOf = NS + "isSystemObjectOf";

	static final String URI_isSystemOf = NS + "isSystemOf";

	static final String URI_isSystemOperation = NS + "isSystemOperation";

	static final String URI_beginNodeId = NS + "beginNodeId";

	static final String URI_checked = NS + "checked";

	static final String URI_endNodeId = NS + "endNodeId";

	static final String URI_id = NS + "id";

	static final String URI_key = NS + "key";

	static final String URI_name = NS + "name";

	static final String URI_objectId1 = NS + "objectId1";

	static final String URI_objectId2 = NS + "objectId2";

	static final String URI_value = NS + "value";

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	@Override
	public void createOntologyModel() {
		m = createOntologyModelBase();

		class_ActionParameters = m.createClass(URI_ActionParameters);
		class_ActionParameters.addLabel("Action Parameters", "en");
		class_ActionParameters.addLabel("Параметры действия", "ru");

		class_Attribute = m.createClass(URI_Attribute);
		class_Attribute.addLabel("Attribute", "en");
		class_Attribute.addLabel("Аттрибут", "ru");

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

		class_System = m.createClass(URI_System);
		class_System.addLabel("System", "en");
		class_System.addLabel("Система", "ru");

		class_SystemObject = m.createClass(URI_SystemObject);
		class_SystemObject.addLabel("System Object", "en");
		class_SystemObject.addLabel("Объект системы", "ru");

		class_SystemOperation = m.createClass(URI_SystemOperation);
		class_SystemOperation.addLabel("System Operation", "en");
		class_SystemOperation.addLabel("Операция системы", "ru");

		objectProperty_hasActionParameters = m.createObjectProperty(URI_hasActionParameters);
		objectProperty_hasActionParameters.addLabel("has action parameters", "en");
		objectProperty_hasActionParameters.addLabel("имеет параметры действия", "ru");
		objectProperty_hasActionParameters.addDomain(class_SystemOperation);
		objectProperty_hasActionParameters.addRange(class_ActionParameters);

		objectProperty_areActionParametersOf = m.createObjectProperty(URI_areActionParametersOf);
		objectProperty_areActionParametersOf.addLabel("are action parameters of", "en");
		objectProperty_areActionParametersOf.addLabel("являются параметрами действиями для", "ru");
		objectProperty_areActionParametersOf.addDomain(class_ActionParameters);
		objectProperty_areActionParametersOf.addRange(class_SystemOperation);

		makeInverse(objectProperty_hasActionParameters, objectProperty_areActionParametersOf);

		objectProperty_hasNode = m.createObjectProperty(URI_hasNode);
		// TODO Auto-generated method stub
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		// TODO Auto-generated method stub
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
		return NS + UUID.randomUUID().toString();
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

	public ObjectProperty getObjectProperty_hasSystemOperation() {
		return objectProperty_hasSystemOperation;
	}

	private ObjectProperty objectProperty_isSystemOperation;

	public ObjectProperty getObjectProperty_isSystemOperationOf() {
		return objectProperty_isSystemOperation;
	}

	private OntClass class_SystemOperation;

	public OntClass getClass_SystemOperation() {
		return class_SystemOperation;
	}

	private DatatypeProperty dataProperty_name;

	@Override
	public DatatypeProperty getDataProperty_name() {
		return dataProperty_name;
	}

	private ObjectProperty objectProperty_hasActionParameters;

	public ObjectProperty getObjectProperty_hasActionParameters() {
		return objectProperty_hasActionParameters;
	}

	private ObjectProperty objectProperty_areActionParametersOf;

	public ObjectProperty getObjectProperty_areActionParametersOf() {
		return objectProperty_areActionParametersOf;
	}

	private OntClass class_ActionParameters;

	public OntClass getClass_ActionParameters() {
		return class_ActionParameters;
	}

	private OntClass class_Parameter;

	public OntClass getClass_Parameter() {
		return class_Parameter;
	}

	private DatatypeProperty dataProperty_key;

	public DatatypeProperty getDataProperty_key() {
		return dataProperty_key;
	}

	private DatatypeProperty dataProperty_value;

	@Override
	public DatatypeProperty getDataProperty_value() {
		return dataProperty_value;
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
}
