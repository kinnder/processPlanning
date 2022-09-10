package application.ui.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import application.ui.UserInterfaceFactory;

public class AboutFrame extends JDialog {
	private static final long serialVersionUID = 8304157905705554451L;

	private JLabel jlVersion;

	private JTextField jtfVersion;

	private JButton jbOk;

	public AboutFrame() {
		initComponents();
		setActions();
	}

	private void setActions() {
		jbOk.setAction(okAction);
	}

	private void initComponents() {
		setTitle("About");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new GridBagLayout());
		GridBagConstraints gbcConstraints;

		jlVersion = new JLabel();
		jlVersion.setText("Version");
		gbcConstraints = new GridBagConstraints();
		gbcConstraints.gridx = 0;
		gbcConstraints.gridy = 0;
		gbcConstraints.insets = new Insets(10, 10, 0, 0);
		getContentPane().add(jlVersion, gbcConstraints);

		jtfVersion = new JTextField();
		jtfVersion.setText("0.3.0");
		jtfVersion.setEditable(false);
		jtfVersion.setHorizontalAlignment(JTextField.CENTER);
		gbcConstraints = new GridBagConstraints();
		gbcConstraints.gridx = 2;
		gbcConstraints.gridy = 0;
		gbcConstraints.insets = new Insets(10, 0, 0, 10);
		getContentPane().add(jtfVersion, gbcConstraints);

		jbOk = new JButton();
		jbOk.setPreferredSize(new Dimension(60, 25));
		gbcConstraints = new GridBagConstraints();
		gbcConstraints.gridx = 1;
		gbcConstraints.gridy = 1;
		gbcConstraints.insets = new Insets(10, 0, 10, 0);
		getContentPane().add(jbOk, gbcConstraints);

		pack();
	}

	Action okAction = new AbstractAction("Ok") {
		private static final long serialVersionUID = 8778056317506828817L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	public static void main(String args[]) throws Exception {
		UserInterfaceFactory.initializeLookAndFeel();
		SwingUtilities.invokeLater(() -> {
			new AboutFrame().setVisible(true);
		});
	}
}
