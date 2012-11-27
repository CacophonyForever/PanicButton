/*
 * 
 */
package videoTrigger.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import videoTrigger.VideoTriggerClient;

// TODO: Auto-generated Javadoc
/**
 * The Class AddHostView.
 */
public class AddHostView extends JFrame {
	JTextField hostNameField;
	JLabel hostNameLabel;
	JTextField portField;
	JLabel portLabel;
	JButton okButton;
	JButton cancelButton;
	ActionListener actionListener;
	VideoTriggerController controller;

	/**
	 * Instantiates a new adds the host view.
	 * 
	 * @param client
	 *            the client
	 */
	public AddHostView(VideoTriggerController control) {
		controller=control;
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		JPanel entryGrid = new JPanel();
		entryGrid.setLayout(new GridLayout(2, 2));
		hostNameField = new JTextField();
		hostNameLabel = new JLabel("Hostname");
		portField = new JTextField();
		portLabel = new JLabel("Port");

		entryGrid.add(hostNameLabel);
		entryGrid.add(hostNameField);
		entryGrid.add(portLabel);
		entryGrid.add(portField);

		JPanel buttonGrid = new JPanel();
		buttonGrid.setLayout(new GridLayout(1, 2));
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		buttonGrid.add(okButton, 0, 0);
		buttonGrid.add(cancelButton, 0, 1);

		borderPanel.add(entryGrid, BorderLayout.CENTER);
		borderPanel.add(buttonGrid, BorderLayout.SOUTH);

		this.getContentPane().add(borderPanel);
		this.pack();

		this.setLocationByPlatform(true);

		this.setVisible(true);

		actionListener = new AddHostListener(control);
		okButton.addActionListener(actionListener);
		cancelButton.addActionListener(actionListener);

	}

	/**
	 * Gets the entered hostname.
	 * 
	 * @return the entered hostname
	 */
	public String getEnteredHostname() {
		return hostNameField.getText();
	}

	/**
	 * Gets the entered port.
	 * 
	 * @return the entered port
	 */
	public int getEnteredPort() {
		return Integer.parseInt(portField.getText());
	}

}
