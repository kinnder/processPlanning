package planning.storage;

import planning.method.NodeNetwork;

public class NodeNetworkXMLFile extends XMLFile<NodeNetwork> {

	public NodeNetworkXMLFile() {
		super(new NodeNetworkXMLSchema());
	}
}
