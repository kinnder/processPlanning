package application.ui.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;
import application.ui.UserInterface;
import application.ui.UserInterfaceFactory;

public class MainViewFrame extends javax.swing.JFrame implements UserInterface {
	private static final long serialVersionUID = 514069652804189117L;

	private UserInterfaceFactory userInterfaceFactory;

	public MainViewFrame(Application application) {
		this(application, new UserInterfaceFactory());
	}

	MainViewFrame(Application application, UserInterfaceFactory userInterfaceFactory) {
		this.application = application;
		this.userInterfaceFactory = userInterfaceFactory;

		initComponents();
		setActions();
	}

	private void setActions() {
		jmiAbout.setAction(aboutAction);
		jmiConvert.setAction(convertAction);
		jmiExit.setAction(exitAction);
		jmiNewTaskDescription.setAction(newTaskDescriptionAction);
		jmiNewSystemTransformations.setAction(newSystemTransformationsAction);
		jmiOptions.setAction(optionsAction);
		jmiPlan.setAction(planAction);
		jmiUsage.setAction(usageAction);
		jmiVerify.setAction(verifyAction);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jtaLog = new javax.swing.JTextArea();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jmiOptions = new javax.swing.JMenuItem();
		jmiExit = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jmiConvert = new javax.swing.JMenuItem();
		jmiNewSystemTransformations = new javax.swing.JMenuItem();
		jmiNewTaskDescription = new javax.swing.JMenuItem();
		jmiPlan = new javax.swing.JMenuItem();
		jmiVerify = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jmiUsage = new javax.swing.JMenuItem();
		jmiAbout = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Process Engineering");
		setName("jfMainView"); // NOI18N

		jtaLog.setEditable(false);
		jtaLog.setColumns(20);
		jtaLog.setRows(5);
		jScrollPane1.setViewportView(jtaLog);

		jMenu1.setText("Application");

		jmiOptions.setText("Options");
		jMenu1.add(jmiOptions);

		jmiExit.setText("Exit");
		jMenu1.add(jmiExit);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Commands");

		jmiConvert.setText("Convert");
		jMenu2.add(jmiConvert);

		jmiNewSystemTransformations.setText("New Transformations");
		jMenu2.add(jmiNewSystemTransformations);

		jmiNewTaskDescription.setText("New Task");
		jMenu2.add(jmiNewTaskDescription);

		jmiPlan.setText("Plan");
		jMenu2.add(jmiPlan);

		jmiVerify.setText("Verify");
		jMenu2.add(jmiVerify);

		jMenuBar1.add(jMenu2);

		jMenu3.setText("Help");

		jmiUsage.setText("Usage");
		jMenu3.add(jmiUsage);

		jmiAbout.setText("About");
		jMenu3.add(jmiAbout);

		jMenuBar1.add(jMenu3);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 219,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	Action optionsAction = new AbstractAction("Options...") {
		private static final long serialVersionUID = 7353392578629559123L;

		@Override
		public void actionPerformed(ActionEvent evt) {
			OptionsFrame optionsFrame = userInterfaceFactory.createOptionsView(application);
			optionsFrame.updateComponents();
			optionsFrame.setVisible(true);
		}
	};

	Action exitAction = new AbstractAction("Exit") {
		private static final long serialVersionUID = 553215988718603009L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	Action planAction = new AbstractAction("Plan") {
		private static final long serialVersionUID = 5229889242759466230L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.plan();
		}
	};

	Action verifyAction = new AbstractAction("Verify") {
		private static final long serialVersionUID = 7353392578629559123L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.verify();
		}
	};

	Action newTaskDescriptionAction = new AbstractAction("New Task") {
		private static final long serialVersionUID = -6559945594959339816L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.newTaskDescription();
		}
	};

	Action newSystemTransformationsAction = new AbstractAction("New Transformations") {
		private static final long serialVersionUID = 8677870804332905954L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.newSystemTransformations();
		}
	};

	Action convertAction = new AbstractAction("Convert") {
		private static final long serialVersionUID = -5142639452014173083L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.convert();
		}
	};

	Action usageAction = new AbstractAction("Usage") {
		private static final long serialVersionUID = -5027301856157970456L;

		@Override
		public void actionPerformed(ActionEvent e) {
			application.usageHelp();
		}
	};

	Action aboutAction = new AbstractAction("About") {
		private static final long serialVersionUID = -968420889758107148L;

		@Override
		public void actionPerformed(ActionEvent e) {
			AboutFrame aboutFrame = userInterfaceFactory.createAboutView();
			aboutFrame.setVisible(true);
		}
	};

	public static void main(String args[]) throws Exception {
		initializeLookAndFeel();
		SwingUtilities.invokeLater(() -> {
			new MainViewFrame(new Application()).setVisible(true);
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JMenuItem jmiAbout;
	private javax.swing.JMenuItem jmiConvert;
	private javax.swing.JMenuItem jmiExit;
	private javax.swing.JMenuItem jmiNewSystemTransformations;
	private javax.swing.JMenuItem jmiNewTaskDescription;
	private javax.swing.JMenuItem jmiOptions;
	private javax.swing.JMenuItem jmiPlan;
	private javax.swing.JMenuItem jmiUsage;
	private javax.swing.JMenuItem jmiVerify;
	private javax.swing.JTextArea jtaLog;
	// End of variables declaration//GEN-END:variables

	private Application application;

	@Override
	public void notifyUserMessage(UserMessageEvent event) {
		jtaLog.append(event.message);
		jtaLog.append("\n");
	}

	@Override
	public void notifyCommandStatus(CommandStatusEvent event) {
		jtaLog.append(event.message);
		jtaLog.append("\n");
	}

	public static void initializeLookAndFeel() throws Exception {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	}

	@Override
	public void run() throws Exception {
		initializeLookAndFeel();
		setVisible(true);
	}
}
