package application.storage.owl;

import planning.method.NodeNetwork;

public class NodeNetworkOWLFile extends OWLFile<NodeNetwork> {

	public NodeNetworkOWLFile() {
		/*
		 * TODO (2020-10-01 #31): перенести схемы из конструктора в параметры расширения
		 */
		super(new NodeNetworkOWLSchema());
	}
}
