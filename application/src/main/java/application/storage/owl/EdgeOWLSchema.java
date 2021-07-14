package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.method.Edge;
import planning.model.SystemOperation;

public class EdgeOWLSchema implements OWLSchema<Edge> {

	private PlanningOWLModel owlModel;

	private SystemOperationOWLSchema systemOperationOWLSchema;

	public EdgeOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemOperationOWLSchema(owlModel));
	}

	EdgeOWLSchema(PlanningOWLModel owlModel, SystemOperationOWLSchema systemOperationOWLSchema) {
		this.owlModel = owlModel;
		this.systemOperationOWLSchema = systemOperationOWLSchema;
	}

	@Override
	public Individual combine(Edge edge) {
		Individual ind_edge = owlModel.newIndividual_Edge();
		ind_edge.addLabel("Edge", "en");
		ind_edge.addLabel("Ребро", "ru");
		ind_edge.addProperty(owlModel.getDataProperty_id(), edge.getId());
		String beginNodeId = edge.getBeginNodeId();
		String endNodeId = edge.getEndNodeId();
		ind_edge.addProperty(owlModel.getDataProperty_beginNodeId(), beginNodeId);
		ind_edge.addProperty(owlModel.getDataProperty_endNodeId(), endNodeId);

		Individual ind_systemOperation = systemOperationOWLSchema.combine(edge.getSystemOperation());
		ind_edge.addProperty(owlModel.getObjectProperty_hasSystemOperation(), ind_systemOperation);

		// toList() call required only to suppress ConcurrentModificationException
		owlModel.getClass_Node().listInstances().toList().forEach((ind_node) -> {
			String nodeId = ind_node.getProperty(owlModel.getDataProperty_id()).getString();
			if (nodeId.equals(beginNodeId)) {
				ind_edge.addProperty(owlModel.getObjectProperty_hasBeginNode(), ind_node);
			}
			if (nodeId.equals(endNodeId)) {
				ind_edge.addProperty(owlModel.getObjectProperty_hasEndNode(), ind_node);
			}
		});

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
