package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import planning.method.Node;
import planning.model.System;

public class NodeOWLSchema implements OWLSchema<Node> {

	private PlanningOWLModel owlModel;

	private SystemOWLSchema systemOWLSchema;

	public NodeOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemOWLSchema(owlModel));
	}

	NodeOWLSchema(PlanningOWLModel owlModel, SystemOWLSchema systemOWLSchema) {
		this.owlModel = owlModel;
		this.systemOWLSchema = systemOWLSchema;
	}

	@Override
	public Individual combine(Node node) {
		final Individual ind_node = owlModel.newIndividual_Node();
		ind_node.addLabel("Node", "en");
		ind_node.addLabel("Узел", "ru");
		ind_node.addProperty(owlModel.getDataProperty_id(), node.getId());
		ind_node.addProperty(owlModel.getDataProperty_checked(), Boolean.toString(node.getChecked()), XSDDatatype.XSDboolean);

		final Individual ind_system = systemOWLSchema.combine(node.getSystem());
		ind_node.addProperty(owlModel.getObjectProperty_hasSystem(), ind_system);

		return ind_node;
	}

	@Override
	public Node parse(Individual ind_node) {
		final String id = ind_node.getProperty(owlModel.getDataProperty_id()).getString();
		final boolean checked = ind_node.getProperty(owlModel.getDataProperty_checked()).getBoolean();

		final Node node = new Node(id, null, checked);

		owlModel.getClass_System().listInstances().filterKeep((ind_system) -> {
			return ind_node.hasProperty(owlModel.getObjectProperty_hasSystem(), ind_system);
		}).forEachRemaining((ind_system) -> {
			final System system = systemOWLSchema.parse(ind_system.asIndividual());
			node.setSystem(system);
		});

		return node;
	}

}
