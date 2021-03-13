package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import planning.method.Node;
import planning.model.System;

public class NodeOWLSchema implements OWLSchema<Node> {

	private NodeNetworkOWLModel owlModel;

	private SystemOWLSchema systemOWLSchema;

	public NodeOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;

		this.systemOWLSchema = new SystemOWLSchema(owlModel);
	}

	@Override
	public Individual combine(Node node) {
		Individual ind_node = owlModel.getClass_Node().createIndividual(owlModel.getUniqueURI());
		ind_node.addLabel("Node", "en");
		ind_node.addLabel("Узел", "ru");
		ind_node.addProperty(owlModel.getDataProperty_id(), node.getId());
		ind_node.addProperty(owlModel.getDataProperty_checked(), Boolean.toString(node.getChecked()), XSDDatatype.XSDboolean);

		Individual ind_system = systemOWLSchema.combine(node.getSystem());
		ind_node.addProperty(owlModel.getObjectProperty_hasSystem(), ind_system);
		ind_system.addProperty(owlModel.getObjectProperty_isSystemOf(), ind_node);

		return ind_node;
	}

	@Override
	public Node parse(Individual ind_node) {
		String id = ind_node.getProperty(owlModel.getDataProperty_id()).getString();
		boolean checked = ind_node.getProperty(owlModel.getDataProperty_checked()).getBoolean();

		Node node = new Node(id, null, checked);

		owlModel.getClass_System().listInstances().filterKeep((ind_system) -> {
			return ind_node.hasProperty(owlModel.getObjectProperty_hasSystem(), ind_system.asIndividual());
		}).forEachRemaining((ind_system) -> {
			System system = systemOWLSchema.parse(ind_system.asIndividual());
			node.setSystem(system);
		});

		return node;
	}

}
