package application.ui.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import application.Application;
import application.ui.UserInterfaceFactory;
import application.ui.gui.editor.AttributesDataModel;
import application.ui.gui.editor.EditorDataModel;
import application.ui.gui.editor.LinksDataModel;
import application.ui.gui.editor.ObjectsDataModel;
import application.ui.gui.editor.SystemDataModel;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemObject;
import planning.model.SystemProcess;
import planning.model.System;

// TODO (2022-09-18 #72): инициализацию компонентов перенести в CustomCode визуального редактора
public class EditorFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = -6624128935908845123L;

	private Application application;

	public EditorFrame(Application application) {
		this(application, new EditorDataModel());
	}

	EditorFrame(Application application, EditorDataModel editorDataModel) {
		this.application = application;
		this.editorDataModel = editorDataModel;
		this.objectsDataModel = new ObjectsDataModel(editorDataModel);
		this.linksDataModel = new LinksDataModel(editorDataModel);

		initComponents();
		setActions();

		this.systemDataModel = new SystemDataModel(jtfSystemName, jcbSystemType, editorDataModel);
	}

	private EditorDataModel editorDataModel;

	private SystemDataModel systemDataModel;

	private ObjectsDataModel objectsDataModel;

	private LinksDataModel linksDataModel;

	private AttributesDataModel attributesDataModel = new AttributesDataModel();

	private void setActions() {
		jmiTaskDescriptionLoad.setAction(taskDescriptionLoadAction);
		jmiTaskDescriptionSave.setAction(taskDescriptionSaveAction);
		jmiSystemTransformationsLoad.setAction(systemTransformationsLoadAction);
		jmiNodeNetworkLoad.setAction(nodeNetworkLoadAction);
		jmiProcessLoad.setAction(processLoadAction);

		jbObjectsInsert.setAction(objectInsertAction);
		jbObjectsDelete.setAction(objectDeleteAction);

		jbLinksInsert.setAction(linkInsertAction);
		jbLinksDelete.setAction(linkDeleteAction);
	}

	Action taskDescriptionLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = 8331309669949257478L;

		@Override
		public void actionPerformed(ActionEvent e) {
			TaskDescription taskDescription = application.loadTaskDescription();
			editorDataModel.loadTaskDescription(taskDescription);
		}
	};

	Action taskDescriptionSaveAction = new AbstractAction("Save") {
		private static final long serialVersionUID = -6152997887082597540L;

		@Override
		public void actionPerformed(ActionEvent e) {
			TaskDescription taskDescription = editorDataModel.saveTaskDescription();
			application.saveTaskDescription(taskDescription);
		}
	};

	Action systemTransformationsLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -6152997887082597540L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SystemTransformations systemTransformations = application.loadSystemTransformations();
			editorDataModel.loadSystemTransformations(systemTransformations);
		}
	};

	Action nodeNetworkLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -6796904580309558162L;

		@Override
		public void actionPerformed(ActionEvent e) {
			NodeNetwork nodeNetwork = application.loadNodeNetwork();
			editorDataModel.loadNodeNetwork(nodeNetwork);
		}
	};

	Action processLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -2934465114601682626L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SystemProcess systemProcess = application.loadSystemProcess();
			editorDataModel.loadSystemProcess(systemProcess);
		}
	};

	Action objectInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -1700274277944696641L;

		@Override
		public void actionPerformed(ActionEvent e) {
			objectsDataModel.insertObject();
		}
	};

	Action objectDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 7657799313389376389L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int idx = jtObjects.getSelectedRow();
			objectsDataModel.deleteObject(idx);
		}
	};

	Action linkInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -9112378954512358406L;

		@Override
		public void actionPerformed(ActionEvent e) {
			linksDataModel.insertLink();
		}
	};

	Action linkDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 5435734964836282376L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int idx = jtLinks.getSelectedRow();
			linksDataModel.deleteLink(idx);
		}
	};

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		bgTransformationType = new javax.swing.ButtonGroup();
		jspWorkArea = new javax.swing.JSplitPane();
		jspData = new javax.swing.JScrollPane();
		jtData = new javax.swing.JTree();
		jtpEditors = new javax.swing.JTabbedPane();
		jpSystemEditor = new javax.swing.JPanel();
		jpSystem = new javax.swing.JPanel();
		jlSystemName = new javax.swing.JLabel();
		jlSystemType = new javax.swing.JLabel();
		jtfSystemName = new javax.swing.JTextField();
		jcbSystemType = new javax.swing.JComboBox<>();
		jpSystemData = new javax.swing.JPanel();
		jspSystemData = new javax.swing.JSplitPane();
		jpObjectsEditor = new javax.swing.JPanel();
		jlObjects = new javax.swing.JLabel();
		jspObjects = new javax.swing.JScrollPane();
		jtObjects = new javax.swing.JTable();
		jpObjectsButtons = new javax.swing.JPanel();
		jbObjectsInsert = new javax.swing.JButton();
		jbObjectsDelete = new javax.swing.JButton();
		jpLinksEditor = new javax.swing.JPanel();
		jlLinks = new javax.swing.JLabel();
		jpLinksButtons = new javax.swing.JPanel();
		jbLinksInsert = new javax.swing.JButton();
		jbLinksDelete = new javax.swing.JButton();
		jspLinks = new javax.swing.JScrollPane();
		jtLinks = new javax.swing.JTable();
		jpNodeEditor = new javax.swing.JPanel();
		jpNode = new javax.swing.JPanel();
		jlNodeId = new javax.swing.JLabel();
		jcbNodeChecked = new javax.swing.JCheckBox();
		jtfNodeId = new javax.swing.JTextField();
		jpEdgesEditor = new javax.swing.JPanel();
		jlEdges = new javax.swing.JLabel();
		jspEdges = new javax.swing.JScrollPane();
		jtEdges = new javax.swing.JTable();
		jpEdgesButtons = new javax.swing.JPanel();
		jbEdgesInsert = new javax.swing.JButton();
		jbEdgesDelete = new javax.swing.JButton();
		jbEdgesUp = new javax.swing.JButton();
		jbEdgesDown = new javax.swing.JButton();
		jpEdgeEditor = new javax.swing.JPanel();
		jpEdge = new javax.swing.JPanel();
		jlEdgeId = new javax.swing.JLabel();
		jlBeginNodeId = new javax.swing.JLabel();
		jlEndNodeId = new javax.swing.JLabel();
		jtfEdgeId = new javax.swing.JTextField();
		jtfBeginNodeId = new javax.swing.JTextField();
		jtfEndNodeId = new javax.swing.JTextField();
		jpOperationEditor = new javax.swing.JPanel();
		jpOperation = new javax.swing.JPanel();
		jlOperationName = new javax.swing.JLabel();
		jtfOperationName = new javax.swing.JTextField();
		jlOperation = new javax.swing.JLabel();
		jpParametersEditor = new javax.swing.JPanel();
		jlParameters = new javax.swing.JLabel();
		jpParametersButtons = new javax.swing.JPanel();
		jbParametersInsert = new javax.swing.JButton();
		jbParametersDelete = new javax.swing.JButton();
		jbParametersUp = new javax.swing.JButton();
		jbParametersDown = new javax.swing.JButton();
		jspParameters = new javax.swing.JScrollPane();
		jtParameters = new javax.swing.JTable();
		jpObjectEditor = new javax.swing.JPanel();
		jpObject = new javax.swing.JPanel();
		jlObjectName = new javax.swing.JLabel();
		jtfObjectName = new javax.swing.JTextField();
		jtfObjectId = new javax.swing.JTextField();
		jlObjectId = new javax.swing.JLabel();
		jpAttributesEditor = new javax.swing.JPanel();
		jlAttributes = new javax.swing.JLabel();
		jspAttributes = new javax.swing.JScrollPane();
		jtAttributes = new javax.swing.JTable();
		jpAttributesButtons = new javax.swing.JPanel();
		jbAttributesInsert = new javax.swing.JButton();
		jbAttributesDelete = new javax.swing.JButton();
		jbAttributesMoveUp = new javax.swing.JButton();
		jbAttributesMoveDown = new javax.swing.JButton();
		jpActionEditor = new javax.swing.JPanel();
		jpAction = new javax.swing.JPanel();
		jlActionName = new javax.swing.JLabel();
		jtfActionName = new javax.swing.JTextField();
		jpActionFunctionsEditor = new javax.swing.JPanel();
		jlActionFunctions = new javax.swing.JLabel();
		jpActionFunctionsButtons = new javax.swing.JPanel();
		jbActionFunctionsInsert = new javax.swing.JButton();
		jbActionFunctionsDelete = new javax.swing.JButton();
		jbActionFunctionsMoveUp = new javax.swing.JButton();
		jbActionFunctionsMoveDown = new javax.swing.JButton();
		jspActionFunctions = new javax.swing.JScrollPane();
		jtActionFunctions = new javax.swing.JTable();
		jpActionFunctionEditor = new javax.swing.JPanel();
		jlActionFunctionType = new javax.swing.JLabel();
		jcbActionFunctionType = new javax.swing.JComboBox<>();
		jspActionFunctionLines = new javax.swing.JScrollPane();
		jtaActionFunctionLines = new javax.swing.JTextArea();
		jpSystemTransformationEditor = new javax.swing.JPanel();
		jlSystemTransformationName = new javax.swing.JLabel();
		jtfSystemTransformationName = new javax.swing.JTextField();
		jpTransformationsEditor = new javax.swing.JPanel();
		jlTransformations = new javax.swing.JLabel();
		jpTransformationsButtons = new javax.swing.JPanel();
		jbTransformationsInsert = new javax.swing.JButton();
		jbTransformationsDelete = new javax.swing.JButton();
		jbTransformationsMoveUp = new javax.swing.JButton();
		jbTransformationsMoveDown = new javax.swing.JButton();
		jspTransformations = new javax.swing.JScrollPane();
		jtTransformations = new javax.swing.JTable();
		jpTransformationEditor = new javax.swing.JPanel();
		jpTransformation = new javax.swing.JPanel();
		jlTransformationObjectId = new javax.swing.JLabel();
		jlTransformationName = new javax.swing.JLabel();
		jtfTransformationObjectId = new javax.swing.JTextField();
		jtfTransformationName = new javax.swing.JTextField();
		jpAttributeTransformation = new javax.swing.JPanel();
		jrbAttributeTransformation = new javax.swing.JRadioButton();
		jlAttributeTransformationType = new javax.swing.JLabel();
		jlAttributeTransformationValue = new javax.swing.JLabel();
		jtfAttributeTransformationValue = new javax.swing.JTextField();
		jcbAttributeTransformationValue = new javax.swing.JComboBox<>();
		jpLinkTransformation = new javax.swing.JPanel();
		jrbLinkTransformation = new javax.swing.JRadioButton();
		jlLinkTransformationId2new = new javax.swing.JLabel();
		jtfLinkTransformationId2new = new javax.swing.JTextField();
		jmbMenu = new javax.swing.JMenuBar();
		jmTaskDescription = new javax.swing.JMenu();
		jmiTaskDescriptionLoad = new javax.swing.JMenuItem();
		jmiTaskDescriptionSave = new javax.swing.JMenuItem();
		jmSystemTransformations = new javax.swing.JMenu();
		jmiSystemTransformationsLoad = new javax.swing.JMenuItem();
		jmNodeNetwork = new javax.swing.JMenu();
		jmiNodeNetworkLoad = new javax.swing.JMenuItem();
		jmProcess = new javax.swing.JMenu();
		jmiProcessLoad = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Editor");
		setName("jfEditor"); // NOI18N

		jspData.setPreferredSize(new java.awt.Dimension(150, 275));

		jtData.setModel(editorDataModel);
		jtData.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				jtDataValueChanged(evt);
			}
		});
		jspData.setViewportView(jtData);

		jspWorkArea.setLeftComponent(jspData);

		jlSystemName.setText("name");

		jlSystemType.setText("type");

		jtfSystemName.setText("system-non-unique-name");

		jcbSystemType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "initial", "regular", "final" }));
		jcbSystemType.setSelectedIndex(1);

		javax.swing.GroupLayout jpSystemLayout = new javax.swing.GroupLayout(jpSystem);
		jpSystem.setLayout(jpSystemLayout);
		jpSystemLayout.setHorizontalGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpSystemLayout.createSequentialGroup().addComponent(jlSystemName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jtfSystemName))
								.addGroup(jpSystemLayout.createSequentialGroup().addComponent(jlSystemType)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jcbSystemType, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addContainerGap()));
		jpSystemLayout.setVerticalGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemName).addComponent(jtfSystemName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemType).addComponent(jcbSystemType,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jspSystemData.setDividerLocation(150);
		jspSystemData.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		jpObjectsEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlObjects.setText("Objects");

		jtObjects.setModel(objectsDataModel);
		jspObjects.setViewportView(jtObjects);

		jbObjectsInsert.setText("Insert");

		jbObjectsDelete.setText("Delete");

		javax.swing.GroupLayout jpObjectsButtonsLayout = new javax.swing.GroupLayout(jpObjectsButtons);
		jpObjectsButtons.setLayout(jpObjectsButtonsLayout);
		jpObjectsButtonsLayout.setHorizontalGroup(jpObjectsButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbObjectsDelete).addComponent(jbObjectsInsert))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpObjectsButtonsLayout.setVerticalGroup(jpObjectsButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbObjectsInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbObjectsDelete).addContainerGap(62, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpObjectsEditorLayout = new javax.swing.GroupLayout(jpObjectsEditor);
		jpObjectsEditor.setLayout(jpObjectsEditorLayout);
		jpObjectsEditorLayout.setHorizontalGroup(jpObjectsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlObjects)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jpObjectsEditorLayout.createSequentialGroup()
						.addComponent(jspObjects, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpObjectsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpObjectsEditorLayout.setVerticalGroup(jpObjectsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlObjects)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpObjectsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspObjects, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE))));

		jspSystemData.setTopComponent(jpObjectsEditor);

		jpLinksEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlLinks.setText("Links");

		jbLinksInsert.setText("Insert");

		jbLinksDelete.setText("Delete");

		javax.swing.GroupLayout jpLinksButtonsLayout = new javax.swing.GroupLayout(jpLinksButtons);
		jpLinksButtons.setLayout(jpLinksButtonsLayout);
		jpLinksButtonsLayout.setHorizontalGroup(jpLinksButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpLinksButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbLinksInsert).addComponent(jbLinksDelete))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpLinksButtonsLayout.setVerticalGroup(jpLinksButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbLinksInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbLinksDelete)
						.addContainerGap(216, Short.MAX_VALUE)));

		jtLinks.setModel(linksDataModel);
		jspLinks.setViewportView(jtLinks);

		javax.swing.GroupLayout jpLinksEditorLayout = new javax.swing.GroupLayout(jpLinksEditor);
		jpLinksEditor.setLayout(jpLinksEditorLayout);
		jpLinksEditorLayout
				.setHorizontalGroup(jpLinksEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinksEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlLinks)
								.addGap(93, 439, Short.MAX_VALUE))
						.addGroup(jpLinksEditorLayout.createSequentialGroup()
								.addComponent(jspLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpLinksButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpLinksEditorLayout.setVerticalGroup(jpLinksEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlLinks)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpLinksEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpLinksButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))));

		jspSystemData.setRightComponent(jpLinksEditor);

		javax.swing.GroupLayout jpSystemDataLayout = new javax.swing.GroupLayout(jpSystemData);
		jpSystemData.setLayout(jpSystemDataLayout);
		jpSystemDataLayout.setHorizontalGroup(jpSystemDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemData));
		jpSystemDataLayout.setVerticalGroup(jpSystemDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemData));

		javax.swing.GroupLayout jpSystemEditorLayout = new javax.swing.GroupLayout(jpSystemEditor);
		jpSystemEditor.setLayout(jpSystemEditorLayout);
		jpSystemEditorLayout.setHorizontalGroup(jpSystemEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpSystem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpSystemData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jpSystemEditorLayout
				.setVerticalGroup(jpSystemEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpSystemEditorLayout.createSequentialGroup()
								.addComponent(jpSystem, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpSystemData, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtpEditors.addTab("System", jpSystemEditor);

		jlNodeId.setText("id");

		jcbNodeChecked.setText("checked");

		jtfNodeId.setText("unique-node-id");

		javax.swing.GroupLayout jpNodeLayout = new javax.swing.GroupLayout(jpNode);
		jpNode.setLayout(jpNodeLayout);
		jpNodeLayout.setHorizontalGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpNodeLayout.createSequentialGroup().addContainerGap().addComponent(jlNodeId)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpNodeLayout.createSequentialGroup().addComponent(jcbNodeChecked).addGap(0, 0,
										Short.MAX_VALUE))
								.addComponent(jtfNodeId, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
						.addContainerGap()));
		jpNodeLayout.setVerticalGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpNodeLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlNodeId).addComponent(jtfNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jcbNodeChecked)));

		jpEdgesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlEdges.setText("Edges");

		jtEdges.setModel(
				new javax.swing.table.DefaultTableModel(new Object[][] { { "unique-edge-id", "another-unique-node-id" },
						{ null, null }, { null, null }, { null, null } }, new String[] { "id", "endNodeId" }) {
					private static final long serialVersionUID = 8867465125994345382L;
					Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class };

					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return types[columnIndex];
					}
				});
		jspEdges.setViewportView(jtEdges);

		jbEdgesInsert.setText("Insert");

		jbEdgesDelete.setText("Delete");

		jbEdgesUp.setText("Up");

		jbEdgesDown.setText("Down");

		javax.swing.GroupLayout jpEdgesButtonsLayout = new javax.swing.GroupLayout(jpEdgesButtons);
		jpEdgesButtons.setLayout(jpEdgesButtonsLayout);
		jpEdgesButtonsLayout.setHorizontalGroup(jpEdgesButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpEdgesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbEdgesInsert).addComponent(jbEdgesDelete).addComponent(jbEdgesUp)
								.addComponent(jbEdgesDown))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpEdgesButtonsLayout.setVerticalGroup(jpEdgesButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbEdgesInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbEdgesDelete)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbEdgesUp)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbEdgesDown)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpEdgesEditorLayout = new javax.swing.GroupLayout(jpEdgesEditor);
		jpEdgesEditor.setLayout(jpEdgesEditorLayout);
		jpEdgesEditorLayout
				.setHorizontalGroup(jpEdgesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlEdges)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(jpEdgesEditorLayout.createSequentialGroup()
								.addComponent(jspEdges, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpEdgesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpEdgesEditorLayout.setVerticalGroup(jpEdgesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlEdges)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpEdgesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspEdges, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))));

		javax.swing.GroupLayout jpNodeEditorLayout = new javax.swing.GroupLayout(jpNodeEditor);
		jpNodeEditor.setLayout(jpNodeEditorLayout);
		jpNodeEditorLayout.setHorizontalGroup(jpNodeEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpNode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpEdgesEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jpNodeEditorLayout
				.setVerticalGroup(jpNodeEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpNodeEditorLayout.createSequentialGroup()
								.addComponent(jpNode, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpEdgesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtpEditors.addTab("Node", jpNodeEditor);

		jlEdgeId.setText("id");

		jlBeginNodeId.setText("beginNodeId");

		jlEndNodeId.setText("endNodeId");

		jtfEdgeId.setText("edge-unique-id");

		jtfBeginNodeId.setText("node-unique-id-1");

		jtfEndNodeId.setText("node-unique-id-2");

		javax.swing.GroupLayout jpEdgeLayout = new javax.swing.GroupLayout(jpEdge);
		jpEdge.setLayout(jpEdgeLayout);
		jpEdgeLayout
				.setHorizontalGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgeLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jlBeginNodeId).addComponent(jlEndNodeId).addComponent(jlEdgeId))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jtfEdgeId).addComponent(jtfEndNodeId,
												javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
										.addComponent(jtfBeginNodeId))
								.addContainerGap()));
		jpEdgeLayout.setVerticalGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdgeLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlEdgeId).addComponent(jtfEdgeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfBeginNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlBeginNodeId))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfEndNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlEndNodeId))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpOperationEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlOperationName.setText("name");

		jtfOperationName.setText("operation-name");

		javax.swing.GroupLayout jpOperationLayout = new javax.swing.GroupLayout(jpOperation);
		jpOperation.setLayout(jpOperationLayout);
		jpOperationLayout.setHorizontalGroup(jpOperationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationLayout.createSequentialGroup().addContainerGap().addComponent(jlOperationName)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jtfOperationName).addContainerGap()));
		jpOperationLayout.setVerticalGroup(jpOperationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlOperationName).addComponent(jtfOperationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jlOperation.setText("Operation");

		jlParameters.setText("Parameters");

		jbParametersInsert.setText("Insert");

		jbParametersDelete.setText("Delete");

		jbParametersUp.setText("Up");

		jbParametersDown.setText("Down");

		javax.swing.GroupLayout jpParametersButtonsLayout = new javax.swing.GroupLayout(jpParametersButtons);
		jpParametersButtons.setLayout(jpParametersButtonsLayout);
		jpParametersButtonsLayout.setHorizontalGroup(
				jpParametersButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpParametersButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpParametersButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbParametersDelete).addComponent(jbParametersUp)
										.addComponent(jbParametersDown))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jpParametersButtonsLayout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jbParametersInsert).addContainerGap()));
		jpParametersButtonsLayout.setVerticalGroup(
				jpParametersButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpParametersButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbParametersInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbParametersDelete)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbParametersUp)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbParametersDown)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtParameters.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { "parameter-diameter", "2" } },
				new String[] { "name", "value" }) {
			private static final long serialVersionUID = 7080823647817852495L;
			Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jspParameters.setViewportView(jtParameters);

		javax.swing.GroupLayout jpParametersEditorLayout = new javax.swing.GroupLayout(jpParametersEditor);
		jpParametersEditor.setLayout(jpParametersEditorLayout);
		jpParametersEditorLayout.setHorizontalGroup(jpParametersEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpParametersEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlParameters)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jpParametersEditorLayout.createSequentialGroup()
								.addComponent(jspParameters, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpParametersButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpParametersEditorLayout.setVerticalGroup(jpParametersEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpParametersEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlParameters)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jpParametersEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpParametersButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspParameters, javax.swing.GroupLayout.DEFAULT_SIZE, 333,
												Short.MAX_VALUE))));

		javax.swing.GroupLayout jpOperationEditorLayout = new javax.swing.GroupLayout(jpOperationEditor);
		jpOperationEditor.setLayout(jpOperationEditorLayout);
		jpOperationEditorLayout.setHorizontalGroup(jpOperationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlOperation)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addComponent(jpOperation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpParametersEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpOperationEditorLayout.setVerticalGroup(jpOperationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlOperation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpOperation, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpParametersEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpEdgeEditorLayout = new javax.swing.GroupLayout(jpEdgeEditor);
		jpEdgeEditor.setLayout(jpEdgeEditorLayout);
		jpEdgeEditorLayout.setHorizontalGroup(jpEdgeEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpEdge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpOperationEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpEdgeEditorLayout
				.setVerticalGroup(jpEdgeEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgeEditorLayout.createSequentialGroup()
								.addComponent(jpEdge, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpOperationEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtpEditors.addTab("Edge", jpEdgeEditor);

		jlObjectName.setText("name");

		jtfObjectName.setText("object-workpiece");

		jtfObjectId.setText("id-workpiece");

		jlObjectId.setText("id");

		javax.swing.GroupLayout jpObjectLayout = new javax.swing.GroupLayout(jpObject);
		jpObject.setLayout(jpObjectLayout);
		jpObjectLayout.setHorizontalGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										javax.swing.GroupLayout.Alignment.TRAILING,
										jpObjectLayout.createSequentialGroup().addComponent(jlObjectName)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(jpObjectLayout.createSequentialGroup().addComponent(jlObjectId).addGap(26, 26,
										26)))
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jtfObjectName, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
								.addComponent(jtfObjectId))
						.addContainerGap()));
		jpObjectLayout.setVerticalGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlObjectName).addComponent(
										jtfObjectName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfObjectId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlObjectId))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpAttributesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlAttributes.setText("Attributes");

		jtAttributes.setModel(attributesDataModel);
		jspAttributes.setViewportView(jtAttributes);

		jbAttributesInsert.setText("Insert");

		jbAttributesDelete.setText("Delete");

		jbAttributesMoveUp.setText("Up");

		jbAttributesMoveDown.setText("Down");

		javax.swing.GroupLayout jpAttributesButtonsLayout = new javax.swing.GroupLayout(jpAttributesButtons);
		jpAttributesButtons.setLayout(jpAttributesButtonsLayout);
		jpAttributesButtonsLayout.setHorizontalGroup(
				jpAttributesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributesButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpAttributesButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbAttributesInsert).addComponent(jbAttributesDelete)
										.addComponent(jbAttributesMoveUp).addComponent(jbAttributesMoveDown))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpAttributesButtonsLayout.setVerticalGroup(
				jpAttributesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributesButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbAttributesInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbAttributesDelete)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbAttributesMoveUp)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbAttributesMoveDown)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpAttributesEditorLayout = new javax.swing.GroupLayout(jpAttributesEditor);
		jpAttributesEditor.setLayout(jpAttributesEditorLayout);
		jpAttributesEditorLayout.setHorizontalGroup(jpAttributesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlAttributes)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jpAttributesEditorLayout.createSequentialGroup()
						.addComponent(jspAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpAttributesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpAttributesEditorLayout.setVerticalGroup(jpAttributesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlAttributes)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jpAttributesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpAttributesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspAttributes, javax.swing.GroupLayout.DEFAULT_SIZE, 429,
												Short.MAX_VALUE))));

		javax.swing.GroupLayout jpObjectEditorLayout = new javax.swing.GroupLayout(jpObjectEditor);
		jpObjectEditor.setLayout(jpObjectEditorLayout);
		jpObjectEditorLayout.setHorizontalGroup(jpObjectEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpObject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpAttributesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpObjectEditorLayout
				.setVerticalGroup(jpObjectEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectEditorLayout.createSequentialGroup()
								.addComponent(jpObject, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtpEditors.addTab("Object", jpObjectEditor);

		jlActionName.setText("name");

		jtfActionName.setText("operation-name");

		javax.swing.GroupLayout jpActionLayout = new javax.swing.GroupLayout(jpAction);
		jpAction.setLayout(jpActionLayout);
		jpActionLayout.setHorizontalGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionLayout.createSequentialGroup().addContainerGap().addComponent(jlActionName)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jtfActionName, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
						.addContainerGap()));
		jpActionLayout.setVerticalGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlActionName).addComponent(jtfActionName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpActionFunctionsEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlActionFunctions.setText("Action Functions");

		jbActionFunctionsInsert.setText("Insert");

		jbActionFunctionsDelete.setText("Delete");

		jbActionFunctionsMoveUp.setText("Up");

		jbActionFunctionsMoveDown.setText("Down");

		javax.swing.GroupLayout jpActionFunctionsButtonsLayout = new javax.swing.GroupLayout(jpActionFunctionsButtons);
		jpActionFunctionsButtons.setLayout(jpActionFunctionsButtonsLayout);
		jpActionFunctionsButtonsLayout.setHorizontalGroup(
				jpActionFunctionsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpActionFunctionsButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbActionFunctionsInsert).addComponent(jbActionFunctionsDelete)
										.addComponent(jbActionFunctionsMoveUp).addComponent(jbActionFunctionsMoveDown))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpActionFunctionsButtonsLayout.setVerticalGroup(
				jpActionFunctionsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbActionFunctionsInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbActionFunctionsDelete)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbActionFunctionsMoveUp)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbActionFunctionsMoveDown)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtActionFunctions.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "unknown", "preConditionChecker" }, { "unknown", "parameterUpdater" } },
				new String[] { "name", "type" }) {
			private static final long serialVersionUID = -6169236409552748177L;
			Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jspActionFunctions.setViewportView(jtActionFunctions);

		javax.swing.GroupLayout jpActionFunctionsEditorLayout = new javax.swing.GroupLayout(jpActionFunctionsEditor);
		jpActionFunctionsEditor.setLayout(jpActionFunctionsEditorLayout);
		jpActionFunctionsEditorLayout.setHorizontalGroup(
				jpActionFunctionsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlActionFunctions)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup()
								.addComponent(jspActionFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpActionFunctionsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpActionFunctionsEditorLayout.setVerticalGroup(
				jpActionFunctionsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlActionFunctions)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpActionFunctionsEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpActionFunctionsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspActionFunctions, javax.swing.GroupLayout.DEFAULT_SIZE, 457,
												Short.MAX_VALUE))));

		javax.swing.GroupLayout jpActionEditorLayout = new javax.swing.GroupLayout(jpActionEditor);
		jpActionEditor.setLayout(jpActionEditorLayout);
		jpActionEditorLayout.setHorizontalGroup(jpActionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpActionFunctionsEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpActionEditorLayout
				.setVerticalGroup(jpActionEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionEditorLayout.createSequentialGroup()
								.addComponent(jpAction, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpActionFunctionsEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtpEditors.addTab("Action", jpActionEditor);

		jlActionFunctionType.setText("type");

		jcbActionFunctionType.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "preConditionChecker", "parameterUpdater" }));

		jtaActionFunctionLines.setColumns(20);
		jtaActionFunctionLines.setRows(5);
		jtaActionFunctionLines.setText("lua line 1\nlua line 2\nlua line 3\nlua line 4");
		jspActionFunctionLines.setViewportView(jtaActionFunctionLines);

		javax.swing.GroupLayout jpActionFunctionEditorLayout = new javax.swing.GroupLayout(jpActionFunctionEditor);
		jpActionFunctionEditor.setLayout(jpActionFunctionEditorLayout);
		jpActionFunctionEditorLayout.setHorizontalGroup(jpActionFunctionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionFunctionEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionFunctionEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jspActionFunctionLines, javax.swing.GroupLayout.DEFAULT_SIZE, 462,
										Short.MAX_VALUE)
								.addGroup(jpActionFunctionEditorLayout.createSequentialGroup()
										.addComponent(jlActionFunctionType)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jcbActionFunctionType, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addContainerGap()));
		jpActionFunctionEditorLayout.setVerticalGroup(jpActionFunctionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionFunctionEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionFunctionEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlActionFunctionType).addComponent(jcbActionFunctionType,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jspActionFunctionLines, javax.swing.GroupLayout.DEFAULT_SIZE, 487,
								Short.MAX_VALUE)
						.addContainerGap()));

		jtpEditors.addTab("Action Function", jpActionFunctionEditor);

		jlSystemTransformationName.setText("name");

		jtfSystemTransformationName.setText("system transformation name");

		javax.swing.GroupLayout jpSystemTransformationEditorLayout = new javax.swing.GroupLayout(
				jpSystemTransformationEditor);
		jpSystemTransformationEditor.setLayout(jpSystemTransformationEditorLayout);
		jpSystemTransformationEditorLayout
				.setHorizontalGroup(
						jpSystemTransformationEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
										jpSystemTransformationEditorLayout.createSequentialGroup().addContainerGap()
												.addComponent(jlSystemTransformationName)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jtfSystemTransformationName,
														javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
												.addContainerGap()));
		jpSystemTransformationEditorLayout.setVerticalGroup(jpSystemTransformationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTransformationEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemTransformationEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemTransformationName).addComponent(jtfSystemTransformationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(499, Short.MAX_VALUE)));

		jtpEditors.addTab("System Transformation", jpSystemTransformationEditor);

		jlTransformations.setText("Transformations");

		jbTransformationsInsert.setText("Insert");

		jbTransformationsDelete.setText("Delete");

		jbTransformationsMoveUp.setText("Up");

		jbTransformationsMoveDown.setText("Down");

		javax.swing.GroupLayout jpTransformationsButtonsLayout = new javax.swing.GroupLayout(jpTransformationsButtons);
		jpTransformationsButtons.setLayout(jpTransformationsButtonsLayout);
		jpTransformationsButtonsLayout.setHorizontalGroup(
				jpTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpTransformationsButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbTransformationsInsert).addComponent(jbTransformationsDelete)
										.addComponent(jbTransformationsMoveUp).addComponent(jbTransformationsMoveDown))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpTransformationsButtonsLayout.setVerticalGroup(
				jpTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbTransformationsInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsDelete)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsMoveUp)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsMoveDown)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtTransformations.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "attributeTransformation", "id-requirement", "attribute-is-diameter" },
						{ "attributeTransformation", "id-requirement", "attribute-is-completed" },
						{ "linkTransformation", "id-requirement", "link-is-requirement" } },
				new String[] { "type", "object-id", "name" }) {
			private static final long serialVersionUID = -5223158952414941746L;
			Class<?>[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jspTransformations.setViewportView(jtTransformations);

		javax.swing.GroupLayout jpTransformationsEditorLayout = new javax.swing.GroupLayout(jpTransformationsEditor);
		jpTransformationsEditor.setLayout(jpTransformationsEditorLayout);
		jpTransformationsEditorLayout.setHorizontalGroup(jpTransformationsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationsEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlTransformations).addContainerGap(382, Short.MAX_VALUE))
				.addGroup(jpTransformationsEditorLayout.createSequentialGroup()
						.addComponent(jspTransformations, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpTransformationsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpTransformationsEditorLayout.setVerticalGroup(
				jpTransformationsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlTransformations)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpTransformationsEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpTransformationsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspTransformations, javax.swing.GroupLayout.DEFAULT_SIZE, 499,
												Short.MAX_VALUE))));

		jtpEditors.addTab("Transformations", jpTransformationsEditor);

		jlTransformationObjectId.setText("object-id");

		jlTransformationName.setText("name");

		jtfTransformationObjectId.setText("id-requirement");

		jtfTransformationName.setText("attribute-diameter-requirement-status");

		javax.swing.GroupLayout jpTransformationLayout = new javax.swing.GroupLayout(jpTransformation);
		jpTransformation.setLayout(jpTransformationLayout);
		jpTransformationLayout.setHorizontalGroup(jpTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jlTransformationObjectId).addComponent(jlTransformationName))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jtfTransformationName, javax.swing.GroupLayout.DEFAULT_SIZE, 408,
												Short.MAX_VALUE)
										.addComponent(jtfTransformationObjectId))
						.addContainerGap()));
		jpTransformationLayout.setVerticalGroup(jpTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlTransformationObjectId).addComponent(jtfTransformationObjectId,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlTransformationName).addComponent(jtfTransformationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpAttributeTransformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		bgTransformationType.add(jrbAttributeTransformation);
		jrbAttributeTransformation.setText("attributeTransformation");

		jlAttributeTransformationType.setText("type");

		jlAttributeTransformationValue.setText("value");

		jtfAttributeTransformationValue.setText("true");

		jcbAttributeTransformationValue
				.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "boolean", "integer", "string" }));

		javax.swing.GroupLayout jpAttributeTransformationLayout = new javax.swing.GroupLayout(
				jpAttributeTransformation);
		jpAttributeTransformation.setLayout(jpAttributeTransformationLayout);
		jpAttributeTransformationLayout.setHorizontalGroup(
				jpAttributeTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributeTransformationLayout.createSequentialGroup()
								.addComponent(jrbAttributeTransformation).addGap(0, 0, Short.MAX_VALUE))
						.addGroup(jpAttributeTransformationLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpAttributeTransformationLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jlAttributeTransformationValue)
										.addComponent(jlAttributeTransformationType))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpAttributeTransformationLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jtfAttributeTransformationValue)
										.addComponent(jcbAttributeTransformationValue, 0,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap()));
		jpAttributeTransformationLayout.setVerticalGroup(jpAttributeTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributeTransformationLayout.createSequentialGroup().addContainerGap()
						.addComponent(jrbAttributeTransformation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpAttributeTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlAttributeTransformationType)
								.addComponent(jcbAttributeTransformationValue, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpAttributeTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlAttributeTransformationValue)
								.addComponent(jtfAttributeTransformationValue, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpLinkTransformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		bgTransformationType.add(jrbLinkTransformation);
		jrbLinkTransformation.setText("linkTransformation");

		jlLinkTransformationId2new.setText("id2new");

		jtfLinkTransformationId2new.setText("id-cylinder-surface");

		javax.swing.GroupLayout jpLinkTransformationLayout = new javax.swing.GroupLayout(jpLinkTransformation);
		jpLinkTransformation.setLayout(jpLinkTransformationLayout);
		jpLinkTransformationLayout.setHorizontalGroup(
				jpLinkTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinkTransformationLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpLinkTransformationLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jpLinkTransformationLayout.createSequentialGroup()
												.addComponent(jrbLinkTransformation).addGap(0, 0, Short.MAX_VALUE))
										.addGroup(jpLinkTransformationLayout.createSequentialGroup()
												.addComponent(jlLinkTransformationId2new)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jtfLinkTransformationId2new)))
								.addContainerGap()));
		jpLinkTransformationLayout.setVerticalGroup(jpLinkTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinkTransformationLayout.createSequentialGroup().addContainerGap()
						.addComponent(jrbLinkTransformation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpLinkTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlLinkTransformationId2new).addComponent(jtfLinkTransformationId2new,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpTransformationEditorLayout = new javax.swing.GroupLayout(jpTransformationEditor);
		jpTransformationEditor.setLayout(jpTransformationEditorLayout);
		jpTransformationEditorLayout.setHorizontalGroup(
				jpTransformationEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jpTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpAttributeTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpLinkTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpTransformationEditorLayout.setVerticalGroup(
				jpTransformationEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationEditorLayout.createSequentialGroup()
								.addComponent(jpTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributeTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpLinkTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 299, Short.MAX_VALUE)));

		jtpEditors.addTab("Transformation", jpTransformationEditor);

		jspWorkArea.setRightComponent(jtpEditors);

		jmTaskDescription.setText("Task Description");

		jmiTaskDescriptionLoad.setText("Load");
		jmTaskDescription.add(jmiTaskDescriptionLoad);

		jmiTaskDescriptionSave.setText("Save");
		jmTaskDescription.add(jmiTaskDescriptionSave);

		jmbMenu.add(jmTaskDescription);

		jmSystemTransformations.setText("System Transformations");

		jmiSystemTransformationsLoad.setText("Load");
		jmSystemTransformations.add(jmiSystemTransformationsLoad);

		jmbMenu.add(jmSystemTransformations);

		jmNodeNetwork.setText("Node Network");

		jmiNodeNetworkLoad.setText("Load");
		jmNodeNetwork.add(jmiNodeNetworkLoad);

		jmbMenu.add(jmNodeNetwork);

		jmProcess.setText("Process");

		jmiProcessLoad.setText("Load");
		jmProcess.add(jmiProcessLoad);

		jmbMenu.add(jmProcess);

		setJMenuBar(jmbMenu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspWorkArea));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspWorkArea));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	@SuppressWarnings("PMD.UnusedFormalParameter")
	private void jtDataValueChanged(javax.swing.event.TreeSelectionEvent evt) {// GEN-FIRST:event_jtDataValueChanged
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jtData.getLastSelectedPathComponent();
		if (selectedNode == null) {
			return;
		}
		Object selectedObject = selectedNode.getUserObject();
		if (selectedObject instanceof TaskDescription) {
			java.lang.System.out.println("selected TaskDescription");
		} else if (selectedObject instanceof System) {
			systemDataModel.clear();
			jtpEditors.setSelectedComponent(jpSystemEditor);
			systemDataModel.loadSystem((System) selectedObject, selectedNode);
			objectsDataModel.loadObjects((System) selectedObject, selectedNode);
			linksDataModel.loadLinks((System) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemObject) {
			jtpEditors.setSelectedComponent(jpObjectEditor);
			loadSystemObject((SystemObject) selectedObject);
			attributesDataModel.loadAttributes((SystemObject) selectedObject);
		} else {
			java.lang.System.out.println("unknown: " + selectedObject.toString());
		}
	}// GEN-LAST:event_jtDataValueChanged

	private void loadSystemObject(SystemObject selectedObject) {
		String name = selectedObject.getName();
		String id = selectedObject.getId();
		jtfObjectName.setText(name);
		jtfObjectId.setText(id);
	}

	public static void main(String args[]) throws Exception {
		UserInterfaceFactory.initializeLookAndFeel();
		SwingUtilities.invokeLater(() -> {
			new EditorFrame(new Application()).setVisible(true);
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup bgTransformationType;
	private javax.swing.JButton jbActionFunctionsDelete;
	private javax.swing.JButton jbActionFunctionsInsert;
	private javax.swing.JButton jbActionFunctionsMoveDown;
	private javax.swing.JButton jbActionFunctionsMoveUp;
	private javax.swing.JButton jbAttributesDelete;
	private javax.swing.JButton jbAttributesInsert;
	private javax.swing.JButton jbAttributesMoveDown;
	private javax.swing.JButton jbAttributesMoveUp;
	private javax.swing.JButton jbEdgesDelete;
	private javax.swing.JButton jbEdgesDown;
	private javax.swing.JButton jbEdgesInsert;
	private javax.swing.JButton jbEdgesUp;
	private javax.swing.JButton jbLinksDelete;
	private javax.swing.JButton jbLinksInsert;
	private javax.swing.JButton jbObjectsDelete;
	private javax.swing.JButton jbObjectsInsert;
	private javax.swing.JButton jbParametersDelete;
	private javax.swing.JButton jbParametersDown;
	private javax.swing.JButton jbParametersInsert;
	private javax.swing.JButton jbParametersUp;
	private javax.swing.JButton jbTransformationsDelete;
	private javax.swing.JButton jbTransformationsInsert;
	private javax.swing.JButton jbTransformationsMoveDown;
	private javax.swing.JButton jbTransformationsMoveUp;
	private javax.swing.JComboBox<String> jcbActionFunctionType;
	private javax.swing.JComboBox<String> jcbAttributeTransformationValue;
	private javax.swing.JCheckBox jcbNodeChecked;
	private javax.swing.JComboBox<String> jcbSystemType;
	private javax.swing.JLabel jlActionFunctionType;
	private javax.swing.JLabel jlActionFunctions;
	private javax.swing.JLabel jlActionName;
	private javax.swing.JLabel jlAttributeTransformationType;
	private javax.swing.JLabel jlAttributeTransformationValue;
	private javax.swing.JLabel jlAttributes;
	private javax.swing.JLabel jlBeginNodeId;
	private javax.swing.JLabel jlEdgeId;
	private javax.swing.JLabel jlEdges;
	private javax.swing.JLabel jlEndNodeId;
	private javax.swing.JLabel jlLinkTransformationId2new;
	private javax.swing.JLabel jlLinks;
	private javax.swing.JLabel jlNodeId;
	private javax.swing.JLabel jlObjectId;
	private javax.swing.JLabel jlObjectName;
	private javax.swing.JLabel jlObjects;
	private javax.swing.JLabel jlOperation;
	private javax.swing.JLabel jlOperationName;
	private javax.swing.JLabel jlParameters;
	private javax.swing.JLabel jlSystemName;
	private javax.swing.JLabel jlSystemTransformationName;
	private javax.swing.JLabel jlSystemType;
	private javax.swing.JLabel jlTransformationName;
	private javax.swing.JLabel jlTransformationObjectId;
	private javax.swing.JLabel jlTransformations;
	private javax.swing.JMenu jmNodeNetwork;
	private javax.swing.JMenu jmProcess;
	private javax.swing.JMenu jmSystemTransformations;
	private javax.swing.JMenu jmTaskDescription;
	private javax.swing.JMenuBar jmbMenu;
	private javax.swing.JMenuItem jmiNodeNetworkLoad;
	private javax.swing.JMenuItem jmiProcessLoad;
	private javax.swing.JMenuItem jmiSystemTransformationsLoad;
	private javax.swing.JMenuItem jmiTaskDescriptionLoad;
	private javax.swing.JMenuItem jmiTaskDescriptionSave;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionEditor;
	private javax.swing.JPanel jpActionFunctionEditor;
	private javax.swing.JPanel jpActionFunctionsButtons;
	private javax.swing.JPanel jpActionFunctionsEditor;
	private javax.swing.JPanel jpAttributeTransformation;
	private javax.swing.JPanel jpAttributesButtons;
	private javax.swing.JPanel jpAttributesEditor;
	private javax.swing.JPanel jpEdge;
	private javax.swing.JPanel jpEdgeEditor;
	private javax.swing.JPanel jpEdgesButtons;
	private javax.swing.JPanel jpEdgesEditor;
	private javax.swing.JPanel jpLinkTransformation;
	private javax.swing.JPanel jpLinksButtons;
	private javax.swing.JPanel jpLinksEditor;
	private javax.swing.JPanel jpNode;
	private javax.swing.JPanel jpNodeEditor;
	private javax.swing.JPanel jpObject;
	private javax.swing.JPanel jpObjectEditor;
	private javax.swing.JPanel jpObjectsButtons;
	private javax.swing.JPanel jpObjectsEditor;
	private javax.swing.JPanel jpOperation;
	private javax.swing.JPanel jpOperationEditor;
	private javax.swing.JPanel jpParametersButtons;
	private javax.swing.JPanel jpParametersEditor;
	private javax.swing.JPanel jpSystem;
	private javax.swing.JPanel jpSystemData;
	private javax.swing.JPanel jpSystemEditor;
	private javax.swing.JPanel jpSystemTransformationEditor;
	private javax.swing.JPanel jpTransformation;
	private javax.swing.JPanel jpTransformationEditor;
	private javax.swing.JPanel jpTransformationsButtons;
	private javax.swing.JPanel jpTransformationsEditor;
	private javax.swing.JRadioButton jrbAttributeTransformation;
	private javax.swing.JRadioButton jrbLinkTransformation;
	private javax.swing.JScrollPane jspActionFunctionLines;
	private javax.swing.JScrollPane jspActionFunctions;
	private javax.swing.JScrollPane jspAttributes;
	private javax.swing.JScrollPane jspData;
	private javax.swing.JScrollPane jspEdges;
	private javax.swing.JScrollPane jspLinks;
	private javax.swing.JScrollPane jspObjects;
	private javax.swing.JScrollPane jspParameters;
	private javax.swing.JSplitPane jspSystemData;
	private javax.swing.JScrollPane jspTransformations;
	private javax.swing.JSplitPane jspWorkArea;
	private javax.swing.JTable jtActionFunctions;
	private javax.swing.JTable jtAttributes;
	private javax.swing.JTree jtData;
	private javax.swing.JTable jtEdges;
	private javax.swing.JTable jtLinks;
	private javax.swing.JTable jtObjects;
	private javax.swing.JTable jtParameters;
	private javax.swing.JTable jtTransformations;
	private javax.swing.JTextArea jtaActionFunctionLines;
	private javax.swing.JTextField jtfActionName;
	private javax.swing.JTextField jtfAttributeTransformationValue;
	private javax.swing.JTextField jtfBeginNodeId;
	private javax.swing.JTextField jtfEdgeId;
	private javax.swing.JTextField jtfEndNodeId;
	private javax.swing.JTextField jtfLinkTransformationId2new;
	private javax.swing.JTextField jtfNodeId;
	private javax.swing.JTextField jtfObjectId;
	private javax.swing.JTextField jtfObjectName;
	private javax.swing.JTextField jtfOperationName;
	private javax.swing.JTextField jtfSystemName;
	private javax.swing.JTextField jtfSystemTransformationName;
	private javax.swing.JTextField jtfTransformationName;
	private javax.swing.JTextField jtfTransformationObjectId;
	private javax.swing.JTabbedPane jtpEditors;
	// End of variables declaration//GEN-END:variables
}
