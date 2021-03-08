package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.method.Edge;
import planning.model.SystemOperation;

public class EdgeOWLSchema implements OWLSchema<Edge> {

	private NodeNetworkOWLModel owlModel;

	private SystemOperationOWLSchema systemOperationOWLSchema;

	public EdgeOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;

		this.systemOperationOWLSchema = new SystemOperationOWLSchema(owlModel);
	}

	@Override
	public Individual combine(Edge edge) {
		Individual ind_edge = owlModel.getClass_Edge().createIndividual(owlModel.getUniqueURI());
		ind_edge.addLabel("Edge", "en");
		ind_edge.addLabel("Ребро", "ru");
		ind_edge.addProperty(owlModel.getDataProperty_id(), edge.getId());
		ind_edge.addProperty(owlModel.getDataProperty_beginNodeId(), edge.getBeginNodeId());
		ind_edge.addProperty(owlModel.getDataProperty_endNodeId(), edge.getEndNodeId());

		Individual ind_systemOperation = systemOperationOWLSchema.combine(edge.getSystemOperation());
		ind_edge.addProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);
		ind_systemOperation.addProperty(owlModel.getObjectProperty_isSystemOperationOf(), ind_edge);

		return ind_edge;
	}

	@Override
	public Edge parse(Individual ind_edge) {
		String id = ind_edge.getProperty(owlModel.getDataProperty_id()).getString();
		String beginNodeId = ind_edge.getProperty(owlModel.getDataProperty_beginNodeId()).getString();
		String endNodeId = ind_edge.getProperty(owlModel.getDataProperty_endNodeId()).getString();

		Edge edge = new Edge(id, beginNodeId, endNodeId, null);

		owlModel.getClass_SystemOperation().listInstances().filterKeep((ind_systemOperation) -> {
			return ind_edge.hasProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);
		}).forEachRemaining((ind_systemOperation) -> {
			SystemOperation systemOperation = systemOperationOWLSchema.parse(ind_systemOperation.asIndividual());
			edge.setSystemOperation(systemOperation);
		});

		return edge;
	}
}
