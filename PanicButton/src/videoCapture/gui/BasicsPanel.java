package videoCapture.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Common.Constants;

// TODO: Auto-generated Javadoc

/**
 * The View portion of the Basic Settings tab of the configuration window for
 * the Video Capturer
 */

public class BasicsPanel extends JPanel {

	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel listenPortLabel;
	private JTextField listenPortField;
	private JLabel publicLabel;
	private JCheckBox publicCheckbox;

	/**
	 * Instantiates a new basics panel.
	 */
	public BasicsPanel() {
		this.setMaximumSize(new Dimension(50, 50));
		JPanel fieldPanel = new JPanel(new GridLayout(3, 2));
		nameLabel = new JLabel("Name: ");
		nameField = new JTextField();

		listenPortLabel = new JLabel("Port to listen on: ");
		listenPortField = new JTextField();

		publicLabel = new JLabel("Advertise on local network: ");
		publicCheckbox = new JCheckBox();

		fieldPanel.add(nameLabel);
		fieldPanel.add(nameField);

		fieldPanel.add(listenPortLabel);
		fieldPanel.add(listenPortField);

		fieldPanel.add(publicLabel);
		fieldPanel.add(publicCheckbox);

		fieldPanel.setPreferredSize(new Dimension(600,
				Constants.VIEW_FIELD_HEIGHT * 3));

		this.add(fieldPanel);
	}

	/**
	 * Gets the capturer name.
	 * 
	 * @return the capturer name
	 */
	public String getCapturerName() {
		return nameField.getText();
	}

	/**
	 * Gets the listen port.
	 * 
	 * @return the listen port
	 */
	public String getListenPort() {
		return listenPortField.getText();
	}

	/**
	 * Does broadcast public.
	 * 
	 * @return true, if successful
	 */
	public boolean doesBroadcastPublic() {
		return publicCheckbox.isSelected();
	}

	/**
	 * Sets the capturer name.
	 * 
	 * @param name
	 *            the new capturer name
	 */
	public void setCapturerName(String name) {
		nameField.setText(name);
	}

	/**
	 * Sets the listen port.
	 * 
	 * @param port
	 *            the new listen port
	 */
	public void setListenPort(int port) {
		listenPortField.setText("" + port);
	}

	/**
	 * Sets the does broadcast.
	 * 
	 * @param broadcast
	 *            the new does broadcast
	 */
	public void setDoesBroadcast(boolean broadcast) {
		this.publicCheckbox.setSelected(broadcast);
	}
}
