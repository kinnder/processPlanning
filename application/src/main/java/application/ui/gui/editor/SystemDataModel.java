package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.System;

public class SystemDataModel {

	private JTextField jtfSystemName;

	private JComboBox<String> jcbSystemType;

//	private EditorDataModel editorDataModel;

	private System system;

	public SystemDataModel(JTextField jtfSystemName, JComboBox<String> jcbSystemType, EditorDataModel editorDataModel) {
		this.jtfSystemName = jtfSystemName;
		this.jcbSystemType = jcbSystemType;
//		this.editorDataModel = editorDataModel;

		jcbSystemType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (system == null) {
						return;
					}
					int index = jcbSystemType.getSelectedIndex();
					switch (index) {
					case 0:
						// initial
						system.setName("initialSystem");
						editorDataModel.nodeChanged(treeNode);
						jtfSystemName.setText("initialSystem");
						break;
					case 2:
						// final
						system.setName("finalSystem");
						editorDataModel.nodeChanged(treeNode);
						jtfSystemName.setText("finalSystem");
						break;
					default:
						// regular
						;
					}
				}
			}
		});

		jtfSystemName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String systemName = jtfSystemName.getText();
				system.setName(systemName);
				editorDataModel.nodeChanged(treeNode);
			}
		});
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystem(System selectedObject, DefaultMutableTreeNode selectedNode) {
		String name = selectedObject.getName();
		// TODO (2022-09-23 #72): перенести в System и заменить на Enum
		int type;
		if ("initialSystem".equals(name)) {
			// initial
			type = 0;
		} else if ("finalSystem".equals(name)) {
			// final
			type = 2;
		} else {
			// regular
			type = 1;
		}
		jtfSystemName.setText(name);
		jcbSystemType.setSelectedIndex(type);

		system = selectedObject;
		treeNode = selectedNode;
	}

	public void clear() {
		system = null;
		treeNode = null;
	}
}
