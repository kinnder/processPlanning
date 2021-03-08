package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import planning.method.NodeNetwork;

public class NodeNetworkOWLModel implements OWLModel<NodeNetwork>  {

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

	public Property getObjectProperty_hasNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_isNodeOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_hasEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_isEdgeOf() {
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

	public DatatypeProperty getDataProperty_id() {
		// TODO Auto-generated method stub
		return null;
	}

	public DatatypeProperty getDataProperty_checked() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_hasSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_isSystemOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_System() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getDataProperty_beginNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getDataProperty_endNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_hasSystemOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	public Property getObjectProperty_isSystemOperationOf() {
		// TODO Auto-generated method stub
		return null;
	}

	public OntClass getClass_SystemOperation() {
		// TODO Auto-generated method stub
		return null;
	}
}
