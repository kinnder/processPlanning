package application.storage.owl;

import planning.method.NodeNetwork;

public class NodeNetworkOWLFile extends OWLFile<NodeNetwork> {

	public NodeNetworkOWLFile() {
		owlModel = new PlanningOWLModel();
		owlSchema = new NodeNetworkOWLSchema((PlanningOWLModel) owlModel);
	}
}
