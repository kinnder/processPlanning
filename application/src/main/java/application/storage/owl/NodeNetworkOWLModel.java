package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import planning.method.NodeNetwork;

public class NodeNetworkOWLModel implements OWLModel<NodeNetwork>, OWLModelCommonPart {

	final String NS = "https://github.com/kinnder/process-engineering/planning/node-network#";

	@Override
	public void createOntologyModel() {
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		// TODO Auto-generated method stub
	}

	@Override
	public OntModel getOntologyModel() {
		// TODO Auto-generated method stub
		return ModelFactory.createOntologyModel();
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
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_NodeNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_isNodeOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_isEdgeOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_Node() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_Edge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatatypeProperty getDataProperty_id() {
		// TODO Auto-generated method stub
		return null;
	}

	public DatatypeProperty getDataProperty_checked() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_isSystemOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntClass getClass_System() {
		// TODO Auto-generated method stub
		return null;
	}

	public DatatypeProperty getDataProperty_beginNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public DatatypeProperty getDataProperty_endNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasSystemOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_isSystemOperationOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_SystemOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatatypeProperty getDataProperty_name() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasActionParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_areActionParametersOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_ActionParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_Parameter() {
		// TODO Auto-generated method stub
		return null;
	}

	public DatatypeProperty getDataProperty_key() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatatypeProperty getDataProperty_value() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_hasParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectProperty getObjectProperty_isParameterOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_isSystemObjectOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_hasSystemObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_isLinkOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_hasLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntClass getClass_SystemObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntClass getClass_Link() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_isAttributeOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectProperty getObjectProperty_hasAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntClass getClass_Attribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatatypeProperty getDataProperty_objectId1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatatypeProperty getDataProperty_objectId2() {
		// TODO Auto-generated method stub
		return null;
	}
}
