package application.ui.gui.editor;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class EditorDataModel extends DefaultTreeModel {

	private static final long serialVersionUID = 2748742512319035267L;

	public EditorDataModel() {
		super(null);
		DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode("Project");
		DefaultMutableTreeNode treeNode2 = new DefaultMutableTreeNode("Task Description");
		DefaultMutableTreeNode treeNode3 = new DefaultMutableTreeNode("initialSystem");
		DefaultMutableTreeNode treeNode4 = new DefaultMutableTreeNode("object-workpiece");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-workpiece-pad");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-workpiece-pad-sketch");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-workpiece-plane");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part-pad");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part-pad-sketch");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part-plane");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part-pocket");
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("object-part-pocket-sketch");
		treeNode3.add(treeNode4);
		treeNode2.add(treeNode3);
		treeNode3 = new DefaultMutableTreeNode("finalSystem");
		treeNode4 = new DefaultMutableTreeNode("object-part-pocket");
		treeNode3.add(treeNode4);
		treeNode2.add(treeNode3);
		treeNode1.add(treeNode2);
		treeNode2 = new DefaultMutableTreeNode("System Transformations");
		treeNode3 = new DefaultMutableTreeNode("2D-Pocket");
		treeNode4 = new DefaultMutableTreeNode("System Template");
		DefaultMutableTreeNode treeNode5 = new DefaultMutableTreeNode("template-object");
		treeNode4.add(treeNode5);
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("Transformations");
		treeNode5 = new DefaultMutableTreeNode("attribute-transformation");
		treeNode4.add(treeNode5);
		treeNode3.add(treeNode4);
		treeNode4 = new DefaultMutableTreeNode("Action");
		treeNode5 = new DefaultMutableTreeNode("action-function");
		treeNode4.add(treeNode5);
		treeNode3.add(treeNode4);
		treeNode2.add(treeNode3);
		treeNode1.add(treeNode2);
		treeNode2 = new DefaultMutableTreeNode("Node Network");
		treeNode3 = new DefaultMutableTreeNode("Nodes");
		treeNode4 = new DefaultMutableTreeNode("node-id-0000");
		treeNode5 = new DefaultMutableTreeNode("system");
		DefaultMutableTreeNode treeNode6 = new DefaultMutableTreeNode("object-id");
		treeNode5.add(treeNode6);
		treeNode4.add(treeNode5);
		treeNode3.add(treeNode4);
		treeNode2.add(treeNode3);
		treeNode3 = new DefaultMutableTreeNode("Edges");
		treeNode4 = new DefaultMutableTreeNode("edge-id-0000");
		treeNode3.add(treeNode4);
		treeNode2.add(treeNode3);
		treeNode1.add(treeNode2);
		treeNode2 = new DefaultMutableTreeNode("Process");
		treeNode3 = new DefaultMutableTreeNode("process-id-0000");
		treeNode2.add(treeNode3);
		treeNode1.add(treeNode2);
		this.root = treeNode1;
	}
}
